package co.edu.uniandes.fourbidden.vinilos.vista.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import co.edu.uniandes.fourbidden.vinilos.R
import co.edu.uniandes.fourbidden.vinilos.databinding.ItemMusicoBinding
import co.edu.uniandes.fourbidden.vinilos.modelo.Musico
import com.squareup.picasso.Picasso

class MusicosAdapter: RecyclerView.Adapter<MusicosAdapter.MusicoViewHolder>() {
    var musicos :List<Musico> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicoViewHolder {
        val withDataBinding: ItemMusicoBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            MusicoViewHolder.LAYOUT,
            parent,
            false)
        return MusicoViewHolder(withDataBinding)
    }

    override fun onBindViewHolder(holder: MusicoViewHolder, position: Int) {
        holder.viewDataBinding.also {
            it.musico = musicos[position]
            val imageView = holder.itemView.findViewById<ImageView>(R.id.musico_cover)
            Picasso.get().load(musicos[position].image).into(imageView)

        }
    }

    override fun getItemCount(): Int {
        return musicos.size
    }

    class MusicoViewHolder(val viewDataBinding: ItemMusicoBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {

        companion object {
            @LayoutRes
            val LAYOUT = R.layout.item_musico
        }
    }

}
