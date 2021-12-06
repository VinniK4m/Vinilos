package co.edu.uniandes.fourbidden.vinilos.vistamodelo

import android.app.Application
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.*
import co.edu.uniandes.fourbidden.vinilos.database.VinylRoomDatabase
import co.edu.uniandes.fourbidden.vinilos.modelo.Track
import co.edu.uniandes.fourbidden.vinilos.modelo.repository.TrackRespository
import org.json.JSONObject


@RequiresApi(Build.VERSION_CODES.O)
class CrearTrackViewModel (application: Application, albumId: Int, parametros : JSONObject) :  AndroidViewModel(application) {

    private val _trackrepository = TrackRespository(application, VinylRoomDatabase.getDatabase(application.applicationContext).tracksDao())

    private val _track = MutableLiveData<Track>()

    val track: LiveData<Track>
        get() = _track

    private var _eventNetworkError = MutableLiveData(false)

    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError

    private var _isNetworkErrorShown = MutableLiveData(false)

    val isNetworkErrorShown: LiveData<Boolean>
        get() = _isNetworkErrorShown

    init {
         _trackrepository.createTrack(albumId, parametros,  { response ->
             Log.d("prueba","")
         },
             {
                 Log.d("prueba","")
             })
    }

    fun onNetworkErrorShown() {
        _isNetworkErrorShown.value = true
    }

    class Factory(val app: Application, private val albumId: Int, val json: JSONObject) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(CrearTrackViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return CrearTrackViewModel(app, albumId, json) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}