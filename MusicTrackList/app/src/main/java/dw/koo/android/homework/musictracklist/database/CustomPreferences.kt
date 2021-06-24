package dw.koo.android.homework.musictracklist.database

import android.content.ContentValues
import android.content.Context
import android.net.Uri

object CustomPreferences {
    private val CONTENT_URI: Uri? = null
    private val KEY: String? = null
    private val VALUE: String? = null

    operator fun set(context: Context, key: String?, value: String?) {
        val cv = ContentValues()
        cv.put(KEY, key)
        cv.put(VALUE, value)
        set(context, cv)
    }

    operator fun get(context: Context, key: String, def: String): String {
        val s = get(context, key)
        return if (s.isEmpty()) def else s
    }

    operator fun get(context: Context, key: String): String {
        var value = ""
        try {
            val c = context.contentResolver.query(
                    CONTENT_URI!!,
                    null,
                    "$KEY='$key'",
                    null,
                    null)
            if (c != null && c.moveToFirst()) {
                value = c.getString(c.getColumnIndex(VALUE))
            }
            c?.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return value
    }

    fun contains(context: Context, key: String): Boolean {
        try {
            val c = context.contentResolver.query(
                    CONTENT_URI!!,
                    null,
                    "$KEY='$key'",
                    null,
                    null)
            if (c != null) {
                val ret = c.count > 0
                c.close()
                return ret
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return false
    }

    private operator fun set(context: Context, cv: ContentValues) {
        try {
            val key = cv.getAsString(KEY)
            if (!contains(context, key)) {
                context.contentResolver.insert(CONTENT_URI!!, cv)
            } else {
                context.contentResolver.update(
                        CONTENT_URI!!,
                        cv,
                        "$KEY='$key'",
                        null)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}