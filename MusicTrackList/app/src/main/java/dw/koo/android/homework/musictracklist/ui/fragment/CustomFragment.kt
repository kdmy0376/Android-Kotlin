package dw.koo.android.homework.musictracklist.ui.fragment

import android.content.Context
import android.view.View
import androidx.fragment.app.Fragment
import dw.koo.android.homework.musictracklist.data.FragmentType
import dw.koo.android.homework.musictracklist.data.Track
import dw.koo.android.homework.musictracklist.ui.MainActivity
import java.util.*

abstract class CustomFragment : Fragment() {
    protected var mActivity: MainActivity? = null
    protected var mRootView: View? = null
    abstract val type: FragmentType?

    open fun setTrackData(trackData: ArrayList<Track>?) {}
    abstract fun initMainUi(): View?

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mActivity = activity as MainActivity?
    }
}