package co.edu.uniandes.fourbidden.vinilos.modelo.servicio

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import co.edu.uniandes.fourbidden.vinilos.modelo.Album
import co.edu.uniandes.fourbidden.vinilos.modelo.Track
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.GsonBuilder
import org.json.JSONArray
import org.json.JSONObject
import java.lang.NullPointerException
import java.time.LocalDate
import java.time.LocalDate.parse

@Suppress("RedundantSamConstructor")
class ServiceAdapter constructor(context: Context) {
    companion object{
        const val URL_API= "https://back-vinyls-populated.herokuapp.com/"
        var instance: ServiceAdapter? = null
        fun getInstance(context: Context) =
            instance ?: synchronized(this) {
                instance ?: ServiceAdapter(context).also {
                    instance = it
                }
            }
    }
    private val requestQueue: RequestQueue by lazy {
        Volley.newRequestQueue(context.applicationContext)
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun getAlbums(onComplete:(resp:List<Album>)->Unit, onError: (error: VolleyError)->Unit){
        requestQueue.add(getRequest("albums",
            Response.Listener<String> { response ->
                val resp = JSONArray(response)
                val list = mutableListOf<Album>()
                for (i in 0 until resp.length()) {
                    val item = resp.getJSONObject(i)
                    val fecha : String =  item!!.getString("releaseDate").substringBefore(delimiter = "T", missingDelimiterValue = "2000-01-01")

                    var releaseDate : LocalDate = parse(fecha)
                    val listTrack = mutableListOf<Track>()



                    list.add(i, Album(id = item.getString("id"),name = item.getString("name"), cover = item.getString("cover"), recordLabel = item.getString("recordLabel"), releaseDate = releaseDate, genre = item.getString("genre"), description = item.getString("description"), tracks = listTrack))
                }
                onComplete(list)
            },
            Response.ErrorListener {
                onError(it)
            }))
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun getAlbum(albumId: Int, onComplete: (resp: Album) -> Unit, onError: (error: VolleyError) -> Unit){
        requestQueue.add(getRequest("albums/$albumId",
            Response.Listener<String> { response ->
                val resp = JSONObject(response)
                val fecha : String =  resp!!.getString("releaseDate").substringBefore(delimiter = "T", missingDelimiterValue = "2000-01-01")

                var releaseDate = parse(fecha)
                val listTrack = mutableListOf<Track>()
                val tracks = resp.getJSONArray("tracks")
                for (j in 0 until tracks.length()) {
                    val itemtrack = tracks.getJSONObject(j)
                    listTrack.add(j, Track(id = itemtrack.getInt("id"), name = itemtrack.getString("name"),duration = itemtrack.getString("duration")))
                }

                val album = Album(id = resp.getString("id"),
                    name = resp.getString("name"),
                    cover = resp.getString("cover"),
                    recordLabel = resp.getString("recordLabel"),
                    releaseDate = releaseDate,
                    genre = resp.getString("genre"),
                    description = resp.getString("description"),
                    tracks = listTrack,
                )

                onComplete(album)
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