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
    primary             = Navy40,
    onPrimary           = Neutral99,
    primaryContainer    = Navy90,
    onPrimaryContainer  = Navy10,

    secondary           = Gold40,
    onSecondary         = Neutral99,
    secondaryContainer  = Gold90,
    onSecondaryContainer = Gold10,

    tertiary            = Teal40,
    onTertiary          = Neutral99,
    tertiaryContainer   = Teal90,
    onTertiaryContainer = Teal10,

    error               = Red40,
    onError             = Neutral99,
    errorContainer      = Red90,
    onErrorContainer    = Red10,

    background          = Neutral99,
    onBackground        = Neutral10,
    surface             = Neutral99,
    onSurface           = Neutral10,
    surfaceVariant      = Neutral95,
    onSurfaceVariant    = Neutral20,
    outline             = Neutral90
)

// ─── Dark Color Scheme ────────────────────────────────────────────────────────
private val DarkColorScheme = darkColorScheme(
    primary             = Navy80,
    onPrimary           = Navy20,
    primaryContainer    = Navy30,
    onPrimaryContainer  = Navy90,

    secondary           = Gold80,
    onSecondary         = Gold20,
    secondaryContainer  = Gold30,
    onSecondaryContainer = Gold90,

    tertiary            = Teal80,
    onTertiary          = Teal20,
    tertiaryContainer   = Teal30,
    onTertiaryContainer = Teal90,

    error               = Red80,
    onError             = Red10,
    errorContainer      = Red40,
    onErrorContainer    = Red90,

    background          = Neutral10,
    onBackground        = Neutral90,
    surface             = Neutral10,
    onSurface           = Neutral90,
    surfaceVariant      = Neutral20,
    onSurfaceVariant    = Neutral90,
    outline             = Neutral20
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

