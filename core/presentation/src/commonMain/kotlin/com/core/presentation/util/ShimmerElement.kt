package com.core.presentation.util

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.toRect
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawOutline
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.node.DrawModifierNode
import androidx.compose.ui.node.ModifierNodeElement
import androidx.compose.ui.node.invalidateDraw
import androidx.compose.ui.platform.InspectorInfo
import androidx.compose.ui.unit.LayoutDirection
import kotlinx.coroutines.launch
import kotlin.math.max

internal data class ShimmerElement(
    val show: Boolean,
    val shape: Shape,
    val color: Color,
    val highlightColor: Color,
    val padding: PaddingValues,
    val shimmerAnimationSpec: AnimationSpec<Float>,
    val fadeAnimationSpec: AnimationSpec<Float>,
) : ModifierNodeElement<ShimmerNode>() {
    override fun create(): ShimmerNode {
        return ShimmerNode(
            show = show,
            shape = shape,
            color = color,
            highlightColor = highlightColor,
            padding = padding,
            shimmerAnimationSpec = shimmerAnimationSpec,
            fadeAnimationSpec = fadeAnimationSpec
        )
    }

    override fun update(node: ShimmerNode) {
        node.update(
            show = show,
            shape = shape,
            color = color,
            highlightColor = highlightColor,
            padding = padding,
            shimmerAnimationSpec = shimmerAnimationSpec,
            fadeAnimationSpec = fadeAnimationSpec
        )
    }

    override fun InspectorInfo.inspectableProperties() {
        name = "shimmer"
        properties["show"] = show
        properties["shape"] = shape
        properties["color"] = color
        properties["padding"] = padding
    }
}

// 3. The Node (Where the logic lives - No 'composed' overhead)
internal class ShimmerNode(
    var show: Boolean,
    var shape: Shape,
    var color: Color,
    var highlightColor: Color,
    var padding: PaddingValues,
    var shimmerAnimationSpec: AnimationSpec<Float>,
    var fadeAnimationSpec: AnimationSpec<Float>,
) : Modifier.Node(), DrawModifierNode {

    // Animation States
    private val placeholderAlpha = Animatable(if (show) 1f else 0f)
    private val shimmerProgress = Animatable(0f)

    // Drawing Cache
    private val paint = Paint()
    private var lastSize: Size? = null
    private var lastLayoutDirection: LayoutDirection? = null
    private var lastOutline: Outline? = null

    override fun onAttach() {
        super.onAttach()
        // Start Shimmer Loop
        coroutineScope.launch {
            // Only shimmer if visible
            if (show) {
                startShimmerLoop()
            }
        }
    }

    fun update(
        show: Boolean,
        shape: Shape,
        color: Color,
        highlightColor: Color,
        padding: PaddingValues,
        shimmerAnimationSpec: AnimationSpec<Float>,
        fadeAnimationSpec: AnimationSpec<Float>
    ) {
        val showChanged = this.show != show
        this.shape = shape
        this.color = color
        this.highlightColor = highlightColor
        this.padding = padding
        this.shimmerAnimationSpec = shimmerAnimationSpec
        this.fadeAnimationSpec = fadeAnimationSpec
        this.show = show

        if (showChanged) {
            coroutineScope.launch {
                if (show) {
                    // Fade In Placeholder
                    launch { startShimmerLoop() }
                    placeholderAlpha.animateTo(1f, fadeAnimationSpec)
                } else {
                    // Fade Out Placeholder
                    placeholderAlpha.animateTo(0f, fadeAnimationSpec)
                }
            }
        }
        invalidateDraw()
    }

    private suspend fun startShimmerLoop() {
        shimmerProgress.snapTo(0f)
        shimmerProgress.animateTo(
            targetValue = 1f,
            animationSpec = shimmerAnimationSpec
        )
    }

    override fun ContentDrawScope.draw() {
        // 1. Draw Content (with fade out if needed)
        val pAlpha = placeholderAlpha.value
        val cAlpha = 1f - pAlpha

        if (cAlpha > 0.01f) {
            if (cAlpha < 0.99f) {
                paint.alpha = cAlpha
                drawIntoCanvas { canvas ->
                    canvas.saveLayer(size.toRect(), paint)
                    drawContent()
                    canvas.restore()
                }
            } else {
                drawContent()
            }
        }

        // 2. Draw Shimmer Placeholder
        if (pAlpha > 0.01f) {
            // Resolve Padding
            val topPx = padding.calculateTopPadding().toPx()
            val bottomPx = padding.calculateBottomPadding().toPx()
            val startPx = padding.calculateStartPadding(layoutDirection).toPx()
            val endPx = padding.calculateEndPadding(layoutDirection).toPx()

            // Calculate Padded Size
            val paddedSize = Size(
                width = (size.width - startPx - endPx).coerceAtLeast(0f),
                height = (size.height - topPx - bottomPx).coerceAtLeast(0f)
            )

            // Prepare Paint for Layer
            paint.alpha = pAlpha

            drawIntoCanvas { canvas ->
                // Only use saveLayer if we are fading
                if (pAlpha < 0.99f) {
                    canvas.saveLayer(size.toRect(), paint)
                }

                // Translate context for Padding
                translate(left = startPx, top = topPx) {
                    drawPlaceholderInternal(paddedSize, shimmerProgress.value)
                }

                if (pAlpha < 0.99f) {
                    canvas.restore()
                }
            }
        }
    }

    private fun DrawScope.drawPlaceholderInternal(drawSize: Size, progress: Float) {
        // Optimizing Cache Checks
        val currentLayoutDirection = layoutDirection
        if (lastSize != drawSize || lastLayoutDirection != currentLayoutDirection || lastOutline == null) {
            lastOutline = if (shape === RectangleShape) {
                null
            } else {
                shape.createOutline(drawSize, currentLayoutDirection, this)
            }
            lastSize = drawSize
            lastLayoutDirection = currentLayoutDirection
        }

        // Prepare Brush
        // We create the brush here. In a generic Node, this is hard to cache without extra logic,
        // but it's much lighter than the `composed` overhead.
        val brush = Brush.radialGradient(
            colors = listOf(
                highlightColor.copy(alpha = 0f),
                highlightColor,
                highlightColor.copy(alpha = 0f),
            ),
            center = Offset(0f, 0f), // Radial starts at 0,0 usually
            radius = (max(drawSize.width, drawSize.height) * progress * 2).coerceAtLeast(0.01f),
        )

        // Draw Logic
        if (lastOutline != null) {
            drawOutline(outline = lastOutline!!, color = color)
            drawOutline(
                outline = lastOutline!!,
                brush = brush,
                alpha = shimmerAlpha(progress)
            )
        } else {
            drawRect(color = color, size = drawSize)
            drawRect(
                brush = brush,
                alpha = shimmerAlpha(progress),
                size = drawSize
            )
        }
    }

    // Helper to calculate Alpha based on progress (Logic ported from your ShimmerHighlight)
    private fun shimmerAlpha(progress: Float): Float {
        val progressForMaxAlpha = 0.6f
        return if (progress <= progressForMaxAlpha) {
            (progress / progressForMaxAlpha).coerceIn(0f, 1f)
        } else {
            ((1f - progress) / (1f - progressForMaxAlpha)).coerceIn(0f, 1f)
        }
    }
}