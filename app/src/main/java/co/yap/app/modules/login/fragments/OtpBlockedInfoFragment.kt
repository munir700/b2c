package co.yap.app.modules.login.fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.app.BR
import co.yap.app.R
import co.yap.app.databinding.FragmentOtpBlockedInfoBinding
import co.yap.app.modules.login.interfaces.IOtpBlockedInfo
import co.yap.app.modules.login.viewmodels.OtpBlockedInfoViewModel
import co.yap.modules.dashboard.main.activities.YapDashboardActivity
import co.yap.networking.CookiesManager
import co.yap.translation.Strings
import co.yap.yapcore.BaseBindingFragment
import co.yap.yapcore.helpers.extentions.makeCall
import co.yap.yapcore.helpers.extentions.makeLinks
import co.yap.yapcore.helpers.extentions.toast
import co.yap.yapcore.helpers.spannables.getText
import co.yap.yapcore.managers.MyUserManager
import com.liveperson.infra.ConversationViewParams
import com.liveperson.infra.InitLivePersonProperties
import com.liveperson.infra.LPAuthenticationParams
import com.liveperson.infra.LPConversationsHistoryStateToDisplay
import com.liveperson.infra.callbacks.InitLivePersonCallBack
import com.liveperson.messaging.sdk.api.LivePerson
import com.liveperson.messaging.sdk.api.model.ConsumerProfile

class OtpBlockedInfoFragment : BaseBindingFragment<IOtpBlockedInfo.ViewModel>(),
    IOtpBlockedInfo.View {
    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.fragment_otp_blocked_info
    override val viewModel: IOtpBlockedInfo.ViewModel
        get() = ViewModelProviders.of(this).get(OtpBlockedInfoViewModel::class.java)

    private val brandId = "17038977"
    private val appInstallId = MyUserManager.user?.uuid

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
        getBinding().tvSubTitle.text = resources.getText(
            getString(Strings.screen_otp_blocked_display_text_details),
            viewModel.state.helpPhoneNo.get() ?: ""
        )
        getBinding().tvSubTitle.makeLinks(
            Pair(viewModel.state.helpPhoneNo.get() ?: "", View.OnClickListener {
                requireContext().makeCall(viewModel.state.helpPhoneNo.get())
            }),
            Pair("Live Chat", View.OnClickListener {
                chatSetup()
            })
        )
    }

    private fun chatSetup() {
        LivePerson.initialize(
            requireContext(),
            InitLivePersonProperties(
                brandId, appInstallId,
                object : InitLivePersonCallBack {
                    override fun onInitSucceed() {
                        openActivity()
                    }
                    override fun onInitFailed(e: Exception) {
                        toast("Unable to open chat")
                    }
                })
        )
    }

    private fun openActivity() {
        val authParams = LPAuthenticationParams(LPAuthenticationParams.LPAuthenticationType.AUTH)
        authParams.hostAppJWT = CookiesManager.jwtToken
        val params = ConversationViewParams(false)
            .setHistoryConversationsStateToDisplay(LPConversationsHistoryStateToDisplay.OPEN)
            .setReadOnlyMode(false)
        LivePerson.showConversation(requireActivity(), authParams, params)
        val consumerProfile = ConsumerProfile.Builder()
            .setFirstName(MyUserManager.user?.currentCustomer?.firstName)
            .setLastName(MyUserManager.user?.currentCustomer?.lastName)
            .setPhoneNumber(MyUserManager.user?.currentCustomer?.getCompletePhone())
            .build()
        LivePerson.setUserProfile(consumerProfile)
    }

    private val onClickObserver = Observer<Int> {
        when (it) {
            R.id.btnGoToDashboard -> {
                startActivity(Intent(requireContext(), YapDashboardActivity::class.java))
                activity?.finish()
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
}