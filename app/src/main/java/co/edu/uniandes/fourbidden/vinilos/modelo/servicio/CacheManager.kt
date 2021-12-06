package co.edu.uniandes.fourbidden.vinilos.modelo.servicio

import android.content.Context
import android.content.SharedPreferences
import co.edu.uniandes.fourbidden.vinilos.modelo.Album
import co.edu.uniandes.fourbidden.vinilos.modelo.Track

class CacheManager (context: Context) {

    companion object{
        var instance: CacheManager? = null
        fun getInstance(context: Context) =
            instance ?: synchronized(this) {
                instance ?: CacheManager(context).also {
                    instance = it
                }
            }
        const val APP_SPREFS = "co.edu.uniandes.fourbidden.vinilos.app"
        const val ALBUMS_SPREFS = "co.edu.uniandes.fourbidden.vinilos.albums"
        const val TRACKS_SPREFS = "co.edu.uniandes.fourbidden.vinilos.tracks"

        fun getPrefs(context: Context, name:String): SharedPreferences {
            return context.getSharedPreferences(name,
                Context.MODE_PRIVATE
            )
        }
    }

    private var tracks: HashMap<Int, List<Track>> = hashMapOf<Int, List<Track>>()

    fun addTracks(albumId: Int, track: List<Track>){
        if (tracks.containsKey(albumId)){
            tracks[albumId] = track
        }
    }
    fun getTracks(albumId: Int) : List<Track>{
        return if (tracks.containsKey(albumId)) tracks[albumId]!! else listOf<Track>()
    }

    private var albums: HashMap<Int, List<Album>> = hashMapOf<Int, List<Album>>()

    fun addAlbums(musicoId: Int, album: List<Album>){
        if (albums.containsKey(musicoId)){
            albums[musicoId] = album
        }
    }
    fun getAlbums(musicoId: Int) : List<Album>{
        return if (albums.containsKey(musicoId)) albums[musicoId]!! else listOf<Album>()
    }


}