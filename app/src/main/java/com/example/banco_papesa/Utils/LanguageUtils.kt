package com.example.banco_papesa.Utils

import android.content.Context
import android.content.res.Configuration
import android.os.Build
import android.os.LocaleList
import java.util.Locale


object LanguageUtils {
	fun setAppLanguage(context: Context, languageCode: String?) {
		val newLocale = Locale(languageCode)

		val resources = context.resources
		val configuration = resources.configuration
		val localeList = LocaleList(newLocale)

		Locale.setDefault(newLocale)
		configuration.locale = newLocale
		resources.updateConfiguration(configuration, resources.displayMetrics)
	}




}
