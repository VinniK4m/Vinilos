package co.edu.uniandes.fourbidden.vinilos.modelo.repository

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.os.Build
import androidx.annotation.RequiresApi
import co.edu.uniandes.fourbidden.vinilos.database.AlbumsDao
import co.edu.uniandes.fourbidden.vinilos.modelo.Album


import co.edu.uniandes.fourbidden.vinilos.modelo.servicio.ServiceAdapter
import com.android.volley.VolleyError


class AlbumRepository (val application: Application, private val albumsDao: AlbumsDao){
    suspend fun refreshData(): List<Album>{
        var cached = albumsDao.getAlbums()
        return if(cached.isNullOrEmpty()){
            val cm = application.baseContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if( cm.activeNetworkInfo?.type != ConnectivityManager.TYPE_WIFI && cm.activeNetworkInfo?.type != ConnectivityManager.TYPE_MOBILE){
                emptyList()
            } else ServiceAdapter.getInstance(application).getAlbums()
        } else cached
    }




    suspend fun refreshDataAlbum(albumId: Int): Album {
        var cached = albumsDao.getAlbum(albumId)
        return if(cached == null){
            val cm = application.baseContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if( cm.activeNetworkInfo?.type != ConnectivityManager.TYPE_WIFI && cm.activeNetworkInfo?.type != ConnectivityManager.TYPE_MOBILE){
                albumsDao.getAlbum(albumId)
            } else ServiceAdapter.getInstance(application).getAlbum(albumId)
        } else cached
    }


}