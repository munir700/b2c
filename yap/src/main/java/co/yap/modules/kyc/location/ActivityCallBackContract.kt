package co.yap.modules.kyc.location

import android.content.Intent

internal interface ActivityCallBackContract {

    fun onPause()
    fun onResume()
    fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray)
    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent)

}
