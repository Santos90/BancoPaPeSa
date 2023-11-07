package com.example.banco_papesa

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.banco_papesa.databinding.ActivityGlobalPositionBinding
import com.example.banco_papesa.pojo.CuentaAdapter
import com.example.bancoapiprofe.bd.MiBancoOperacional
import com.example.bancoapiprofe.pojo.Cliente
import com.example.bancoapiprofe.pojo.Cuenta

class GlobalPositionActivity : AppCompatActivity(), OnClickListener {

    private lateinit var binding: ActivityGlobalPositionBinding
    private lateinit var cuentaAdapter: CuentaAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var itemDecoration: DividerItemDecoration
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGlobalPositionBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val mbo: MiBancoOperacional? = MiBancoOperacional.getInstance(this)

        val cliente = intent.getSerializableExtra("cliente") as Cliente

        Log.i("Cliente", cliente.toString())


        var listaCuentas: ArrayList<Cuenta> = mbo?.getCuentas(cliente) as ArrayList<Cuenta>


        cuentaAdapter = CuentaAdapter(listaCuentas, this)


        linearLayoutManager = LinearLayoutManager(this)
        itemDecoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)


        binding.recyclerId.apply {
            layoutManager = linearLayoutManager
            adapter = cuentaAdapter
            addItemDecoration(itemDecoration)
        }


    }

    override fun onClick(obj: Any?) {
        Toast.makeText(this, (obj as Cuenta).getNumeroCuenta(), Toast.LENGTH_SHORT).show()
    }
}