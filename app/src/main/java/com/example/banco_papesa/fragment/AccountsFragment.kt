package com.example.banco_papesa.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.banco_papesa.adapter.OnClickListener
import com.example.banco_papesa.adapter.AccountsAdapter
import com.example.banco_papesa.databinding.ActivityMainBinding
import com.example.banco_papesa.databinding.FragmentAccountsBinding
import com.example.bancoapiprofe.bd.MiBancoOperacional
import com.example.bancoapiprofe.pojo.Cliente


private const val ARG_CLIENTE = "cliente"

class AccountsFragment : Fragment(), OnClickListener {

    private lateinit var cliente : Cliente

    private lateinit var binding: FragmentAccountsBinding
    private lateinit var mainBinding: ActivityMainBinding

    private lateinit var accountsAdapter: AccountsAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager


    private lateinit var listener: OnClickListener

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
        binding = FragmentAccountsBinding.inflate(layoutInflater)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)



        val mbo: MiBancoOperacional? = MiBancoOperacional.getInstance(context)



        Log.i("Cliente", cliente.toString())


        val listaCuentas = mbo?.getCuentas(cliente) as ArrayList<Any>


        accountsAdapter = AccountsAdapter(listaCuentas, this)


        linearLayoutManager = LinearLayoutManager(context)



        binding.recyclerView.apply {
            layoutManager = linearLayoutManager
            adapter = accountsAdapter

        }

        return binding.root
    }

    companion object {
        fun newInstance(cliente: Cliente) =
            AccountsFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_CLIENTE, cliente)
                }
            }
    }

    fun setListener (listener: OnClickListener) {
        this.listener = listener
    }
    override fun onItemClick(obj: Any?) {
        listener.onItemClick(obj)
/*
        var frgMovementsFilter = FilterMovementsFragment.newInstance(obj as Cuenta)

        Log.i("Landscape:", true.toString())
        childFragmentManager
            .beginTransaction()
            .replace(
                mainBinding.fragmentContainerBig!!.id,
                frgMovementsFilter,
                FilterMovementsFragment::class.java.name
            )
            .addToBackStack(null)
            .commit()

 */



    }

    override fun onSelectedItem() {
        TODO("Not yet implemented")
    }
}