package com.example.banco_papesa.activity


import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceFragmentCompat
import com.example.banco_papesa.R
import com.example.banco_papesa.Utils.LanguageUtils
import com.example.banco_papesa.Utils.MyMediaPlayer
import com.example.banco_papesa.databinding.SettingsActivityBinding


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

			// Carga las preferencias desde el archivo XML
			addPreferencesFromResource(R.xml.root_preferences)
			// Obtiene el objeto SharedPreferences
			val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity)

			// Registra un listener para detectar cambios en las preferencias
			sharedPreferences.registerOnSharedPreferenceChangeListener { sharedPreferences, key ->
				// Actúa según la preferencia que cambió
				if (key == "musica") {
					mediaPlayer.playPause()
				}
			}
		}

		override fun onDestroy() {
			super.onDestroy()
			val pref: SharedPreferences =
				PreferenceManager.getDefaultSharedPreferences(context)
			if (pref.getString("idioma", "SO_LANGUAGE") != "SO_LANGUAGE") {
				val idiomaSeleccionado = pref.getString("idioma", "ES")
				LanguageUtils.setAppLanguage(requireContext(), idiomaSeleccionado); // Cambia el idioma a español
			}
		}
	}
}