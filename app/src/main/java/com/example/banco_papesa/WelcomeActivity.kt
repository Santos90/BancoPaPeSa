package com.example.banco_papesa

import android.animation.Animator
import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.airbnb.lottie.Lottie
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieConfig
import com.example.banco_papesa.R.id.lottieAnimationView
import kotlinx.coroutines.android.awaitFrame

class WelcomeActivity : AppCompatActivity() {
    //private lateinit var lottieAnimationView: LottieAnimationView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val loginIntent = Intent(this, LoginActivity::class.java)

        /*
        lottieAnimationView = findViewById(R.id.lottieAnimationView)
        lottieAnimationView.playAnimation()
        lottieAnimationView.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(p0: Animator) {
                TODO("Not yet implemented")
            }

            override fun onAnimationEnd(animation: Animator) {
                // La animación ha terminado. Puedes iniciar la siguiente actividad aquí.


                startActivity(loginIntent)
                finish()
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