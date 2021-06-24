package dw.koo.android.homework.musictracklist.ui

import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.os.Message
import dw.koo.android.homework.musictracklist.ModuleSet
import dw.koo.android.homework.musictracklist.data.LocalApi

class MsgHandle {
    private val mHandler: Handler
    private val mUiHandler: Handler

    interface IOnUiUpdateListener {
        @Deprecated("")
        fun onUpdate(command: String?, extra: Bundle?)
    }

    fun quitHandlerLoopers() {
        mHandler.looper.quit()
        mUiHandler.looper.quit()
    }

    private val mHandlerCallback = Handler.Callback { msg: Message ->
        val localApi = LocalApi.whatOf(msg.what)
        when (localApi) {
            LocalApi.RequestTrack -> ModuleSet.get()!!.trackInfoLoader!!.loadTrackInfo()
            LocalApi.LoadFavoriteTrackDb -> ModuleSet.get()!!.trackDbHelper!!.loadFavoriteTrackDb()
            else -> {
            }
        }
        return@Callback false
    }

    private val mUiHandlerCallback = Handler.Callback { msg: Message? -> false }

    init {
        val handlerThread = HandlerThread(TAG)
        handlerThread.start()
        mHandler = Handler(handlerThread.looper, mHandlerCallback)
        mUiHandler = Handler(mUiHandlerCallback)
    }

    fun sendUIMessage(localApi: LocalApi) {
        sendUIMessage(localApi.what, 0, 0, null)
    }

    private fun sendUIMessage(what: Int, arg1: Int, arg2: Int, `object`: Any?) {
        try {
            mUiHandler.obtainMessage(what, arg1, arg2, `object`).sendToTarget()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun sendMessage(localApi: LocalApi) {
        sendMessage(localApi.what, 0, 0, null)
    }

    private fun sendMessage(what: Int, arg1: Int, arg2: Int, `object`: Any?) {
        try {
            mHandler.obtainMessage(what, arg1, arg2, `object`).sendToTarget()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    companion object {
        private val TAG = MsgHandle::class.java.simpleName
    }
}