package co.edu.uniandes.fourbidden.vinilos.vista.fragmentos

import android.content.Intent
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
import co.edu.uniandes.fourbidden.vinilos.vista.CrearTrackActivity
import co.edu.uniandes.fourbidden.vinilos.vista.adapter.TrackAdapter
import co.edu.uniandes.fourbidden.vinilos.vistamodelo.DetalleAlbumViewModel
import co.edu.uniandes.fourbidden.vinilos.vistamodelo.TrackViewModel
import com.squareup.picasso.Picasso

class DetalleAlbumFragment : Fragment() {

    private var _binding: DetalleAlbumBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: DetalleAlbumViewModel
    private lateinit var recyclerView: RecyclerView
    private var _bindingT: TrackAlbumBinding? = null
    private val bindingT get() = _bindingT!!

    private lateinit var viewModelT: TrackViewModel
    private var viewModelAdapter: TrackAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _bindingT = TrackAlbumBinding.inflate(inflater, container, false)
        viewModelAdapter = TrackAdapter()
        _binding = DetalleAlbumBinding.inflate(inflater, container, false)
        _binding!!.btCrearTrack.setOnClickListener {
            Log.d("","en el action del boton crear album..................")
            val intent = Intent (getActivity(), CrearTrackActivity::class.java)
            val argsTrack: DetalleAlbumFragmentArgs by navArgs()
            val albumId = argsTrack.albumId.toString()
            intent.putExtra("albumId", albumId)
            startActivity(intent)
        }
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

        viewModel = ViewModelProvider(this, DetalleAlbumViewModel.Factory(activity.application, args.albumId.toString())).get(
            DetalleAlbumViewModel::class.java)

        viewModel.album.observe(viewLifecycleOwner, Observer {
            binding.album = it

            Picasso.get().load(binding.album?.cover).into(binding.cover)
            //binding.tracksRv = it.tracks
            // todo revisar funcionamiento
            /*
            viewModelAdapter!!.tracks =it.tracks
            Log.d("lista", it.tracks.toString())
            val esta = Track(id = 10, name = "la listya ",duration = "5:20")
                bindingT.track = esta
             */


        })
        viewModelT = ViewModelProvider(this, TrackViewModel.Factory(activity.application, args.albumId)).get(TrackViewModel::class.java)
        viewModelT.tracks.observe(viewLifecycleOwner, Observer<List<Track>> {
            it.apply {
                viewModelAdapter!!.tracks = this
            }
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