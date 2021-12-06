package co.edu.uniandes.fourbidden.vinilos.vistamodelo


import android.app.Application
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.*
import co.edu.uniandes.fourbidden.vinilos.database.VinylRoomDatabase
import co.edu.uniandes.fourbidden.vinilos.modelo.Album
import co.edu.uniandes.fourbidden.vinilos.modelo.repository.AlbumRepository
import com.android.volley.VolleyError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject

@RequiresApi(Build.VERSION_CODES.O)
class AlbumViewModel (application: Application, musicoId: Int) :  AndroidViewModel(application) {

    private val _albumrepository = AlbumRepository(application, VinylRoomDatabase.getDatabase(application.applicationContext).albumsDao())
    private val _albums = MutableLiveData<List<Album>>()

    val musicoId:Int = musicoId

    val albums: LiveData<List<Album>>
        get() = _albums

    private var _eventNetworkError = MutableLiveData(false)

    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError

    private var _isNetworkErrorShown = MutableLiveData(false)

    val isNetworkErrorShown: LiveData<Boolean>
        get() = _isNetworkErrorShown

    init {
        refreshDataFromNetwork()

    }


    public fun crearAlbum(json: JSONObject,onComplete:(resp:JSONObject)->Unit , onError: (error: VolleyError)->Unit){

        try {
            viewModelScope.launch(Dispatchers.Default) {
                withContext(Dispatchers.IO) {
                    val data = _albumrepository.createAlbum(json,onComplete ,onError )
                }
            }
        }
    catch (e:Exception){
        _eventNetworkError.value = true
    }



    }



    private fun refreshDataFromNetwork() {
        try {
            viewModelScope.launch (Dispatchers.Default){
                withContext(Dispatchers.IO){
                    Log.d("musico ID", musicoId.toString())
                    if (musicoId > 0){
                        val data = _albumrepository.refreshData(musicoId)
                        _albums.postValue(data)
                    }else{
                        val data = _albumrepository.refreshData()
                        _albums.postValue(data)
                    }
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

    class Factory(val app: Application, val musicoId: Int) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(AlbumViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return AlbumViewModel(app,musicoId ) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }

}