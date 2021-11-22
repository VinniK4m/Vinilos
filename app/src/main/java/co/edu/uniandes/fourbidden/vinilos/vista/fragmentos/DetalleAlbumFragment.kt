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
import co.edu.uniandes.fourbidden.vinilos.databinding.DetalleAlbumBinding
import co.edu.uniandes.fourbidden.vinilos.databinding.TrackAlbumBinding
import co.edu.uniandes.fourbidden.vinilos.modelo.Track
import co.edu.uniandes.fourbidden.vinilos.vista.adapter.TrackAdapter
import co.edu.uniandes.fourbidden.vinilos.vistamodelo.DetalleAlbumViewModel
import com.squareup.picasso.Picasso

class DetalleAlbumFragment : Fragment() {

    private var _binding: DetalleAlbumBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: DetalleAlbumViewModel
    private lateinit var recyclerView: RecyclerView
    private var _bindingT: TrackAlbumBinding? = null
    private val bindingT get() = _bindingT!!

    private var viewModelAdapter: TrackAdapter? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _bindingT = TrackAlbumBinding.inflate(inflater, container, false)
        viewModelAdapter = TrackAdapter()
        _binding = DetalleAlbumBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = binding.trackRv
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

        val args: DetalleAlbumFragmentArgs by navArgs()

        viewModel = ViewModelProvider(this, DetalleAlbumViewModel.Factory(activity.application, args.albumId)).get(
            DetalleAlbumViewModel::class.java)

        viewModel.album.observe(viewLifecycleOwner, Observer {
            binding.album = it

            Picasso.get().load(binding.album?.cover).into(binding.cover)
            //binding.tracksRv = it.tracks
            viewModelAdapter!!.tracks =it.tracks
            Log.d("lista", it.tracks.toString())
            val esta = Track(id = 10, name = "la listya ",duration = "5:20")
                bindingT.track = esta




        })
        viewModel.eventNetworkError.observe(viewLifecycleOwner, Observer { isNetworkError ->
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