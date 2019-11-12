package co.yap.modules.dashboard.more.help.fragments

import android.content.Context
import android.content.Intent
import android.content.Intent.ACTION_DIAL
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.core.content.ContextCompat
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
import co.yap.yapcore.helpers.LivePersonStorage
import co.yap.yapcore.helpers.Utils
import com.google.android.material.appbar.AppBarLayout
import com.liveperson.infra.*
import com.liveperson.infra.callbacks.InitLivePersonCallBack
import com.liveperson.messaging.sdk.api.LivePerson
import com.liveperson.messaging.sdk.api.model.ConsumerProfile
import com.thefinestartist.finestwebview.FinestWebView


class HelpSupportFragment : MoreBaseFragment<IHelpSupport.ViewModel>(), IHelpSupport.View {

    lateinit var adapter: HelpSupportAdaptor

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_help_support

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
                chatSetup()
            }
            R.id.lyCall -> {
                openDialer()
            }
            R.id.tbBtnBack -> {
                activity?.finish()
            }
        }
    }

    private fun chatSetup() {
        storeParams()
        LivePerson.initialize(
            requireContext().applicationContext,
            InitLivePersonProperties(
                LivePersonStorage.getInstance(context!!)?.account,
                LivePersonStorage.SDK_SAMPLE_FCM_APP_ID,
                null,
                object : InitLivePersonCallBack {

                    override fun onInitSucceed() {
                        LivePersonStorage.getInstance(requireContext())?.sdkMode =
                            (LivePersonStorage.SDKMode.ACTIVITY)
                        LivePersonStorage.getInstance(requireContext())?.firstName = ""
                        LivePersonStorage.getInstance(requireContext())?.lastName = ""
                        LivePersonStorage.getInstance(requireContext())?.phoneNumber = ""
                        LivePersonStorage.getInstance(requireContext())?.authCode = ""
                        LivePersonStorage.getInstance(requireContext())?.publicKey = ""
                        activity!!.runOnUiThread(Runnable {
                            openActivity()
                        })
                    }

                    override fun onInitFailed(e: Exception) {
                        Toast.makeText(context!!, "Init failed", Toast.LENGTH_SHORT).show()
                    }
                })
        )
    }

    private fun storeParams() {
        LivePersonStorage.getInstance(requireContext())?.account = "17038977"
        LivePersonStorage.getInstance(requireContext())?.appInstallId = "17038977"
    }

    private fun initActivityConversation() {

        LivePerson.initialize(
            context!!,
            InitLivePersonProperties(
                LivePersonStorage.getInstance(context!!)?.account,
                LivePersonStorage.SDK_SAMPLE_FCM_APP_ID,
                object : InitLivePersonCallBack {
                    override fun onInitSucceed() {

                    }

                    override fun onInitFailed(e: Exception) {
                    }
                })
        )
    }

    private fun openActivity() {

        val authCode = LivePersonStorage.getInstance(context!!)?.authCode
        val publicKey = LivePersonStorage.getInstance(context!!)?.publicKey

        val authParams = LPAuthenticationParams()
        authParams.authKey = authCode
        authParams.addCertificatePinningKey(publicKey)

        val campaignInfo = getCampaignInfo(context!!)
        val params = ConversationViewParams()
            .setHistoryConversationsStateToDisplay(LPConversationsHistoryStateToDisplay.ALL)
            .setCampaignInfo(campaignInfo).setReadOnlyMode(isReadOnly())
        //        setWelcomeMessage(params);  //This method sets the welcome message with quick replies. Uncomment this line to enable this feature.
        LivePerson.showConversation(requireActivity(), authParams, params)

        val consumerProfile = ConsumerProfile.Builder()
            .setFirstName(LivePersonStorage.getInstance(requireContext())?.firstName)
            .setLastName(LivePersonStorage.getInstance(requireContext())?.lastName)
            .setPhoneNumber(LivePersonStorage.getInstance(requireContext())?.phoneNumber)
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
    fun getCampaignInfo(context: Context): CampaignInfo? {
        var campaignInfo: CampaignInfo? = null
        if (LivePersonStorage.getInstance(context)?.campaignId != null || LivePersonStorage.getInstance(
                context
            )?.engagementId != null ||
            LivePersonStorage.getInstance(context)?.sessionId != null || LivePersonStorage.getInstance(
                context
            )?.visitorId != null
        ) {

            try {
                campaignInfo = CampaignInfo(
                    LivePersonStorage.getInstance(context)?.campaignId!!,
                    LivePersonStorage.getInstance(context)?.engagementId!!,
                    LivePersonStorage.getInstance(context)?.interactionContextId!!,
                    LivePersonStorage.getInstance(context)?.sessionId,
                    LivePersonStorage.getInstance(context)?.visitorId
                )
            } catch (e: BadArgumentException) {
                return null
            }

        }
        return campaignInfo
    }

    private fun openFaqsPage(url: String) {
        FinestWebView.Builder(activity!!)
            .titleDefault(viewModel.state.title.get()!!)
            .updateTitleFromHtml(true)
            .toolbarScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL or AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS)
            .gradientDivider(false)
            .dividerHeight(2)
            .titleColor(ContextCompat.getColor(context!!, R.color.colorPrimaryDark))
            .toolbarColorRes(R.color.colorWhite)
            .dividerColorRes(R.color.colorPrimaryDark)
            .iconDefaultColorRes(R.color.colorPrimary)
            .iconDisabledColorRes(R.color.light_grey)
            .iconPressedColorRes(R.color.colorPrimaryDark)
            .progressBarHeight(Utils.convertDpToPx(context!!, 3f))
            .progressBarColorRes(R.color.colorPrimaryDark)
            .backPressToClose(false)
            .webViewUseWideViewPort(true)
            .webViewSupportZoom(true)
            .webViewBuiltInZoomControls(true)
            .setCustomAnimations(
                R.anim.activity_open_enter,
                R.anim.activity_open_exit,
                R.anim.activity_close_enter,
                R.anim.activity_close_exit
            )
            .show(url)
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