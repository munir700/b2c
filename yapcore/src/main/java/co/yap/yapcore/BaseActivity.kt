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
import androidx.databinding.Observable
import co.yap.translation.Translator
import co.yap.yapcore.helpers.NetworkConnectionManager
import co.yap.yapcore.helpers.PermissionsManager
import com.google.android.material.snackbar.Snackbar
import java.util.*

abstract class BaseActivity<V : IBase.ViewModel<*>> : AppCompatActivity(), IBase.View<V>,
    NetworkConnectionManager.OnNetworkStateChangeListener, PermissionsManager.OnPermissionGrantedListener {

    private var snackbar: Snackbar? = null
    private var DURATION_CODE = -2
    private var checkConnectivity: Boolean = true
    private lateinit var permissionsManager: PermissionsManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        this.window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)

        NetworkConnectionManager.init(this)
        NetworkConnectionManager.subscribe(this)
        permissionsManager = PermissionsManager(this, this, this)
        registerStateListeners()

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
        if(checkConnectivity && isConnected){
            checkConnectivity=false

        }else{
            showInternetSnack(!isConnected)
            checkConnectivity=false
        }
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
            .setActionTextColor(resources.getColor(R.color.colorPrimary))
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

    override fun showLoader(isVisible: Boolean) {
        if (isVisible) {
            if (!progressDialogueFragment.isVisible && !progressDialogueFragment.isAdded) progressDialogueFragment.show(supportFragmentManager, "loading")
        } else {
            if (progressDialogueFragment.isVisible) progressDialogueFragment.dismiss()
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
            layout.setBackgroundColor(activity.resources.getColor(R.color.colorAccent))
            val snackbarView = snackbar.view
            val textView = snackbarView.findViewById<View>(com.google.android.material.R.id.snackbar_text) as TextView
            textView.setTextColor(Color.WHITE)
        }
        return snackbar
    }

    override fun onDestroy() {
        NetworkConnectionManager.unsubscribe(this)
        unregisterStateListeners()
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

    override fun getString(resourceKey: String): String = Translator.getString(this, resourceKey)

    private val stateObserver = object : Observable.OnPropertyChangedCallback() {
        override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
            if (propertyId == BR.toast && viewModel.state.toast.isNotBlank()) {
                showToast(viewModel.state.toast)
            }
            if (propertyId == BR.loading) {
                showLoader(viewModel.state.loading)
            }
        }
    }

    private fun registerStateListeners() {
        if (viewModel is BaseViewModel<*>) {
            viewModel.registerLifecycleOwner(this)
        }
        if (viewModel.state is BaseState) {
            (viewModel.state as BaseState).addOnPropertyChangedCallback(stateObserver)
        }
    }

    private fun unregisterStateListeners() {
        if (viewModel is BaseViewModel<*>) {
            viewModel.unregisterLifecycleOwner(this)
        }
        if (viewModel.state is BaseState) {
            (viewModel.state as BaseState).removeOnPropertyChangedCallback(stateObserver)
        }
    }

}
