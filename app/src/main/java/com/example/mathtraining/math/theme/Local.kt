package com.example.mathtraining.math.theme

import com.example.mathtraining.R



sealed class LocaleApp{
    object Russian: LocaleApp()
    object English: LocaleApp()
}

val enLocal = Localization(
    screenProfile = R.string.profile,
    screenLessons = R.string.lesson,
    screenStatistic = R.string.statistic,
    joined = R.string.joined,
    settings = R.string.setting,
    learned = R.string.learned,
    notification = R.string.notifications,
    darkMode = R.string.darkMode,
    selectLang = R.string.chooseLanguage,
    changeCourse = R.string.changeCourse,
    applicationSettings = R.string.applicationSettings,
    accountSettings = R.string.accountSettings,
    localeApp = LocaleApp.English,
    titleCreateAccount = R.string.titleCreateAccount,
    inputName = R.string.inputName,
    inputSurname= R.string.surname,
    inputCountry = R.string.country,
    register = R.string.register,
    countryUsa = R.string.inputCountry,
    countryRussia= R.string.countryRussia,
    toastLabel = R.string.toastLabel
)

val ruLocal = Localization(
    screenProfile = R.string.ruProfile,
    screenLessons = R.string.ruLesson,
    screenStatistic = R.string.ruStatistic,
    joined = R.string.ruJoined,
    settings = R.string.ruSetting,
    learned = R.string.ruLearned,
    notification = R.string.ruNotifications,
    darkMode = R.string.ruDarkMode,
    selectLang = R.string.ruChooseLanguage,
    changeCourse = R.string.ruChangeCourse,
    applicationSettings = R.string.ruApplicationSettings,
    accountSettings = R.string.ruAccountSettings,
    localeApp = LocaleApp.Russian,
    titleCreateAccount = R.string.ruRitleCreateAccount,
    inputName = R.string.ruInputName,
    inputSurname= R.string.ruSurname,
    inputCountry = R.string.tuCountry,
    register = R.string.ruRegister,
    countryUsa = R.string.ruInputCountry,
    countryRussia= R.string.ruCountryRussia,
    toastLabel = R.string.ruToasLabel
)