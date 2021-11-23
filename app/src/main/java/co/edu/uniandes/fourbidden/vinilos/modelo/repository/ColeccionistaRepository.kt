package co.edu.uniandes.fourbidden.vinilos.modelo.repository

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.os.Build
import androidx.annotation.RequiresApi
import co.edu.uniandes.fourbidden.vinilos.database.ColeccionistasDao
import co.edu.uniandes.fourbidden.vinilos.database.MusicosDao
import co.edu.uniandes.fourbidden.vinilos.modelo.Album
import co.edu.uniandes.fourbidden.vinilos.modelo.Coleccionista
import co.edu.uniandes.fourbidden.vinilos.modelo.Musico
import co.edu.uniandes.fourbidden.vinilos.modelo.servicio.ServiceAdapter
import co.edu.uniandes.fourbidden.vinilos.modelo.servicio.ServiceAdapterColeccionista
import co.edu.uniandes.fourbidden.vinilos.modelo.servicio.ServiceAdapterMusico
import com.android.volley.VolleyError


class ColeccionistaRepository(val application: Application, private val coleccionistasDao: ColeccionistasDao){
    suspend fun refreshData(): List<Coleccionista>{
        var cached = coleccionistasDao.getColeccionistas()
        return if(cached.isNullOrEmpty()){
            val cm = application.baseContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if( cm.activeNetworkInfo?.type != ConnectivityManager.TYPE_WIFI && cm.activeNetworkInfo?.type != ConnectivityManager.TYPE_MOBILE){
                emptyList()
            } else ServiceAdapterColeccionista.getInstance(application).getColeccionistas()
        } else cached
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun refreshDataColeccionista(coleccionistaId: String, callback:(Coleccionista)->Unit, onError:(VolleyError)->Unit) {
        ServiceAdapterColeccionista.getInstance(application).getColeccionista( Integer.parseInt(coleccionistaId),{
            callback(it)
        },onError)
    }

}