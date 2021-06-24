package dw.koo.android.homework.musictracklist.ui.fragment

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dw.koo.android.homework.musictracklist.R
import dw.koo.android.homework.musictracklist.adapter.IOnHolderListener
import dw.koo.android.homework.musictracklist.adapter.TrackListAdapter
import dw.koo.android.homework.musictracklist.data.FragmentType
import dw.koo.android.homework.musictracklist.data.Track
import dw.koo.android.homework.musictracklist.utils.DebugLog.Companion.get
import java.util.*


class FavoriteTrackListFragment : CustomFragment(), IOnHolderListener {
    private var mMainTrackListView: RecyclerView? = null
    private var mTrackListAdapter: TrackListAdapter? = null
    override val type: FragmentType = FragmentType.FAVORITE_TRACK_LIST_FRAGMENT

    override fun setTrackData(trackData: ArrayList<Track>?) {
        super.setTrackData(trackData)
        mTrackListAdapter?.let { it.setTrackData(trackData!!) }
    }

    override fun initMainUi(): View {
        mMainTrackListView = mRootView!!.findViewById(R.id.main_track_recyclerview)
        mTrackListAdapter = TrackListAdapter(context!!, this)
        mMainTrackListView?.let {
            it.setLayoutManager(LinearLayoutManager(mActivity))
            it.setAdapter(mTrackListAdapter)
        }
        return mRootView!!
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        get().i(TAG, "onCreateView:")
        mRootView = inflater.inflate(R.layout.track_list, container, false)
        return initMainUi()
    }

    override fun onHolderClicked(position: Int, view: View?) {
        mTrackListAdapter?.let {
            it.removeTrackData(position)
            it.notifyDataSetChanged()
        }
    }

    companion object {
        private val TAG = FavoriteTrackListFragment::class.java.simpleName
    }
}