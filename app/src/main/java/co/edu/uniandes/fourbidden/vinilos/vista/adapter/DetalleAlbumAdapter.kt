package co.edu.uniandes.fourbidden.vinilos.vista.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import co.edu.uniandes.fourbidden.vinilos.R
import co.edu.uniandes.fourbidden.vinilos.databinding.ItemAlbumBinding
import co.edu.uniandes.fourbidden.vinilos.modelo.Album

class DetalleAlbumAdapter: RecyclerView.Adapter<DetalleAlbumAdapter.AlbumViewHolder>() {
    var album :Album ? = null
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        val withDataBinding: ItemAlbumBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            AlbumViewHolder.LAYOUT,
            parent,
            false)
        return AlbumViewHolder(withDataBinding)
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        holder.viewDataBinding.also {
            it.album  = album
        }
        /*holder.viewDataBinding.root.setOnClickListener {
            val action = AlbumFragmentDirections.actionAlbumFragmentToCommentFragment(albums[position].id)
            // Navigate using that action
            holder.viewDataBinding.root.findNavController().navigate(action)
        }*/
    }


    override fun getItemCount(): Int {
        return 1
    }


    class AlbumViewHolder(val viewDataBinding: ItemAlbumBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {
        companion object {
            @LayoutRes
            val LAYOUT = R.layout.item_album
        }
    }



}
