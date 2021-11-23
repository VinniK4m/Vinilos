package co.edu.uniandes.fourbidden.vinilos.modelo

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate


@Entity(tableName = "musicos_table")
data class Musico(

    @PrimaryKey
    val id: String,
    val name: String,
    val image: String,
    val birthDate: String,
    val description: String,
    //var albums: List<Album>
)