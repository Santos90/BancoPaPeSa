package com.example.banco_papesa.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.preference.PreferenceManager
import android.util.Log
import com.example.banco_papesa.Utils.LanguageUtils
import com.example.banco_papesa.databinding.ActivityWelcomeBinding

class WelcomeActivity : AppCompatActivity() {
    //private lateinit var lottieAnimationView: LottieAnimationView
    private lateinit var binding: ActivityWelcomeBinding
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val loginIntent = Intent(this, LoginActivity::class.java)
        val pref: SharedPreferences =
            PreferenceManager.getDefaultSharedPreferences(this@WelcomeActivity)

        if (pref.getString("idioma", "SO_LANGUAGE") != "SO_LANGUAGE") {
            val idiomaSeleccionado = pref.getString("idioma", "ES")
            Log.i("Idioma: ", idiomaSeleccionado!!)
            LanguageUtils.setAppLanguage(this, idiomaSeleccionado); // Cambia el idioma a español
        }
        if (pref.getBoolean("animacion", true)) {
            binding = ActivityWelcomeBinding.inflate(layoutInflater)
            setContentView(binding.root)

            with(binding.lottieAnimationView) {
                setMinAndMaxFrame(0, 200)
                animate().setDuration(1000).setStartDelay(100)
            }

            Handler().postDelayed({
                // Realiza acciones después de un retraso
                startActivity(loginIntent)
                finish()
            }, 5000)

        }
        else { //Skip animation
            startActivity(loginIntent)
            finish()
        }




/*
        binding.lottieAnimationView.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(p0: Animator) {
                TODO("Not yet implemented")
            }

            override fun onAnimationEnd(animation: Animator) {
                // La animación ha terminado. Puedes iniciar la siguiente actividad aquí.


            }

            override fun onAnimationCancel(p0: Animator) {
                TODO("Not yet implemented")
            }

            override fun onAnimationRepeat(p0: Animator) {
                TODO("Not yet implemented")
            }

        })
*/


    }
}