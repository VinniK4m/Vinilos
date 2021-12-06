package co.edu.uniandes.fourbidden.vinilos.modelo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "musicos_table")
data class Musico(
    @PrimaryKey
    val id: Int,
    val name: String,
    val image: String,
    val birthDate: String,
    val description: String
)