package dw.koo.android.homework.musictracklist.database

import android.provider.BaseColumns

class DataBases {
    object Track : BaseColumns {
        const val TRACK_ID = "track_id"
        const val TRACK_NAME = "track_name"
        const val COLLECTION_NAME = "collection_name"
        const val ARTIST_NAME = "artist_name"
        const val ARTWORK_URL_60 = "artwork_url_60"
        const val FAVORITE = "favorite"
        const val _TABLENAME = "track_information"
        const val _CREATE = ("create table " + _TABLENAME + "("
                + BaseColumns._ID + " integer primary key autoincrement, "
                + TRACK_ID + " text not null , "
                + TRACK_NAME + " text not null , "
                + COLLECTION_NAME + " text not null , "
                + ARTIST_NAME + " text not null , "
                + ARTWORK_URL_60 + " text not null , "
                + FAVORITE + " text not null );")
    }
}