package co.edu.uniandes.fourbidden.vinilos.vista.fragmentos

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import co.edu.uniandes.fourbidden.vinilos.databinding.FragmentAlbumNewBinding
import co.edu.uniandes.fourbidden.vinilos.vista.adapter.AlbumsAdapter
import co.edu.uniandes.fourbidden.vinilos.vistamodelo.AlbumViewModel
import co.edu.uniandes.fourbidden.vinilos.vistamodelo.CrearAlbumViewModel
import co.edu.uniandes.fourbidden.vinilos.vistamodelo.DetalleAlbumViewModel
import co.edu.uniandes.fourbidden.vinilos.vistamodelo.TrackViewModel
import com.android.volley.Response
import com.google.android.material.textfield.TextInputEditText
import org.json.JSONObject
import android.view.Gravity

import android.text.Html
import android.R
import android.app.AlertDialog
import android.content.DialogInterface
import android.text.TextUtils
import android.widget.EditText


class AlbumNewFragment : Fragment() {
    private var _binding: FragmentAlbumNewBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: CrearAlbumViewModel
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
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }

    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
       val btCrearAlbum: Button = binding.createAlbum
        btCrearAlbum.setOnClickListener {
            val nameTxt : TextInputEditText = binding.formAlbumName
            val coverTxt : TextInputEditText = binding.formAlbumCover
            /*formato 1984-08-01T00:00:00-05:00*/
            var releasedateTxt : TextInputEditText = binding.formAlbumDate
            val descriptionTxt : TextInputEditText = binding.formAlbumDescr
            val genreTxt : TextInputEditText = binding.formAlbumGenre
            val recordlabelTxt : TextInputEditText = binding.formAlbumRecordlabel

            if (validarCampos(nameTxt, coverTxt, releasedateTxt,descriptionTxt, genreTxt , recordlabelTxt )){
                val postParams = mapOf<String, Any>(
                    "name" to nameTxt.text.toString(),
                    "cover" to coverTxt.text.toString(),
                    "releaseDate" to releasedateTxt.text.toString()+"T00:00:00-05:00",
                    "description" to descriptionTxt.text.toString(),
                    "genre" to genreTxt.text.toString(),
                    "recordLabel" to recordlabelTxt.text.toString()
                )

                val activity = requireNotNull(this.activity) {
                    "You can only access the viewModel after onActivityCreated()"
                }
                viewModel = ViewModelProvider(this, CrearAlbumViewModel.Factory(activity.application, JSONObject(postParams))).get(
                    CrearAlbumViewModel::class.java)
            }




    }

    }
    private fun validarCampos(nameTxt: TextInputEditText, coverTxt: TextInputEditText,
                              releasedateTxt: TextInputEditText,descriptionTxt: TextInputEditText,
                              genreTxt: TextInputEditText , recordlabelTxt: TextInputEditText): Boolean {

        if (TextUtils.isEmpty(nameTxt.getText().toString())) {
            mensajeToast("El nombre esta vacío, intente de nuevo");
            return false;
        }
        if (TextUtils.isEmpty(coverTxt.getText().toString())) {
            mensajeToast("La carátula esta vacía, intente de nuevo");
            return false;
        }
        if (TextUtils.isEmpty(releasedateTxt.getText().toString())) {
            mensajeToast("La fecha esta vacía, intente de nuevo");
            return false;
        }
        if (TextUtils.isEmpty(descriptionTxt.getText().toString())) {
            mensajeToast("La descripción esta vacía, intente de nuevo");
            return false;
        }
        if (TextUtils.isEmpty(genreTxt.getText().toString())) {
            mensajeToast("El género esta vacío, intente de nuevo");
            return false;
        }
        if (TextUtils.isEmpty(recordlabelTxt.getText().toString())) {
            mensajeToast("El género esta vacío, intente de nuevo");
            return false;
        }
        return true
    }
    var alertDialog: AlertDialog? = null
    private fun mensajeToast(mensaje : String) {
        //val mensajeT =Toast.makeText(getActivity(), mensaje, Toast.LENGTH_SHORT);
        //mensajeT.setGravity(Gravity.CENTER,0,0);
        //mensajeT.show();
        val alertDialogBuilder = AlertDialog.Builder(getActivity())
        alertDialogBuilder.setTitle("Atención")
        alertDialogBuilder.setMessage(mensaje)
        //alertDialogBuilder.setPositiveButton("Yes", { dialogInterface: DialogInterface, i: Int -> })
        alertDialogBuilder.setNegativeButton("Continuar", { dialogInterface: DialogInterface, i: Int -> })

        alertDialog = alertDialogBuilder.create()
        alertDialog?.show()

    }


}