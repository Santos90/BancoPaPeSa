package com.example.banco_papesa.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.banco_papesa.R
import com.example.banco_papesa.adapter.MovementsAdapter
import com.example.banco_papesa.adapter.OnClickListener
import com.example.banco_papesa.databinding.ActivityMovementsBinding
import com.example.banco_papesa.databinding.FragmentMovementsBinding
import com.example.banco_papesa.databinding.FragmentPasswordChangeBinding
import com.example.bancoapiprofe.bd.MiBancoOperacional
import com.example.bancoapiprofe.pojo.Cliente
import com.example.bancoapiprofe.pojo.Cuenta
import com.example.bancoapiprofe.pojo.Movimiento

private const val ARG_CLIENTE = "cliente"
class MovementsFragment : Fragment(), OnClickListener {

	private lateinit var cliente : Cliente

	private lateinit var binding: FragmentMovementsBinding

	private lateinit var movementsAdapter: MovementsAdapter
	private lateinit var linearLayoutManager: LinearLayoutManager

	private var mbo: MiBancoOperacional? = null

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		arguments?.let {
			cliente = it.getSerializable(ARG_CLIENTE) as Cliente
		}
	}

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		// Inflate the layout for this fragment
		binding = FragmentMovementsBinding	.inflate(layoutInflater)


		mbo = MiBancoOperacional.getInstance(context)

		Log.i("Cliente", cliente.toString())


		val listaCuentas = mbo?.getCuentas(cliente) as ArrayList<Cuenta>
		var listaMovimientos = ArrayList<Movimiento>()

		if (listaCuentas.isNotEmpty()) {
			listaMovimientos = mbo!!.getMovimientos(listaCuentas[0]) as ArrayList<Movimiento>
		}

		movementsAdapter = MovementsAdapter(listaMovimientos, this)
		linearLayoutManager = LinearLayoutManager(context)

		binding.recyclerId.apply {
			layoutManager = linearLayoutManager
			adapter = movementsAdapter
			//addItemDecoration(itemDecoration)
		}

		//CONSTRUCCION SPINERS
		var listaCuentasSpinner = ArrayList<String?>()

		if (listaCuentas.isNotEmpty()) {
			for (c in listaCuentas) {
				listaCuentasSpinner.add(c.toIban())
			}
		}
		// Crear un ArrayAdapter usando los datos y un diseño predeterminado
		val adapterCuentas = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, listaCuentasSpinner)
		// Especificar el diseño del menú desplegable
		adapterCuentas.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

		// Asignar el ArrayAdapter al Spinner
		binding.spCuenta.adapter = adapterCuentas

		val contexto = this

		// Manejar la selección de elementos del Spinner
		binding.spCuenta.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
			override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

				listaMovimientos = mbo!!.getMovimientos(listaCuentas[position]) as ArrayList<Movimiento>

				//Actualizamos el RecicledView con la nueva lista
				movementsAdapter = MovementsAdapter(listaMovimientos, contexto)
				binding.recyclerId.adapter = movementsAdapter

			}

			override fun onNothingSelected(parent: AdapterView<*>?) {
				// Acciones a realizar si no se selecciona nada

			}
		})


		return binding.root
	}

	companion object {
		fun newInstance(cliente: Cliente) =
			MovementsFragment().apply {
				arguments = Bundle().apply {
					putSerializable(ARG_CLIENTE, cliente)
				}
			}
	}

	override fun onClick(obj: Any?) {
		Toast.makeText(context, (obj as Movimiento).getDescripcion(), Toast.LENGTH_SHORT).show()

	}

}