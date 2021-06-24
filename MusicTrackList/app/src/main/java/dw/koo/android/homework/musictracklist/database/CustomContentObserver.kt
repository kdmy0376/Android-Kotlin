package dw.koo.android.homework.musictracklist.database

import android.content.Context
import android.database.ContentObserver
import android.net.Uri
import android.os.Handler
import dw.koo.android.homework.musictracklist.utils.DebugLog.Companion.get

abstract class CustomContentObserver(handler: Handler?) : ContentObserver(handler) {
    @Deprecated("")
    interface IOnTrackInfoListener {
    }

    override fun deliverSelfNotifications(): Boolean {
        return true
    }

    override fun onChange(selfChange: Boolean) {
        super.onChange(selfChange)
        get().d(TAG, "onChange: selfChange $selfChange")
    }

    override fun onChange(selfChange: Boolean, uri: Uri) {
        super.onChange(selfChange, uri)
        get().d(TAG, "onChange: selfChange $selfChange uri => $uri")
        onChanged(uri)
        onChanged()
    }

    fun registerContentObserver(context: Context, uri: Uri) {
        get().d(TAG, "registerContentObserver: uri => $uri")
        val resolver = context.contentResolver
        resolver.registerContentObserver(uri, true, this)
    }

    fun unregisterContentObserver(context: Context) {
        val resolver = context.contentResolver
        resolver.unregisterContentObserver(this)
    }

    private fun onChanged(uri: Uri) {
        get().d(TAG, "onChanged: uri => $uri")
    }

    protected abstract fun onChanged()

    companion object {
        private val TAG = CustomContentObserver::class.java.simpleName
    }
}