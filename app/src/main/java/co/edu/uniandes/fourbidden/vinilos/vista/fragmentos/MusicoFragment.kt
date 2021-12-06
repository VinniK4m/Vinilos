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
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.edu.uniandes.fourbidden.vinilos.databinding.FragmentMusicoBinding
import co.edu.uniandes.fourbidden.vinilos.vista.adapter.MusicosAdapter
import co.edu.uniandes.fourbidden.vinilos.vistamodelo.MusicoViewModel

/**
 * Objeto para visualizar los musicos.
 */
class MusicoFragment : Fragment() {

    private var _binding: FragmentMusicoBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: MusicoViewModel
    private var viewModelAdapter: MusicosAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMusicoBinding.inflate(inflater, container, false)
        val view = binding.root
        viewModelAdapter = MusicosAdapter()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = binding.musicosRv
        recyclerView.layoutManager = GridLayoutManager(context, 3)//LinearLayoutManager(context)
        recyclerView.layoutManager
        recyclerView.adapter = viewModelAdapter
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }

        viewModel = ViewModelProvider(this, MusicoViewModel.Factory(activity.application)).get(
            MusicoViewModel::class.java)

        viewModel.musicos.observe(viewLifecycleOwner, Observer{

            it.apply {
                viewModelAdapter!!.musicos = this
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