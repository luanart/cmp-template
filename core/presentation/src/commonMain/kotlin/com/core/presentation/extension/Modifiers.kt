package com.core.presentation.extension

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import com.core.presentation.util.ShimmerElement

@Stable
inline fun Modifier.applyIf(
    condition: Boolean,
    block: Modifier.() -> Modifier
): Modifier = if (condition) {
    then(block(Modifier))
} else {
    this
}

@Stable
fun Modifier.cardContainer(
    color: Color,
    shape: Shape = RoundedCornerShape(size = 12.dp)
) = fillMaxWidth()
    .clip(shape = shape)
    .background(color = color, shape = shape)

@Stable
fun Modifier.shimmer(
    show: Boolean,
    shape: Shape = RoundedCornerShape(size = 12.dp),
    color: Color = Color.LightGray,
    highlightColor: Color = Color.Gray,
    padding: PaddingValues = PaddingValues(1.dp),
    shimmerAnimationSpec: AnimationSpec<Float> = infiniteRepeatable(
        animation = tween(durationMillis = 1700, delayMillis = 200),
        repeatMode = RepeatMode.Restart
    ),
    fadeAnimationSpec: AnimationSpec<Float> = spring(),
): Modifier {
    return this then ShimmerElement(
        show = show,
        shape = shape,
        color = color,
        highlightColor = highlightColor,
        padding = padding,
        shimmerAnimationSpec = shimmerAnimationSpec,
        fadeAnimationSpec = fadeAnimationSpec
    )
}
