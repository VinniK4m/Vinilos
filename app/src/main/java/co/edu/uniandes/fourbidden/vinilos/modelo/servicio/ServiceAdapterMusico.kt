package co.edu.uniandes.fourbidden.vinilos.modelo.servicio

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import co.edu.uniandes.fourbidden.vinilos.modelo.Album
import co.edu.uniandes.fourbidden.vinilos.modelo.Musico
import co.edu.uniandes.fourbidden.vinilos.modelo.Track
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONObject
import java.time.LocalDate
import java.time.LocalDate.parse
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

@Suppress("RedundantSamConstructor")
class ServiceAdapterMusico constructor(context: Context) {
    companion object{
        const val URL_API= "https://back-vinyls-populated.herokuapp.com/"
        var instance: ServiceAdapterMusico? = null
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
                for (i in 0 until resp.length()) {
                    val item = resp.getJSONObject(i)
                    val fecha : String =  item!!.getString("birthDate").substringBefore(delimiter = "T", missingDelimiterValue = "2000-01-01")
                    var releaseDate : LocalDate = parse(fecha)
                    val listAlbums = mutableListOf<Album>()
                    list.add(i, Musico(id = item.getString("id"),name = item.getString("name"), image = item.getString("image"),
                        birthDate = releaseDate, description = item.getString("description"), albums = listAlbums))
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
                val fecha : String =  resp!!.getString("birthDate").substringBefore(delimiter = "T", missingDelimiterValue = "2000-01-01")
                var releaseDate = parse(fecha)
                val listAlbums = mutableListOf<Album>()
                val listTracks = mutableListOf<Track>()
                val albums = resp.getJSONArray("albums")
                for (j in 0 until albums.length()) {
                    val itemAlbum = albums.getJSONObject(j)
                    listAlbums.add(j, Album(id = itemAlbum.getString("id"),
                        name = itemAlbum.getString("name"),
                        cover = itemAlbum.getString("cover"),
                        recordLabel = itemAlbum.getString("recordLabel"),
                        releaseDate = releaseDate,
                        genre = itemAlbum.getString("genre"),
                        description = itemAlbum.getString("description"), tracks = listTracks))
                }
                val musico = Musico(id = resp.getString("id"),name = resp.getString("name"), image = resp.getString("image"),
                    birthDate = releaseDate, description = resp.getString("description"), albums = listAlbums)
                onComplete(musico)
            },
            Response.ErrorListener {
                onError(it)
            }))
    }

    private fun getRequest(path:String, responseListener: Response.Listener<String>, errorListener: Response.ErrorListener): StringRequest {
        return StringRequest(Request.Method.GET, URL_API+path, responseListener,errorListener)
    }

    private fun postRequest(path: String, body: JSONObject, responseListener: Response.Listener<JSONObject>, errorListener: Response.ErrorListener ):JsonObjectRequest{
        return  JsonObjectRequest(Request.Method.POST, URL_API+path, body, responseListener, errorListener)
    }

    private fun putRequest(path: String, body: JSONObject, responseListener: Response.Listener<JSONObject>, errorListener: Response.ErrorListener ):JsonObjectRequest{
        return  JsonObjectRequest(Request.Method.PUT, URL_API+path, body, responseListener, errorListener)
    }
}