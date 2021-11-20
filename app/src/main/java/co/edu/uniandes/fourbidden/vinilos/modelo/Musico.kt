package co.edu.uniandes.fourbidden.vinilos.modelo

import java.time.LocalDate

data class Musico(
    val id: String,
    val name: String,
    val image: String,
    val birthDate: LocalDate,
    val description: String,
    var albums: List<Album>
)