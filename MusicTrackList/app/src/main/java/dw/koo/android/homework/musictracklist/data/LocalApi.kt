package dw.koo.android.homework.musictracklist.data

import java.util.*

enum class LocalApi(var what: Int) {
    RequestTrack(0xCB01), DrawTrackList(0xCB02), ChangeFragment(0xCB03), UpdateTrackList(0xCB04),
    UpdateFavoriteTrackList(0xCB05), LoadFavoriteTrackDb(0xCB06), None(0xffff);

    override fun toString(): String {
        return String.format(Locale.getDefault(), "%s(0x%08x)", name, what)
    }

    companion object {
        fun whatOf(what: Int): LocalApi {
            val values = values()
            for (api in values) {
                if (api.what == what) {
                    return api
                }
            }
            return None
        }
    }
}