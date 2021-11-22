package co.edu.uniandes.fourbidden.vinilos.modelo.servicio

import android.content.Context
import android.content.SharedPreferences
import co.edu.uniandes.fourbidden.vinilos.modelo.Album
import co.edu.uniandes.fourbidden.vinilos.modelo.Coleccionista
import co.edu.uniandes.fourbidden.vinilos.modelo.Musico

class CacheManager (context: Context) {

    companion object{
        var instance: CacheManager? = null
        fun getInstance(context: Context) =
            instance ?: synchronized(this) {
                instance ?: CacheManager(context).also {
                    instance = it
                }
            }
        const val APP_SPREFS = "co.edu.uniandes.fourbidden.vinilos.app"
        const val ALBUMS_SPREFS = "co.edu.uniandes.fourbidden.vinilos.albums"
        fun getPrefs(context: Context, name:String): SharedPreferences {
            return context.getSharedPreferences(name,
                Context.MODE_PRIVATE
            )
        }
    }
    private var albums: HashMap<Int, List<Album>> = hashMapOf<Int, List<Album>>()
    private var musicos: HashMap<Int, List<Musico>> = hashMapOf<Int, List<Musico>>()
    private var coleccionista: HashMap<Int, List<Coleccionista>> = hashMapOf<Int, List<Coleccionista>>()

}