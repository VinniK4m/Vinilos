package co.edu.uniandes.fourbidden.vinilos.vistamodelo

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.*
import co.edu.uniandes.fourbidden.vinilos.modelo.Musico
import co.edu.uniandes.fourbidden.vinilos.modelo.repository.MusicoRepository


@RequiresApi(Build.VERSION_CODES.O)
class DetalleMusicoViewModel (application: Application, musicoId: String) :  AndroidViewModel(application) {

    private val _musico = MutableLiveData<Musico>()
    private val _musicorepository = MusicoRepository(application)

    val musico: LiveData<Musico>
        get() = _musico

    private var _eventNetworkError = MutableLiveData(false)

    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError

    private var _isNetworkErrorShown = MutableLiveData(false)

    val isNetworkErrorShown: LiveData<Boolean>
        get() = _isNetworkErrorShown

    init {
        _musicorepository.refreshDataMusico(musicoId,{_musico.value = it},{})
    }


    fun onNetworkErrorShown() {
        _isNetworkErrorShown.value = true
    }

    class Factory(val app: Application, private val musicoId: String) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(DetalleMusicoViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return DetalleMusicoViewModel(app, musicoId) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}