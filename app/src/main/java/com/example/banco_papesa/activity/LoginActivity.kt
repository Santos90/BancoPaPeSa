package com.example.banco_papesa.activity

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import com.example.banco_papesa.R
import com.example.banco_papesa.Utils.MyMediaPlayer
import com.example.banco_papesa.databinding.ActivityLoginBinding
import com.example.bancoapiprofe.bd.MiBancoOperacional
import com.example.bancoapiprofe.pojo.Cliente
import com.google.android.material.snackbar.Snackbar


class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    private lateinit var mediaPlayer : MyMediaPlayer
    private var tiempoMusica: Int = 0

    private lateinit var pref: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        pref = PreferenceManager.getDefaultSharedPreferences(this@LoginActivity)

        var pass: String
        var dni: String

        binding.btnEntrar.setOnClickListener {
            dni = binding.dniInput.text.toString()
            pass = binding.passInput.text.toString()
            val dniValido = esDNIValido(dni)
            Log.e("password", "Contraseña:$pass.")

            val mbo: MiBancoOperacional? = MiBancoOperacional.getInstance(this)

            val cliente = Cliente()
            cliente.setNif(dni)
            cliente.setClaveSeguridad(pass)

            val clienteDevuelto = mbo?.login(cliente) ?: -1


            if (clienteDevuelto != -1){
                Log.i("Cliente", clienteDevuelto.toString())
                val mainIntent = Intent(this, MainActivity::class.java)
                mainIntent.putExtra("cliente", clienteDevuelto)

                startActivity(mainIntent)

            } else {
                val mensajeError = getString(R.string.dni_y_contrasenya_no_coinciden)
                Snackbar.make(binding.root, mensajeError, Snackbar.LENGTH_SHORT).show()
                binding.dniInput.error = mensajeError
                binding.passInput.error = mensajeError
            }

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
        /*
        val dniPattern = Regex("^(\\d{8})([A-HJ-NP-TV-Za-hj-np-tv-z])$")
        if (dniPattern.matches(dni)) {
            val numero = dni.substring(0, 8).toInt()
            val letras = "TRWAGMYFPDXBNJZSQVHLCKE"
            val letraControl = letras[numero % 23]
            Log.i("letra DNI", "${dni[8].uppercaseChar()}")
            return dni[8].uppercaseChar() == letraControl
        }
        return false
        */
        return true
    }

    override fun onStart() {
        super.onStart()
        Log.i("Musica", pref.getBoolean("musica", true).toString())
        if (pref.getBoolean("musica", true)) {
            mediaPlayer = MyMediaPlayer.getInstance(this, R.raw.bossa_nova)
        }

    }

    override fun onResume() {
        super.onResume()
        if (pref.getBoolean("musica", true)) {
            mediaPlayer.playMusic()
        }
    }

    override fun onPause() {
        super.onPause()
        if (pref.getBoolean("musica", true)) {
            mediaPlayer.pauseMusic()
        }
    }

    override fun onStop() {
        super.onStop()
        //mediaPlayer? = null
    }
}

