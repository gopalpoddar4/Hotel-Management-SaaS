package com.nexifotech.hotelsaas.core.ui.adaptive

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.dp

/**
 * Width-based breakpoints matching the Material 3 window size class specification.
 *
 * | Class    | Width range  | Typical device         |
 * |----------|--------------|------------------------|
 * | Compact  | < 600 dp     | Phone (portrait)       |
 * | Medium   | 600–839 dp   | Tablet (portrait)      |
 * | Expanded | ≥ 840 dp     | Tablet/Desktop/Web     |
 */
enum class WindowSizeClass { Compact, Medium, Expanded }

/**
 * Calculates and remembers the [WindowSizeClass] for the current window.
 * Uses [LocalWindowInfo] which is available across all Compose Multiplatform targets.
 */
@Composable
fun rememberWindowSizeClass(): WindowSizeClass {
    val density    = LocalDensity.current
    val windowInfo = LocalWindowInfo.current
    return remember(windowInfo.containerSize, density) {
        val widthDp = with(density) { windowInfo.containerSize.width.toDp() }
        when {
            widthDp < 600.dp -> WindowSizeClass.Compact
            widthDp < 840.dp -> WindowSizeClass.Medium
            else             -> WindowSizeClass.Expanded
        }
    }
}
