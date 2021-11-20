package co.edu.uniandes.fourbidden.vinilos.vistamodelo


import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.*
import co.edu.uniandes.fourbidden.vinilos.modelo.Coleccionista
import co.edu.uniandes.fourbidden.vinilos.modelo.repository.ColeccionistaRepository


@RequiresApi(Build.VERSION_CODES.O)
class ColeccionistaViewModel (application: Application) :  AndroidViewModel(application) {

    private val _coleccionistas = MutableLiveData<List<Coleccionista>>()
    private val _coleccionistarepository = ColeccionistaRepository(application)

    val coleccionistas: LiveData<List<Coleccionista>>
        get() = _coleccionistas

    private var _eventNetworkError = MutableLiveData<Boolean>(false)

    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError

    private var _isNetworkErrorShown = MutableLiveData<Boolean>(false)

    val isNetworkErrorShown: LiveData<Boolean>
        get() = _isNetworkErrorShown

    init {
        refreshDataFromNetwork()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun refreshDataFromNetwork() {
        _coleccionistarepository.refreshDataColeccionista( { _coleccionistas.postValue(it)
            _eventNetworkError.value = false
            _isNetworkErrorShown.value = false
        },{
            _eventNetworkError.value = true
        })
    }

    fun onNetworkErrorShown() {
        _isNetworkErrorShown.value = true
    }

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ColeccionistaViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return ColeccionistaViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}