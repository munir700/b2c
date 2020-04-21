package co.yap.yapcore.helpers.extentions

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Parcelable
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import androidx.annotation.Keep
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import co.yap.yapcore.R
import co.yap.yapcore.helpers.Utils
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.navigation.NavigationView

@Keep
enum class ExtraType {
    STRING, INT, BOOLEAN, DOUBLE, LONG, PARCEABLE;
}

fun Intent.getValue(key: String, type: String): Any? {
    return if (hasExtra(key)) {
        return if (ExtraType.valueOf(type).name.isNotEmpty()) {
            when (ExtraType.valueOf(type)) {
                ExtraType.BOOLEAN ->
                    getBooleanExtra(key, false)
                ExtraType.STRING ->
                    getStringExtra(key)
                ExtraType.DOUBLE ->
                    getDoubleExtra(key, 0.0)
                ExtraType.INT ->
                    getIntExtra(key, 0)
                ExtraType.LONG ->
                    getLongExtra(key, 0)
                ExtraType.PARCEABLE ->
                    getParcelableExtra<Parcelable>(key)
            }
        } else null
    } else return null
}

fun Activity.preventTakeScreenShot(isPrevent: Boolean) {
    if (isPrevent)
        window?.addFlags(WindowManager.LayoutParams.FLAG_SECURE)
    else
        window?.clearFlags(WindowManager.LayoutParams.FLAG_SECURE)
}

fun Fragment.preventTakeScreenShot(isPrevent: Boolean) {
    if (isPrevent)
        activity?.window?.addFlags(WindowManager.LayoutParams.FLAG_SECURE)
    else
        activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_SECURE)
}

fun ImageView.loadImage(path: String, requestOptions: RequestOptions) {
    Glide.with(this)
        .load(path)
        .apply(requestOptions)
        .into(this)
}

fun ImageView.loadImage(resourceId: Int, requestOptions: RequestOptions) {
    Glide.with(this)
        .load(resourceId)
        .apply(requestOptions)
        .into(this)
}

fun ImageView.loadImage(path: String) {
    Glide.with(this)
        .load(path).centerCrop()
        .into(this)
}
fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//            if (p0?.length ?: 0 > 0) {
//                this@afterTextChanged.gravity = Gravity.CENTER_HORIZONTAL or Gravity.CENTER_VERTICAL
//            } else {
//                this@afterTextChanged.gravity = Gravity.CENTER_HORIZONTAL or Gravity.CENTER_VERTICAL
//            }
        }

        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }
    })
}

fun AppCompatActivity.replaceFragment(tag: String?, id: Int, fragment: Fragment) {
    val fragmentTransaction = supportFragmentManager.beginTransaction()
    fragmentTransaction.replace(id, fragment, tag)
    fragmentTransaction.commit()
}

fun RecyclerView.fixSwipeToRefresh(refreshLayout: SwipeRefreshLayout): RecyclerViewSwipeToRefresh {
    return RecyclerViewSwipeToRefresh(refreshLayout).also {
        this.addOnScrollListener(it)
    }
}


class RecyclerViewSwipeToRefresh(private val refreshLayout: SwipeRefreshLayout) :
    RecyclerView.OnScrollListener() {
    companion object {
        private const val DIRECTION_UP = -1
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        refreshLayout.isEnabled = !(recyclerView?.canScrollVertically(DIRECTION_UP) ?: return)
    }

}


fun NavigationView?.navViewWidth(percent: Int) {
    this?.let {
        val params = it.layoutParams
        params.width = Utils.getDimensionInPercent(it.context, true, percent)
    }
}

fun Context?.isNetworkAvailable(): Boolean {
    return this?.let {
        val connectivityManager =
            it.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
        connectivityManager?.let {
            connectivityManager.activeNetworkInfo?.let {
                return connectivityManager.activeNetworkInfo.isConnected
            } ?: false
        } ?: false
    } ?: false
}
