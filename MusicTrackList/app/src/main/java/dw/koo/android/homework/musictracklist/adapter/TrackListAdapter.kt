package dw.koo.android.homework.musictracklist.adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dw.koo.android.homework.musictracklist.ModuleSet
import dw.koo.android.homework.musictracklist.R
import dw.koo.android.homework.musictracklist.data.Track
import dw.koo.android.homework.musictracklist.utils.DebugLog
import java.util.*

class TrackListAdapter(
        private val mContext: Context,
        private val mOnHolderListener: IOnHolderListener) : RecyclerView.Adapter<TrackListAdapter.ViewHolder>() {

    private val mTrackData: ArrayList<Track>?

    fun setTrackData(trackData: ArrayList<Track>) {
        DebugLog.get().i(TAG, "setTrackData:")
        mTrackData?.let {
            DebugLog.get().d(TAG, "setTrackData: Set track data")
            it.clear()
            it.addAll(trackData)
            notifyDataSetChanged()
        }
    }

    fun removeTrackData(position: Int) {
        mTrackData!!.removeAt(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        DebugLog.get().i(TAG, "onCreateViewHolder:")
        val inflater = parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.track_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val trackData = mTrackData!![position]

        holder.trackNameTextView.text = trackData.trackName
        holder.collectionNameTextView.text = trackData.collectionName
        holder.artistNameTextView.text = trackData.artistName

        val trackDbHelper = ModuleSet.get()!!.trackDbHelper
        holder.favoriteImageView.setImageResource(
                if (trackDbHelper!!.hasMatchedTrack(trackData.trackId!!))
                    R.drawable.btn_favorite_selected_n else R.drawable.btn_favorite_selected_f)

        val trackImgUri = Uri.parse(trackData.artworkUrl60)
        Glide.with(mContext).load(trackImgUri)
                .error(R.drawable.ic_launcher_background).fitCenter().into(holder.trackImageView)
    }

    override fun getItemCount(): Int {
        return mTrackData?.size ?: 0
    }

    inner class ViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var rootView: View
        var trackImageView: ImageView
        var trackNameTextView: TextView
        var collectionNameTextView: TextView
        var artistNameTextView: TextView
        var favoriteImageView: ImageView

        init {
            itemView.setOnClickListener { v: View? ->
                val clickedPosition = adapterPosition
                if (clickedPosition != RecyclerView.NO_POSITION) {
                    val track = mTrackData!![clickedPosition]
                    ModuleSet.get()!!.trackDbHelper!!.insertColumn(track)
                    DebugLog.get().d(TAG, "Temporary Log message, ViewHolder: $track")
                    mOnHolderListener.onHolderClicked(clickedPosition, itemView)
                }
            }
            rootView = itemView
            trackImageView = itemView.findViewById(R.id.track_image)
            trackNameTextView = itemView.findViewById(R.id.track_name)
            collectionNameTextView = itemView.findViewById(R.id.collection_name)
            artistNameTextView = itemView.findViewById(R.id.artist_name)
            favoriteImageView = itemView.findViewById(R.id.favorite_image)
        }
    }

    companion object {
        private val TAG = TrackListAdapter::class.java.simpleName
    }

    init {
        mTrackData = ArrayList()
    }
}