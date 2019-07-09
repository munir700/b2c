package co.yap.yapcore.interfaces

import androidx.fragment.app.Fragment

/**
 * Created by bilal on 6/6/14.
 */
class BackPressImpl(private val parentFragment: Fragment?) : OnBackPressedListener {

    override fun onBackPressed(): Boolean =
        run {
            if (parentFragment == null) return false
            (parentFragment.childFragmentManager.fragments[0] as? OnBackPressedListener)?.onBackPressed()!!
        }
}