package co.edu.uniandes.fourbidden.vinilos.modelo.servicio

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import co.edu.uniandes.fourbidden.vinilos.modelo.Album
import co.edu.uniandes.fourbidden.vinilos.modelo.Musico
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONObject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

@Suppress("RedundantSamConstructor")
class ServiceAdapterMusico constructor(context: Context) {
    companion object{
        const val URL_API= "https://back-vinyls-populated.herokuapp.com/"
        private var instance: ServiceAdapterMusico? = null
        fun getInstance(context: Context) =
            instance ?: synchronized(this) {
                instance ?: ServiceAdapterMusico(context).also {
                    instance = it
                }
            }
    }
    private val requestQueue: RequestQueue by lazy {
        Volley.newRequestQueue(context.applicationContext)
    }

    suspend fun getMusicos() = suspendCoroutine<List<Musico>>{ cont->
        requestQueue.add(getRequest("musicians",
            Response.Listener<String> { response ->
                val resp = JSONArray(response)
                val list = mutableListOf<Musico>()
                var item:JSONObject? = null
                var fecha : String = ""
                for (i in 0 until resp.length()) {
                    item = resp.getJSONObject(i)
                    fecha =  item!!.getString("birthDate").substringBefore(delimiter = "T", missingDelimiterValue = "2000-01-01")

                    list.add(i, Musico(id = item.getInt("id"),name = item.getString("name"), image = item.getString("image"),
                        birthDate = fecha, description = item.getString("description")))
                }
                cont.resume(list)
            },
            Response.ErrorListener {
                cont.resumeWithException(it)
            }))
    }



    @RequiresApi(Build.VERSION_CODES.O)
    fun getMusico(musicoId: Int, onComplete: (resp: Musico) -> Unit, onError: (error: VolleyError) -> Unit){
        requestQueue.add(getRequest("musicians/$musicoId",
            Response.Listener<String> { response ->
                val resp = JSONObject(response)
                val fecha : String =  resp.getString("birthDate").substringBefore(delimiter = "T", missingDelimiterValue = "2000-01-01")
                //val releaseDate = parse(fecha)

                val musico = Musico(id = resp.getInt("id"),name = resp.getString("name"), image = resp.getString("image"),
                    birthDate = fecha, description = resp.getString("description"))
                onComplete(musico)
            },
            Response.ErrorListener {
                onError(it)
            }))
    }
    suspend fun getAlbums(idMusico: Int) = suspendCoroutine<List<Album>>{ cont->
        requestQueue.add(getRequest("musicians/$idMusico/albums",
            Response.Listener<String> { response ->
                val resp = JSONArray(response)
                val list = mutableListOf<Album>()
                var item:JSONObject? = null
                var fecha : String = ""
                for (i in 0 until resp.length()) {
                    item = resp.getJSONObject(i)
                    fecha =  item.getString("releaseDate").substringBefore(delimiter = "T", missingDelimiterValue = "2000-01-01")
                    list.add(i, Album(id = item.getInt("id"),
                        name = item.getString("name"),
                        cover = item.getString("cover"),
                        recordLabel = item.getString("recordLabel"),
                        releaseDate = fecha,
                        genre = item.getString("genre"),
                        description = item.getString("description"),
                        idMusico = idMusico))
                }
                cont.resume(list)
            },
            Response.ErrorListener {
                cont.resumeWithException(it)
            }))
    }

    private fun getRequest(path:String, responseListener: Response.Listener<String>, errorListener: Response.ErrorListener): StringRequest {
        return StringRequest(Request.Method.GET, URL_API+path, responseListener,errorListener)
    }


}