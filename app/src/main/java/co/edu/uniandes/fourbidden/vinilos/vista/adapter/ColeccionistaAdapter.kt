package co.edu.uniandes.fourbidden.vinilos.vista.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import co.edu.uniandes.fourbidden.vinilos.R
import co.edu.uniandes.fourbidden.vinilos.databinding.ItemColeccionistaBinding
import co.edu.uniandes.fourbidden.vinilos.modelo.Coleccionista
import co.edu.uniandes.fourbidden.vinilos.vista.fragmentos.ColeccionistaFragmentDirections

class ColeccionistaAdapter: RecyclerView.Adapter<ColeccionistaAdapter.ColeccionistaViewHolder>() {
    var coleccionistas :List<Coleccionista> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColeccionistaViewHolder {
        val withDataBinding: ItemColeccionistaBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            ColeccionistaViewHolder.LAYOUT,
            parent,
            false)
        return ColeccionistaViewHolder(withDataBinding)
    }

    override fun onBindViewHolder(holder: ColeccionistaViewHolder, position: Int) {
        holder.viewDataBinding.also {
            it.coleccionista = coleccionistas[position]


        }
        holder.viewDataBinding.root.setOnClickListener {
            val action = ColeccionistaFragmentDirections.actionColeccionistaFragmentToFragmentDetalleColeccionista(coleccionistas[position].id)
            holder.viewDataBinding.root.findNavController().navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return coleccionistas.size
    }

    class ColeccionistaViewHolder(val viewDataBinding: ItemColeccionistaBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {


        companion object {
            @LayoutRes
            val LAYOUT = R.layout.item_coleccionista
        }
    }

}
