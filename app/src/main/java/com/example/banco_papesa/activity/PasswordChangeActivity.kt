package com.example.banco_papesa.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.banco_papesa.R
import com.example.banco_papesa.databinding.ActivityPasswordChangeBinding
import com.example.bancoapiprofe.bd.MiBancoOperacional
import com.example.bancoapiprofe.pojo.Cliente
import com.google.android.material.snackbar.Snackbar

class PasswordChangeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPasswordChangeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPasswordChangeBinding.inflate(layoutInflater)
        setContentView(binding.root)



        val dni = intent.getStringExtra("dni")
        val cliente = Cliente()
        cliente.setNif(dni)



        binding.btnCambiarContrasenya.setOnClickListener {

            val mbo: MiBancoOperacional? = MiBancoOperacional.getInstance(this)
            cliente.setClaveSeguridad(binding.inputtextViejaContrasenya.text.toString())
            val resultado = mbo?.login(cliente) ?: -1
            if (resultado == -1) {
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

                cliente.setClaveSeguridad(binding.inputtextRepetirContrasenya.text.toString())
                mbo?.changePassword(cliente)

                Toast.makeText(this, getString(R.string.contrasenya_cambiada_con_exito), Toast.LENGTH_LONG).show()
                finish()
            }


        }


        binding.btnSalir.setOnClickListener {
            finish()
        }
    }
}