package com.example.banco_papesa

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.banco_papesa.databinding.ActivityPasswordChangeBinding
import com.google.android.material.snackbar.Snackbar

class PasswordChangeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPasswordChangeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPasswordChangeBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnSalir.setOnClickListener {
            finish()
        }

        binding.btnCambiarContrasenya.setOnClickListener {
            Log.i("Contraseña", intent.getStringExtra("pass").toString())
            if (binding.inputtextViejaContrasenya.text.toString() != intent.getStringExtra("pass").toString()) {
                val mensajeError = getString(R.string.contrasenya_incorrecta)
                Snackbar.make(binding.root, mensajeError, Snackbar.LENGTH_SHORT).show()
                binding.inputtextViejaContrasenya.error = mensajeError
            }
            else if( binding.inputtextNuevaContrasenya.text.toString() != binding.inputtextRepetirContrasenya.text.toString()) {
                val mensajeError = getString(R.string.contrasenyas_nuevas_no_coinciden)
                Snackbar.make(binding.root, mensajeError, Snackbar.LENGTH_SHORT).show()
                binding.inputtextViejaContrasenya.error = mensajeError
            }
            else {
                intent.putExtra("pass", binding.inputtextNuevaContrasenya.text.toString())

                Snackbar.make(binding.root, getString(R.string.contrasenya_cambiada_con_exito), Snackbar.LENGTH_SHORT).show()
                val mainIntent = Intent(this, LoginActivity::class.java)
                mainIntent.putExtras(intent)
                startActivity(mainIntent)
            }


        }
    }
}