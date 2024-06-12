package com.umang.reminderapp.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.umang.reminderapp.R

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)


val robotoMonoFontFamily = FontFamily(
    Font(R.font.roboto_mono_bold, FontWeight.Bold),
    Font(R.font.roboto_mono_regular, FontWeight.Normal),
    Font(R.font.roboto_mono_medium, FontWeight.Medium),
    Font(R.font.roboto_mono_semi_bold, FontWeight.SemiBold),
    Font(R.font.roboto_mono_light, FontWeight.Light),
    Font(R.font.roboto_mono_thin, FontWeight.Thin),
    Font(R.font.roboto_mono_italic, FontWeight.Normal, FontStyle.Italic),
    Font(R.font.roboto_mono_medium_italic, FontWeight.Medium, FontStyle.Italic),
    Font(R.font.roboto_mono_semi_bold_italic, FontWeight.SemiBold, FontStyle.Italic),
    Font(R.font.roboto_mono_light_italic, FontWeight.Light, FontStyle.Italic),
    Font(R.font.roboto_mono_thin_italic, FontWeight.Thin, FontStyle.Italic),
    Font(R.font.roboto_mono_bold_italic, FontWeight.Bold, FontStyle.Italic),
    Font(R.font.roboto_mono_extra_light, FontWeight.ExtraLight, FontStyle.Normal),
    Font(R.font.roboto_mono_extra_light_italic, FontWeight.ExtraLight, FontStyle.Italic),


)