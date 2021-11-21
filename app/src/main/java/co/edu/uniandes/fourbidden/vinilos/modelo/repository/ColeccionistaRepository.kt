package co.edu.uniandes.fourbidden.vinilos.modelo.repository

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import co.edu.uniandes.fourbidden.vinilos.modelo.Coleccionista
import co.edu.uniandes.fourbidden.vinilos.modelo.servicio.ServiceAdapterColeccionista
import com.android.volley.VolleyError


class ColeccionistaRepository(val application: Application) {

    @RequiresApi(Build.VERSION_CODES.O)
    fun refreshDataColeccionista(callback:(List<Coleccionista>)->Unit, onError:(VolleyError)->Unit) {
        ServiceAdapterColeccionista.getInstance(application).getColeccionistas({
            callback(it)
        },onError)
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun refreshDataColeccionista(coleccionistaId: String, callback:(Coleccionista)->Unit, onError:(VolleyError)->Unit) {
        ServiceAdapterColeccionista.getInstance(application).getColeccionista( Integer.parseInt(coleccionistaId),{
            callback(it)
        },onError)
    }

}