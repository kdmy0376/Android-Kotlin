package dw.koo.android.homework.musictracklist.utils

import android.content.Context
import android.content.pm.PackageManager
import android.provider.Settings
import java.io.File
import java.io.FileNotFoundException
import java.io.FileWriter

object Utils {
    private val TAG = Utils::class.simpleName

    @Deprecated("")
    fun isDeviceProvisioned(context: Context): Boolean {
        try {
            val provision = Settings.Global.getInt(context.contentResolver, Settings.Global.DEVICE_PROVISIONED)
            return provision == 1
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return false
    }

    @Deprecated("Not Used")
    fun getVersionCode(context: Context, packageName: String?): Int {
        val pm = context.packageManager
        try {
            val packageInfo = pm.getPackageInfo(packageName, PackageManager.GET_META_DATA)
            return packageInfo.versionCode
        } catch (ignored: PackageManager.NameNotFoundException) {
        }
        return -1
    }

    @Deprecated("Not Used")
    fun saveMusicTrackJsonToFile(context: Context, musicTrackJson: String?) {
        try {
            val file = File(context.filesDir, "music_track.json")
            val writer = FileWriter(file)
            writer.write(musicTrackJson)
            writer.close()
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}