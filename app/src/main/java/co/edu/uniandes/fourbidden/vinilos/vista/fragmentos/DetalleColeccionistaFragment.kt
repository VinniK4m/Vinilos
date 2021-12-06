package co.edu.uniandes.fourbidden.vinilos.vista.fragmentos

import android.os.Build
import android.os.Bundle
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
import co.edu.uniandes.fourbidden.vinilos.databinding.AlbumColeccionistaBinding
import co.edu.uniandes.fourbidden.vinilos.databinding.DetalleColeccionistaBinding
import co.edu.uniandes.fourbidden.vinilos.modelo.Album
import co.edu.uniandes.fourbidden.vinilos.vista.adapter.AlbumColeccionistaAdapter
import co.edu.uniandes.fourbidden.vinilos.vistamodelo.AlbumColeccionistaViewModel
import co.edu.uniandes.fourbidden.vinilos.vistamodelo.DetalleColeccionistaViewModel

class DetalleColeccionistaFragment : Fragment() {

    private var _binding: DetalleColeccionistaBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: DetalleColeccionistaViewModel
    private lateinit var recyclerView: RecyclerView
    private var _bindingT: AlbumColeccionistaBinding? = null


    private var viewModelAdapter: AlbumColeccionistaAdapter? = null
    private lateinit var viewModelA: AlbumColeccionistaViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _bindingT = AlbumColeccionistaBinding.inflate(inflater, container, false)
        viewModelAdapter = AlbumColeccionistaAdapter()
        _binding = DetalleColeccionistaBinding.inflate(inflater, container, false)
        return binding.root
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

        val args: DetalleColeccionistaFragmentArgs by navArgs()

        viewModel = ViewModelProvider(
            this,
            DetalleColeccionistaViewModel.Factory(activity.application, args.coleccionistaId.toString())
        ).get(
            DetalleColeccionistaViewModel::class.java
        )

        viewModel.coleccionista.observe(viewLifecycleOwner, Observer {
            binding.coleccionista = it
        })
        viewModelA = ViewModelProvider(this, AlbumColeccionistaViewModel.Factory(activity.application, args.coleccionistaId)).get(AlbumColeccionistaViewModel::class.java)
        viewModelA.albums.observe(viewLifecycleOwner, Observer<List<Album>> {
            it.apply {
                viewModelAdapter!!.albums = this
            }
        })
        viewModel.eventNetworkError.observe(
            viewLifecycleOwner,
            Observer { isNetworkError ->
                if (isNetworkError) onNetworkError()
            })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun onNetworkError() {
        if (!viewModel.isNetworkErrorShown.value!!) {
            Toast.makeText(activity, "Network Error", Toast.LENGTH_LONG).show()
            viewModel.onNetworkErrorShown()
        }
    }
}