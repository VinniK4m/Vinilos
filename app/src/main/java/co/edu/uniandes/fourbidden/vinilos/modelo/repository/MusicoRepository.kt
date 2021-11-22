package co.edu.uniandes.fourbidden.vinilos.modelo.repository

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import co.edu.uniandes.fourbidden.vinilos.modelo.Musico
import co.edu.uniandes.fourbidden.vinilos.modelo.servicio.ServiceAdapterMusico
import com.android.volley.VolleyError


class MusicoRepository(val application: Application) {


    suspend fun refreshDataMusicos(): List<Musico>{
        return ServiceAdapterMusico.getInstance(application).getMusicos()
    }



    @RequiresApi(Build.VERSION_CODES.O)
    fun refreshDataMusico(musicoId: String, callback:(Musico)->Unit, onError:(VolleyError)->Unit) {
        ServiceAdapterMusico.getInstance(application).getMusico( Integer.parseInt(musicoId),{
            callback(it)
        },onError)
    }

}