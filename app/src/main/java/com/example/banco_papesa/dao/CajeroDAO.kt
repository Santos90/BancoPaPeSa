package com.example.banco_papesa.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.banco_papesa.pojo.CajeroEntity

@Dao
interface CajeroDAO {

	//● fun getAllCajeros() : MutableList<CajeroEntity>
	//● fun insertAll(CajeroEntityList : List<CajeroEntity>)
	//● fun addCajero(cajeroEntity: CajeroEntity)
	//● fun updateCajero(cajeroEntity: CajeroEntity)
	//● fun deleteCajero(cajeroEntity: CajeroEntity)
	@Query("SELECT * From CajeroEntity")
	fun getAllCajeros() : MutableList<CajeroEntity>

	@Insert
	fun addCajero(cajeroEntity : CajeroEntity) : Long

	@Insert
	fun insertAll(CajeroEntityList : List<CajeroEntity>)

	@Update
	fun updateStore (cajeroEntity: CajeroEntity)

	@Delete
	fun deleteStore (cajeroEntity: CajeroEntity)


}