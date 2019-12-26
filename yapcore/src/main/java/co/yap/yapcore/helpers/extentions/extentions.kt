package co.yap.yapcore.helpers.extentions

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

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
        }
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }
    })
}

fun AppCompatActivity.addFragment(tag: String?, id: Int, fragment: Fragment) {
    val fragmentTransaction = supportFragmentManager.beginTransaction()
    fragmentTransaction.add(id, fragment, tag)
    fragmentTransaction.addToBackStack(tag)
    fragmentTransaction.commit()
}

fun AppCompatActivity.replaceFragment(tag: String?, id: Int, fragment: Fragment) {
    val fragmentTransaction = supportFragmentManager.beginTransaction()
    fragmentTransaction.replace(id, fragment, tag)
    fragmentTransaction.commit()
}

fun Fragment.addFragment(tag: String?, id: Int, fragmentManager: FragmentManager) {
    val fragmentTransaction = fragmentManager.beginTransaction()
    fragmentTransaction.add(id, this, tag)
    fragmentTransaction.addToBackStack(tag)
    fragmentTransaction.commit()
}

fun Fragment.replaceFragment(tag: String?, id: Int, fragmentManager: FragmentManager) {
    val fragmentTransaction = fragmentManager.beginTransaction()
    fragmentTransaction.replace(id, this, tag)
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

fun View?.hideKeyboard() {
    this?.let { v ->
        val imm =
            this.context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        imm?.hideSoftInputFromWindow(v.windowToken, 0)
    }
}
