package co.edu.uniandes.fourbidden.vinilos.vista.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import co.edu.uniandes.fourbidden.vinilos.R
import co.edu.uniandes.fourbidden.vinilos.databinding.AlbumColeccionistaBinding
import co.edu.uniandes.fourbidden.vinilos.databinding.AlbumMusicoBinding
import co.edu.uniandes.fourbidden.vinilos.modelo.Album

class AlbumColeccionistaAdapter: RecyclerView.Adapter<AlbumColeccionistaAdapter.AlbumColeccionistaViewHolder>() {
    var albums :List<Album> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumColeccionistaViewHolder {
        val withDataBinding: AlbumColeccionistaBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            AlbumColeccionistaViewHolder.LAYOUT,
            parent,
            false)
        return AlbumColeccionistaViewHolder(withDataBinding)
    }

    override fun onBindViewHolder(holder: AlbumColeccionistaViewHolder, position: Int) {

        holder.viewDataBinding.also {
            it.album  = albums[position]

        }

    }

    override fun getItemCount(): Int {
        return albums.size
    }
    class AlbumColeccionistaViewHolder(val viewDataBinding: AlbumColeccionistaBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {

        companion object {
            @LayoutRes
            val LAYOUT = R.layout.album_coleccionista
        }
    }
}