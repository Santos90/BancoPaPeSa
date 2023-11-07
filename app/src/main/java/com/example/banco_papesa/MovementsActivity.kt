package com.example.banco_papesa

import android.R
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.banco_papesa.databinding.ActivityMovementsBinding
import com.example.banco_papesa.pojo.MovimientoAdapter
import com.example.bancoapiprofe.bd.MiBancoOperacional
import com.example.bancoapiprofe.pojo.Cliente
import com.example.bancoapiprofe.pojo.Cuenta
import com.example.bancoapiprofe.pojo.Movimiento

class MovementsActivity : AppCompatActivity(), OnClickListener {

    private lateinit var binding: ActivityMovementsBinding
    private lateinit var movimientoAdapter: MovimientoAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var itemDecoration: DividerItemDecoration


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

        movimientoAdapter = MovimientoAdapter(listaMovimientos as ArrayList<Any>, this)
        linearLayoutManager = LinearLayoutManager(this)
        itemDecoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        binding.recyclerId.apply {
            layoutManager = linearLayoutManager
            adapter = movimientoAdapter
            addItemDecoration(itemDecoration)
        }

        //CONSTRUCCION SPINERS
        var listaCuentasSpinner = ArrayList<String?>()

        if (listaCuentas.isNotEmpty()) {
            for (c in listaCuentas) {
                listaCuentasSpinner.add(c.getNumeroCuenta())
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

                movimientoAdapter = MovimientoAdapter(listaMovimientos as ArrayList<Any>, contexto)

                binding.recyclerId.adapter = movimientoAdapter

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Acciones a realizar si no se selecciona nada

            }
        })


    }

    override fun onClick(obj: Any?) {
        Toast.makeText(this, (obj as Movimiento).getDescripcion(), Toast.LENGTH_SHORT).show()
    }
}