package dw.koo.android.homework.musictracklist.utils

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.UserHandle

object HiddenApi {
    private const val TAG = "HiddenApi";

    class ContextWrapper(base: Context?) : android.content.ContextWrapper(base) {
        private val mReflector = Reflector<Context>(this)
        override fun startService(service: Intent): ComponentName? {
            val userHandle = Reflector.getStaticField(UserHandle::class.java, "CURRENT").asObject() as UserHandle
            userHandle?.let {
                try {
                    return mReflector.invoke("startServiceAsUser", service, userHandle) as ComponentName
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            return super.startService(service)
        }
    }

    object PackageManager {
        var INSTALL_REPLACE_EXISTING = 0
        var INSTALL_SUCCEEDED = 0
        var DELETE_SUCCEEDED = 0
        var GET_UNINSTALLED_PACKAGES = 0
        var GET_DISABLED_COMPONENTS = 0

        init {
            INSTALL_REPLACE_EXISTING = Reflector.getStaticField(android.content.pm.PackageManager::class.java, "INSTALL_REPLACE_EXISTING").asInt()
            INSTALL_SUCCEEDED = Reflector.getStaticField(android.content.pm.PackageManager::class.java, "INSTALL_SUCCEEDED").asInt()
            DELETE_SUCCEEDED = Reflector.getStaticField(android.content.pm.PackageManager::class.java, "DELETE_SUCCEEDED").asInt()
            GET_UNINSTALLED_PACKAGES = Reflector.getStaticField(android.content.pm.PackageManager::class.java, "GET_UNINSTALLED_PACKAGES").asInt()
            GET_DISABLED_COMPONENTS = Reflector.getStaticField(android.content.pm.PackageManager::class.java, "GET_DISABLED_COMPONENTS").asInt()
        }
    }
}