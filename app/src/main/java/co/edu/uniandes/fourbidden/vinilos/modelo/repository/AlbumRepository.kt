package co.edu.uniandes.fourbidden.vinilos.modelo.repository

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.os.Build
import androidx.annotation.RequiresApi
import co.edu.uniandes.fourbidden.vinilos.database.AlbumsDao
import co.edu.uniandes.fourbidden.vinilos.modelo.Album
import co.edu.uniandes.fourbidden.vinilos.modelo.Track
import co.edu.uniandes.fourbidden.vinilos.modelo.servicio.ServiceAdapter
import co.edu.uniandes.fourbidden.vinilos.modelo.servicio.ServiceAdapterMusico
import com.android.volley.VolleyError


class AlbumRepository(val application: Application, private val albumsDao: AlbumsDao) {

    suspend fun refreshData(): List<Album>{
        var cached = albumsDao.getAlbums()
        return if(cached.isNullOrEmpty()){
            val cm = application.baseContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if( cm.activeNetworkInfo?.type != ConnectivityManager.TYPE_WIFI && cm.activeNetworkInfo?.type != ConnectivityManager.TYPE_MOBILE){
                emptyList()
            } else ServiceAdapter.getInstance(application).getAlbums()
        } else cached
    }
    suspend fun refreshData(musicoId: Int): List<Album>{
        var cached = albumsDao.getAlbumsByMusico(musicoId)
        return if(cached.isNullOrEmpty()){
            val cm = application.baseContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if( cm.activeNetworkInfo?.type != ConnectivityManager.TYPE_WIFI && cm.activeNetworkInfo?.type != ConnectivityManager.TYPE_MOBILE){
                emptyList()
            } else ServiceAdapterMusico.getInstance(application).getAlbums(musicoId)
        } else cached
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun refreshDataAlbum(albumId: String, callback:(Album)->Unit, onError:(VolleyError)->Unit) {
        ServiceAdapter.getInstance(application).getAlbum( Integer.parseInt(albumId),{
            callback(it)
        },onError)
    }
    /*
    private fun getAlbumes(): List<Album> {
        val prefs = CacheManager.getPrefs(application.baseContext, CacheManager.ALBUMS_SPREFS)

        return listOf<Album>()
    }



    */
}