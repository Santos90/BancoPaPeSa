package com.example.banco_papesa.pojo

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "CajeroEntity")
class CajeroEntity (
	@PrimaryKey(autoGenerate = true)
	var id : Long,
	var direccion : String = "",
	var latitud : Double = 0.0,
	var longitud : Double = 0.0,
	var zoom : String = ""
)  {
	override fun equals(other: Any?): Boolean {
		if (this === other) return true
		if (javaClass != other?.javaClass) return false

		other as CajeroEntity

		return id == other.id
	}

	override fun hashCode(): Int {
		return id.hashCode()
	}

}