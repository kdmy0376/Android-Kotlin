package dw.koo.android.homework.musictracklist.data

import android.content.ContentValues
import dw.koo.android.homework.musictracklist.data.TrackJsonKey.TrackJsonDefault
import dw.koo.android.homework.musictracklist.utils.DebugLog.Companion.get
import org.json.JSONObject

class Track() {
    private var mWrapperType: String? = null
    private var mKind: String? = null
    private var mArtistId: String? = null
    private var mCollectionId: String? = null
    var trackId: String? = null
        private set
    var artistName: String? = null
        private set
    var collectionName: String? = null
        private set
    var trackName: String? = null
        private set
    private var mCollectionCensoredName: String? = null
    private var mTrackCensoredName: String? = null
    private var mArtistViewUrl: String? = null
    private var mCollectionViewUrl: String? = null
    private var mTrackViewUrl: String? = null
    private var mPreviewUrl: String? = null
    private var mArtworkUrl30: String? = null
    var artworkUrl60: String? = null
        private set
    private var mArtworkUrl100: String? = null
    private var mCollectionPrice = 0.0
    private var mTrackPrice = 0.0
    private var mReleaseDate: String? = null
    private var mCollectionExplicitness: String? = null
    private var mTrackExplicitness: String? = null
    private var mDiscCount = 0
    private var mDiscNumber = 0
    private var mTrackCount = 0
    private var mTrackNumber = 0
    private var mTrackTimeMillis: Long = 0
    private var mCountry: String? = null
    private var mCurrency: String? = null
    private var mPrimaryGenreName: String? = null
    private var mContentAdvisoryRating: String? = null
    private var mIsStreamble = false

    constructor(trackJson: JSONObject) : this() {
        mWrapperType = trackJson.optString("wrapperType", TrackJsonDefault.DEFAULT_TRACK_JSON_STRING)
        mKind = trackJson.optString("kind", TrackJsonDefault.DEFAULT_TRACK_JSON_STRING)
        mArtistId = trackJson.optString("artistId", TrackJsonDefault.DEFAULT_TRACK_JSON_STRING)
        mCollectionId = trackJson.optString("collectionId", TrackJsonDefault.DEFAULT_TRACK_JSON_STRING)
        trackId = trackJson.optString("trackId", TrackJsonDefault.DEFAULT_TRACK_JSON_STRING)
        artistName = trackJson.optString("artistName", TrackJsonDefault.DEFAULT_TRACK_JSON_STRING)
        collectionName = trackJson.optString("collectionName", TrackJsonDefault.DEFAULT_TRACK_JSON_STRING)
        trackName = trackJson.optString("trackName", TrackJsonDefault.DEFAULT_TRACK_JSON_STRING)
        mCollectionCensoredName = trackJson.optString("collectionCensoredName", TrackJsonDefault.DEFAULT_TRACK_JSON_STRING)
        mTrackCensoredName = trackJson.optString("trackCensoredName", TrackJsonDefault.DEFAULT_TRACK_JSON_STRING)
        mArtistViewUrl = trackJson.optString("artistViewUrl", TrackJsonDefault.DEFAULT_TRACK_JSON_STRING)
        mCollectionViewUrl = trackJson.optString("collectionViewUrl", TrackJsonDefault.DEFAULT_TRACK_JSON_STRING)
        mTrackViewUrl = trackJson.optString("trackViewUrl", TrackJsonDefault.DEFAULT_TRACK_JSON_STRING)
        mPreviewUrl = trackJson.optString("previewUrl", TrackJsonDefault.DEFAULT_TRACK_JSON_STRING)
        mArtworkUrl30 = trackJson.optString("artworkUrl30", TrackJsonDefault.DEFAULT_TRACK_JSON_STRING)
        artworkUrl60 = trackJson.optString("artworkUrl60", TrackJsonDefault.DEFAULT_TRACK_JSON_STRING)
        mArtworkUrl100 = trackJson.optString("artworkUrl100", TrackJsonDefault.DEFAULT_TRACK_JSON_STRING)
        mCollectionPrice = trackJson.optDouble("collectionPrice", TrackJsonDefault.DEFAULT_TRACK_JSON_DOUBLE)
        mTrackPrice = trackJson.optDouble("trackPrice", TrackJsonDefault.DEFAULT_TRACK_JSON_DOUBLE)
        mReleaseDate = trackJson.optString("releaseDate", TrackJsonDefault.DEFAULT_TRACK_JSON_STRING)
        mCollectionExplicitness = trackJson.optString("collectionExplicitness", TrackJsonDefault.DEFAULT_TRACK_JSON_STRING)
        mTrackExplicitness = trackJson.optString("trackExplicitness", TrackJsonDefault.DEFAULT_TRACK_JSON_STRING)
        mDiscCount = trackJson.optInt("discCount", TrackJsonDefault.DEFAULT_TRACK_JSON_INT)
        mDiscNumber = trackJson.optInt("discNumber", TrackJsonDefault.DEFAULT_TRACK_JSON_INT)
        mTrackCount = trackJson.optInt("trackCount", TrackJsonDefault.DEFAULT_TRACK_JSON_INT)
        mTrackNumber = trackJson.optInt("trackNumber", TrackJsonDefault.DEFAULT_TRACK_JSON_INT)
        mTrackTimeMillis = trackJson.optLong("trackTimeMillis", TrackJsonDefault.DEFAULT_TRACK_JSON_LONG)
        mCountry = trackJson.optString("country", TrackJsonDefault.DEFAULT_TRACK_JSON_STRING)
        mCurrency = trackJson.optString("currency", TrackJsonDefault.DEFAULT_TRACK_JSON_STRING)
        mPrimaryGenreName = trackJson.optString("primaryGenreName", TrackJsonDefault.DEFAULT_TRACK_JSON_STRING)
        mContentAdvisoryRating = trackJson.optString("contentAdvisoryRating", TrackJsonDefault.DEFAULT_TRACK_JSON_STRING)
        mIsStreamble = trackJson.optBoolean("isStreamable", TrackJsonDefault.DEFAULT_TRACK_JSON_BOOLEAN)
    }

    fun setTrackId(trackId: String?): Track {
        this.trackId = trackId
        return this
    }

    fun setArtistName(artistName: String?): Track {
        this.artistName = artistName
        return this
    }

    fun setCollectionName(collectionName: String?): Track {
        this.collectionName = collectionName
        return this
    }

    fun setTrackName(trackName: String?): Track {
        this.trackName = trackName
        return this
    }

    fun setArtworkUrl60(artworkUrl60: String?): Track {
        this.artworkUrl60 = artworkUrl60
        return this
    }

    override fun toString(): String {
        return "Track{" +
                "mWrapperType='" + mWrapperType + '\'' +
                ", mKind='" + mKind + '\'' +
                ", mArtistId='" + mArtistId + '\'' +
                ", mCollectionId='" + mCollectionId + '\'' +
                ", mTrackId='" + trackId + '\'' +
                ", mArtistName='" + artistName + '\'' +
                ", mCollectionName='" + collectionName + '\'' +
                ", mTrackName='" + trackName + '\'' +
                ", mCollectionCensoredName='" + mCollectionCensoredName + '\'' +
                ", mTrackCensoredName='" + mTrackCensoredName + '\'' +
                ", mArtistViewUrl='" + mArtistViewUrl + '\'' +
                ", mCollectionViewUrl='" + mCollectionViewUrl + '\'' +
                ", mTrackViewUrl='" + mTrackViewUrl + '\'' +
                ", mPreviewUrl='" + mPreviewUrl + '\'' +
                ", mArtworkUrl30='" + mArtworkUrl30 + '\'' +
                ", mArtworkUrl60='" + artworkUrl60 + '\'' +
                ", mArtworkUrl100='" + mArtworkUrl100 + '\'' +
                ", mCollectionPrice=" + mCollectionPrice +
                ", mTrackPrice=" + mTrackPrice +
                ", mReleaseDate='" + mReleaseDate + '\'' +
                ", mCollectionExplicitness='" + mCollectionExplicitness + '\'' +
                ", mTrackExplicitness='" + mTrackExplicitness + '\'' +
                ", mDiscCount=" + mDiscCount +
                ", mDiscNumber=" + mDiscNumber +
                ", mTrackCount=" + mTrackCount +
                ", mTrackNumber=" + mTrackNumber +
                ", mTrackTimeMillis=" + mTrackTimeMillis +
                ", mCountry='" + mCountry + '\'' +
                ", mCurrency='" + mCurrency + '\'' +
                ", mPrimaryGenreName='" + mPrimaryGenreName + '\'' +
                ", mContentAdvisoryRating='" + mContentAdvisoryRating + '\'' +
                ", mIsStreamble=" + mIsStreamble +
                '}'
    }

    init {
        get().i(ContentValues.TAG, "Track")
    }
}