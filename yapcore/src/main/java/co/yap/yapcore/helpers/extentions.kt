package co.yap.yapcore.helpers

import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
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