package dw.koo.android.homework.musictracklist.ui

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import dw.koo.android.homework.musictracklist.ModuleSet
import dw.koo.android.homework.musictracklist.R
import dw.koo.android.homework.musictracklist.data.*
import dw.koo.android.homework.musictracklist.database.CustomContentObserver
import dw.koo.android.homework.musictracklist.database.TrackDbHelper.IOnTrackDataChangeListener
import dw.koo.android.homework.musictracklist.itunes.TrackInfoLoader.IOnTrackResponseResultListener
import dw.koo.android.homework.musictracklist.ui.MsgHandle.IOnUiUpdateListener
import dw.koo.android.homework.musictracklist.ui.fragment.CustomFragment
import dw.koo.android.homework.musictracklist.utils.DebugLog
import java.util.*


class MainActivity : AppCompatActivity(), IOnUiUpdateListener {
    private var mTrackContentObserver: TrackContentObserver? = null
    private var mFragmentMap: FragmentMap? = null
    private var mBottomNavigationView: BottomNavigationView? = null
    private lateinit var mContext: Context
    private var mModuleSet: ModuleSet? = null
    private val mMsgHandle: MsgHandle = MsgHandle()

    private inner class TrackContentObserver(handler: Handler?) : CustomContentObserver(handler) {
        override fun onChanged() {
            DebugLog.get().i(TAG, "onChanged:")
        }
    }

    private val mOnTrackDataChangeListener: IOnTrackDataChangeListener = object : IOnTrackDataChangeListener {
        override fun onTrackDataChange(trackData: Bundle?) {
            DebugLog.get().i(TAG, "onTrackDataChange:")
        }

        override fun onTrackDbLoadingCompleted(trackArrayList: ArrayList<Track>?) {
            DebugLog.get().i(TAG, "onTrackDbLoadingCompleted:")
            applyTrackDataToFragment(trackArrayList, FragmentType.FAVORITE_TRACK_LIST_FRAGMENT)
        }
    }

    private val mOnTrackResponseResultListener: IOnTrackResponseResultListener = object : IOnTrackResponseResultListener {
        override fun onSuccess(trackCount: Int, trackArrayList: ArrayList<Track>?) {
            DebugLog.get().i(TAG, "IOnTrackResponseResultListener: " +
                    "onSuccess: trackCount => " + trackCount)
            applyTrackDataToFragment(trackArrayList, FragmentType.MAIN_FRAGMENT)
        }

        override fun onFailed(reason: String?) {
            DebugLog.get().i(TAG, "IOnTrackResponseResultListener: onFailed: reason => $reason")
            when (reason) {
                TrackRequestError.REASON_TRACK_JSON_ERROR,
                TrackRequestError.REASON_TRACK_LIST_EMPTY,
                TrackRequestError.REASON_TRACK_RESULT_NOT_EXIST,
                TrackRequestError.REASON_UNKNOWN_ERROR -> {
                }
                else -> {
                }
            }
        }
    }

    private fun applyTrackDataToFragment(trackArrayList: ArrayList<Track>?, fragmentType: FragmentType) {
        runOnUiThread {
            val customFragment = currentFragment
            if (customFragment == null) {
                DebugLog.get().d(TAG, "applyTrackDataToFragment: customeFragment is null")
                return@runOnUiThread
            }
            if (customFragment.type == fragmentType) {
                customFragment!!.setTrackData(trackArrayList)
            }
        }
    }

    private val mNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener {
        item: MenuItem ->
        DebugLog.get().i(TAG, "OnNavigationItemSelectedListener:")
        val itemId = item.itemId
        val fragmentType: FragmentType? = when (itemId) {
            R.id.main_screen_item -> FragmentType.MAIN_FRAGMENT
            R.id.favorite_screen_item -> FragmentType.FAVORITE_TRACK_LIST_FRAGMENT
            R.id.exit_item -> {
                finish()
                return@OnNavigationItemSelectedListener false
            }
            else -> null
        }
        changeFragment(fragmentType)
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mContext = applicationContext
        mModuleSet = ModuleSet.from(mContext)
        mFragmentMap = FragmentMap()

        initFragment(FragmentType.MAIN_FRAGMENT)
        initBottomNavigation()
        initDatabase()

        mModuleSet?.let {
            it.trackInfoLoader.setOnTrackResponseResult(mOnTrackResponseResultListener)
        }
        mMsgHandle.sendMessage(LocalApi.RequestTrack)
    }

    private fun initFragment(fragmentType: FragmentType) {
        val supportFragmentManager = supportFragmentManager
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        val fragment = mFragmentMap!!.getFragment(fragmentType)
        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
        fragment?.let { fragmentTransaction.replace(R.id.main_container, it).commit() }
    }

    private fun initBottomNavigation() {
        mBottomNavigationView = findViewById(R.id.bottom_navigation)
        mBottomNavigationView?.let {
            it.setOnNavigationItemSelectedListener(mNavigationItemSelectedListener) }
    }

    private fun initDatabase() {
        mModuleSet?.let {
            it.trackDbHelper.setOnTrackDataChangeListener(mOnTrackDataChangeListener)
            it.trackDbHelper.open()
        }
        mTrackContentObserver = TrackContentObserver(Handler())
    }

    private val currentFragment: CustomFragment?
        private get() {
            val supportFragmentManager = supportFragmentManager
            return supportFragmentManager.findFragmentById(R.id.main_container) as CustomFragment?
        }

    private fun changeFragment(fragmentType: FragmentType?) {
        if (fragmentType == null) {
            DebugLog.get().d(TAG, "changeFragment: fragmentType is null !!!")
            return
        }
        val currentFragment = currentFragment
        val supportFragmentManager = supportFragmentManager
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        val fragmentToBeChanged = mFragmentMap!!.getFragment(fragmentType)
        if (fragmentToBeChanged == null) {
            DebugLog.get().d(TAG, "changeFragment: fragmentToBeChanged is null !!!")
            return
        }
        if (currentFragment!!.type == fragmentToBeChanged.type) {
            DebugLog.get().d(TAG, "changeFragment: " +
                    "current fragment is the same with the fragment to be changed")
            return
        }
        DebugLog.get().d(TAG, "changeFragment: replace fragment")
        fragmentTransaction.replace(R.id.main_container, fragmentToBeChanged).commitAllowingStateLoss()
        if (fragmentToBeChanged.type == FragmentType.MAIN_FRAGMENT) {
            mMsgHandle.sendMessage(LocalApi.RequestTrack)

        } else if (fragmentToBeChanged.type == FragmentType.FAVORITE_TRACK_LIST_FRAGMENT) {
            mMsgHandle.sendMessage(LocalApi.LoadFavoriteTrackDb)
        }
    }

    override fun onUpdate(command: String?, extra: Bundle?) {
        when (command) {
            else -> {
            }
        }
    }

    override fun onDestroy() {
        mTrackContentObserver?.let { it!!.unregisterContentObserver(this) }
        mModuleSet?.let { it.trackDbHelper.close() }
        mMsgHandle.quitHandlerLoopers()
        super.onDestroy()
    }

    companion object {
        private val TAG = MainActivity::class.java.simpleName
    }
}