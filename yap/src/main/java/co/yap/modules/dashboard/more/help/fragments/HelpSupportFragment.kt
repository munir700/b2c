package co.yap.modules.dashboard.more.help.fragments

import android.content.Intent
import android.content.Intent.ACTION_DIAL
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.annotation.Nullable
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.BR
import co.yap.R
import co.yap.databinding.FragmentHelpSupportBinding
import co.yap.modules.dashboard.main.activities.YapDashboardActivity
import co.yap.modules.dashboard.more.help.adaptor.HelpSupportAdaptor
import co.yap.modules.dashboard.more.help.interfaces.IHelpSupport
import co.yap.modules.dashboard.more.help.viewmodels.HelpSupportViewModel
import co.yap.modules.dashboard.more.main.fragments.MoreBaseFragment
import co.yap.modules.webview.WebViewFragment
import co.yap.networking.CookiesManager
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.helpers.extentions.OpenWhatsApp
import co.yap.yapcore.helpers.extentions.makeCall
import co.yap.yapcore.helpers.extentions.startFragment
import co.yap.yapcore.helpers.extentions.toast
import co.yap.yapcore.managers.MyUserManager
import com.liveperson.infra.CampaignInfo
import com.liveperson.infra.ConversationViewParams
import com.liveperson.infra.InitLivePersonProperties
import com.liveperson.infra.LPAuthenticationParams
import com.liveperson.infra.callbacks.InitLivePersonCallBack
import com.liveperson.messaging.sdk.api.LivePerson
import com.liveperson.messaging.sdk.api.model.ConsumerProfile


class HelpSupportFragment : MoreBaseFragment<IHelpSupport.ViewModel>(), IHelpSupport.View {

    lateinit var adapter: HelpSupportAdaptor

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_help_support

    val accountId = "17038977"
    val appInstallId = "17038977"
    val FCMID = "17038977"

    override val viewModel: IHelpSupport.ViewModel
        get() = ViewModelProviders.of(this).get(HelpSupportViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setObservers()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getHelpDeskPhone()
    }

    private fun setObservers() {
        viewModel.clickEvent.observe(this, observer)
        viewModel.urlUpdated.observe(this, Observer {
            if (!it.isNullOrEmpty()) {
                openFaqsPage(it)
            } else {
                showToast("Invalid url.")
            }
        })
    }

    private val observer = Observer<Int> {
        when (it) {
            R.id.lLyFaqs -> {
                viewModel.getFaqsUrl()
            }
            R.id.lyChat -> {
                chatSetup()
            }
            R.id.lyLiveWhatsApp -> {
                OpenWhatsApp()
            }
            R.id.lyCall -> {
                requireContext().makeCall(viewModel.state.contactPhone.get())
            }
            R.id.tbBtnBack -> {
                activity?.finish()
            }
        }
    }

    private fun chatSetup() {
        LivePerson.initialize(
            requireContext(),
            InitLivePersonProperties(
                "17038977", "17038977",
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

        val authCode = "authCode"
        val publicKey = "publicKey"

        val authParams = LPAuthenticationParams(LPAuthenticationParams.LPAuthenticationType.AUTH)
        // authParams.authKey = authCode
        authParams.hostAppJWT = CookiesManager.jwtToken
        //authParams.addCertificatePinningKey(publicKey)

        //  val campaignInfo = getCampaignInfo()
        val params = ConversationViewParams()

        /*.setHistoryConversationsStateToDisplay(LPConversationsHistoryStateToDisplay.ALL)
        .setCampaignInfo(campaignInfo).setReadOnlyMode(isReadOnly())*/
        //        setWelcomeMessage(params);  //This method sets the welcome message with quick replies. Uncomment this line to enable this feature.
        LivePerson.showConversation(requireActivity(), authParams, params)

        val consumerProfile = ConsumerProfile.Builder()
            .setFirstName(MyUserManager.user?.currentCustomer?.firstName)
            .setLastName(MyUserManager.user?.currentCustomer?.lastName)
            .setPhoneNumber(MyUserManager.user?.currentCustomer?.getCompletePhone())
            .build()

        LivePerson.setUserProfile(consumerProfile)


        //Constructing the notification builder for the upload/download foreground service and passing it to the SDK.
        //val uploadBuilder = NotificationUI.createUploadNotificationBuilder(getApplicationContext())
        //val downloadBuilder =
        //    NotificationUI.createDownloadNotificationBuilder(getApplicationContext())
        //LivePerson.setImageServiceUploadNotificationBuilder(uploadBuilder)
        //LivePerson.setImageServiceDownloadNotificationBuilder(downloadBuilder)
    }

    @Nullable
    fun getCampaignInfo(): CampaignInfo? {
        return CampaignInfo(
            "campaignId".toLong(), "engagementId".toLong(),
            "interactionContextId", "sessionId",
            "visitorId"
        )
    }

    private fun openFaqsPage(url: String) {
        startFragment(
            fragmentName = WebViewFragment::class.java.name,
            bundle = bundleOf(
                Constants.PAGE_URL to url
            ), toolBarTitle = viewModel.state.title.get() ?: "", showToolBar = true
        )
    }
    override fun onResume() {
        super.onResume()
        if (activity is YapDashboardActivity)
            (activity as YapDashboardActivity).showHideBottomBar(false)
    }

    override fun onStop() {
        super.onStop()
        if (activity is YapDashboardActivity)
            (activity as YapDashboardActivity).showHideBottomBar(true)
    }

    override fun getBinding(): FragmentHelpSupportBinding {
        return viewDataBinding as FragmentHelpSupportBinding
    }
}