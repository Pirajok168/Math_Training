package com.example.mathtraining.math.theme

import androidx.compose.ui.graphics.Color

val baseLightPalette = MathColor(
    selectedNavItem = Color(0xFFFF8489),
    navBarColor = listOf(Color(0xFFA7ACD9), Color(0xFF9E8FB2)),
    accentColor = Color(0xFF745B96),
    additionalColor = Color(0xFF9E8FB2),
    borderProfileColors = listOf(Color(0xFFA7ACD9), Color(0xFF9E8FB2)),

    backgroundSettingColor =  Color(0x6370B2D9),
    tintSettingsColor = Color(0xFF5899E2),

    backgroundLearnedColor = Color(0xBAFFCC66),
    tintLearnedColor = Color(0xFFFF9933),

    backgroundColor = listOf(Color(0x54E9E2CB), Color(0x48E6B6A8)),


    backgroundColorIconNotifications= Color(0x5B7080D7),
    tintColorIconNotifications=Color(0xFF4A60D7),

    backgroundColorIconDarkMode= Color(0xFFDEE8EC),
    tintColorIconDarkMode= Color(0xFF296EB9),

    backgroundColorIconChangeLange=Color(0x60FF8373),
    tintColorIconChangeLange = Color(0xFFFF1E00),

    backgroundColorIconChooseCourse=Color(0x70AD66D5),
    tintColorIconChooseCourse=Color(0xFF9F3ED5),

    tintColorEdit= Color.Black,
    colorEditName=Color.Black,
    headerColorSetting=Color(0xFF46365C),

    backgroundTobBarColor= Color(0x54E9E2CB),
    colorDivider =   Color.LightGray,

    backgroundCreateAccount = Color(0xFF79CCEE),
    backgroundInputSurface =  Color(0xFFFDE8E9),
    focusedInputColor = Color(0xFFF68F02),
    buttonColorRegistr= Color(0xFFF68F02),
)

val testLightPalette = baseLightPalette.copy(
    backgroundColor = listOf(Color(0xFFFFFFFF), Color(0xFFFFFFFF)),
    accentColor = Color.Black,
    additionalColor = Color.LightGray,

    borderProfileColors = listOf(Color(0xFFFDB515), Color(0xFFFDB515)),
    backgroundTobBarColor = Color.White,

    backgroundSettingColor =  Color(0xFFEFF9FF),
    tintSettingsColor = Color(0xFF5899E2),

    backgroundLearnedColor = Color(0xFFFFF1D4),
    tintLearnedColor = Color(0xFFFF9933),

    backgroundColorIconNotifications= Color(0xFFDFE4FF),
    tintColorIconNotifications=Color(0xFF4A60D7),

    backgroundColorIconDarkMode= Color(0xFFF1FBFF),
    tintColorIconDarkMode= Color(0xFF000000),

    backgroundColorIconChangeLange=Color(0xFFFFDFDB),
    tintColorIconChangeLange = Color(0xFFFF1E00),

    backgroundColorIconChooseCourse=Color(0xFFF2E7F8),
    tintColorIconChooseCourse=Color(0xFF9F3ED5),

    navBarColor=listOf(Color(0xFF79CCEE), Color(0xFF79CCEE)),
    selectedNavItem =Color(0xFFFDB515),

)

val baseDarkPalette = baseLightPalette.copy(
    backgroundColor = listOf(Color(0xFF191B24), Color(0xFF191B24)),
    accentColor =  Color(0xFFF0E1ED),
    additionalColor = Color(0x9AFBE8FF),

    backgroundLearnedColor = Color(0x60F3EBA7),
    tintLearnedColor =  Color(0xFFFFEB3B),

    tintColorEdit = Color(0xFFF0E1ED),

    colorEditName=Color(0xFFFFEB3B),

    backgroundColorIconNotifications= Color(0x60F3EBA7),
    tintColorIconNotifications=Color(0xFFFFEB3B),

    backgroundColorIconChooseCourse=Color(0xFFDECBE9),
    tintColorIconChooseCourse=Color(0xFF9F3ED5),

    backgroundColorIconChangeLange=Color(0xFFFFD6D0),
    tintColorIconChangeLange = Color(0xFFFF1E00),

    headerColorSetting=Color(0xFFF8F2F7),

    backgroundTobBarColor= Color(0xFF191B24),

    colorDivider = Color.Gray
)