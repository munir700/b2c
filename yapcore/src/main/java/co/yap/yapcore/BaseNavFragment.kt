package co.yap.yapcore

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions

abstract class BaseNavFragment : Fragment() {

    val options = navOptions {
        anim {
            enter = R.anim.slide_in_right
            exit = R.anim.slide_out_left
            popEnter = R.anim.slide_in_left
            popExit = R.anim.slide_out_right
        }
    }

    protected fun navigate(navFragmentId: Int, args: Bundle? = null, navOptions: NavOptions? = options) {
        findNavController().navigate(navFragmentId, args, navOptions)
    }

    protected fun navigateBack(destinationId: Int = -1, inclusive: Boolean = false) {
        if (destinationId != -1) {
            findNavController().popBackStack(destinationId, inclusive)
        } else {
            findNavController().popBackStack()
        }
    }
}