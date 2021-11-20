package co.edu.uniandes.fourbidden.vinilos.modelo.repository

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import co.edu.uniandes.fourbidden.vinilos.modelo.Album


import co.edu.uniandes.fourbidden.vinilos.modelo.servicio.ServiceAdapter
import com.android.volley.VolleyError


class AlbumRepository(val application: Application) {

    @RequiresApi(Build.VERSION_CODES.O)
    fun refreshDataAlbums(callback:(List<Album>)->Unit, onError:(VolleyError)->Unit) {
        ServiceAdapter.getInstance(application).getAlbums({
            callback(it)
        },onError)
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun refreshDataAlbum(albumId: String, callback:(Album)->Unit, onError:(VolleyError)->Unit) {
        ServiceAdapter.getInstance(application).getAlbum( Integer.parseInt(albumId),{
            callback(it)
        },onError)
    }

}