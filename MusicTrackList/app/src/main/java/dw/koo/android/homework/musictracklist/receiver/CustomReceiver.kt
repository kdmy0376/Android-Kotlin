package dw.koo.android.homework.musictracklist.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import dw.koo.android.homework.musictracklist.utils.DebugLog.Companion.get

class CustomReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val action = intent.action
        if (action == null) {
            get().e(TAG, "onReceive: action is null")
            return
        }
        when (action) {
            else -> {
            }
        }
    }

    companion object {
        private val TAG = CustomReceiver::class.java.simpleName
    }
}