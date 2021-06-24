package dw.koo.android.homework.musictracklist.utils

import android.util.Log

class DebugLog {
    private var debug = true
    fun init(debug: Boolean) {
        this.debug = debug
    }

    fun v(tag: String, log: String?) {
        if (debug) {
            Log.v("$TAG[$tag]", log)
        }
    }

    fun i(tag: String, log: String?) {
        if (debug) {
            Log.i("$TAG[$tag]", log)
        }
    }

    fun d(tag: String, log: String?) {
        if (debug) {
            Log.d("$TAG[$tag]", log)
        }
    }

    fun w(tag: String, log: String?) {
        Log.w("$TAG[$tag]", log)
    }

    fun e(tag: String, log: String?) {
        Log.e("$TAG[$tag]", log)
    }

    companion object {
        private const val TAG = "DebugLog"
        private val mDebugLog: DebugLog = DebugLog()

        fun get(): DebugLog {
            return mDebugLog
        }
    }
}