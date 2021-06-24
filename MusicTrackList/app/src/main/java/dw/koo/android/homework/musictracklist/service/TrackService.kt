package dw.koo.android.homework.musictracklist.service

import android.app.Service
import android.content.Intent
import android.os.IBinder

class TrackService : Service() {
    override fun onBind(intent: Intent): IBinder? {
        return null
    }
}