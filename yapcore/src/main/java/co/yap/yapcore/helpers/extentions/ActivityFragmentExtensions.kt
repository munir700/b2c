package co.yap.yapcore.helpers.extentions

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import co.yap.modules.frame.FrameActivity

/**
 * Extensions for simpler launching of Activities
 */

inline fun <reified T : Any> Activity.launchActivity(
    requestCode: Int = -1,
    options: Bundle? = null,
    noinline init: Intent.() -> Unit = {}
) {

    val intent = newIntent<T>(this)
    intent.init()
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
        startActivityForResult(intent, requestCode, options)
    } else {
        startActivityForResult(intent, requestCode)
    }
}

inline fun <reified T : Any> Context.launchActivity(
    options: Bundle? = null,
    noinline init: Intent.() -> Unit = {}
) {

    val intent = newIntent<T>(this)
    intent.init()
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
        startActivity(intent, options)
    } else {
        startActivity(intent)
    }
}

inline fun <reified T : Any> newIntent(context: Context): Intent =
    Intent(context, T::class.java)

/**
 * Extension method to get a new Intent for an Activity class
 */
inline fun <reified T : Any> Context.intent() = Intent(this, T::class.java)

/**
 * Create an intent for [T] and apply a lambda on it
 */
inline fun <reified T : Any> Context.intent(body: Intent.() -> Unit): Intent {
    val intent = Intent(this, T::class.java)
    intent.body()
    return intent
}

/**
 * Extension method to startActivity for Context.
 */
inline fun <reified T : Activity> Context?.startActivity() =
    this?.startActivity(Intent(this, T::class.java))


fun <T : Fragment> FrameActivity.instantiateFragment(fragmentName: String) =
    Fragment.instantiate(this, fragmentName)

fun Fragment.instantiateFragment(fragmentName: String) {
    Fragment.instantiate(this.requireActivity(), fragmentName)
   // FragmentFactory.instantiate
}

