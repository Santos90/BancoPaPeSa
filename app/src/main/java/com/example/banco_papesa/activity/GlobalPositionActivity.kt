package com.example.banco_papesa.activity

import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.banco_papesa.adapter.OnClickListener
import com.example.banco_papesa.databinding.ActivityGlobalPositionBinding
import com.example.banco_papesa.fragment.AccountsFragment
import com.example.banco_papesa.fragment.AccountsMovementsFragment
import com.example.banco_papesa.fragment.FilterMovementsFragment
import com.example.bancoapiprofe.pojo.Cliente
import com.example.bancoapiprofe.pojo.Cuenta
import com.example.bancoapiprofe.pojo.Movimiento

class GlobalPositionActivity : AppCompatActivity(), OnClickListener {

    private lateinit var binding: ActivityGlobalPositionBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityGlobalPositionBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //Recuperar el cliente
        val clienteLogeado = intent.getSerializableExtra("cliente") as Cliente
        //Creamos la instancia del fragment
        val frgAccounts: AccountsFragment = AccountsFragment.newInstance(clienteLogeado)

        supportFragmentManager
            .beginTransaction()
            .add(binding.fragmentContainer!!.id, frgAccounts, AccountsFragment::class.java.name)
            .commit()

        frgAccounts.setListener(this)
    }

    override fun onClick(obj: Any?) {
        if (obj is Cuenta) {
            Log.i("Tipo de objeto", "Es una Cuenta")
            //Creamos la instancia del fragment
            var frgMovementsFragment = AccountsMovementsFragment.newInstance(obj as Cuenta)

            val isLandscape = resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

            if (isLandscape) {

                Log.i("Landscape:", true.toString())
                supportFragmentManager
                    .beginTransaction()
                    .replace(
                        binding.fragmentMovement!!.id,
                        frgMovementsFragment,
                        FilterMovementsFragment::class.java.name
                    )

                    .commit()

            } else {

                val movimientosIntent = Intent(this, GlobalPositionDetailsActivity::class.java)
                movimientosIntent.putExtra("cuenta", obj as Cuenta)
                startActivity(movimientosIntent)

            }
        } else if (obj is Movimiento) Log.i("Tipo de objeto", "Es un Movimiento")


    }
}