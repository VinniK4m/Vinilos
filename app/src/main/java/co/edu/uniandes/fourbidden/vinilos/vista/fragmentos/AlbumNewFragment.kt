package co.edu.uniandes.fourbidden.vinilos.vista.fragmentos

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import co.edu.uniandes.fourbidden.vinilos.databinding.FragmentAlbumNewBinding
import co.edu.uniandes.fourbidden.vinilos.vista.adapter.AlbumsAdapter
import co.edu.uniandes.fourbidden.vinilos.vistamodelo.AlbumViewModel
import com.android.volley.Response
import com.google.android.material.textfield.TextInputEditText
import org.json.JSONObject


class AlbumNewFragment : Fragment() {
    private var _binding: FragmentAlbumNewBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: AlbumViewModel
    private var viewModelAdapter: AlbumsAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAlbumNewBinding.inflate(inflater, container, false)
        val view = binding.root
        viewModelAdapter = AlbumsAdapter()
        return view
    }



    @RequiresApi(Build.VERSION_CODES.O)
    fun createdFormularioAlbum() {

        val btCrearAlbum: Button = binding.createAlbum
        //val postResultTextView : TextView = findViewById(R.id.post_result_text)
        btCrearAlbum.setOnClickListener {
            val nameTxt : TextInputEditText = binding.formAlbumName
            val coverTxt : TextInputEditText = binding.formAlbumCover
            val releasedateTxt : TextInputEditText = binding.formAlbumDate
            val descriptionTxt : TextInputEditText = binding.formAlbumDescr
            val genreTxt : TextInputEditText = binding.formAlbumGenre
            val recordlabelTxt : TextInputEditText = binding.formAlbumRecordlabel
            val postParams = mapOf<String, Any>(
                "name" to nameTxt.text.toString(),
                "cover" to coverTxt.text.toString(),
                "releasedate" to releasedateTxt.text.toString(),
                "description" to descriptionTxt.text.toString(),
                "genre" to genreTxt.text.toString(),
                "recordlabel" to recordlabelTxt.text.toString()
            )
            viewModel?.crearAlbum(JSONObject(postParams),
                { response ->
                    Log.d("prueba","")
                },
                {
                    Log.d("prueba","")
                }
            )
        }

    }



    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val btCrearAlbum: Button = binding.createAlbum
        //val postResultTextView : TextView = findViewById(R.id.post_result_text)
        btCrearAlbum.setOnClickListener {
            val nameTxt : TextInputEditText = binding.formAlbumName
            val coverTxt : TextInputEditText = binding.formAlbumCover
            val releasedateTxt : TextInputEditText = binding.formAlbumDate
            val descriptionTxt : TextInputEditText = binding.formAlbumDescr
            val genreTxt : TextInputEditText = binding.formAlbumGenre
            val recordlabelTxt : TextInputEditText = binding.formAlbumRecordlabel
            val postParams = mapOf<String, Any>(
                "name" to nameTxt.text.toString(),
                "cover" to coverTxt.text.toString(),
                "releasedate" to releasedateTxt.text.toString(),
                "description" to descriptionTxt.text.toString(),
                "genre" to genreTxt.text.toString(),
                "recordlabel" to recordlabelTxt.text.toString()
            )
            viewModel?.crearAlbum(JSONObject(postParams),
                 { response ->
                   Log.d("prueba","")
                },
                 {
                    Log.d("prueba","")
                }
            )
        }

    }

}