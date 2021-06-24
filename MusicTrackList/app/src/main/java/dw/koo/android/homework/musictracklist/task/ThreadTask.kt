package dw.koo.android.homework.musictracklist.task

import android.os.Handler
import android.os.Message

/* Created a similar class since the AsyncTask was deprecated */
abstract class ThreadTask<T1, T2> : Runnable, Handler.Callback {
    private var mArgument: T1? = null
    private var mResult: T2? = null
    private val WORK_DONE = 0
    private var mResultHandler: Handler? = null

    override fun handleMessage(msg: Message): Boolean {
        onPostExecute(mResult)
        return false
    }

    fun execute(arg: T1) {
        mArgument = arg
        onPreExecute()
        mResultHandler = Handler(this)
        val thread = Thread(this)
        thread.start()
    }

    override fun run() {
        mResult = doInBackground(mArgument)
        mResultHandler!!.sendEmptyMessage(WORK_DONE)
    }

    private fun onPreExecute() {}
    protected abstract fun doInBackground(arg: T1?): T2
    protected open fun onPostExecute(result: T2?) {
        mResultHandler!!.removeCallbacksAndMessages(null)
        mResultHandler = null
    }
}