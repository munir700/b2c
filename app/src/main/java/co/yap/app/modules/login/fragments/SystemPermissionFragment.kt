package co.yap.app.modules.login.fragments

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import co.yap.app.BR
import co.yap.app.R
import co.yap.app.constants.Constants
import co.yap.app.modules.login.interfaces.ISystemPermission
import co.yap.app.modules.login.viewmodels.SystemPermissionViewModel
import co.yap.modules.webview.WebViewFragment
import co.yap.yapcore.BaseBindingFragment
import co.yap.yapcore.helpers.extentions.startFragment
import co.yap.yapcore.managers.SessionManager

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class SystemPermissionFragment : BaseBindingFragment<ISystemPermission.ViewModel>(),
    ISystemPermission.View {

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_biometric_permission

    override val viewModel: ISystemPermission.ViewModel
        get() = ViewModelProviders.of(this).get(SystemPermissionViewModel::class.java)

    private fun getScreenType(): String {
        return arguments?.let { SystemPermissionFragmentArgs.fromBundle(it).screenType } as String
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.screenType = getScreenType()
        viewModel.registerLifecycleOwner(this)
        setObservers()
    }

    override fun setObservers() {
        viewModel.clickEvent.observe(this, clickObserver)
    }

    override fun removeObservers() {
        viewModel.clickEvent.removeObserver(clickObserver)
    }

    private val clickObserver = Observer<Int> { view ->
        when (view) {
            R.id.tvTermsAndConditions -> {
                startFragment(
                    fragmentName = WebViewFragment::class.java.name, bundle = bundleOf(
                        co.yap.yapcore.constants.Constants.PAGE_URL to co.yap.yapcore.constants.Constants.URL_TERMS_CONDITION
                    ), showToolBar = false
                )
            }
            R.id.tvNoThanks -> {
                grantPermissions(false)
            }
            R.id.btnTouchId -> {
                grantPermissions(true)
            }

        }
    }

    private fun grantPermissions(isGranted: Boolean) {
        when (viewModel.screenType) {
            Constants.TOUCH_ID_SCREEN_TYPE -> {
                viewModel.getTouchScreenValues(isGranted)
                navigateFromTouchScreen()
            }
            Constants.NOTIFICATION_SCREEN_TYPE -> {
                viewModel.getNotificationScreenValues(isGranted)
                navigateToDashboard()
            }
            else -> {
            }
        }
    }

    private fun navigateFromTouchScreen() {
        val action =
            SystemPermissionFragmentDirections.actionSystemPermissionFragmentToSystemPermissionFragmentNotification(
                Constants.NOTIFICATION_SCREEN_TYPE
            )
        findNavController().navigate(action)
    }

    private fun navigateToDashboard() {
        if (SessionManager.user?.otpBlocked == true || SessionManager.user?.freezeInitiator != null)
            startFragment(fragmentName = OtpBlockedInfoFragment::class.java.name)
        else
            findNavController().navigate(R.id.action_goto_yapDashboardActivity)

        activity?.finish()
    }

    override fun onDestroyView() {
        removeObservers()
        super.onDestroyView()

    }
}
