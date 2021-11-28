package co.edu.uniandes.fourbidden.vinilos.vistamodelo

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.*
import co.edu.uniandes.fourbidden.vinilos.database.VinylRoomDatabase
import co.edu.uniandes.fourbidden.vinilos.modelo.Album
import co.edu.uniandes.fourbidden.vinilos.modelo.Coleccionista
import co.edu.uniandes.fourbidden.vinilos.modelo.repository.AlbumRepository
import co.edu.uniandes.fourbidden.vinilos.modelo.repository.ColeccionistaRepository


@RequiresApi(Build.VERSION_CODES.O)
class DetalleColeccionistaViewModel(application: Application, albumId: String) :
    AndroidViewModel(application) {

    private val _coleccionistarepository = ColeccionistaRepository(
        application,
        VinylRoomDatabase.getDatabase(application.applicationContext).coleccionistasDao()
    )

    private val _coleccionista = MutableLiveData<Coleccionista>()
    //private val _albumrepository = AlbumRepository(application)

    val coleccionista: LiveData<Coleccionista>
        get() = _coleccionista

    private var _eventNetworkError = MutableLiveData(false)

    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError

    private var _isNetworkErrorShown = MutableLiveData(false)

    val isNetworkErrorShown: LiveData<Boolean>
        get() = _isNetworkErrorShown

    init {
        _coleccionistarepository.refreshDataColeccionista(albumId,
            { _coleccionista.value = it },
            {})
    }


    fun onNetworkErrorShown() {
        _isNetworkErrorShown.value = true
    }

    class Factory(val app: Application, private val coleccionistaId: String) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(DetalleColeccionistaViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return DetalleColeccionistaViewModel(app, coleccionistaId) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}