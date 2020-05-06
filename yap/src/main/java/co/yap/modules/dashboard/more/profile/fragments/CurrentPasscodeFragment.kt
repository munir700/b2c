package co.yap.modules.dashboard.more.profile.fragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import co.yap.BR
import co.yap.R
import co.yap.modules.dashboard.more.main.activities.MoreActivity
import co.yap.modules.dashboard.more.profile.viewmodels.CurrentPasscodeViewModel
import co.yap.modules.setcardpin.pinflow.IPin
import co.yap.yapcore.BaseBindingFragment
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.constants.Constants.KEY_IS_USER_LOGGED_IN
import co.yap.yapcore.databinding.FragmentPinBinding
import co.yap.yapcore.helpers.SharedPreferenceManager
import co.yap.yapcore.helpers.Utils
import co.yap.yapcore.helpers.extentions.toast
import kotlinx.android.synthetic.main.activity_create_passcode.*

open class CurrentPasscodeFragment : BaseBindingFragment<IPin.ViewModel>(), IPin.View {
    private lateinit var sharedPreferenceManager: SharedPreferenceManager

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_pin

    override val viewModel: IPin.ViewModel
        get() = ViewModelProviders.of(this).get(CurrentPasscodeViewModel::class.java)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setObservers()
        if (context is MoreActivity)
            (context as MoreActivity).goneToolbar()
        sharedPreferenceManager = SharedPreferenceManager(requireContext())
        viewModel.forgotPasscodeclickEvent.observe(this, Observer {

            if (sharedPreferenceManager.getValueBoolien(
                    KEY_IS_USER_LOGGED_IN,
                    false
                )
            ) {
                sharedPreferenceManager.getDecryptedUserName()?.let {
                    val action =
                        CurrentPasscodeFragmentDirections.actionCurrentPasscodeFragmentToForgotPasscodeNavigation(
                            it,
                            !Utils.isUsernameNumeric(it),
                            viewModel.mobileNumber,
                            Constants.FORGOT_PASSCODE_FROM_CHANGE_PASSCODE
                        )
                    findNavController().navigate(action)
                } ?: toast("Invalid username found")
            }

        })

        viewModel.errorEvent.observe(this, Observer {
            dialer.startAnimation()
        })

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getBinding().dialer.updateDialerLength(6)
        getBinding().dialer.upDatedDialerPad(viewModel.state.pincode)
        getBinding().dialer.hideFingerprintView()
        if (activity is MoreActivity) {
            (activity as MoreActivity).viewModel.preventTakeDeviceScreenShot.value = true
        }
    }

    override fun setObservers() {
        viewModel.clickEvent.observe(this, Observer {
            when (it) {
                R.id.btnAction -> {
                    findNavController().navigate(R.id.action_currentPasscodeFragment_to_updateNewPasscodeFragment)
                }
            }
        })

    }

    override fun onDestroy() {
        viewModel.forgotPasscodeclickEvent.removeObservers(this)
        viewModel.clickEvent.removeObservers(this)
        super.onDestroy()
    }

    override fun loadData() {

    }

    fun getBinding(): FragmentPinBinding {
        return viewDataBinding as FragmentPinBinding
    }
}