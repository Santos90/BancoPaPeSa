package com.example.banco_papesa

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.banco_papesa.databinding.ActivityMainBinding
import com.example.bancoapiprofe.pojo.Cliente

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val cliente = intent.getSerializableExtra("cliente") as Cliente

        binding.dniLabel.text = cliente.getNombre()


        binding.btnCambiarContrasenya?.setOnClickListener {
            val changePasswordIntent = Intent(this, PasswordChangeActivity::class.java)
            changePasswordIntent.putExtras(intent)
            startActivity(changePasswordIntent)
        }

        binding.btnTransferencias?.setOnClickListener {
            val transferenciaIntent = Intent(this, TransferActivity::class.java)
            transferenciaIntent.putExtras(intent)
            startActivity(transferenciaIntent)
        }

        binding.btnPosGlobal?.setOnClickListener {
            val posGlobalIntent = Intent(this, GlobalPositionActivity::class.java)
            startActivity(posGlobalIntent)
        }

        binding.btnMovimientos?.setOnClickListener {
            val movimientosIntent = Intent(this, MovementsActivity::class.java)
            movimientosIntent.putExtras(intent)
            startActivity(movimientosIntent)
        }

        binding.btnSalir.setOnClickListener {
            finish()
        }
    }
}