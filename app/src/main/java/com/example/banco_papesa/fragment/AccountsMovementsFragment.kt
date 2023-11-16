package com.example.banco_papesa.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.banco_papesa.adapter.OnClickListener
import com.example.banco_papesa.adapter.CuentaAdapter
import com.example.banco_papesa.adapter.MovimientoAdapter
import com.example.banco_papesa.databinding.FragmentAccountsBinding
import com.example.banco_papesa.databinding.FragmentAccountsMovementsBinding
import com.example.bancoapiprofe.bd.MiBancoOperacional
import com.example.bancoapiprofe.pojo.Cuenta
import com.example.bancoapiprofe.pojo.Movimiento


private const val ARG_CUENTA = "cuenta"

class AccountsMovementsFragment : Fragment(), OnClickListener {

    private lateinit var cuenta: Cuenta

    private lateinit var binding: FragmentAccountsMovementsBinding

    private lateinit var cuentaAdapter: MovimientoAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var itemDecoration: DividerItemDecoration

    private lateinit var listener: OnClickListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            cuenta = it.getSerializable(ARG_CUENTA) as Cuenta
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAccountsMovementsBinding.inflate(layoutInflater)



        val mbo: MiBancoOperacional? = MiBancoOperacional.getInstance(context)




        val listaMovimientos = mbo?.getMovimientos(cuenta) as ArrayList<Movimiento>


        cuentaAdapter = MovimientoAdapter(listaMovimientos, this)


        linearLayoutManager = LinearLayoutManager(context)
        itemDecoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)


        binding.recyclerView.apply {
            layoutManager = linearLayoutManager
            adapter = cuentaAdapter
            addItemDecoration(itemDecoration)
        }

        return binding.root
    }

    companion object {
        fun newInstance(cuenta: Cuenta) =
            AccountsMovementsFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_CUENTA, cuenta)
                }
            }
    }

    fun setListener (listener: OnClickListener) {
        this.listener = listener
    }
    override fun onClick(obj: Any?) {
        if (listener != null) listener.onClick(obj)
    }
}