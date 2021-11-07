package co.edu.uniandes.fourbidden.vinilos.vista.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import co.edu.uniandes.fourbidden.vinilos.R
import co.edu.uniandes.fourbidden.vinilos.databinding.TrackAlbumBinding
import co.edu.uniandes.fourbidden.vinilos.modelo.Track

class TrackAdapter: RecyclerView.Adapter<TrackAdapter.TrackViewHolder>() {
    var tracks :List<Track> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        val withDataBinding: TrackAlbumBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            TrackViewHolder.LAYOUT,
            parent,
            false)
        return TrackViewHolder(withDataBinding)
    }

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {

        holder.viewDataBinding.also {
            it.track  = tracks[position]

        }

    }

    override fun getItemCount(): Int {
        return tracks.size
    }
    class TrackViewHolder(val viewDataBinding: TrackAlbumBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {

        companion object {
            @LayoutRes
            val LAYOUT = R.layout.track_album
        }
    }
}