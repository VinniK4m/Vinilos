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
import co.edu.uniandes.fourbidden.vinilos.databinding.FragmentTrackNewBinding
import co.edu.uniandes.fourbidden.vinilos.vistamodelo.*


class TrackNewFragment : Fragment() {
    private var _binding: FragmentTrackNewBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: CrearTrackViewModel
    private var viewModelAdapter: AlbumsAdapter? = null
    private var albumIdG: Int = 1


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val albumId = activity?.intent?.extras?.get("albumId")
        Log.d("albumId", albumId.toString())
        albumIdG = albumId.toString().toInt()
        _binding = FragmentTrackNewBinding.inflate(inflater, container, false)
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
        val btCrearTrack: Button = binding.createTrack
        btCrearTrack.setOnClickListener {
            val nameTxt : TextInputEditText = binding.formTrackName
            val duracionTxt : TextInputEditText = binding.formTrackDuracion

            if (validarCampos(nameTxt, duracionTxt)){
                val postParams = mapOf<String, Any>(
                    "name" to nameTxt.text.toString(),
                    "duration" to duracionTxt.text.toString(),
                )

                val activity = requireNotNull(this.activity) {
                    "You can only access the viewModel after onActivityCreated()"
                }
                viewModel = ViewModelProvider(this, CrearTrackViewModel.Factory(activity.application,albumIdG,JSONObject(postParams))).get(
                    CrearTrackViewModel::class.java)

                mensajeToast("Canción creada con éxito");
                nameTxt.setText("")
                duracionTxt.setText("")
            }

        }
    }


    private fun validarCampos(nameTxt: TextInputEditText, duracionTxt: TextInputEditText): Boolean {

        if (TextUtils.isEmpty(nameTxt.getText().toString())) {
            mensajeToast("El nombre esta vacío, intente de nuevo");
            return false;
        }
        if (TextUtils.isEmpty(duracionTxt.getText().toString())) {
            mensajeToast("La duración esta vacía, intente de nuevo");
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