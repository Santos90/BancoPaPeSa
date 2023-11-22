package com.example.banco_papesa.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.banco_papesa.R
import com.example.banco_papesa.adapter.OnClickListener
import com.example.banco_papesa.databinding.ActivityGlobalPositionBinding
import com.example.banco_papesa.fragment.AccountsFragment
import com.example.banco_papesa.fragment.AccountsMovementsFragment
import com.example.bancoapiprofe.pojo.Cliente
import com.example.bancoapiprofe.pojo.Cuenta

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

            //Creamos la instancia del fragment
        var frgMovementsFragment = AccountsMovementsFragment.newInstance(obj as Cuenta)

        var hay2fragments =  binding.fragmentMovement?.let {
                supportFragmentManager.findFragmentById(it.id)
            } != null


        if (true) {

            Log.i("Landscape:", true.toString())
            supportFragmentManager
                .beginTransaction()
                .replace(
                    binding.fragmentMovement!!.id,
                    frgMovementsFragment,
                    AccountsMovementsFragment::class.java.name
                )
                .commit()

        } else {
            supportFragmentManager
                .beginTransaction()
                .replace(
                    binding.fragmentContainer.id,
                    frgMovementsFragment,
                    AccountsMovementsFragment::class.java.name
                )
                .addToBackStack(null)
                .commit()
            Log.i("Landscape:", false.toString())
        }
        frgMovementsFragment.setListener(this)

        Toast.makeText(this, (obj as Cuenta).getNumeroCuenta(), Toast.LENGTH_SHORT).show()
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
        } else {
            super.onBackPressed()
        }
    }
}