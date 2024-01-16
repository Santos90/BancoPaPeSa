package com.example.banco_papesa.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.banco_papesa.activity.MainActivity
import com.example.banco_papesa.databinding.FragmentCajeroFormBinding
import com.example.banco_papesa.databinding.FragmentCajerosBinding
import com.example.banco_papesa.pojo.CajeroEntity


class CajeroFormFragment : Fragment() {

    private lateinit var mActivity: MainActivity
    private lateinit var binding: FragmentCajeroFormBinding

    var editCajero : CajeroEntity? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentCajeroFormBinding.inflate(inflater)

        return binding.root
    }

    @SuppressLint("RestrictedApi")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mActivity = activity as MainActivity
        mActivity?.supportActionBar?.setDefaultDisplayHomeAsUpEnabled(true)


        editCajero = arguments?.getSerializable("store") as CajeroEntity ?: null

        if (editCajero != null) {
            mActivity?.supportActionBar?.title = "Editar Cajero"
            setUiStore()
        }
        else mActivity?.supportActionBar?.title = "Añadir Cajero"



    }

    private fun setUiStore() {

        with(binding) {
            etDireccion.setText(editCajero!!.direccion)
            etLatitud.setText(editCajero!!.latitud.toString())
            etLongitud.setText(editCajero!!.longitud.toString())
            etZoom.setText(editCajero!!.zoom)
        }
    }




}


