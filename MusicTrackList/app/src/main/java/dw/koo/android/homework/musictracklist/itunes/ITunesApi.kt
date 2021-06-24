package dw.koo.android.homework.musictracklist.itunes

import android.net.Uri

class ITunesApi {
    private val mUriBuilder: Uri.Builder = Uri.parse(ITUNES_APPLE_SEARCH_DOMAIN).buildUpon()

    fun getUri(keyword: String?): Uri {
        mUriBuilder.appendQueryParameter(SEARCH_KEYWORD, keyword)
        mUriBuilder.appendQueryParameter(ENTITY, ENTITY_SONE)
        return mUriBuilder.build()
    }

    companion object {
        private const val ITUNES_APPLE_SEARCH_DOMAIN = "https://itunes.apple.com/search"
        private const val SEARCH_KEYWORD = "term"
        private const val ENTITY = "entity"
        private const val ENTITY_SONE = "song"
    }

}