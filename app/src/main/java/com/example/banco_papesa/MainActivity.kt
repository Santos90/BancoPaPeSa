package com.example.banco_papesa

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.banco_papesa.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dni = intent.getStringExtra("dni")

        binding.dniLabel.text = dni


        binding.btnCambiarContrasenya?.setOnClickListener {
            val changePasswordIntent = Intent(this, PasswordChangeActivity::class.java)
            changePasswordIntent.putExtras(intent)
            startActivity(changePasswordIntent)

        }

        binding.btnSalir.setOnClickListener {
            finish()
        }
    }
}