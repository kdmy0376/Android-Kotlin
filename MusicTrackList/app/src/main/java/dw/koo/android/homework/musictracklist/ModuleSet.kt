package dw.koo.android.homework.musictracklist

import android.content.Context
import dw.koo.android.homework.musictracklist.database.TrackDbHelper
import dw.koo.android.homework.musictracklist.itunes.TrackInfoLoader
import dw.koo.android.homework.musictracklist.utils.DebugLog

class ModuleSet private constructor(context: Context) {
    private val mContext: Context
    val trackInfoLoader: TrackInfoLoader
    val trackDbHelper: TrackDbHelper

    init {
        DebugLog.get()?.let { it.i(TAG, "ModuleSet") }
        mContext = context
        trackInfoLoader = TrackInfoLoader()
        trackDbHelper = TrackDbHelper(mContext)
    }

    companion object {
        private val TAG = "ModuleSet"

        @Volatile
        private var mModuleSet: ModuleSet? = null

        @kotlin.jvm.JvmStatic
        fun from(context: Context): ModuleSet? {
            if (mModuleSet == null) {
                synchronized(ModuleSet::class) {
                    if (mModuleSet == null) {
                        mModuleSet = ModuleSet(context)
                    }
                }
            }
            return mModuleSet
        }

        @kotlin.jvm.JvmStatic
        fun get(): ModuleSet? {
            return mModuleSet
        }
    }
}