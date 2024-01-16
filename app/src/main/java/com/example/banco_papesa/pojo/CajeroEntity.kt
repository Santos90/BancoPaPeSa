package com.example.banco_papesa.pojo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "CajeroEntity")
class CajeroEntity (
	@PrimaryKey(autoGenerate = true)
	var id : Long,
	var direccion : String = "",
	var latitud : Double = 0.0,
	var longitud : Double = 0.0,
	var zoom : String = ""
) {

	//Para ello, crearemos la entidad Cajeros, de los cuales guardaremos su id (que se autogenerará por
	//Room), la dirección (String), la latitud (Double), la longitud (Double) y el zoom (String.
}