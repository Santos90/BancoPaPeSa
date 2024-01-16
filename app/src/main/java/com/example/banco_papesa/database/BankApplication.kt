package com.example.banco_papesa.database

import android.app.Application
import androidx.room.Room
import com.example.banco_papesa.pojo.CajeroEntity
import com.simarro.stores.database.BankDatabase
import java.util.concurrent.LinkedBlockingDeque


class BankApplication : Application() {

	companion object {
		lateinit var database : BankDatabase
	}

	override fun onCreate() {
		super.onCreate()
		/*
		val MIGRATION_1_2 = object: Migration(1,2) {
			override fun migrate(db: SupportSQLiteDatabase) {
				db.execSQL("ALTER TABLE StoreEntity ADD COLUMN photoUrl TEXT NOT NULL DEFAULT ''")
			}
		}

		 */
		database = Room.databaseBuilder(this, BankDatabase::class.java, "BankDatabase")
			//.addMigrations(MIGRATION_1_2)
			.build()

	}


}


