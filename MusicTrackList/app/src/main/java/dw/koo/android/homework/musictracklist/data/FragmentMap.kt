package dw.koo.android.homework.musictracklist.data

import dw.koo.android.homework.musictracklist.ui.fragment.CustomFragment
import dw.koo.android.homework.musictracklist.ui.fragment.FavoriteTrackListFragment
import dw.koo.android.homework.musictracklist.ui.fragment.MainFragment
import dw.koo.android.homework.musictracklist.utils.DebugLog.Companion.get
import java.util.*

class FragmentMap {
    private val mFragmentHashMap = HashMap<FragmentType, CustomFragment>()

    fun getFragment(type: FragmentType): CustomFragment? {
        get().d(TAG, "getFragment: Type => " + type.name)
        var fragment = mFragmentHashMap[type]
        if (fragment == null) {
            fragment = when (type) {
                FragmentType.MAIN_FRAGMENT -> MainFragment()
                FragmentType.FAVORITE_TRACK_LIST_FRAGMENT -> FavoriteTrackListFragment()
                FragmentType.NONE -> return null
            }
            mFragmentHashMap[type] = fragment
        }
        return fragment
    }

    companion object {
        private val TAG = FragmentMap::class.java.simpleName
    }
}