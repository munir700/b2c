package co.yap.modules.dashboard.more.help.fragments

import android.content.Context
import android.content.Intent
import android.content.Intent.ACTION_DIAL
import android.net.Uri
import android.os.Bundle
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.view.View
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import co.yap.BR
import co.yap.R
import co.yap.databinding.FragmentHelpSupportBinding
import co.yap.modules.dashboard.more.fragments.MoreBaseFragment
import co.yap.modules.dashboard.more.help.adaptor.HelpSupportAdaptor
import co.yap.modules.dashboard.more.help.interfaces.IHelpSupport
import co.yap.modules.dashboard.more.help.viewmodels.HelpSupportViewModel
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


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setObservers()
        initComponents()
    }

    private fun initComponents() {
        val content = SpannableString(viewModel.state.contactPhone.get())
        content.setSpan(UnderlineSpan(), 0, content.length, 0)
        getBinding().tvCallPhone.text = content
    }

    private fun setObservers() {
        viewModel.clickEvent.observe(this, observer)
    }


    private val observer = Observer<Int> {
        when (it) {
            R.id.lLyFaqs -> {
                openFaqsPage("https://www.google.com")
            }
            R.id.lyChat -> {
                chatSetup()
            }
            R.id.lyLiveWhatsApp -> {
            }
            R.id.lyCall -> {
                openDialer()
            }
            R.id.tbBtnBack -> {
                findNavController().navigateUp()
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

        //storeData()

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
            .titleColor(ContextCompat.getColor(context!!,R.color.colorPrimaryDark))
            .toolbarColorRes(R.color.colorWhite)
            .dividerColorRes(R.color.colorPrimaryDark)
            .iconDefaultColorRes(R.color.colorPrimary)
            .iconDisabledColorRes(R.color.light_grey)
            .iconPressedColorRes(R.color.colorPrimaryDark)
            .progressBarHeight(Utils.convertDpToPx(context!!, 3f))
            .progressBarColorRes(R.color.colorPrimaryDark)
            .backPressToClose(false)
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

    override fun getBinding(): FragmentHelpSupportBinding {
        return viewDataBinding as FragmentHelpSupportBinding
    }
}