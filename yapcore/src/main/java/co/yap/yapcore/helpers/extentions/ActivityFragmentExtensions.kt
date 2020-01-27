package co.yap.yapcore.helpers.extentions

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.annotation.AnimRes
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import co.yap.modules.frame.FrameActivity
import co.yap.modules.frame.FrameActivity.Companion.EXTRA
import co.yap.modules.frame.FrameActivity.Companion.FRAGMENT_CLASS
import co.yap.yapcore.R
import com.github.florent37.inlineactivityresult.kotlin.startForResult

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

fun <T : Fragment> Fragment.removeFragment(fragment: Fragment) {
    val ft = requireFragmentManager().beginTransaction()
    ft.remove(fragment)
    ft.commitNow()
}

fun Fragment.removeFragmentByTag(tag: String) {
    val ft = requireFragmentManager().beginTransaction()
    val fragment = requireFragmentManager().findFragmentByTag(tag) ?: return
    ft.remove(fragment)
    ft.commitNow()
}

fun Fragment.removeFragmentById(id: Int) {
    val ft = requireFragmentManager().beginTransaction()
    val fragment = requireFragmentManager().findFragmentById(id) ?: return
    ft.remove(fragment)
    ft.commitNow()
}

fun <T : Fragment> FragmentActivity.createFragmentInstance(
    fragment: T,
    bundle: Bundle = Bundle()
): T {
    fragment.arguments = bundle
    replaceFragment(fragment, R.id.container, bundle)
    return fragment
}

fun <T : Fragment> FragmentActivity.createFragmentInstance(fragment: T): T {
    replaceFragment(fragment, R.id.container)
    return fragment
}

fun FragmentActivity.replaceFragment(
    fragment: Fragment, @IdRes container: Int, bundle: Bundle = Bundle(),
    addToBackStack: Boolean = false, backStackName: String = "",
    @AnimRes inAnimationRes: Int = 0, @AnimRes outAnimationRes: Int = 0
) {
    val ft = supportFragmentManager.beginTransaction()
    if (inAnimationRes != 0 && outAnimationRes != 0) {
        ft.setCustomAnimations(inAnimationRes, outAnimationRes)
    }
    ft.replace(container, fragment)

    if (addToBackStack) {
        ft.addToBackStack(backStackName)
    }

    ft.commit()
}

fun FragmentActivity.addFragment(
    fragment: Fragment, @IdRes container: Int,
    addToBackStack: Boolean = false, backStackName: String = "",
    @AnimRes inAnimationRes: Int = 0, @AnimRes outAnimationRes: Int = 0
) {
    val ft = supportFragmentManager.beginTransaction()
    if (inAnimationRes != 0 && outAnimationRes != 0) {
        ft.setCustomAnimations(inAnimationRes, outAnimationRes)
    }
    ft.add(container, fragment)

    if (addToBackStack) {
        ft.addToBackStack(backStackName)
    }

    ft.commit()
}

fun <T : Fragment> FragmentActivity.startFragment(
    fragmentName: String,
    clearAllPrevious: Boolean = false,
    bundle: Bundle = Bundle(), requestCode: Int = -1
) {
    val intent = Intent(this, FrameActivity::class.java)
    intent.putExtra(FRAGMENT_CLASS, fragmentName)
    intent.putExtra(EXTRA, bundle)
    if (requestCode > 0) {
        startActivityForResult(intent, requestCode)
    } else {
        startActivity(intent)
    }

    if (clearAllPrevious) {
        finish()
    }
}

fun Fragment.startFragment(
    fragmentName: String,
    clearAllPrevious: Boolean = false,
    bundle: Bundle = Bundle(), requestCode: Int = -1
) {
    val intent = Intent(requireActivity(), FrameActivity::class.java)
    intent.putExtra(FRAGMENT_CLASS, fragmentName)
    intent.putExtra(EXTRA, bundle)
    if (requestCode > 0) {
        startActivityForResult(intent, requestCode)
    } else {
        startActivity(intent)
    }

    if (clearAllPrevious) {
        requireActivity().finish()
    }
}


fun <T : Fragment> FragmentActivity.startFragmentForResult(
    fragmentName: String,
    bundle: Bundle = Bundle(),
    completionHandler: ((resultCode: Int, data: Intent?) -> Unit)? = null
) {
    val intent = Intent(this, FrameActivity::class.java)
    try {
        intent.putExtra(FRAGMENT_CLASS, fragmentName)
        intent.putExtra(EXTRA, bundle)

        (this as AppCompatActivity).startForResult(intent) { result ->
            completionHandler?.invoke(result.resultCode, result.data)
        }.onFailed { result ->
            completionHandler?.invoke(result.resultCode, result.data)
        }

    }
    catch (e: Exception) {
        if (e is ClassNotFoundException) {
            toast(
                "InlineActivityResult library not installed falling back to default method, please install \" +\n" +
                        "\"it from https://github.com/florent37/InlineActivityResult if you want to get inline activity results."
            )
            startActivity(intent)
        }
    }
}

fun <T : Fragment> Fragment.startFragmentForResult(
    fragmentName: String,
    bundle: Bundle = Bundle(),
    completionHandler: ((resultCode: Int, data: Intent?) -> Unit)? = null
) {
    val intent = Intent(requireActivity(), FrameActivity::class.java)
    try {
        intent.putExtra(FRAGMENT_CLASS, fragmentName)
        intent.putExtra(EXTRA, bundle)

        this.startForResult(intent) { result ->
            completionHandler?.invoke(result.resultCode, result.data)
        }.onFailed { result ->
            completionHandler?.invoke(result.resultCode, result.data)
        }

    }
    catch (e: Exception) {
        if (e is ClassNotFoundException) {
            toast(
                "InlineActivityResult library not installed falling back to default method, please install \" +\n" +
                        "\"it from https://github.com/florent37/InlineActivityResult if you want to get inline activity results."
            )
            startActivity(intent)
        }
    }
}

