package co.edu.uniandes.fourbidden.vinilos.vista.fragmentos

import android.R
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.edu.uniandes.fourbidden.vinilos.databinding.AlbumMusicoBinding
import co.edu.uniandes.fourbidden.vinilos.databinding.DetalleMusicoBinding
import co.edu.uniandes.fourbidden.vinilos.modelo.Album
import co.edu.uniandes.fourbidden.vinilos.modelo.Musico
import co.edu.uniandes.fourbidden.vinilos.vista.adapter.AlbumsAdapter
import co.edu.uniandes.fourbidden.vinilos.vista.adapter.TrackAdapter
import co.edu.uniandes.fourbidden.vinilos.vistamodelo.DetalleAlbumViewModel
import co.edu.uniandes.fourbidden.vinilos.vistamodelo.DetalleMusicoViewModel
import com.squareup.picasso.Picasso

class DetalleMusicoFragment : Fragment() {

    private var _binding: DetalleMusicoBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: DetalleMusicoViewModel
    private lateinit var recyclerView: RecyclerView
    private var _bindingT: AlbumMusicoBinding? = null
    private val bindingT get() = _bindingT!!

    private var viewModelAdapter: AlbumsAdapter? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _bindingT = AlbumMusicoBinding.inflate(inflater, container, false)
        viewModelAdapter = AlbumsAdapter()
        _binding = DetalleMusicoBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = binding.albumRv
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager
        recyclerView.adapter = viewModelAdapter
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }

        val args: DetalleMusicoFragmentArgs by navArgs()

        viewModel = ViewModelProvider(this, DetalleMusicoViewModel.Factory(activity.application, args.musicoId)).get(
            DetalleMusicoViewModel::class.java)

        viewModel.musico.observe(viewLifecycleOwner, Observer<Musico> {
            binding.musico = it

            Picasso.get().load(binding.musico?.image).into(binding.cover)
            //binding.tracksRv = it.tracks
            viewModelAdapter!!.albums =it.albums
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