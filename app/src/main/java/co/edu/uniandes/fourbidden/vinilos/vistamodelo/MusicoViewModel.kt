package co.edu.uniandes.fourbidden.vinilos.vistamodelo


import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.*
import co.edu.uniandes.fourbidden.vinilos.modelo.Musico
import co.edu.uniandes.fourbidden.vinilos.modelo.repository.MusicoRepository


@RequiresApi(Build.VERSION_CODES.O)
class MusicoViewModel (application: Application) :  AndroidViewModel(application) {

    private val _musicos = MutableLiveData<List<Musico>>()
    private val _musciorepository = MusicoRepository(application)

    val musicos: LiveData<List<Musico>>
        get() = _musicos

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
        _musciorepository.refreshDataMusicos( { _musicos.postValue(it)
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
            if (modelClass.isAssignableFrom(MusicoViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MusicoViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}