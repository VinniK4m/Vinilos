package co.edu.uniandes.fourbidden.vinilos.modelo

import androidx.room.*
import java.time.LocalDate


data class AlbumWithTracks (

    @Embedded val album: Album,
    @Relation(
        parentColumn = "id",
        entityColumn = "id"
    )
    val tracks: List<Track>
)

