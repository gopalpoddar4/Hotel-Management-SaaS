package com.nexifotech.hotelsaas.core.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf

// ─── Light Color Scheme ───────────────────────────────────────────────────────
private val LightColorScheme = lightColorScheme(

    // Brand
    primary = Primary,
    onPrimary =  White,

    primaryContainer = PrimaryLight,
    onPrimaryContainer = TextPrimaryLight,

    secondary = Secondary,
    onSecondary =  White,

    secondaryContainer = SecondaryLight,
    onSecondaryContainer = TextPrimaryLight,

    tertiary = Tertiary,
    onTertiary =  White,

    // Background
    background = BackgroundLight,
    onBackground = TextPrimaryLight,

    // Cards & Sheets
    surface = SurfaceLight,
    onSurface = TextPrimaryLight,

    surfaceVariant = SurfaceVariantLight,
    onSurfaceVariant = TextSecondaryLight,

    // Error
    error = Error,
    onError = White,
    errorContainer = ErrorContainer,
    onErrorContainer = Error,

    // Outline
    outline = BorderLight,

    // Inverse
    inverseSurface = SurfaceDark,
    inverseOnSurface = TextPrimaryDark,

    // Extra
    surfaceTint = Primary,
)

// ─── Dark Color Scheme ────────────────────────────────────────────────────────
private val DarkColorScheme = darkColorScheme(

    // Brand
    primary = PrimaryLight,
    onPrimary = Black,

    primaryContainer = PrimaryDark,
    onPrimaryContainer = White,

    secondary = SecondaryLight,
    onSecondary = Black,

    secondaryContainer = Secondary,
    onSecondaryContainer = White,

    tertiary = Tertiary,
    onTertiary =  Black,

    // Background
    background = BackgroundDark,
    onBackground = TextPrimaryDark,

    // Cards
    surface = SurfaceDark,
    onSurface = TextPrimaryDark,

    surfaceVariant = SurfaceVariantDark,
    onSurfaceVariant = TextSecondaryDark,

    // Error
    error = Error,
    onError = White,
    errorContainer = Error,
    onErrorContainer = White,

    // Outline
    outline = BorderDark,

    // Inverse
    inverseSurface = BackgroundLight,
    inverseOnSurface = TextPrimaryLight,

    surfaceTint = PrimaryLight,
)

// ─── Custom CompositionLocals ─────────────────────────────────────────────────

val LocalSpacing = staticCompositionLocalOf { Spacing }

@Composable
fun HotelSaasTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme: ColorScheme = when {
        // Dynamic color: Android 12+ only, other platforms ignore
        darkTheme    -> DarkColorScheme
        else         -> LightColorScheme
    }

    CompositionLocalProvider(
        LocalSpacing provides Spacing
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography  = HotelTypography,
            content     = content
        )
    }
}

