package com.example.banco_papesa

import android.R
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.banco_papesa.databinding.ActivityTransferBinding
import com.example.banco_papesa.databinding.ActivityWelcomeBinding
import org.w3c.dom.Text

class TransferActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTransferBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTransferBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var resultado = ""

        // Datos para el Spinner
        val datos = arrayOf("ES87 0182 6112 19 880647 9323", "ES84 0049 6752 25 769513 3798", "ES76 0128 5965 16 815893 9803", "ES42 2100 2427 02 895940 4429")

        // Crear un ArrayAdapter usando los datos y un diseño predeterminado
        val adapter = ArrayAdapter(this, R.layout.simple_spinner_item, datos)
        // Especificar el diseño del menú desplegable
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        val spCuentaOrigen = binding.spCuentaOrigen

        // Asignar el ArrayAdapter al Spinner
        spCuentaOrigen.adapter = adapter

        var seleccionOrigen = ""
        // Manejar la selección de elementos del Spinner
        spCuentaOrigen.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                seleccionOrigen = datos[position]

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Acciones a realizar si no se selecciona nada

            }
        })


        val spCuentaDestino = binding.spCuentaDestino


        // Asignar el ArrayAdapter al Spinner
        spCuentaDestino.adapter = adapter
        var seleccionDestino = ""
        // Manejar la selección de elementos del Spinner
        spCuentaDestino.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                seleccionDestino = datos[position]

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Acciones a realizar si no se selecciona nada

            }
        })

        binding.rgTipoCuenta.setOnClickListener {//pq no va esta forma?
            if (binding.rbCuentaAjena.isSelected) {
                binding.spCuentaDestino.visibility = View.GONE
                binding.tiCuentaDestino.visibility = View.VISIBLE
            } else if (binding.rbCuentaPropia.isSelected) {
                binding.spCuentaDestino.visibility = View.VISIBLE
                binding.tiCuentaDestino.visibility = View.GONE
            }
        }

        binding.rbCuentaAjena.setOnClickListener {
            binding.spCuentaDestino.visibility = View.GONE
            binding.tiCuentaDestino.visibility = View.VISIBLE
        }



        binding.rbCuentaPropia.setOnClickListener {
            binding.spCuentaDestino.visibility = View.VISIBLE
            binding.tiCuentaDestino.visibility = View.GONE
        }

        binding.btnEnviar.setOnClickListener {

            var resultado = "Cuenta Origen:\n" +
                    "$seleccionOrigen\n" +
                    "A cuenta "

            if (binding.rbCuentaPropia.isSelected){ //No va
                resultado += "propia:\n" +
                        "$seleccionDestino\n"
            } else {
                resultado += "ajena:\n" +
                        "${binding.tiCuentaDestino.text}\n"
            }

            resultado += "Importe: ${binding.tiCantidadTransferencia.text}\n" +
                    "Enciar justificante: ${binding.cbEnviarJustificante.isActivated}"


            Toast.makeText(this, resultado, Toast.LENGTH_LONG).show()
            Log.i("Resultado transacción:", resultado)
        }

        binding.btnCancelar.setOnClickListener {
            finish()

            with(binding){
                tiCantidadTransferencia.setText("")
                tiCuentaDestino.setText("")
                cbEnviarJustificante.isActivated = false
            }
        }

    }
}