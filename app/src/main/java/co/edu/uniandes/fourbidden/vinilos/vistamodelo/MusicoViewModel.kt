package co.edu.uniandes.fourbidden.vinilos.vistamodelo


import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.*
import co.edu.uniandes.fourbidden.vinilos.database.VinylRoomDatabase
import co.edu.uniandes.fourbidden.vinilos.modelo.Musico
import co.edu.uniandes.fourbidden.vinilos.modelo.repository.ColeccionistaRepository
import co.edu.uniandes.fourbidden.vinilos.modelo.repository.MusicoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@RequiresApi(Build.VERSION_CODES.O)
class MusicoViewModel (application: Application) :  AndroidViewModel(application) {

    private val _musicos = MutableLiveData<List<Musico>>()
    //private val _musciorepository = MusicoRepository(application)
    private val _musciorepository = MusicoRepository(application, VinylRoomDatabase.getDatabase(application.applicationContext).musicosDao())

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

     private fun refreshDataFromNetwork() {
        try {
            viewModelScope.launch (Dispatchers.Default){
                withContext(Dispatchers.IO){
                    var data = _musciorepository.refreshDataMusicos()
                    _musicos.postValue(data)
                }
                _eventNetworkError.postValue(false)
                _isNetworkErrorShown.postValue(false)
            }
        }
        catch (e:Exception){
            _eventNetworkError.value = true
        }
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