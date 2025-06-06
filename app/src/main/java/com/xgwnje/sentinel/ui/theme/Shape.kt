package com.xgwnje.sentinel.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.graphics.RectangleShape // Can be used directly if preferred over RoundedCornerShape(0.dp)
import androidx.compose.ui.unit.dp

// For a global sharp-cornered theme, all shapes are effectively rectangles.
// Using RoundedCornerShape(0.dp) is equivalent to RectangleShape for most drawing purposes
// and is a common way to define "no rounding" within the Shapes system.
val AppShapes = Shapes(
    extraSmall = RoundedCornerShape(0.dp),
    small = RoundedCornerShape(0.dp),
    medium = RoundedCornerShape(0.dp),
    large = RoundedCornerShape(0.dp),
    extraLarge = RoundedCornerShape(0.dp)
)