package co.edu.uniandes.fourbidden.vinilos.vista.fragmentos

import android.content.Intent
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
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.edu.uniandes.fourbidden.vinilos.R
import co.edu.uniandes.fourbidden.vinilos.databinding.FragmentAlbumBinding
import co.edu.uniandes.fourbidden.vinilos.modelo.Album
import co.edu.uniandes.fourbidden.vinilos.vista.CrearAlbumActivity
import co.edu.uniandes.fourbidden.vinilos.vista.adapter.AlbumsAdapter
import co.edu.uniandes.fourbidden.vinilos.vistamodelo.AlbumViewModel

/**
 * Objeto para visualizar los albumes.
 */
class AlbumFragment : Fragment() {

    private var _binding: FragmentAlbumBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: AlbumViewModel
    private var viewModelAdapter: AlbumsAdapter? = null
    var button: Button? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAlbumBinding.inflate(inflater, container, false)
        val view = binding.root
        viewModelAdapter = AlbumsAdapter()



        //var myView = inflater.inflate(R.layout.fragment_album_new, container, false)
        button = _binding!!.btCrearAlbum
        button!!.setOnClickListener {
            Log.d("","en el action del boton crear album..................")
            val intent = Intent (getActivity(), CrearAlbumActivity::class.java)
            getActivity()?.startActivity(intent)

        }




        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = binding.albumsRv
        recyclerView.layoutManager = GridLayoutManager(context, 3)//LinearLayoutManager(context)
        recyclerView.layoutManager
        recyclerView.adapter = viewModelAdapter






/*
        val btCrearAlbum= _binding?.btCrearAlbum
        btCrearAlbum?.setOnClickListener {
            val activityCrearAlbum = Intent(this, CrearAlbumActivity::class.java)

        }

*/

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }

        viewModel = ViewModelProvider(this, AlbumViewModel.Factory(activity.application, 0)).get(
            AlbumViewModel::class.java)


        viewModel.albums.observe(viewLifecycleOwner, Observer<List<Album>> {

            it.apply {
                viewModelAdapter!!.albums = this
            }
        })
        viewModel.eventNetworkError.observe(viewLifecycleOwner, Observer<Boolean> { isNetworkError ->
            if (isNetworkError) onNetworkError()
        })
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun onNetworkError() {
        if(!viewModel.isNetworkErrorShown.value!!) {
            Toast.makeText(activity, "Network Error", Toast.LENGTH_LONG).show()
            viewModel.onNetworkErrorShown()
        }
    }
}