package co.edu.uniandes.fourbidden.vinilos.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import co.edu.uniandes.fourbidden.vinilos.modelo.Coleccionista


@Dao
interface ColeccionistasDao {
    @Query("SELECT * FROM coleccionistas_table")
    fun getColeccionistas():List<Coleccionista>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(coleccionista: Coleccionista)

    @Query("DELETE FROM coleccionistas_table")
    suspend fun deleteAll(): Int
}