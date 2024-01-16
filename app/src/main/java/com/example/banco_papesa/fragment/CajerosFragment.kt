package com.example.banco_papesa.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.banco_papesa.R
import com.example.banco_papesa.adapter.CajeroAdapter
import com.example.banco_papesa.adapter.OnClickListener
import com.example.banco_papesa.database.BankApplication
import com.example.banco_papesa.databinding.FragmentCajerosBinding
import com.example.banco_papesa.pojo.CajeroEntity
import java.util.concurrent.LinkedBlockingDeque

class CajerosFragment : Fragment(), OnClickListener {
	private lateinit var binding: FragmentCajerosBinding
	private lateinit var mAdapter: CajeroAdapter
	private lateinit var mGridLayout: GridLayoutManager


	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		// Inflate the layout for this fragment
		binding = FragmentCajerosBinding.inflate(layoutInflater)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		binding.addCajero.setOnClickListener {
			val cola = LinkedBlockingDeque<MutableList<CajeroEntity>>()
			Thread {
				val cajerosEntityLists : MutableList<CajeroEntity> = mutableListOf(
					CajeroEntity(1, "Carrer del Clariano, 1, 46021 Valencia, Valencia, España",
						39.47600769999999, -0.3524475000000393, ""),
					CajeroEntity(2, "Avinguda del Cardenal Benlloch, 65, 46021 València, Valencia, España",
						39.4710366, -0.3547525000000178, ""),
					CajeroEntity(3, "Av. del Port, 237, 46011 València, Valencia, España",
						39.46161999999999, -0.3376299999999901, ""),
					CajeroEntity(4, "Carrer del Batxiller, 6, 46010 València, Valencia, España",
						39.4826729, -0.3639118999999482, ""),
					CajeroEntity(5, "Av. del Regne de València, 2, 46005 València, Valencia, España",
						39.4647669, -0.3732760000000326, "")
				)
				cola.add(cajerosEntityLists)
			}.start()

			BankApplication.database.cajeroDAO().insertAll(cola.take())
		}
	}

	private fun setupRecyclerView() {
		mAdapter = CajeroAdapter(mutableListOf(), this)
		mGridLayout = GridLayoutManager(context, 2)
		getCajeros()
		binding.recycledView.apply {
			setHasFixedSize(true)
			layoutManager = mGridLayout
			adapter = mAdapter
		}
	}

	private fun getCajeros() {
		val cola = LinkedBlockingDeque<MutableList<CajeroEntity>>()
		Thread {
			val stores = BankApplication.database.cajeroDAO().getAllCajeros()
			cola.add(stores)

		}.start()
		mAdapter.setStores(cola.take())
	}

	override fun onClick(obj: Any?) {
		TODO("Not yet implemented")
	}


}