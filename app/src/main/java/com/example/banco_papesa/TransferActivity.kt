package com.example.banco_papesa

import android.R
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.banco_papesa.databinding.ActivityTransferBinding

class TransferActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTransferBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTransferBinding.inflate(layoutInflater)
        setContentView(binding.root)


    //CONSTRUCCION SPINERS


        // Datos para el Spinner
        val listaCuentasPropias = arrayOf("ES87 0182 6112 19 880647 9323", "ES84 0049 6752 25 769513 3798", "ES76 0128 5965 16 815893 9803", "ES42 2100 2427 02 895940 4429")

        // Crear un ArrayAdapter usando los datos y un diseño predeterminado
        val adapterCuentas = ArrayAdapter(this, R.layout.simple_spinner_dropdown_item, listaCuentasPropias)
        // Especificar el diseño del menú desplegable
        adapterCuentas.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        val spCuentaOrigen = binding.spCuentaOrigen


        // Asignar el ArrayAdapter al Spinner
        spCuentaOrigen.adapter = adapterCuentas

        var seleccionOrigen = ""
        // Manejar la selección de elementos del Spinner
        spCuentaOrigen.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                seleccionOrigen = listaCuentasPropias[position]

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Acciones a realizar si no se selecciona nada

            }
        })


        val spCuentaDestino = binding.spCuentaDestino


        // Asignar el ArrayAdapter al Spinner
        spCuentaDestino.adapter = adapterCuentas
        var seleccionDestino = ""
        // Manejar la selección de elementos del Spinner
        spCuentaDestino.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                seleccionDestino = listaCuentasPropias[position]

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Acciones a realizar si no se selecciona nada

            }
        })

        val listaDivisas = arrayOf("€", "$", "£", "¥")

        // Crear un ArrayAdapter usando los datos y un diseño predeterminado
        val adapterDivisas = ArrayAdapter(this, R.layout.simple_spinner_dropdown_item, listaDivisas)
        val spDivisas = binding.spDivisa


        // Asignar el ArrayAdapter al Spinner
        spDivisas.adapter = adapterDivisas

        var seleccionDivisas = ""
        // Manejar la selección de elementos del Spinner
        spDivisas.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                seleccionDivisas = listaDivisas[position]

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Acciones a realizar si no se selecciona nada

            }
        })


        binding.rgTipoCuenta.setOnClickListener {                       //pq no va esta forma?
            if (binding.rbCuentaAjena.isChecked) {
                binding.spCuentaDestino.visibility = View.GONE
                binding.tiCuentaDestino.visibility = View.VISIBLE
            } else if (binding.rbCuentaPropia.isSelected) {
                binding.spCuentaDestino.visibility = View.VISIBLE
                binding.tiCuentaDestino.visibility = View.GONE
            }
        }



    /// RADIO BUTTONS.

        binding.rbCuentaAjena.setOnClickListener {
            binding.spCuentaDestino.visibility = View.INVISIBLE
            binding.tiCuentaDestino.visibility = View.VISIBLE
        }

        binding.rbCuentaPropia.setOnClickListener {
            binding.spCuentaDestino.visibility = View.VISIBLE
            binding.tiCuentaDestino.visibility = View.INVISIBLE
        }

        binding.btnEnviar.setOnClickListener {
            if (binding.tiCantidadTransferencia.text.isNullOrEmpty()) {
                Toast.makeText(this,
                    getString(com.example.banco_papesa.R.string.error_icantidad_transferencia), Toast.LENGTH_LONG).show()

                if (binding.rbCuentaAjena.isChecked && binding.tiCuentaDestino.text.toString() == "") {
                    Toast.makeText(this, getString(com.example.banco_papesa.R.string.error_cuenta_destino), Toast.LENGTH_LONG).show()
                }

            } else if (binding.rbCuentaAjena.isChecked && binding.tiCuentaDestino.text.toString() == "") {
                
                Toast.makeText(this, getString(com.example.banco_papesa.R.string.error_cuenta_destino), Toast.LENGTH_LONG).show()

            } else {

                var resultado = "\n${getString(com.example.banco_papesa.R.string.cuenta_origen)}\n" +
                    "$seleccionOrigen\n\n" +
                        getString(com.example.banco_papesa.R.string.cuenta_destino_conf)

                resultado += if (binding.rbCuentaPropia.isChecked) {
                    getString(com.example.banco_papesa.R.string.propia_txt) + "\n$seleccionDestino\n\n"
                } else {
                    getString(com.example.banco_papesa.R.string.ajena_txt) + "\n${binding.tiCuentaDestino.text}\n\n"
                }

                resultado += getString(com.example.banco_papesa.R.string.importe) + "${binding.tiCantidadTransferencia.text}$seleccionDivisas\n" +
                        getString(com.example.banco_papesa.R.string.enviar_justificante_txt) + "${binding.cbEnviarJustificante.isChecked}\n"


                //Toast.makeText(this, resultado, Toast.LENGTH_SHORT).show()
                //Log.i("Resultado transacción:", resultado)

                dialogoDeConfirmacion(resultado,
                    getString(com.example.banco_papesa.R.string.confirmar_transaccion_titulo_dialogo))
            }


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

    fun dialogoDeConfirmacion(message: String, title:String) {
        // 1. Instantiate an <code><a href="/reference/android/app/AlertDialog.Builder.html">AlertDialog.Builder</a></code> with its constructor
        val builder: AlertDialog.Builder? = this?.let {
            AlertDialog.Builder(it)
        }

// 2. Chain together various setter methods to set the dialog characteristics


// 3. Get the <code><a href="/reference/android/app/AlertDialog.html">AlertDialog</a></code> from <code><a href="/reference/android/app/AlertDialog.Builder.html#create()">create()</a></code>
        val dialog: AlertDialog? = builder?.create()


        val alertDialog: AlertDialog? = this?.let {
            val builder = AlertDialog.Builder(it)
            builder.apply {
                setPositiveButton(R.string.ok,
                    DialogInterface.OnClickListener { dialog, id ->

                    })
                setNegativeButton(R.string.cancel,
                    DialogInterface.OnClickListener { dialog, id ->
                        // User cancelled the dialog
                    })
            }
            // Set other dialog properties


            // Create the AlertDialog
            builder.create()
        }
        alertDialog?.setMessage(message)
        alertDialog?.setTitle(title)
        alertDialog?.show()
    }
}