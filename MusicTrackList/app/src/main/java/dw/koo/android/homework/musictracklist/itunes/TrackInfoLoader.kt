package dw.koo.android.homework.musictracklist.itunes

import dw.koo.android.homework.musictracklist.data.Track
import dw.koo.android.homework.musictracklist.data.TrackRequestError
import dw.koo.android.homework.musictracklist.itunes.TrackRequest.IOnRequestResultListener
import dw.koo.android.homework.musictracklist.utils.DebugLog.Companion.get
import org.json.JSONException
import org.json.JSONObject
import java.util.*

class TrackInfoLoader : IOnRequestResultListener {
    private val mTrackArrayList: ArrayList<Track>
    private val mTrackRequest: TrackRequest
    private val mITunesApi: ITunesApi
    private var mTrackCount: Int

    interface IOnTrackResponseResultListener {
        fun onSuccess(trackCount: Int, trackArrayList: ArrayList<Track>?)
        fun onFailed(reason: String?)
    }

    private var mOnTrackResponseResultListener: IOnTrackResponseResultListener? = null

    fun setOnTrackResponseResult(listener: IOnTrackResponseResultListener?) {
        mOnTrackResponseResultListener = listener
    }

    fun loadTrackInfo() {
        val uri = mITunesApi.getUri("greenday")
        val uriString = uri.toString()
        get().d(TAG, "loadTrackInfo: uriString => $uriString")
        mTrackRequest.execute(uriString)
    }

    override fun onResult(response: TrackResponse?) {
        if (response == null) {
            get().e(TAG, "loadTrackInfo: trackResponse is null !!!")
            return
        }
        if (response.responseOk()) {
            val responseBody = response.body
            try {
                val responseJson = JSONObject(responseBody)
                mTrackCount = responseJson.optInt("resultCount", 0)
                val trackJsonArray = responseJson.optJSONArray("results")
                if (trackJsonArray == null) {
                    get().e(TAG, "loadTrackInfo: trackJsonArray is null !!!")
                    mOnTrackResponseResultListener!!.onFailed(TrackRequestError.REASON_TRACK_RESULT_NOT_EXIST)
                    return
                }
                mTrackArrayList.clear()
                for (i in 0 until trackJsonArray.length()) {
                    val trackJson = trackJsonArray.getJSONObject(i) ?: continue
                    val track = Track(trackJson)
                    get().d(TAG, "loadTrackInfo: Track info => $track")
                    mTrackArrayList.add(track)
                }
                if (mTrackArrayList.size > 0) {
                    mOnTrackResponseResultListener!!.onSuccess(mTrackCount, mTrackArrayList)
                } else {
                    mOnTrackResponseResultListener!!.onFailed(TrackRequestError.REASON_TRACK_LIST_EMPTY)
                }
            } catch (e: JSONException) {
                e.printStackTrace()
                mOnTrackResponseResultListener!!.onFailed(TrackRequestError.REASON_TRACK_JSON_ERROR)
            } catch (e: Exception) {
                e.printStackTrace()
                mOnTrackResponseResultListener!!.onFailed(TrackRequestError.REASON_UNKNOWN_ERROR)
            }
        }
    }

    companion object {
        private val TAG = TrackInfoLoader::class.java.simpleName
    }

    init {
        get().d(TAG, "TrackInfoLoader")
        mITunesApi = ITunesApi()
        mTrackCount = 0
        mTrackArrayList = ArrayList()
        mTrackRequest = TrackRequest(this)
    }
}