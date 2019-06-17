package co.yap.yapcore;

import android.os.Bundle
import androidx.lifecycle.LifecycleOwner
import co.yap.yapcore.helpers.PermissionsManager

abstract class PermissionAwareActivity : BaseActivity(), PermissionsManager.OnPermissionGrantedListener,
    LifecycleOwner {

    private lateinit var permissionsManager: PermissionsManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        permissionsManager = PermissionsManager(this, this, this)
    }

    override fun onPermissionGranted(permission: String?) {
    }

    override fun onPermissionNotGranted(permission: String?) {
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        permissionsManager.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    fun isPermissionGranted(permission: String): Boolean {
        return permissionsManager.isPermissionGranted(permission)
    }

    fun requestPermissions() {
        return permissionsManager.requestAppPermissions()
    }
}
