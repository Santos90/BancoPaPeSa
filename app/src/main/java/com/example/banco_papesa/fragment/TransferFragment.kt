package com.example.banco_papesa.fragment

import android.R
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.banco_papesa.databinding.ActivityTransferBinding
import com.example.banco_papesa.databinding.FragmentTransferBinding
import com.example.bancoapiprofe.bd.MiBancoOperacional
import com.example.bancoapiprofe.pojo.Cliente
import com.example.bancoapiprofe.pojo.Cuenta


private const val ARG_CLIENTE = "cliente"
class TransferFragment : Fragment() {

	private lateinit var cliente : Cliente

	private lateinit var binding: FragmentTransferBinding

	private var mbo: MiBancoOperacional? = null


	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		arguments?.let {
			cliente = it.getSerializable(ARG_CLIENTE) as Cliente
		}
	}

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
								savedInstanceState: Bundle?): View? 				{
		binding = FragmentTransferBinding.inflate(layoutInflater)

		mbo = MiBancoOperacional.getInstance(context)

		var cuentaOrigen = Cuenta()
		var cuentaDestino = Cuenta()
		var seleccionDivisas = ""
		//CONSTRUCCION SPINERS

		val listaSpinnerOrigen : List<String>
		var listaSpinnerDestino : List<String>

		val listaCuentasOrigen : ArrayList<Cuenta>
		var listaCuentasDestino : ArrayList<Cuenta>

		val adapterCuentasOrigen: ArrayAdapter<Any>
		var adapterCuentasDestino: ArrayAdapter<Any>

		if (mbo != null) {

			listaCuentasOrigen = mbo?.getCuentas(cliente) as ArrayList<Cuenta>
			// Datos para el Spinner
			listaSpinnerOrigen = listaCuentasToString(listaCuentasOrigen)

			// Crear un ArrayAdapter usando los datos y un diseño predeterminado
			adapterCuentasOrigen = ArrayAdapter(requireContext(), R.layout.simple_spinner_dropdown_item, listaSpinnerOrigen)
			// Especificar el diseño del menú desplegable
			adapterCuentasOrigen.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
			// Asignar el ArrayAdapter al Spinner
			binding.spCuentaOrigen.adapter = adapterCuentasOrigen

			var context: Context = requireContext()
			// Manejar la selección de elementos del Spinner
			binding.spCuentaOrigen.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
				override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
					cuentaOrigen = listaCuentasOrigen[position]

					//Creamos el nuevo spinner a partir de la seleccion del anterior spinner. Esto evita que origen y destino sean iguales

					listaCuentasDestino = listaCuentasOrigen.minusElement(cuentaOrigen) as ArrayList<Cuenta> //Eliminamos la cuenta origen
					listaSpinnerDestino = listaCuentasToString(listaCuentasOrigen.minusElement(cuentaOrigen))
					adapterCuentasDestino = ArrayAdapter(requireContext(), R.layout.simple_spinner_dropdown_item, listaSpinnerDestino)
					// Asignar el ArrayAdapter al Spinner
					binding.spCuentaDestino.adapter = adapterCuentasDestino
					// Manejar la selección de elementos del Spinner
					binding.spCuentaDestino.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
						override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
							cuentaDestino = listaCuentasDestino[position]
						}
						override fun onNothingSelected(parent: AdapterView<*>?) {
							// Acciones a realizar si no se selecciona nada
						}
					})
				}
				override fun onNothingSelected(parent: AdapterView<*>?) {
					// Acciones a realizar si no se selecciona nada
				}
			})


			//------------------ SPINNER DIVISAS -----------------
			val listaDivisas = arrayOf("€", "$", "£", "¥")

			// Crear un ArrayAdapter usando los datos y un diseño predeterminado
			val adapterDivisas = ArrayAdapter(context, R.layout.simple_spinner_dropdown_item, listaDivisas)
			val spDivisas = binding.spDivisa


			// Asignar el ArrayAdapter al Spinner
			spDivisas.adapter = adapterDivisas


			// Manejar la selección de elementos del Spinner
			spDivisas.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
				override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
					seleccionDivisas = listaDivisas[position]

				}

				override fun onNothingSelected(parent: AdapterView<*>?) {
					// Acciones a realizar si no se selecciona nada

				}
			})

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
		// BOTONES

		binding.btnEnviar.setOnClickListener {
			if (binding.tiCantidadTransferencia.text.isNullOrEmpty()) {
				Toast.makeText(context,
					getString(com.example.banco_papesa.R.string.error_icantidad_transferencia), Toast.LENGTH_LONG).show()

				if (binding.rbCuentaAjena.isChecked && binding.tiCuentaDestino.text.toString() == "") {
					Toast.makeText(context, getString(com.example.banco_papesa.R.string.error_cuenta_destino), Toast.LENGTH_LONG).show()
				}

			} else if (binding.rbCuentaAjena.isChecked && binding.tiCuentaDestino.text.toString() == "") {

				Toast.makeText(context, getString(com.example.banco_papesa.R.string.error_cuenta_destino), Toast.LENGTH_LONG).show()

			} else {

				var resultado = "\n${getString(com.example.banco_papesa.R.string.cuenta_origen)}\n" +
						"${cuentaOrigen.toIban()}\n\n" +
						getString(com.example.banco_papesa.R.string.cuenta_destino_conf)

				resultado += if (binding.rbCuentaPropia.isChecked) {
					getString(com.example.banco_papesa.R.string.propia_txt) + "\n${cuentaDestino.toIban()}\n\n"
				} else {
					getString(com.example.banco_papesa.R.string.ajena_txt) + "\n${binding.tiCuentaDestino.text}\n\n"
				}

				resultado += getString(com.example.banco_papesa.R.string.importe) + "${binding.tiCantidadTransferencia.text}$seleccionDivisas\n" +
						getString(com.example.banco_papesa.R.string.enviar_justificante_txt) + "${binding.cbEnviarJustificante.isChecked}\n"

				dialogoDeConfirmacion(getString(com.example.banco_papesa.R.string.confirmar_transaccion_titulo_dialogo), resultado)
			}


		}

		binding.btnCancelar.setOnClickListener {
			requireActivity().finish()

			with(binding){
				tiCantidadTransferencia.setText("")
				tiCuentaDestino.setText("")
				cbEnviarJustificante.isActivated = false
			}
		}
		return binding.root
	}

	fun dialogoDeConfirmacion(title:String, message: String) {

		val alertDialog: AlertDialog = this.let {
			val builder = context?.let { it1 -> AlertDialog.Builder(it1) }
			builder?.apply {
				setPositiveButton(
					R.string.ok,
					DialogInterface.OnClickListener { dialog, id ->
						//Insertar movimiento en base de datos
					})
				setNegativeButton(
					R.string.cancel,
					DialogInterface.OnClickListener { dialog, id ->
						// User cancelled the dialog
					})
			}

			builder!!.create()
		}
		alertDialog.setMessage(message)
		alertDialog.setTitle(title)
		alertDialog.show()
	}

	fun listaCuentasToString(lista : List<Cuenta>) : List<String>{
		var listaStrings = ArrayList<String>()
		for (cuenta in lista) {
			listaStrings.add(cuenta.toIban())
		}
		return  listaStrings
	}

	companion object {
		fun newInstance(cliente: Cliente) =
			TransferFragment().apply {
				arguments = Bundle().apply {
					putSerializable(ARG_CLIENTE, cliente)
				}
			}
	}

}




