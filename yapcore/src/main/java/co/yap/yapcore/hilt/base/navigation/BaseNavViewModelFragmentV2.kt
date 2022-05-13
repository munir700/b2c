package co.yap.yapcore.hilt.base.navigation

import android.os.Bundle
import android.transition.ChangeBounds
import android.transition.Fade
import android.transition.Slide
import androidx.annotation.IdRes
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import androidx.navigation.fragment.findNavController
import co.yap.yapcore.IBase
import co.yap.yapcore.R
import co.yap.yapcore.dagger.base.interfaces.ManageToolBarListener
import co.yap.yapcore.dagger.base.navigation.*
import co.yap.yapcore.hilt.base.fragment.BaseViewModelFragmentV2
import co.yap.yapcore.hilt.base.viewmodel.HiltBaseViewModel
import kotlin.properties.Delegates

/**
 * Created by Safi ur Rehman
 */
private const val ARGUMENT_NAVIGATION_REQUEST_CODE = "NAVIGATION_REQUEST_CODE"
const val DESTINATION_NOT_SET = -1
const val REQUEST_CODE_NOT_SET = -1

const val NAVIGATION_RESULT_CANCELED = 0
const val NAVIGATION_RESULT_OK = -1

abstract class BaseNavViewModelFragmentV2<VB : ViewDataBinding, S : IBase.State, VM : HiltBaseViewModel<S>> :
    BaseViewModelFragmentV2<VB, S, VM>() {
    protected open val hasUpNavigation: Boolean = true
    private val requestCode: Int
        get() = arguments?.getInt(ARGUMENT_NAVIGATION_REQUEST_CODE, REQUEST_CODE_NOT_SET)
            ?: REQUEST_CODE_NOT_SET

    override fun postExecutePendingBindings(savedInstanceState: Bundle?) {
        super.postExecutePendingBindings(savedInstanceState)
        if (activity is ManageToolBarListener) {
            (activity as ManageToolBarListener).toolBarTitle = getToolBarTitle()
            (activity as ManageToolBarListener).toolBarVisibility = toolBarVisibility()
            (activity as ManageToolBarListener).displayHomeAsUpEnabled = setDisplayHomeAsUpEnabled()
            (activity as ManageToolBarListener).homeAsUpIndicator = setHomeAsUpIndicator()
        }
    }

    override fun getToolBarTitle(): String? = null
    override fun toolBarVisibility(): Boolean? = true
    override fun setDisplayHomeAsUpEnabled(): Boolean? = true
    override fun setHomeAsUpIndicator() = R.drawable.ic_back_arrow_left

    /**
     * Navigates to the specified destination screen.
     *
     * @param destinationId the id of the destination screen (either the new Activity or Fragment)
     * @param extras the extra arguments to be passed to the destination screen
     * @param navigationExtras
     */
    fun navigate(
        @IdRes destinationId: Int,
        extras: Bundle? = null,
        navigationExtras: Navigator.Extras? = null
    ) {
        findNavController().navigate(
            destinationId,
            extras,
            null,
            navigationExtras
        )
    }

    /**
     * Navigates to the specified destination screen.
     *
     * @param directions the direction that leads to the destiantion screen.
     * @param navigationExtras
     */
    protected fun navigate(directions: NavDirections, navigationExtras: Navigator.Extras? = null) {
        navigationExtras?.let { navExtras ->
            findNavController().navigate(directions, navExtras)
        } ?: run {
            findNavController().navigate(directions)
        }
    }

    /**
     * Navigates back (pops the back stack) to the previous [MvvmFragment] on the stack.
     */
    protected fun navigateBack() {
        findNavController().popBackStack()
    }

    protected fun navigateUp(): Boolean = findNavController().navigateUp()

    protected fun navigateForResultWithAnimation(
        requestCode: Int, navDirections: NavDirections, navOptions: NavOptions? = null
    ) {
        // val extras = FragmentNavigatorExtras(appBarLayout to appBarTransition)
        this.exitTransition = Fade()
        navigateForResult(
            resId = navDirections.actionId,
            requestCode = requestCode,
            args = navDirections.arguments,
            navOptions = navOptions,
            navigatorExtras = null
        )
    }

    protected fun navigateForResult(
        requestCode: Int, navDirections: NavDirections, navOptions: NavOptions? = null,
        navigatorExtras: Navigator.Extras? = null
    ) =
        navigateForResult(
            resId = navDirections.actionId,
            requestCode = requestCode,
            args = navDirections.arguments,
            navOptions = navOptions,
            navigatorExtras = navigatorExtras
        )

    protected fun navigateForResult(
        @IdRes resId: Int, requestCode: Int, args: Bundle? = null, navOptions: NavOptions? = null,
        navigatorExtras: Navigator.Extras? = null
    ) {
        val argsWithRequestCode = (args ?: Bundle()).apply {
            putInt(ARGUMENT_NAVIGATION_REQUEST_CODE, requestCode)
        }
        navigatorExtras?.let {
            findNavController().navigate(
                resId,
                argsWithRequestCode,
                navOptions,
                navigatorExtras
            )
        } ?: findNavController().navigate(resId, argsWithRequestCode, navOptions)
    }

    protected fun navigateForwardWithAnimation(navDirections: NavDirections) {
        // val extras = FragmentNavigatorExtras(appBarLayout to appBarTransition)
        this.exitTransition = Slide()
        findNavController().navigate(navDirections)
    }

    protected fun navigateForwardWithAnimation(
        navDirections: NavDirections,
        args: Bundle?,
        exitTransition: Any? = Slide()
    ) {
        // val extras = FragmentNavigatorExtras(appBarLayout to appBarTransition)
        // exitTransition?.let { this.exitTransition = it }
//        this.enterTransition = Slide(Gravity.RIGHT)
        navigateForResult(navDirections.actionId, REQUEST_CODE_NOT_SET, args)
    }

    protected fun navigateForward(
        navDirections: NavDirections,
        args: Bundle?
    ) {
        navigateForResult(navDirections.actionId, REQUEST_CODE_NOT_SET, args)
    }

    protected fun navigateBackWithResult(resultCode: Int, data: Bundle? = null): Boolean =
        navigateBackWithResult(
            DESTINATION_NOT_SET,
            BackNavigationResult(requestCode, resultCode, data)
        )

    protected fun navigateBackWithResult(
        @IdRes destination: Int,
        resultCode: Int,
        data: Bundle? = null
    ): Boolean =
        navigateBackWithResult(destination, BackNavigationResult(requestCode, resultCode, data))

    protected fun initEnterTransitions() {
        sharedElementEnterTransition = ChangeBounds()
        enterTransition = Fade()
    }

    private fun navigateBackWithResult(
        @IdRes destination: Int,
        result: BackNavigationResult
    ): Boolean {
        val childFragmentManager =
            requireActivity().supportFragmentManager.findFragmentById(R.id.nav_host_fragment)?.childFragmentManager
        var backStackListener: FragmentManager.OnBackStackChangedListener by Delegates.notNull()
        backStackListener = FragmentManager.OnBackStackChangedListener {
            (childFragmentManager?.fragments?.get(0) as? BackNavigationResultListener)?.onNavigationResult(
                result
            )
            childFragmentManager?.removeOnBackStackChangedListener(backStackListener)
        }
        childFragmentManager?.addOnBackStackChangedListener(backStackListener)
        val backStackPopped = if (destination == DESTINATION_NOT_SET) {
            findNavController().popBackStack()
        } else {
            findNavController().popBackStack(destination, true)
        }
        if (!backStackPopped) {
            childFragmentManager?.removeOnBackStackChangedListener(backStackListener)
        }
        return backStackPopped
    }
}