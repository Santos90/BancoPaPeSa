package com.example.banco_papesa

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import com.example.banco_papesa.databinding.ActivityLoginBinding
import com.google.android.material.snackbar.Snackbar


class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.btnEntrar.setOnClickListener {
            val dni = binding.dniInput.text.toString()
            val pass = binding.passInput.text.toString()
            val dniValido = esDNIValido(dni)
            Log.e("password", "Contraseña:$pass.")
            if ( ! esDNIValido(dni)) {
                val mensajeError = "DNI no válido"
                Snackbar.make(binding.root, mensajeError, Snackbar.LENGTH_SHORT).show()
                binding.dniInput.error = "DNI no válido"
            }
            else binding.dniInput.error = null

            if (pass.isEmpty() ) {
                val mensajeError = "Contraseña requerida"
                Snackbar.make(binding.root, mensajeError, Snackbar.LENGTH_SHORT).show()
                binding.passInput.error = "Contraseña requerida"
                binding.passInput.setHintTextColor(67)
            }
            else binding.passInput.error = null

            if (dniValido && pass.isNotEmpty()){
                val mainIntent = Intent(this, MainActivity::class.java)
                mainIntent.putExtra("dni", dni)
                startActivity(mainIntent)
            }
        }

        binding.btnSalir.setOnClickListener {
            finish()
        }

        binding.dniInput.onFocusChangeListener = View.OnFocusChangeListener { view, hasFocus ->
            if (!hasFocus) { // Se ejecuta cuando el TextInput pierde el foco
                val dni = binding.dniInput.text.toString()
                if (!esDNIValido(dni)) {
                    binding.dniInput.error = "DNI no válido"
                }
            }
        }

        binding.passInput.onFocusChangeListener = View.OnFocusChangeListener { view, hasFocus ->
            if (!hasFocus) { // Se ejecuta cuando el TextInput pierde el foco
                val pass = binding.passInput.text.toString()
                if (pass.isEmpty()) {
                    binding.dniInput.error = "DNI no válido"
                }
            }
        }

        //Permite iniciar sesión desde el teclado
        binding.passInput.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                binding.btnEntrar.performClick()
                return@setOnEditorActionListener true
            }
            false
        }


    }

    fun esDNIValido(dni: String): Boolean {
        val dniPattern = Regex("^(\\d{8})([A-HJ-NP-TV-Za-hj-np-tv-z])$")
        if (dniPattern.matches(dni)) {
            val numero = dni.substring(0, 8).toInt()
            val letras = "TRWAGMYFPDXBNJZSQVHLCKE"
            val letraControl = letras[numero % 23]
            Log.i("letra DNI", "${dni[8].uppercaseChar()}")
            return dni[8].uppercaseChar() == letraControl
        }
        return false
    }
}