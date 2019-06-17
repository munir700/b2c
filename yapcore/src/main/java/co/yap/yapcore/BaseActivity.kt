package co.yap.yapcore

import android.annotation.TargetApi
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar

abstract class BaseActivity : AppCompatActivity(), IFragmentHolder, IBase.View {
    private var snackbar: Snackbar? = null
    private var DURATION_CODE = -2


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val window = this.window
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
    }

    private val progressDialogueFragment: ProgressDialogueFragment = ProgressDialogueFragment()

    @TargetApi(Build.VERSION_CODES.M)
    fun requestPermissionsSafely(permissions: Array<String>, requestCode: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, requestCode)
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    fun hasPermission(permission: String): Boolean {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M || checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED
    }

    fun hideKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    override fun showToast(msg: String) {
        if ("" != msg.trim { it <= ' ' }) {
            Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
        }
    }

    override fun onConnectivityChange(isAvailable: Boolean) {
        if (isAvailable) showSnackBar() else dissmissSnackBar()
    }

    fun showSnackBar() {
        snackbar = setSnackBar(
            getContext(),
            "No internet connection",
            Snackbar.LENGTH_INDEFINITE
        )

            .setAction("Settings",
                View.OnClickListener { getContext().startActivity(Intent(Settings.ACTION_WIFI_SETTINGS).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)) })
            .setActionTextColor(getContext().getResources().getColor(R.color.colorPrimary))
        snackbar!!.show()
        //(getContext() as MyApplication).mInternetConnectionListener.onInternetUnavailable()
    }
    fun dissmissSnackBar(){
        val snackbarConnected = setSnackBar(
            getContext(),
            "Internet connected.",
            Snackbar.LENGTH_SHORT
        )
        snackbarConnected.show()
        snackbar?.dismiss()
//        (context as MyApplication).mInternetConnectionListener.onInternetAvailble()
    }

    override fun getContext(): Context {
        return this
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

    private fun setSnackBar(context: Context, message: String, duration: Int): Snackbar {
        val activity = context as Activity
        val layout: View
        val snackbar = Snackbar
            .make(activity.findViewById(android.R.id.content), message, duration)
        layout = snackbar.view
        //      layout.setBackgroundColor(getThemeAccentColor(context));
        layout.setBackgroundColor(context.resources.getColor(R.color.colorDarkGreen))
        val text = layout.findViewById<View>(com.google.android.material.R.id.snackbar_text) as android.widget.TextView
        text.setTextColor(context.resources.getColor(R.color.colorWhite))

        if (duration == DURATION_CODE) {
            layout.setBackgroundColor(context.resources.getColor(R.color.colorGreyVeryDark))
            val snackbarView = snackbar.view
            val textView = snackbarView.findViewById<View>(com.google.android.material.R.id.snackbar_text) as TextView
            textView.setTextColor(Color.WHITE)
        }
        return snackbar

    }

}
