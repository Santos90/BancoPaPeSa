package com.example.banco_papesa.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.banco_papesa.R
import com.example.banco_papesa.activity.MainActivity
import com.example.banco_papesa.adapter.CajeroAdapter
import com.example.banco_papesa.adapter.OnClickListener
import com.example.banco_papesa.database.BankApplication
import com.example.banco_papesa.databinding.FragmentCajerosBinding
import com.example.banco_papesa.pojo.CajeroEntity
import java.util.concurrent.LinkedBlockingDeque

class CajerosListFragment : Fragment(), OnClickListener {
	private lateinit var binding: FragmentCajerosBinding
	private lateinit var mAdapter: CajeroAdapter
	private lateinit var mGridLayout: GridLayoutManager

	private lateinit var mActivity: MainActivity


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
		setupRecyclerView()

		mActivity = activity as MainActivity
		mActivity?.supportActionBar?.title = "Cajeros"


		binding.addCajero.setOnClickListener {
			val frgFormCajero = CajeroFormFragment()
			launchFragment(frgFormCajero)
		}

	}

	private fun launchFragment(fragment : Fragment) {
		mActivity.supportFragmentManager.beginTransaction()
			.replace(R.id.fragment_container_big, fragment)
			.addToBackStack(null).commit()
	}

	private fun setupRecyclerView() {
		mAdapter = CajeroAdapter(mutableListOf(), this)
		mGridLayout = GridLayoutManager(context, 1)
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
		mAdapter.setCajero(cola.take())
	}


	override fun onClick(obj: Any?) {
		var args = Bundle()
		args.putSerializable("store", obj as CajeroEntity )


		val frgFormCajero = CajeroFormFragment()
		if (args != null) frgFormCajero.arguments = args

		mActivity.supportFragmentManager.beginTransaction()
			.add(R.id.fragment_container_big, frgFormCajero)
			.addToBackStack(null)
			.commit()
	}


}