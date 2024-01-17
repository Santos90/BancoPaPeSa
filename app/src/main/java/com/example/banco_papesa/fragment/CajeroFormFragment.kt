package com.example.banco_papesa.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import com.example.banco_papesa.R
import com.example.banco_papesa.activity.MainActivity
import com.example.banco_papesa.database.BankApplication
import com.example.banco_papesa.databinding.FragmentCajeroFormBinding
import com.example.banco_papesa.pojo.CajeroEntity
import com.google.android.material.snackbar.Snackbar
import java.util.concurrent.LinkedBlockingDeque


class CajeroFormFragment : Fragment() {

    private lateinit var mActivity: MainActivity
    private var frgListCajeros: CajerosListFragment? = null
    private lateinit var binding: FragmentCajeroFormBinding

    private var editCajero : CajeroEntity? = null
    private var idCajeroSeleccionado = -1L
    @SuppressLint("RestrictedApi")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCajeroFormBinding.inflate(inflater)

        mActivity = activity as MainActivity
        frgListCajeros = mActivity.supportFragmentManager.findFragmentByTag("CajerosListFragment") as CajerosListFragment
        mActivity.supportActionBar!!.setDefaultDisplayHomeAsUpEnabled(true)
        setHasOptionsMenu(true)

        idCajeroSeleccionado = arguments?.getLong("idCajeroSeleccionado") ?: -1L

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        if (idCajeroSeleccionado != -1L) {
            val cola = LinkedBlockingDeque<CajeroEntity>()
            Thread {
                val obj = BankApplication.database.cajeroDAO().getById(idCajeroSeleccionado)
                cola.add(obj)
            }.start()

            editCajero = cola.take()
        }


        if (editCajero != null) {
            mActivity.supportActionBar?.title = "Editar Cajero"
            setUiStore()
        }
        else mActivity.supportActionBar?.title = "Añadir Cajero"

    }

    private fun setUiStore() {

        with(binding) {
            etDireccion.setText(editCajero!!.direccion)
            etLatitud.setText(editCajero!!.latitud.toString())
            etLongitud.setText(editCajero!!.longitud.toString())
            etZoom.setText(editCajero!!.zoom)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.cajero_form_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var returnValue = false
        when (item.itemId) {
            R.id.action_delete -> {

                if (editCajero != null) {
                    val cola = LinkedBlockingDeque<Unit>()
                    Thread {
                        val obj = BankApplication.database.cajeroDAO().deleteCajero(editCajero!!)
                        cola.add(obj)
                    }.start()
                    cola.take()
                }

                frgListCajeros?.deleteStore(editCajero!!)

                returnValue = true
            }

            R.id.action_save -> {
                val cajeroForm = CajeroEntity(0)
                cajeroForm.direccion = binding.etDireccion.text.toString().trim()
                cajeroForm.latitud = binding.etLatitud.text.toString().trim().toDouble()
                cajeroForm.longitud = binding.etLongitud.text.toString().trim().toDouble()
                cajeroForm.zoom = binding.etZoom.text.toString().trim()

                val cola = LinkedBlockingDeque<Long>()
                Thread {
                    if (editCajero == null) {
                        val id = BankApplication.database.cajeroDAO().addCajero(cajeroForm)
                        cajeroForm.id = id
                        cola.add(id)
                    }
                    else {
                        cajeroForm.id = editCajero!!.id
                        BankApplication.database.cajeroDAO().updateCajero(cajeroForm)
                        cola.add(0L)
                    }
                }.start()

                with (cola.take()) {
                    if (editCajero == null) {
                        Snackbar.make(binding.root,
                            "Tienda agregada correctamente", Snackbar.LENGTH_SHORT).show()
                        frgListCajeros?.addCajero(cajeroForm)
                    } else {
                        Snackbar.make(binding.root,
                            "Tienda modificada correctamente", Snackbar.LENGTH_SHORT).show()
                        frgListCajeros?.updateCajero(editCajero!! ,cajeroForm)
                    }

                }
                returnValue = true

            } else -> super.onOptionsItemSelected(item)
        }
        frgListCajeros?.setVisibilityFAB(true)
        mActivity.onBackPressedDispatcher.onBackPressed()
        return  returnValue

    }







}


