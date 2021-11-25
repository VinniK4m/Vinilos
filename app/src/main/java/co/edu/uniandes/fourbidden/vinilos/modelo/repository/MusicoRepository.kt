package co.edu.uniandes.fourbidden.vinilos.modelo.repository

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.os.Build
import androidx.annotation.RequiresApi
import co.edu.uniandes.fourbidden.vinilos.database.AlbumsDao
import co.edu.uniandes.fourbidden.vinilos.database.MusicosDao
import co.edu.uniandes.fourbidden.vinilos.modelo.Album
import co.edu.uniandes.fourbidden.vinilos.modelo.Musico
import co.edu.uniandes.fourbidden.vinilos.modelo.servicio.ServiceAdapter
import co.edu.uniandes.fourbidden.vinilos.modelo.servicio.ServiceAdapterMusico
import com.android.volley.VolleyError


class MusicoRepository(val application: Application, private val musicosDao: MusicosDao){
    suspend fun refreshData(): List<Musico>{
        var cached = musicosDao.getMusicos()
        return if(cached.isNullOrEmpty()){
            val cm = application.baseContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if( cm.activeNetworkInfo?.type != ConnectivityManager.TYPE_WIFI && cm.activeNetworkInfo?.type != ConnectivityManager.TYPE_MOBILE){
                emptyList()
            } else ServiceAdapterMusico.getInstance(application).getMusicos()
        } else cached
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun refreshDataMusico(musicoId: String, callback:(Musico)->Unit, onError:(VolleyError)->Unit) {
        ServiceAdapterMusico.getInstance(application).getMusico( Integer.parseInt(musicoId),{
            callback(it)
        },onError)
    }

}