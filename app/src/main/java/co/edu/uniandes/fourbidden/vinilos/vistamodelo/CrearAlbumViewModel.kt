package co.edu.uniandes.fourbidden.vinilos.vistamodelo

import android.app.Application
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.*
import co.edu.uniandes.fourbidden.vinilos.database.VinylRoomDatabase
import co.edu.uniandes.fourbidden.vinilos.modelo.Album
import co.edu.uniandes.fourbidden.vinilos.modelo.repository.AlbumRepository
import org.json.JSONObject


@RequiresApi(Build.VERSION_CODES.O)
class CrearAlbumViewModel (application: Application,parametros : JSONObject) :  AndroidViewModel(application) {

    private val _albumrepository = AlbumRepository(application, VinylRoomDatabase.getDatabase(application.applicationContext).albumsDao())

    private val _album = MutableLiveData<Album>()

    val album: LiveData<Album>
        get() = _album

    private var _eventNetworkError = MutableLiveData(false)

    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError

    private var _isNetworkErrorShown = MutableLiveData(false)

    val isNetworkErrorShown: LiveData<Boolean>
        get() = _isNetworkErrorShown

    init {
         _albumrepository.createAlbum(parametros,  { response ->
             Log.d("prueba","")
         },
             {
                 Log.d("prueba","")
             })
    }

    fun onNetworkErrorShown() {
        _isNetworkErrorShown.value = true
    }

    class Factory(val app: Application,private val json: JSONObject) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(CrearAlbumViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return CrearAlbumViewModel(app, json) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}