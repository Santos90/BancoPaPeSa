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

        var pass: String
        var dni: String
        var passGuardado = "1234"
        if (intent.getStringExtra("pass") != null) {
            passGuardado = intent.getStringExtra("pass").toString()
            Snackbar.make(binding.root, getString(R.string.contrasenya_cambiada_con_exito), Snackbar.LENGTH_SHORT).show()
        }




        binding.btnEntrar.setOnClickListener {
            dni = binding.dniInput.text.toString()
            pass = binding.passInput.text.toString()
            val dniValido = esDNIValido(dni)
            Log.e("password", "Contraseña:$pass.")
            if ( ! esDNIValido(dni)) {
                val mensajeError = getString(R.string.dni_no_valido)
                Snackbar.make(binding.root, mensajeError, Snackbar.LENGTH_SHORT).show()
                binding.dniInput.error = mensajeError
            }
            else binding.dniInput.error = null

            if (pass.isEmpty() ) {
                val mensajeError = getString(R.string.contrasenya_requerida)
                Snackbar.make(binding.root, mensajeError, Snackbar.LENGTH_SHORT).show()
                binding.passInput.error = mensajeError

            }
            else binding.passInput.error = null

            if (dniValido && pass == passGuardado){
                val mainIntent = Intent(this, MainActivity::class.java)
                mainIntent.putExtra("dni", dni)
                mainIntent.putExtra("pass", pass)
                startActivity(mainIntent)

            }
        }

        binding.btnSalir.setOnClickListener {
            finish()
        }
        val mensajeError = getString(R.string.dni_no_valido)
        binding.dniInput.onFocusChangeListener = View.OnFocusChangeListener { view, hasFocus ->
            if (!hasFocus) { // Se ejecuta cuando el TextInput pierde el foco
                val dni = binding.dniInput.text.toString()
                if (!esDNIValido(dni)) {
                    binding.dniInput.error = mensajeError
                }
            }
        }

        binding.passInput.onFocusChangeListener = View.OnFocusChangeListener { view, hasFocus ->
            if (!hasFocus) { // Se ejecuta cuando el TextInput pierde el foco
                val pass = binding.passInput.text.toString()
                if (pass.isEmpty()) {
                    binding.dniInput.error = mensajeError
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