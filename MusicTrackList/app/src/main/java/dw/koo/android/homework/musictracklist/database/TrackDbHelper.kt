package dw.koo.android.homework.musictracklist.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Bundle
import dw.koo.android.homework.musictracklist.data.Track
import dw.koo.android.homework.musictracklist.utils.DebugLog.Companion.get
import java.util.*

class TrackDbHelper(private val mContext: Context) {
    private var mDatabaseHelper: DatabaseHelper? = null
    private var mOnTrackDataChangeListener: IOnTrackDataChangeListener? = null

    interface IOnTrackDataChangeListener {
        @Deprecated("")
        fun onTrackDataChange(trackData: Bundle?)
        fun onTrackDbLoadingCompleted(trackArrayList: ArrayList<Track>?)
    }

    fun setOnTrackDataChangeListener(listener: IOnTrackDataChangeListener?) {
        mOnTrackDataChangeListener = listener
    }

    private inner class DatabaseHelper(
            context: Context?,
            name: String?,
            factory: SQLiteDatabase.CursorFactory?,
            version: Int) : SQLiteOpenHelper(context, name, factory, version) {
        override fun onCreate(db: SQLiteDatabase) {
            db.execSQL(DataBases.Track._CREATE)
        }

        override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
            db.execSQL("DROP TABLE IF EXISTS " + DataBases.Track._TABLENAME)
            onCreate(db)
        }
    }

    fun open(): TrackDbHelper {
        mDatabaseHelper = DatabaseHelper(mContext, DATABASE_NAME, null, DATABASE_VERSION)
        mDatabase = mDatabaseHelper!!.writableDatabase
        return this
    }

    fun close() {
        mDatabase!!.close()
    }

    fun insertColumn(track: Track): Long {
        if (hasMatchedTrack(track.trackId)) {
            deleteColumn(track.trackId)
            return -1
        }
        val values = ContentValues()
        values.put(DataBases.Track.TRACK_ID, track.trackId)
        values.put(DataBases.Track.TRACK_NAME, track.trackName)
        values.put(DataBases.Track.COLLECTION_NAME, track.collectionName)
        values.put(DataBases.Track.ARTIST_NAME, track.artistName)
        values.put(DataBases.Track.ARTWORK_URL_60, track.artworkUrl60)
        values.put(DataBases.Track.FAVORITE, "")
        return mDatabase!!.insert(DataBases.Track._TABLENAME, null, values)
    }

    @Deprecated("")
    fun insertColumn(trackId: String?,
                     trackName: String?,
                     collectionName: String?,
                     artistName: String?,
                     artworkUrl60: String?,
                     favorite: String?): Long {
        val values = ContentValues()
        values.put(DataBases.Track.TRACK_ID, trackId)
        values.put(DataBases.Track.TRACK_NAME, trackName)
        values.put(DataBases.Track.COLLECTION_NAME, collectionName)
        values.put(DataBases.Track.ARTIST_NAME, artistName)
        values.put(DataBases.Track.ARTWORK_URL_60, artworkUrl60)
        values.put(DataBases.Track.FAVORITE, favorite)
        return mDatabase!!.insert(DataBases.Track._TABLENAME, null, values)
    }

    @Deprecated("")
    fun updateColumn(id: Long,
                     trackId: String?,
                     trackName: String?,
                     collectionName: String?,
                     artistName: String?,
                     artworkUrl60: String?,
                     favorite: String?): Boolean {
        val values = ContentValues()
        values.put(DataBases.Track.TRACK_ID, trackId)
        values.put(DataBases.Track.TRACK_NAME, trackName)
        values.put(DataBases.Track.COLLECTION_NAME, collectionName)
        values.put(DataBases.Track.ARTIST_NAME, artistName)
        values.put(DataBases.Track.ARTWORK_URL_60, artworkUrl60)
        values.put(DataBases.Track.FAVORITE, favorite)
        return mDatabase!!.update(DataBases.Track._TABLENAME, values, "_id=$id", null) > 0
    }

    fun deleteColumn(trackId: String?): Boolean {
        return mDatabase!!.delete(DataBases.Track._TABLENAME, "track_id='$trackId'", null) > 0
    }

    val allColumns: Cursor
        get() = mDatabase!!.query(
                DataBases.Track._TABLENAME,
                null,
                null,
                null,
                null,
                null,
                null)

    fun loadFavoriteTrackDb() {
        val cursor = allColumns
        if (cursor == null || cursor.count <= 0) {
            get().d(TAG, "loadFavoriteTrackDb: cursor is null or less than and equal to 0 !!!")
            return
        }
        val favoriteTrackList = ArrayList<Track>()
        while (cursor.moveToNext()) {
            favoriteTrackList.add(Track()
                    .setTrackId(cursor.getString(cursor.getColumnIndex("track_id")))
                    .setTrackName(cursor.getString(cursor.getColumnIndex("track_name")))
                    .setCollectionName(cursor.getString(cursor.getColumnIndex("collection_name")))
                    .setArtistName(cursor.getString(cursor.getColumnIndex("artist_name")))
                    .setArtworkUrl60(cursor.getString(cursor.getColumnIndex("artwork_url_60"))))
        }
        mOnTrackDataChangeListener!!.onTrackDbLoadingCompleted(favoriteTrackList)
    }

    @Deprecated("")
    fun getColumn(id: Long): Cursor? {
        val cursor = mDatabase!!.query(DataBases.Track._TABLENAME, null,
                "_id=$id", null, null, null, null)
        if (cursor != null && cursor.count != 0) cursor.moveToFirst()
        return cursor
    }

    fun hasMatchedTrack(trackId: String?): Boolean {
        val cursor = mDatabase!!.rawQuery("select * from track_information where track_Id='$trackId'", null)
        if (cursor == null) {
            get().d(TAG, "hasMatchedTrack: cursor is null")
            return false
        }
        return cursor.moveToNext()
    }

    companion object {
        private val TAG = "TrackDbHelper"
        private const val DATABASE_NAME = "track_information.db"
        private const val DATABASE_VERSION = 1
        private var mDatabase: SQLiteDatabase? = null
    }
}