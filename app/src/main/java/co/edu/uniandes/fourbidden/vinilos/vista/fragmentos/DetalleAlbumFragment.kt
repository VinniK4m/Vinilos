package co.edu.uniandes.fourbidden.vinilos.vista.fragmentos

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.edu.uniandes.fourbidden.vinilos.databinding.FragmentAlbumBinding
import co.edu.uniandes.fourbidden.vinilos.modelo.Album
import co.edu.uniandes.fourbidden.vinilos.modelo.servicio.ServiceAdapter
import co.edu.uniandes.fourbidden.vinilos.vista.adapter.AlbumsAdapter
import co.edu.uniandes.fourbidden.vinilos.vista.adapter.DetalleAlbumAdapter
import co.edu.uniandes.fourbidden.vinilos.vistamodelo.AlbumViewModel
import co.edu.uniandes.fourbidden.vinilos.vistamodelo.DetalleAlbumViewModel
import co.edu.uniandes.fourbidden.vinilos.databinding.FragmentDetalleAlbumBinding

class DetalleAlbumFragment : Fragment() {

    private var _binding: FragmentDetalleAlbumBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: DetalleAlbumViewModel
    private var viewModelAdapter: DetalleAlbumAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetalleAlbumBinding.inflate(inflater, container, false)
        val view = binding.root
        viewModelAdapter = DetalleAlbumAdapter()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = binding.albumRv
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = viewModelAdapter
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }

        val args: DetalleAlbumFragmentArgs by navArgs()

        viewModel = ViewModelProvider(this, DetalleAlbumViewModel.Factory(activity.application, args.albumId)).get(
            DetalleAlbumViewModel::class.java)


        viewModel.album.observe(viewLifecycleOwner, Observer<Album> {

            it.apply {
                viewModelAdapter!!.album = this
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