package dw.koo.android.homework.musictracklist.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dw.koo.android.homework.musictracklist.R
import dw.koo.android.homework.musictracklist.adapter.IOnHolderListener
import dw.koo.android.homework.musictracklist.adapter.TrackListAdapter
import dw.koo.android.homework.musictracklist.data.FragmentType
import dw.koo.android.homework.musictracklist.data.Track
import dw.koo.android.homework.musictracklist.utils.DebugLog
import java.util.*


class MainFragment : CustomFragment(), IOnHolderListener {
    private var mMainTrackListView: RecyclerView? = null
    private var mTrackListAdapter: TrackListAdapter? = null
    override val type: FragmentType = FragmentType.MAIN_FRAGMENT

    override fun setTrackData(trackData: ArrayList<Track>?) {
        super.setTrackData(trackData)
        mTrackListAdapter!!.setTrackData(trackData!!)
    }

    override fun initMainUi(): View {
        mMainTrackListView = mRootView!!.findViewById(R.id.main_track_recyclerview)
        mTrackListAdapter = TrackListAdapter(context!!, this)
        mMainTrackListView?.let {
            it.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            it.setLayoutManager(LinearLayoutManager(mActivity))
            it.setAdapter(mTrackListAdapter)
        }
        return mRootView!!
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        DebugLog.get().i(TAG, "onCreateView:")
        mRootView = inflater.inflate(R.layout.track_list, container, false)
        return initMainUi()
    }

    override fun onHolderClicked(position: Int, view: View?) {
        DebugLog.get().i(TAG, "onHolderClicked:")
        mTrackListAdapter?.let { it.notifyDataSetChanged() }
    }

    companion object {
        private val TAG = "MainFragment"
    }
}