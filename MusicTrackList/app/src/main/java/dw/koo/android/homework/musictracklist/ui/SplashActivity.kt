package dw.koo.android.homework.musictracklist.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Message
import androidx.appcompat.app.AppCompatActivity
import dw.koo.android.homework.musictracklist.R
import dw.koo.android.homework.musictracklist.utils.DebugLog

class SplashActivity : AppCompatActivity(), Handler.Callback {
    private var mHandler: Handler? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_main)

        mHandler = Handler(this)
        mHandler!!.sendMessageDelayed(
                mHandler!!.obtainMessage(MSG_START_MAIN_ACTIVITY), START_MAIN_ACTIVITY_DELAY_TIME)
    }

    override fun handleMessage(msg: Message): Boolean {
        val msgWhat = msg.what
        DebugLog.get().i(TAG, "handleMessage: msgWhat => $msgWhat")

        when (msgWhat) {
            MSG_START_MAIN_ACTIVITY -> {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
        return false
    }

    companion object {
        private val TAG = SplashActivity::class.java.simpleName
        private const val MSG_START_MAIN_ACTIVITY = 100
        private const val START_MAIN_ACTIVITY_DELAY_TIME: Long = 3000
    }
}