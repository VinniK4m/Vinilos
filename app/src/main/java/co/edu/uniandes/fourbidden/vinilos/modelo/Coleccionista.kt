package co.edu.uniandes.fourbidden.vinilos.modelo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "coleccionistas_table")
data class Coleccionista (
    @PrimaryKey
    val id: Int,
    val name: String,
    val email: String,
    //val comments: List,
    //val collectorAlbums: List
    )