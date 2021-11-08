package co.edu.uniandes.fourbidden.vinilos.vista.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import co.edu.uniandes.fourbidden.vinilos.R
import co.edu.uniandes.fourbidden.vinilos.databinding.DetalleAlbumBinding
import co.edu.uniandes.fourbidden.vinilos.databinding.ItemAlbumBinding
import co.edu.uniandes.fourbidden.vinilos.modelo.Album
import co.edu.uniandes.fourbidden.vinilos.modelo.Track
import com.squareup.picasso.Picasso

class DetalleAlbumAdapter: RecyclerView.Adapter<DetalleAlbumAdapter.AlbumViewHolder>() {
    var album :Album ? = null
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        val withDataBinding: DetalleAlbumBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            AlbumViewHolder.LAYOUT,
            parent,
            false)
        return AlbumViewHolder(withDataBinding)
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        holder.viewDataBinding.also {
            it.album  = album
            val imageView = holder.itemView.findViewById<ImageView>(R.id.album_cover)
            Picasso.get().load(album?.cover).into(imageView)

        }
    }


    override fun getItemCount(): Int {
        return 1
    }


    class AlbumViewHolder(val viewDataBinding: DetalleAlbumBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {
        companion object {
            @LayoutRes
            val LAYOUT = R.layout.detalle_album
        }
    }



}
