package com.example.banco_papesa.activity

import android.R
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.banco_papesa.databinding.ActivityMovementsBinding
import com.example.banco_papesa.adapter.MovementsAdapter
import com.example.banco_papesa.adapter.OnClickListener
import com.example.bancoapiprofe.bd.MiBancoOperacional
import com.example.bancoapiprofe.pojo.Cliente
import com.example.bancoapiprofe.pojo.Cuenta
import com.example.bancoapiprofe.pojo.Movimiento

class MovementsActivity : AppCompatActivity(), OnClickListener {

    private lateinit var binding: ActivityMovementsBinding
    private lateinit var movementsAdapter: MovementsAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager
    //private lateinit var itemDecoration: DividerItemDecoration


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovementsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mbo: MiBancoOperacional? = MiBancoOperacional.getInstance(this)
        val cliente = intent.getSerializableExtra("cliente") as Cliente
        Log.i("Cliente", cliente.toString())


        val listaCuentas = mbo?.getCuentas(cliente) as ArrayList<Cuenta>
        var listaMovimientos = ArrayList<Movimiento>()

        if (listaCuentas.isNotEmpty()) {
            listaMovimientos = mbo.getMovimientos(listaCuentas[0]) as ArrayList<Movimiento>
        }

        movementsAdapter = MovementsAdapter(listaMovimientos, this)
        linearLayoutManager = LinearLayoutManager(this)

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
        val adapterCuentas = ArrayAdapter(this, R.layout.simple_spinner_dropdown_item, listaCuentasSpinner)
        // Especificar el diseño del menú desplegable
        adapterCuentas.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        // Asignar el ArrayAdapter al Spinner
        binding.spCuenta.adapter = adapterCuentas

        val contexto = this

        // Manejar la selección de elementos del Spinner
        binding.spCuenta.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                listaMovimientos = mbo.getMovimientos(listaCuentas[position]) as ArrayList<Movimiento>

                //Actualizamos el RecicledView con la nueva lista
                movementsAdapter = MovementsAdapter(listaMovimientos, contexto)
                binding.recyclerId.adapter = movementsAdapter

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Acciones a realizar si no se selecciona nada

            }
        })


    }

    override fun onItemClick(obj: Any?) {
        Toast.makeText(this, (obj as Movimiento).getDescripcion(), Toast.LENGTH_SHORT).show()
    }

    override fun onSelectedItem() {
        TODO("Not yet implemented")
    }
}