package co.edu.uniandes.fourbidden.vinilos.modelo.repository

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import co.edu.uniandes.fourbidden.vinilos.database.TracksDao
import co.edu.uniandes.fourbidden.vinilos.modelo.Track
import co.edu.uniandes.fourbidden.vinilos.modelo.servicio.ServiceAdapter
import com.android.volley.VolleyError
import org.json.JSONObject

class TrackRespository (val application: Application, private val tracksDao: TracksDao) {

    suspend fun refreshData(albumId: Int): List<Track>{
        var cached = tracksDao.getTracks(albumId)
        return if(cached.isNullOrEmpty()){
            val cm = application.baseContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if( cm.activeNetworkInfo?.type != ConnectivityManager.TYPE_WIFI && cm.activeNetworkInfo?.type != ConnectivityManager.TYPE_MOBILE){
                emptyList()
            } else ServiceAdapter.getInstance(application).getTracks(albumId)
        } else cached
    }

    fun createTrack(albumId:Int, newtrack: JSONObject, onComplete:(resp: JSONObject)->Unit, onError: (error: VolleyError)->Unit) {
        ServiceAdapter.getInstance(application).postTrack(albumId,newtrack,onComplete,  onError)
    }
}