package co.yap.app.modules.login.fragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import co.yap.app.BR
import co.yap.app.R
import co.yap.app.activities.MainActivity
import co.yap.app.constants.Constants
import co.yap.app.modules.login.interfaces.ISystemPermission
import co.yap.app.modules.login.viewmodels.SystemPermissionViewModel
import co.yap.yapcore.BaseBindingFragment
import co.yap.yapcore.helpers.SharedPreferenceManager

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class SystemPermissionFragment : BaseBindingFragment<ISystemPermission.ViewModel>(), ISystemPermission.View {

    private lateinit var sharedPreferenceManager: SharedPreferenceManager

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_biometric_permission

    override val viewModel: ISystemPermission.ViewModel
        get() = ViewModelProviders.of(this).get(SystemPermissionViewModel::class.java)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPreferenceManager = SharedPreferenceManager(context as MainActivity)

        viewModel.screenType = getScreenType()
        viewModel.registerLifecycleOwner(this)

        viewModel.permissionGrantedPressEvent.observe(this, permissionGrantedObserver)
        viewModel.permissionNotGrantedPressEvent.observe(this, permissionNotGrantedObserver)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.permissionGrantedPressEvent.removeObservers(this)
        viewModel.permissionNotGrantedPressEvent.removeObservers(this)
    }

    private val permissionGrantedObserver = Observer<Boolean> {
        if (viewModel.screenType == Constants.TOUCH_ID_SCREEN_TYPE) {
            sharedPreferenceManager.save(SharedPreferenceManager.KEY_TOUCH_ID_ENABLED, true)
            val action =
                SystemPermissionFragmentDirections.actionSystemPermissionFragmentToSystemPermissionFragmentNotification(
                    Constants.NOTIFICATION_SCREEN_TYPE
                )
            findNavController().navigate(action)
        } else {
            navigateToDashboard()
        }
    }

    private val permissionNotGrantedObserver = Observer<Boolean> {
        if (viewModel.screenType == Constants.TOUCH_ID_SCREEN_TYPE) {
            sharedPreferenceManager.save(SharedPreferenceManager.KEY_TOUCH_ID_ENABLED, false)
            val action =
                SystemPermissionFragmentDirections.actionSystemPermissionFragmentToSystemPermissionFragmentNotification(
                    Constants.NOTIFICATION_SCREEN_TYPE
                )
            findNavController().navigate(action)
        } else {
            navigateToDashboard()
        }
    }

    private fun navigateToDashboard() {
        findNavController().navigate(R.id.action_goto_yapDashboardActivity)
        activity?.finish()
    }

    private fun getScreenType(): String {
        return arguments?.let { SystemPermissionFragmentArgs.fromBundle(it).screenType } as String
    }

}
