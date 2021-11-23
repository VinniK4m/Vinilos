package co.edu.uniandes.fourbidden.vinilos.modelo

import java.time.LocalDate
import java.util.*

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "albums_table")
data class Album (

    @PrimaryKey
    val id: String,
    val name: String,
    val cover: String,
    val releaseDate: String,
    val description: String,
    val genre: String,
    val recordLabel: String,
    //var tracks: List<Track>
)