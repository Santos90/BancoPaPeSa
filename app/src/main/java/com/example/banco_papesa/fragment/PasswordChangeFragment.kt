package com.example.banco_papesa.fragment

import android.R
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.banco_papesa.databinding.ActivityTransferBinding
import com.example.banco_papesa.databinding.FragmentPasswordChangeBinding
import com.example.bancoapiprofe.bd.MiBancoOperacional
import com.example.bancoapiprofe.pojo.Cliente
import com.example.bancoapiprofe.pojo.Cuenta


private const val ARG_CLIENTE = "cliente"
class PasswordChangeFragment : Fragment() {

	private lateinit var cliente : Cliente

	private lateinit var binding: FragmentPasswordChangeBinding

	private var mbo: MiBancoOperacional? = null


	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		arguments?.let {
			cliente = it.getSerializable(ARG_CLIENTE) as Cliente
		}
	}

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
							  savedInstanceState: Bundle?): View? 				{
		binding = FragmentPasswordChangeBinding	.inflate(layoutInflater)


		return binding.root
	}


	companion object {
		fun newInstance(cliente: Cliente) =
			PasswordChangeFragment().apply {
				arguments = Bundle().apply {
					putSerializable(ARG_CLIENTE, cliente)
				}
			}
	}

}




