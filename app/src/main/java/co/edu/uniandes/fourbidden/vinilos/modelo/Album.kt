package co.edu.uniandes.fourbidden.vinilos.modelo

import java.time.LocalDate
import java.util.*

data class Album (
    val id: String,
    val name: String,
    val cover: String,
    val releaseDate: LocalDate,
    val description: String,
    val genre: String,
    val recordLabel: String,
    var tracks: List<Track>
)