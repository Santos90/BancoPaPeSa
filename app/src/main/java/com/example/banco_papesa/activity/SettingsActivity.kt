package com.example.banco_papesa.activity


import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import androidx.preference.PreferenceFragmentCompat
import com.example.banco_papesa.R
import com.example.banco_papesa.Utils.LanguageUtils
import com.example.banco_papesa.Utils.MyMediaPlayer
import java.util.Locale


class SettingsActivity : AppCompatActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.settings_activity)
		if (savedInstanceState == null) {
			supportFragmentManager
				.beginTransaction()
				.replace(R.id.settings, SettingsFragment())
				.commit()
		}
		supportActionBar?.setDisplayHomeAsUpEnabled(true)
	}

	class SettingsFragment : PreferenceFragmentCompat() {
		private lateinit var mediaPlayer : MyMediaPlayer

		override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
			setPreferencesFromResource(R.xml.root_preferences, rootKey)

			mediaPlayer = MyMediaPlayer.getInstance(requireContext(), R.raw.bossa_nova)

			// Obtiene el objeto SharedPreferences
			val pref = PreferenceManager.getDefaultSharedPreferences(activity)
			var contexto = requireContext();
			var defaultLocale: Locale? = null
			// Registra un listener para detectar cambios en las preferencias
			pref.registerOnSharedPreferenceChangeListener { sharedPreferences, key ->
				// Actúa según la preferencia que cambió
				if (key == "musica") {
					mediaPlayer.playPause()
				}
				var idiomaSeleccionado : String?
				if (key == "idioma" && contexto != null) {

					idiomaSeleccionado = pref.getString("idioma", "SO_LANGUAGE")?: "null"
					if (idiomaSeleccionado == "SO_LANGUAGE") {

						if (defaultLocale == null) defaultLocale = Locale.getDefault()
						idiomaSeleccionado = defaultLocale!!.language ?: "null" //me trau el idioma actual de la app
						//idiomaSeleccionado = context?.resources?.configuration?.locales?.get(0)?.language ?: "null"
					}

					Log.i("Idioma", idiomaSeleccionado)
					LanguageUtils.setAppLanguage(contexto, idiomaSeleccionado); // Cambia el idioma a español

					setPreferencesFromResource(R.xml.root_preferences, rootKey)
				}
			}



		}

		override fun onDestroy() {
			super.onDestroy()


		}
	}
}