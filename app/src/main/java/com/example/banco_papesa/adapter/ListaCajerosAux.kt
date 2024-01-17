package com.example.banco_papesa.adapter

import com.example.banco_papesa.pojo.CajeroEntity

interface ListaCajerosAux {

	fun setVisibilityFAB (isVisible : Boolean)

	fun addCajero (cajero: CajeroEntity)
	fun updateCajero(original: CajeroEntity, editado: CajeroEntity)

	fun deleteStore(cajero: CajeroEntity)
}