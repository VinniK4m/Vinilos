package co.edu.uniandes.fourbidden.vinilos.modelo.repository

import android.content.Context
import android.app.Application
import android.net.ConnectivityManager
import android.os.Build
import androidx.annotation.RequiresApi
import co.edu.uniandes.fourbidden.vinilos.modelo.Album
import co.edu.uniandes.fourbidden.vinilos.modelo.servicio.CacheManager
import org.json.JSONArray

import co.edu.uniandes.fourbidden.vinilos.modelo.servicio.ServiceAdapter
import com.android.volley.VolleyError


class AlbumRepository(val application: Application) {

    suspend fun refreshDataAlbums(): List<Album>{
        var albums = getAlbumes()
        return if(albums.isNullOrEmpty()){
            val cm = application.baseContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if( cm.activeNetworkInfo?.type != ConnectivityManager.TYPE_WIFI && cm.activeNetworkInfo?.type != ConnectivityManager.TYPE_MOBILE){
                emptyList()
            } else {
                albums = ServiceAdapter.getInstance(application).getAlbums()
                albums
            }
        }else albums
    }

    private fun getAlbumes(): List<Album> {
        val prefs = CacheManager.getPrefs(application.baseContext, CacheManager.ALBUMS_SPREFS)

        return listOf<Album>()
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun refreshDataAlbum(albumId: String, callback:(Album)->Unit, onError:(VolleyError)->Unit) {
        ServiceAdapter.getInstance(application).getAlbum( Integer.parseInt(albumId),{
            callback(it)
        },onError)
    }

}