package com.example.banco_papesa.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.banco_papesa.activity.GlobalPositionActivity
import com.example.banco_papesa.activity.MovementsActivity
import com.example.banco_papesa.activity.PasswordChangeActivity
import com.example.banco_papesa.activity.TransferActivity
import com.example.banco_papesa.adapter.OnClickListener
import com.example.banco_papesa.adapter.AccountsAdapter
import com.example.banco_papesa.databinding.FragmentAccountsBinding
import com.example.banco_papesa.databinding.FragmentMainBinding
import com.example.bancoapiprofe.bd.MiBancoOperacional
import com.example.bancoapiprofe.pojo.Cliente


private const val ARG_CLIENTE = "cliente"

class MainFragment : Fragment() {

	private lateinit var cliente : Cliente
	private lateinit var binding: FragmentMainBinding

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
		binding = FragmentMainBinding.inflate(layoutInflater)

		Log.i("Cliente", cliente.toString())


		binding.dniLabel.text = cliente.getNombre()


		binding.btnCambiarContrasenya?.setOnClickListener {
			val changePasswordIntent = Intent(context, PasswordChangeActivity::class.java)
			changePasswordIntent.putExtra("cliente", cliente)
			startActivity(changePasswordIntent)
		}

		binding.btnTransferencias?.setOnClickListener {
			val transferenciaIntent = Intent(context, TransferActivity::class.java)
			transferenciaIntent.putExtra("cliente", cliente)
			startActivity(transferenciaIntent)
		}

		binding.btnPosGlobal?.setOnClickListener {
			val posGlobalIntent = Intent(context, GlobalPositionActivity::class.java)
			posGlobalIntent.putExtra("cliente", cliente)
			startActivity(posGlobalIntent)
		}

		binding.btnMovimientos?.setOnClickListener {
			val movimientosIntent = Intent(context, MovementsActivity::class.java)
			movimientosIntent.putExtra("cliente", cliente)
			startActivity(movimientosIntent)
		}

		binding.btnSalir.setOnClickListener {
			requireActivity().finish()
		}


		return binding.root
	}



	companion object {
		fun newInstance(cliente: Cliente) =
			MainFragment().apply {
				arguments = Bundle().apply {
					putSerializable(ARG_CLIENTE, cliente)
				}
			}
	}

}