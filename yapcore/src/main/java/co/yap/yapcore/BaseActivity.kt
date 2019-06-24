package co.yap.yapcore

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import co.yap.yapcore.helpers.NetworkConnectionManager
import co.yap.yapcore.helpers.PermissionsManager
import com.google.android.material.snackbar.Snackbar

abstract class BaseActivity : AppCompatActivity(), IFragmentHolder, IBase.View,
    NetworkConnectionManager.OnNetworkStateChangeListener, PermissionsManager.OnPermissionGrantedListener {

    private var snackbar: Snackbar? = null
    private var DURATION_CODE = -2
    private lateinit var permissionsManager: PermissionsManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        this.window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)

        NetworkConnectionManager.init(this)
        NetworkConnectionManager.subscribe(this)
        permissionsManager = PermissionsManager(this, this, this)
    }

    private val progressDialogueFragment: ProgressDialogueFragment =
        ProgressDialogueFragment()

    fun hideKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    override fun showToast(msg: String) {
        if ("" != msg.trim { it <= ' ' }) {
            Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
        }
    }

    override fun onNetworkStateChanged(isConnected: Boolean) {
        showInternetSnack(!isConnected)
    }

    override fun showInternetSnack(isVisible: Boolean) {
        if (isVisible) showNoInternetSnackBar() else showInternetConnectedSnackBar()
    }

    private fun showNoInternetSnackBar() {
        snackbar = setSnackBar(
            this,
            // TODO: Use strings for these
            "No internet connection",
            Snackbar.LENGTH_INDEFINITE
        )
            .setAction(
                // TODO: Use strings for these
                "Settings"
            ) { startActivity(Intent(Settings.ACTION_WIFI_SETTINGS).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)) }
            .setActionTextColor(getResources().getColor(R.color.colorPrimary))
        snackbar!!.show()
    }

    private fun showInternetConnectedSnackBar() {
        val snackbarConnected = setSnackBar(
            this,
            // TODO: Use strings for these
            "Internet connected.",
            Snackbar.LENGTH_SHORT
        )
        snackbarConnected.show()
        snackbar?.dismiss()
    }

    override fun onFragmentAttached() {

    }

    override fun onFragmentDetached(tag: String) {

    }

    override fun showLoader(isVisible: Boolean) {
        if (isVisible) {
            progressDialogueFragment.show(supportFragmentManager, "loading")
        } else {
            progressDialogueFragment.dismiss()

        }
    }

    private fun setSnackBar(activity: Activity, message: String, duration: Int): Snackbar {
        val layout: View
        val snackbar = Snackbar
            .make(activity.findViewById(android.R.id.content), message, duration)
        layout = snackbar.view
        layout.setBackgroundColor(activity.resources.getColor(R.color.colorDarkGreen))
        val text = layout.findViewById<View>(com.google.android.material.R.id.snackbar_text) as TextView
        text.setTextColor(activity.resources.getColor(R.color.colorWhite))

        if (duration == DURATION_CODE) {
            layout.setBackgroundColor(activity.resources.getColor(R.color.colorGreyVeryDark))
            val snackbarView = snackbar.view
            val textView = snackbarView.findViewById<View>(com.google.android.material.R.id.snackbar_text) as TextView
            textView.setTextColor(Color.WHITE)
        }
        return snackbar
    }

    override fun onDestroy() {
        NetworkConnectionManager.unsubscribe(this)
        super.onDestroy()
    }

    override fun onPermissionGranted(permission: String?) {
    }

    override fun onPermissionNotGranted(permission: String?) {
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        permissionsManager.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    /**
     * Checks if user has granted the specific permission
     */
    override fun isPermissionGranted(permission: String): Boolean {
        return permissionsManager.isPermissionGranted(permission)
    }

    /**
     * Request permissions explicitly
     */
    override fun requestPermissions() {
        return permissionsManager.requestAppPermissions()
    }

}
