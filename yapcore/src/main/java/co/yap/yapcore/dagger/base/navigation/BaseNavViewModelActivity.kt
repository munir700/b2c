package co.yap.yapcore.dagger.base.navigation

import android.os.Bundle
import android.view.MenuItem
import androidx.annotation.CallSuper
import androidx.annotation.IdRes
import androidx.annotation.NavigationRes
import androidx.appcompat.widget.Toolbar
import androidx.databinding.ViewDataBinding
import androidx.navigation.*
import co.yap.yapcore.IBase
import co.yap.yapcore.R
import co.yap.yapcore.constants.Constants.EXTRA
import co.yap.yapcore.dagger.base.BaseViewModelActivity
import co.yap.yapcore.dagger.base.MvvmNavHostFragment
import co.yap.yapcore.dagger.base.interfaces.ManageToolBarListener
import co.yap.yapcore.dagger.base.viewmodel.DaggerBaseViewModel
import co.yap.yapcore.helpers.extentions.addExtras
import co.yap.yapcore.helpers.extentions.bindView
import co.yap.yapcore.helpers.extentions.plus

/**
 * A base BaseNavViewModel Activity with built-in support for Android X Navigation Concept and ViewModel.
 */
abstract class BaseNavViewModelActivity<VB : ViewDataBinding, S : IBase.State, VM : DaggerBaseViewModel<S>> :
    BaseViewModelActivity<VB, S, VM>(), ManageToolBarListener {

    /**
     * Used to obtain the exact id of the navigation graph to be used by this activity.
     *
     * @return the id of the navigation graph
     */
    @get:NavigationRes
    protected open val navigationGraphId: Int = 0

    /**
     * Override this property to specify a custom Start Destination.
     *
     * @return the exact id of the destination to be used as the starting one.
     */
    @get:IdRes
    protected open val navigationGraphStartDestination: Int = 0

    /**
     * Accesses the The NavController associated with the current activity.
     */
    private val navController: NavController
        get() = findNavController(R.id.nav_host_fragment)

    /**
     * The initial input to be provided to the start destination fragment.
     */
    protected open var startDestinationInput: Bundle? = Bundle()
    protected open var extrasBundle = Bundle()
    private var navHostFragment: MvvmNavHostFragment? = null

    @CallSuper
    override fun init(savedInstanceState: Bundle?) {
        initNavigationGraph()
    }

    override fun preInit(savedInstanceState: Bundle?) {
        super.preInit(savedInstanceState)
        if (intent?.hasExtra(EXTRA) == true) {
            startDestinationInput = intent?.getBundleExtra(EXTRA)
        }
        intent?.extras?.let(::fetchExtras)
    }

    private val onDestinationChangedListener =
        NavController.OnDestinationChangedListener { controller, destination, arguments ->
            onDestinationChanged(controller, destination, arguments)
        }

    override fun onResume() {
        super.onResume()
        navController.addOnDestinationChangedListener(onDestinationChangedListener)
    }

    override fun onPause() {
        super.onPause()
        navController.removeOnDestinationChangedListener(onDestinationChangedListener)
    }

    abstract fun onDestinationChanged(
        controller: NavController?,
        destination: NavDestination?,
        arguments: Bundle?
    )

    /**
     * Gets called right before the pre-initialization stage ([preInit] method call),
     * if the received [Bundle] is not null.
     *
     * @param extras the bundle of arguments
     */
    @CallSuper
    override fun fetchExtras(extras: Bundle?) {
        extras?.let { extrasBundle = it }
    }

    override fun postExecutePendingBindings(savedInstanceState: Bundle?) {
        super.postExecutePendingBindings(savedInstanceState)
        val toolbar: Toolbar? by bindView(
            R.id.toolbar
        )
        setupToolbar(toolbar)
    }

    override fun setupToolbar(toolbar: Toolbar?) {
        var toolBar = toolbar
        if (toolBar == null)
            toolBar = bindView<Toolbar>(R.id.toolbar).value
        toolBar.apply {
            title = ""
            setNavigationIcon(R.drawable.ic_back_arrow_left)
            setSupportActionBar(this)
            displayHomeAsUpEnabled = true
            homeAsUpIndicator = R.drawable.ic_back_arrow_left
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?) =
        when (item?.itemId) {
            android.R.id.home -> {
                hideKeyboard()
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    override var toolBarTitle: String? = ""
        set(value) {
            field = value
            field?.let { viewModel.state.toolbarTitle = it }

        }
    override var toolBarVisibility: Boolean? = true
        set(value) {
            field = value
            field?.let { viewModel.state.toolsBarVisibility = it }
        }
    override var displayHomeAsUpEnabled: Boolean? = true
        set(value) {
            supportActionBar?.apply {
                field = value
                field?.let {
                    setDisplayHomeAsUpEnabled(it)
                    setHomeButtonEnabled(it)
                    setDisplayShowCustomEnabled(it)
                }

            }
        }
    override var homeAsUpIndicator: Int? = R.drawable.ic_back_arrow_left
        set(value) {
            supportActionBar?.apply {
                field = value
                field?.let { setHomeAsUpIndicator(it) }

            }
        }

    fun setToolbarTitle(listener: ManageToolBarListener) {
        listener.toolBarTitle?.let {
            viewModel.state.toolbarTitle = it
        }
        listener.toolBarVisibility?.let {
            viewModel.state.toolsBarVisibility = it
        }
    }

    private fun getCurrentFragment() = navHostFragment?.childFragmentManager?.fragments?.get(0)


    private fun initNavigationGraph() {
        try {
            navHostFragment =
                supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as MvvmNavHostFragment?
            navHostFragment?.navController?.apply {
                graph = navInflater.inflate(navigationGraphId).also {
                    it.startDestination =
                        (if (navigationGraphStartDestination != 0) navigationGraphStartDestination else it.startDestination)
                    it.addExtras(extrasBundle.plus(startDestinationInput ?: Bundle()))
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            throw RuntimeException(e.message)
        }
    }


    /**
     * Navigates to the specified destination screen.
     *
     * @param destinationId the id of the destination screen (either the new Activity or Fragment)
     * @param extras the extra arguments to be passed to the destination screen
     */
    protected fun navigate(@IdRes destinationId: Int, extras: Bundle? = null) {
        navController.navigate(destinationId, extras)
    }


    /**
     * Navigates to the specified destination screen.
     *
     * @param directions the direction that leads to the destiantion screen.
     * @param navigationExtras
     */
    protected fun navigate(directions: NavDirections, navigationExtras: Navigator.Extras? = null) {
        navigationExtras?.let { navExtras ->
            navController.navigate(directions, navExtras)
        } ?: run {
            navController.navigate(directions)
        }
    }
}
