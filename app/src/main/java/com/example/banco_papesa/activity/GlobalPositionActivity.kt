package com.example.banco_papesa.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
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
            .add(binding.fragmentContainer.id, frgAccounts, AccountsFragment::class.java.name)
            .commit()

        frgAccounts.setListener(this)
    }

    override fun onClick(obj: Any?) {

        //Creamos la instancia del fragment
        var frgMovementsFragment : AccountsMovementsFragment = AccountsMovementsFragment.newInstance(obj as Cuenta)

        supportFragmentManager
            .beginTransaction()
            .replace(binding.fragmentContainer.id, frgMovementsFragment, AccountsMovementsFragment::class.java.name)
            .addToBackStack(null)
            .commit()

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