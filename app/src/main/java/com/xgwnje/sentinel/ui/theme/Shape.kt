package com.xgwnje.sentinel.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

val AppShapes = Shapes(
    extraSmall = RoundedCornerShape(4.dp),
    small = RoundedCornerShape(8.dp),
    medium = RoundedCornerShape(12.dp), // Slightly more rounded for a modern feel
    large = RoundedCornerShape(16.dp),
    extraLarge = RoundedCornerShape(28.dp) // For things like FABs or bottom sheets
)