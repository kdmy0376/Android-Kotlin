package dw.koo.android.homework.musictracklist.itunes

import android.net.TrafficStats
import dw.koo.android.homework.musictracklist.task.ThreadTask
import dw.koo.android.homework.musictracklist.utils.DebugLog.Companion.get
import okhttp3.Headers
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class TrackRequest(listener: IOnRequestResultListener) {
    private val mHttpClient: OkHttpClient = OkHttpClient()

    interface IOnRequestResultListener {
        fun onResult(response: TrackResponse?)
    }

    private val mOnRequestResultListener: IOnRequestResultListener = listener

    fun execute(url: String?) {
        val trackResponse = TrackResponse()
        if (url == null) {
            get().w(TAG, "execute: url is null !!!")
            trackResponse.setError(-4)
            mOnRequestResultListener.onResult(trackResponse)
        }
        get().d(TAG, "execute: url => $url")
        executeOkHttp(url)
    }

    private fun executeOkHttp(url: String?) {
        val trackResponse = TrackResponse()
        val requestBuilder = Request.Builder().url(url!!)
        val request = requestBuilder.build()
        TrafficStats.setThreadStatsTag(request.hashCode())

        /* Temporary Code to avoid the crash */object : ThreadTask<Void?, Response?>() {
            override fun doInBackground(arg: Void?): Response? {
                return try {
                    mHttpClient.newCall(request).execute()
                } catch (e: IOException) {
                    e.printStackTrace()
                    null
                }
            }

            override fun onPostExecute(result: Response?) {
                if (result != null) {
                    trackResponse.setError(0)
                    trackResponse.setCode(result.code)
                    trackResponse.setHeaders(Headers.Builder().addAll(result.headers).build())
                    val responseBody = result.body
                    if (responseBody != null) {
                        get().d(TAG, "onPostExecute: responseBody is not null")
                        try {
                            trackResponse.body = responseBody.string()
                        } catch (e: IOException) {
                            e.printStackTrace()
                            trackResponse.setError(-5)
                        }
                    }
                } else {
                    trackResponse.setError(-6)
                }
                mOnRequestResultListener.onResult(trackResponse)
            }
        }.execute(null)
    }

    companion object {
        private val TAG = TrackRequest::class.java.simpleName
    }

}