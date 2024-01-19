package com.example.banco_papesa.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.replace
import androidx.recyclerview.widget.GridLayoutManager
import com.example.banco_papesa.R
import com.example.banco_papesa.activity.MainActivity
import com.example.banco_papesa.adapter.CajeroAdapter
import com.example.banco_papesa.adapter.ListaCajerosAux
import com.example.banco_papesa.adapter.OnClickListener
import com.example.banco_papesa.database.BankApplication
import com.example.banco_papesa.databinding.FragmentCajerosBinding
import com.example.banco_papesa.pojo.CajeroEntity
import java.util.concurrent.LinkedBlockingDeque

class CajerosListFragment : Fragment(), OnClickListener, ListaCajerosAux {
	private lateinit var binding: FragmentCajerosBinding
	private lateinit var mAdapter: CajeroAdapter
	private lateinit var mGridLayout: GridLayoutManager
	private lateinit var listaCajeros:  MutableList<CajeroEntity>

	private lateinit var mActivity: MainActivity


	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		// Inflate the layout for this fragment
		binding = FragmentCajerosBinding.inflate(layoutInflater)
		setupRecyclerView()
		setHasOptionsMenu(true)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		mActivity = activity as MainActivity
		mActivity.supportActionBar?.title = getString(R.string.cajeros)

		binding.addCajero.setOnClickListener {
			val frgFormCajero = CajeroFormFragment()
			launchFragment(frgFormCajero)
		}
		super.onViewCreated(view, savedInstanceState)
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
		listaCajeros = cola.take()
		mAdapter.setCajero(listaCajeros)
	}

	override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
		inflater.inflate(R.menu.cajero_form_menu, menu)
		super.onCreateOptionsMenu(menu, inflater)
	}

	override fun onPrepareOptionsMenu(menu: Menu) {
		if (mAdapter.elementosSeleccionados.isEmpty()) {
			menu.findItem(R.id.action_delete).isVisible = false
		}
		menu.findItem(R.id.action_save).isVisible = false
		return super.onPrepareOptionsMenu(menu)
	}

	override fun onOptionsItemSelected(item: MenuItem): Boolean {
		var returnValue = false

		val iterator = mAdapter.elementosSeleccionados.iterator()
		when (item.itemId) {
			R.id.action_delete -> {
				val elementosSeleccionadosCopy = mAdapter.elementosSeleccionados.toSet()
				Thread {
					for ( cajero in elementosSeleccionadosCopy) {
						BankApplication.database.cajeroDAO().deleteCajero(cajero as CajeroEntity)
					}


				}.start()

				mActivity.runOnUiThread {
					mAdapter.deleteSelectedItems()
					mAdapter.elementosSeleccionados.clear()
					mActivity.invalidateOptionsMenu()
				}
			}
		}
		return  returnValue
	}


	override fun onItemClick(obj: Any?) {

		val frgFormCajero = CajeroFormFragment()
		var args = Bundle()
		args.putLong("idCajeroSeleccionado", (obj as CajeroEntity).id )
		if (args != null) frgFormCajero.arguments = args

		launchFragment(frgFormCajero)
	}

	override fun onSelectedItem() {

		mActivity.invalidateOptionsMenu()
	}

	private fun launchFragment(fragment : Fragment) {
		mActivity.supportFragmentManager.beginTransaction()
			.add(R.id.fragment_container_big, fragment)
			.addToBackStack(null).commit()
	}

	override fun setVisibilityFAB(isVisible: Boolean) {
		if (isVisible)
			binding.addCajero.show()
		else
			binding.addCajero.hide()
	}

	override fun onSaveInstanceState(outState: Bundle) {
		super.onSaveInstanceState(outState)
		outState.putAll(outState)
	}

	override fun addCajero(cajero: CajeroEntity) {
		mAdapter.add(cajero)
	}

	override fun updateCajero(cajero: CajeroEntity, editado: CajeroEntity) {

		mAdapter.update(cajero, editado)
	}

	override fun deleteStore(cajero: CajeroEntity) {
		mAdapter.delete(cajero)
	}


}