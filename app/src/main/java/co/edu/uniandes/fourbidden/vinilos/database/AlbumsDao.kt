package co.edu.uniandes.fourbidden.vinilos.database
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import co.edu.uniandes.fourbidden.vinilos.modelo.Album

@Dao
interface AlbumsDao {
    @Query("SELECT * FROM albums_table")
    fun getAlbums():List<Album>


    @Query("SELECT * FROM albums_table WHERE id = :albumId")
    fun getAlbum(albumId:Int):Album



    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(album: Album)

    @Query("DELETE FROM albums_table")
    suspend fun deleteAll(): Int
}