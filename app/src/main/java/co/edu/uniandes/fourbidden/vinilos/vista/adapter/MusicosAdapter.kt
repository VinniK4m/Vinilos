package co.edu.uniandes.fourbidden.vinilos.vista.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.LayoutRes
import androidx.core.net.toUri
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import co.edu.uniandes.fourbidden.vinilos.R
import co.edu.uniandes.fourbidden.vinilos.databinding.ItemMusicoBinding
import co.edu.uniandes.fourbidden.vinilos.modelo.Album
import co.edu.uniandes.fourbidden.vinilos.modelo.Musico
import co.edu.uniandes.fourbidden.vinilos.vista.fragmentos.AlbumFragmentDirections
import co.edu.uniandes.fourbidden.vinilos.vista.fragmentos.MusicoFragmentDirections
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
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
        holder.bind(musicos[position])
        holder.viewDataBinding.root.setOnClickListener {
            val action = MusicoFragmentDirections.actionMusicoFragmentToFragmentDetalleMusico(musicos[position].id)
            // Navigate using that action
            holder.viewDataBinding.root.findNavController().navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return musicos.size
    }

    class MusicoViewHolder(val viewDataBinding: ItemMusicoBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {

        fun bind(musico: Musico) {
            Glide.with(itemView)
                .load(musico.image.toUri().buildUpon().scheme("https").build())
                .apply(
                    RequestOptions()
                    //.placeholder(R.drawable.loading_animation)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .error(R.drawable.ic_broken_image))
                .into(viewDataBinding.musicoCover)
        }


        companion object {
            @LayoutRes
            val LAYOUT = R.layout.item_musico
        }
    }

}
