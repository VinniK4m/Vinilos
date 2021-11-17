package co.edu.uniandes.fourbidden.vinilos.vista.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import co.edu.uniandes.fourbidden.vinilos.R
import co.edu.uniandes.fourbidden.vinilos.databinding.DetalleMusicoBinding
import co.edu.uniandes.fourbidden.vinilos.modelo.Album
import co.edu.uniandes.fourbidden.vinilos.modelo.Musico

import com.squareup.picasso.Picasso

class DetalleMusicoAdapter: RecyclerView.Adapter<DetalleMusicoAdapter.MusicoViewHolder>() {
    var musico :Musico ? = null
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicoViewHolder {
        val withDataBinding: DetalleMusicoBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            MusicoViewHolder.LAYOUT,
            parent,
            false)
        return MusicoViewHolder(withDataBinding)
    }

    override fun onBindViewHolder(holder: MusicoViewHolder, position: Int) {
        holder.viewDataBinding.also {
            it.musico  = musico
            val imageView = holder.itemView.findViewById<ImageView>(R.id.musico_cover)
            Picasso.get().load(musico?.image).into(imageView)

        }
    }


    override fun getItemCount(): Int {
        return 1
    }


    class MusicoViewHolder(val viewDataBinding: DetalleMusicoBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {
        companion object {
            @LayoutRes
            val LAYOUT = R.layout.detalle_musico
        }
    }



}
