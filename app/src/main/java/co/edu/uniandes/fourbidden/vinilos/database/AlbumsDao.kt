package co.edu.uniandes.fourbidden.vinilos.database

import androidx.room.*
import co.edu.uniandes.fourbidden.vinilos.modelo.Album
import co.edu.uniandes.fourbidden.vinilos.modelo.AlbumWithTracks
import co.edu.uniandes.fourbidden.vinilos.modelo.Track


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


    @Transaction
    @Query("SELECT * FROM albums_table")
    fun getAlbumsWithTracks(): List<AlbumWithTracks>


    /*
    @Insert
    fun insertTrackList(tracks: List<Track>?)


    @Query("SELECT * FROM tracks_table WHERE albumId =:albumId")
    fun getTrackList(albumId:String): List<Track>

    fun insertAlbumWithTrack(album: Album) {
        val tracks: List<Track> = getTrackList(album.id)
        for (i in tracks.indices) {
            tracks[i].setUserId(album.id)
        }
        insertTrackList(tracks)
        insert(album)
    }


    fun getAlbumWithTracks(id: Int): Album? {
        val album: Album = getAlbum(id)
        val tracks: List<Track> = getTrackList(id)
        album.setPetList(tracks)
        return album
    }

*/

}