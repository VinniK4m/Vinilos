package co.edu.uniandes.fourbidden.vinilos.modelo.repository

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import co.edu.uniandes.fourbidden.vinilos.modelo.Musico
import co.edu.uniandes.fourbidden.vinilos.modelo.servicio.ServiceAdapterMusico
import com.android.volley.VolleyError


class MusicoRepository(val application: Application) {

    @RequiresApi(Build.VERSION_CODES.O)
    fun refreshDataMusicos(callback:(List<Musico>)->Unit, onError:(VolleyError)->Unit) {
        ServiceAdapterMusico.getInstance(application).getMusicos({
            callback(it)
        },onError)
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun refreshDataMusico(musicoId: String, callback:(Musico)->Unit, onError:(VolleyError)->Unit) {
        ServiceAdapterMusico.getInstance(application).getMusico( Integer.parseInt(musicoId),{
            callback(it)
        },onError)
    }

}