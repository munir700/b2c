package co.yap.modules.dashboard.more.help.fragments

import android.content.Context
import android.content.Intent
import android.content.Intent.ACTION_DIAL
import android.content.pm.PackageManager
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
import co.yap.networking.customers.requestdtos.Contact
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.helpers.Utils
import co.yap.yapcore.helpers.extentions.startFragment
import co.yap.yapcore.helpers.extentions.toast
import co.yap.yapcore.managers.MyUserManager
import com.liveperson.infra.*
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
        initComponents()
        viewModel.getHelpDeskPhone()
    }

    private fun initComponents() {
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
                openDialer()
            }
            R.id.tbBtnBack -> {
                activity?.finish()
            }
        }
    }

    private fun OpenWhatsApp() {
        val contact = "+971 4 365 3789" // use country code with your phone number
        val url = "https://api.whatsapp.com/send?phone=$contact"
        try {
            val pm = requireContext().packageManager
            pm?.getPackageInfo("com.whatsapp", PackageManager.GET_ACTIVITIES)
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(url)
            requireContext().startActivity(i)
        } catch (e: PackageManager.NameNotFoundException) {
            toast("Whatsapp app not installed in your phone")
        }
    }

    private fun inviteViaWhatsapp(contact: Contact) {
        val url =
            "https://api.whatsapp.com/send?phone=${Utils.getFormattedPhoneNumber(
                requireContext(),
                "${contact.countryCode}${contact.mobileNo!!}"
            )}&text=${Utils.getBody(requireContext(), contact)}"
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(url)
        startActivity(i)
    }

    private fun chatSetup() {
        LivePerson.initialize(
            requireContext(),
            InitLivePersonProperties(
                accountId,
                FCMID,
                null,
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

        val authParams = LPAuthenticationParams()
        authParams.authKey = authCode
        authParams.addCertificatePinningKey(publicKey)

        val campaignInfo = getCampaignInfo()
        val params = ConversationViewParams()
            .setHistoryConversationsStateToDisplay(LPConversationsHistoryStateToDisplay.ALL)
            .setCampaignInfo(campaignInfo).setReadOnlyMode(isReadOnly())
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

    private fun isReadOnly(): Boolean {
        return false
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

    private fun openDialer() {
        val intent = Intent(ACTION_DIAL)
        intent.data = Uri.parse("tel:" + viewModel.state.contactPhone.get())
        startActivity(intent)
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