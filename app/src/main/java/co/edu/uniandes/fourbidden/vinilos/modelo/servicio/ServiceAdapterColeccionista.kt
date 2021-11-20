package co.edu.uniandes.fourbidden.vinilos.modelo.servicio

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import co.edu.uniandes.fourbidden.vinilos.modelo.Coleccionista
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

@Suppress("RedundantSamConstructor")
class ServiceAdapterColeccionista constructor(context: Context) {
    companion object{
        const val URL_API= "https://back-vinyls-populated.herokuapp.com/"
        var instance: ServiceAdapterColeccionista? = null
        fun getInstance(context: Context) =
            instance ?: synchronized(this) {
                instance ?: ServiceAdapterColeccionista(context).also {
                    instance = it
                }
            }
    }
    private val requestQueue: RequestQueue by lazy {
        Volley.newRequestQueue(context.applicationContext)
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun getColeccionistas(onComplete:(resp:List<Coleccionista>)->Unit, onError: (error: VolleyError)->Unit){
        requestQueue.add(getRequest("collectors",
            Response.Listener<String> { response ->
                val resp = JSONArray(response)
                val list = mutableListOf<Coleccionista>()
                for (i in 0 until resp.length()) {
                    val item = resp.getJSONObject(i)
                    list.add(i, Coleccionista(id = item.getInt("id"),name = item.getString("name"), email = item.getString("email")))
                }
                onComplete(list)
            },
            Response.ErrorListener {
                onError(it)
            }))
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getColeccionista(ColeccionistaId: Int, onComplete: (resp: Coleccionista) -> Unit, onError: (error: VolleyError) -> Unit){
        requestQueue.add(getRequest("collectors/$ColeccionistaId",
            Response.Listener<String> { response ->
                val resp = JSONObject(response)
                val coleccionista = Coleccionista(id = resp.getInt("id"),name = resp.getString("name"), email = resp.getString("email"))
                onComplete(coleccionista)
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