package co.edu.uniandes.fourbidden.vinilos.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import co.edu.uniandes.fourbidden.vinilos.modelo.Musico


@Dao
interface MusicosDao {
    @Query("SELECT * FROM musicos_table")
    fun getMusicos():List<Musico>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(musico: Musico)

    @Query("DELETE FROM musicos_table")
    suspend fun deleteAll(): Int
}