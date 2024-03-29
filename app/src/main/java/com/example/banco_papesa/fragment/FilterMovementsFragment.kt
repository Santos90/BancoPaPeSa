package com.example.banco_papesa.fragment

import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.banco_papesa.R
import com.example.banco_papesa.adapter.OnClickListener
import com.example.banco_papesa.adapter.MovementsAdapter
import com.example.banco_papesa.databinding.DialogMovementBinding
import com.example.banco_papesa.databinding.FragmentAccountsMovementsBinding
import com.example.banco_papesa.databinding.FragmentFilterMovementsBinding
import com.example.bancoapiprofe.bd.MiBancoOperacional
import com.example.bancoapiprofe.pojo.Cuenta
import com.example.bancoapiprofe.pojo.Movimiento
import com.google.android.material.dialog.MaterialAlertDialogBuilder


private const val ARG_CUENTA = "cuenta"

class FilterMovementsFragment : Fragment(), OnClickListener {

    private lateinit var cuenta: Cuenta

    private lateinit var binding: FragmentFilterMovementsBinding
    private lateinit var dialogBinding : DialogMovementBinding

    private lateinit var cuentaAdapter: MovementsAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager

    private var tipoFilto = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            cuenta = it.getSerializable(ARG_CUENTA) as Cuenta
        }
    }

    companion object {
        fun newInstance(cuenta: Cuenta) = FilterMovementsFragment().apply {
            arguments = Bundle().apply {
                putSerializable(ARG_CUENTA, cuenta)
            }
        }
    }


    fun setFilter(tipo : Int) {
        tipoFilto = tipo
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFilterMovementsBinding.inflate(layoutInflater)


        var fragment = AccountsMovementsFragment.newInstance(cuenta)
        fragment.setFilter(-1)


        childFragmentManager
            .beginTransaction()
            .add(binding.fragmentMovement.id, fragment, FilterMovementsFragment::class.java.name)
            .commit()
        binding.bottomNavigation?.visibility = View.INVISIBLE

        binding.bottomNavigation?.setOnNavigationItemSelectedListener {
            it.isChecked = true
            fragment = AccountsMovementsFragment.newInstance(cuenta)
            when (it.itemId) {
                R.id.filter_all -> {
                    fragment.setFilter(-1)
                }

                R.id.filter_1 -> {
                    fragment.setFilter(0)
                }

                R.id.filter_2 -> {
                    fragment.setFilter(1)
                }

                R.id.filter_3 -> {
                    fragment.setFilter(2)
                }
            }

            childFragmentManager
                .beginTransaction()
                .replace(binding.fragmentMovement.id, fragment, FilterMovementsFragment::class.java.name)
                .commitNow()
            true
        }

        return binding.root
    }


    override fun onItemClick(obj: Any?) {

        dialogBinding = DialogMovementBinding.inflate(layoutInflater)
        val movimiento = obj as Movimiento

        dialogBinding.tvId.text = "Id: ${movimiento.getId()}"
        dialogBinding.tvCuentaDestino.text = getString(
            R.string.cuenta_destino_dialogo,
            movimiento.getCuentaDestino()!!.toIban()
        )
        dialogBinding.tvCuentaOrigen.text = "Cuenta origen:\n${movimiento.getCuentaOrigen()!!.toIban()}"
        dialogBinding.tvDescripcion.text = "Descripción:\n${movimiento.getDescripcion()}\n"
        dialogBinding.tvTipo.text = "Tipo: ${movimiento.getTipo()}"
        dialogBinding.tvFecha.text = "Fecha: ${movimiento.getFechaOperacion().toString()}"
        dialogBinding.tvImporte.text = "Importe: ${movimiento.getImporte()}"

        //Con el setView le pasamos la vista
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Detalle Movimiento")
            .setView(dialogBinding.root)
            .setPositiveButton("Aceptar", DialogInterface.OnClickListener { dialog, i ->
                //Código a ejecutar en caso de Aceptar
                dialog.cancel()
            })
            .show()
    }

    override fun onSelectedItem() {
        TODO("Not yet implemented")
    }
}