package com.simarro.stores.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.banco_papesa.dao.CajeroDAO
import com.example.banco_papesa.pojo.CajeroEntity

@Database (entities = arrayOf(CajeroEntity::class), version = 1, exportSchema = false)
abstract class BankDatabase : RoomDatabase() {

	abstract fun cajeroDAO() : CajeroDAO

}