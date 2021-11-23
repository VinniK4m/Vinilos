package co.edu.uniandes.fourbidden.vinilos.vista.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import co.edu.uniandes.fourbidden.vinilos.R
import co.edu.uniandes.fourbidden.vinilos.databinding.AlbumMusicoBinding
import co.edu.uniandes.fourbidden.vinilos.modelo.Album

class AlbumListAdapter: RecyclerView.Adapter<AlbumListAdapter.AlbumListViewHolder>() {
    var albums :List<Album> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumListViewHolder {
        val withDataBinding: AlbumMusicoBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            AlbumListViewHolder.LAYOUT,
            parent,
            false)
        return AlbumListViewHolder(withDataBinding)
    }

    override fun onBindViewHolder(holder: AlbumListViewHolder, position: Int) {

        holder.viewDataBinding.also {
            it.album  = albums[position]

        }

    }

    override fun getItemCount(): Int {
        return albums.size
    }
    class AlbumListViewHolder(val viewDataBinding: AlbumMusicoBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {

        companion object {
            @LayoutRes
            val LAYOUT = R.layout.album_musico
        }
    }
}