package co.edu.uniandes.fourbidden.vinilos.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import co.edu.uniandes.fourbidden.vinilos.modelo.Track


@Dao
interface TracksDao {
    @Query("SELECT * FROM tracks_table WHERE albumId = :albumId")
    fun getTracks(albumId:Int):List<Track>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(track: Track)

    suspend fun clear():Void

    @Query("DELETE FROM tracks_table WHERE albumId = :albumId")
    suspend fun deleteAll(albumId: Int): Int


}