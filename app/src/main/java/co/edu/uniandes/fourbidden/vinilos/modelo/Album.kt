package co.edu.uniandes.fourbidden.vinilos.modelo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "albums_table")
data class Album (
    @PrimaryKey
    val id: Int,
    val name: String,
    val cover: String,
    val releaseDate: String,
    val description: String,
    val genre: String,
    val recordLabel: String,
    val idMusico : Int

)