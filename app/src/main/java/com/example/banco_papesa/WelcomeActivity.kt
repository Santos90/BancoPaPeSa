package com.example.banco_papesa

import android.animation.Animator
import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.airbnb.lottie.Lottie
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieConfig
import com.example.banco_papesa.R.id.lottieAnimationView
import com.example.banco_papesa.databinding.ActivityPasswordChangeBinding
import com.example.banco_papesa.databinding.ActivityWelcomeBinding
import kotlinx.coroutines.android.awaitFrame
import kotlin.concurrent.timer

class WelcomeActivity : AppCompatActivity() {
    //private lateinit var lottieAnimationView: LottieAnimationView
    private lateinit var binding: ActivityWelcomeBinding
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val loginIntent = Intent(this, LoginActivity::class.java)


        with(binding.lottieAnimationView) {
            setMinAndMaxFrame(0, 200)
            animate().setDuration(1000).setStartDelay(100)
        }

        Handler().postDelayed({
            // Realiza acciones después de un retraso
            startActivity(loginIntent)
            finish()
        }, 5000)


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