package co.yap.yapcore

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.*
import androidx.navigation.fragment.findNavController

abstract class BaseNavFragment : Fragment() {

    val defaultAnimation: AnimBuilder = anim {
        enter = R.anim.slide_in_right
        exit = R.anim.slide_out_left
        popEnter = R.anim.slide_in_left
        popExit = R.anim.slide_out_right
    }

    val defaultNavOptions = navOptions {
        anim { defaultAnimation }
    }

    protected fun navigate(destinationId: Int, args: Bundle? = null, navOptions: NavOptions? = defaultNavOptions) {
        findNavController().navigate(destinationId, args, navOptions)
    }

    protected fun navigate(destinationId: Int, args: Bundle? = null, optionsBuilder: NavOptions.Builder.() -> Unit?) {
        findNavController().navigate(destinationId, args, NavOptions.Builder().apply {
            setEnterAnim(defaultAnimation.enter)
            setExitAnim(defaultAnimation.exit)
            setPopEnterAnim(defaultAnimation.popEnter)
            setPopExitAnim(defaultAnimation.popExit)
            optionsBuilder
        }.build())
    }

    protected fun navigateBack(destinationId: Int = -1, inclusive: Boolean = false) {
        if (destinationId != -1) {
            findNavController().popBackStack(destinationId, inclusive)
        } else {
            findNavController().popBackStack()
        }
    }

    fun anim(animBuilder: AnimBuilder.() -> Unit): AnimBuilder = AnimBuilder().apply(animBuilder)

}