package com.core.presentation.util

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp


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
