package co.yap.app.modules.login.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.app.BR
import co.yap.app.R
import co.yap.app.databinding.FragmentOtpBlockedInfoBinding
import co.yap.app.modules.login.interfaces.IOtpBlockedInfo
import co.yap.app.modules.login.viewmodels.OtpBlockedInfoViewModel
import co.yap.modules.dashboard.main.activities.YapDashboardActivity
import co.yap.modules.otp.GenericOtpFragment
import co.yap.modules.otp.OtpDataModel
import co.yap.modules.otp.getOtpMessageFromComposer
import co.yap.translation.Strings
import co.yap.yapcore.BaseBindingFragment
import co.yap.yapcore.enums.OTPActions
import co.yap.yapcore.helpers.extentions.chatSetup
import co.yap.yapcore.helpers.extentions.makeCall
import co.yap.yapcore.helpers.extentions.makeLinks
import co.yap.yapcore.helpers.extentions.startFragmentForResult
import co.yap.yapcore.helpers.spannables.getText
import co.yap.yapcore.managers.SessionManager

class OtpBlockedInfoFragment : BaseBindingFragment<IOtpBlockedInfo.ViewModel>(),
    IOtpBlockedInfo.View {
    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.fragment_otp_blocked_info
    override val viewModel: IOtpBlockedInfo.ViewModel
        get() = ViewModelProviders.of(this).get(OtpBlockedInfoViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setObservers()
        viewModel.getHelpPhoneNo()
    }

    override fun setObservers() {
        viewModel.clickEvent.observe(this, onClickObserver)
        viewModel.onHelpNoSuccess.observe(this, Observer {
            if (it) setDetailTextView()
        })
    }

    private fun setDetailTextView() {
        if (SessionManager.user?.freezeInitiator != null) {
            getBinding().tvSubTitle.text = resources.getText(
                getString(Strings.screen_otp_blocked_display_text_details_card_blocked),
                viewModel.state.helpPhoneNo.get() ?: ""
            )
            getBinding().tvSubTitle.makeLinks(
                Pair(viewModel.state.helpPhoneNo.get() ?: "", View.OnClickListener {
                    requireContext().makeCall(viewModel.state.helpPhoneNo.get())
                }),
                Pair("Live Chat", View.OnClickListener {
                    requireActivity().chatSetup()
                })
            )
        } else if (SessionManager.user?.otpBlocked == true) {
            getBinding().tvSubTitle.text = resources.getText(
                getString(Strings.screen_otp_blocked_display_text_details),
                viewModel.state.helpPhoneNo.get() ?: ""
            )
            getBinding().tvSubTitle.makeLinks(
                Pair(viewModel.state.helpPhoneNo.get() ?: "", View.OnClickListener {
                    requireContext().makeCall(viewModel.state.helpPhoneNo.get())
                }),
                Pair("Live Chat", View.OnClickListener {
                    requireActivity().chatSetup()
                })
            )
        }
    }

    private val onClickObserver = Observer<Int> {
        when (it) {
            R.id.btnGoToDashboard -> {
//                startActivity(Intent(requireContext(), YapDashboardActivity::class.java))
//                activity?.finish()

                //TODO this is just  for testing we will remove this after proper implementation For KYC INFO
                startOtpFragment()
            }
        }
    }

    private fun getBinding(): FragmentOtpBlockedInfoBinding {
        return (viewDataBinding as FragmentOtpBlockedInfoBinding)
    }

    override fun removeObservers() {
        viewModel.clickEvent.removeObserver(onClickObserver)
        viewModel.onHelpNoSuccess.removeObservers(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        removeObservers()
    }
    //TODO this is just  for testing we will remove this after proper implementation For KYC INFO
    private fun startOtpFragment() {
        startFragmentForResult<GenericOtpFragment>(
            GenericOtpFragment::class.java.name,
            bundleOf(
                OtpDataModel::class.java.name to OtpDataModel(
                    OTPActions.EMPLOYMENT_AMENDMENT.name,//action,
                    otpMessage = "Message Required",
                    mobileNumber = "+971890990818"
                )
            ),
            showToolBar = true
        ) { resultCode, _ ->
            if (resultCode == Activity.RESULT_OK) {
                showToast("OTP is accepted")
            }
        }
    }
}