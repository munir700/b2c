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
import co.yap.yapcore.helpers.SampleAppStorage
import com.liveperson.infra.*
import com.liveperson.infra.callbacks.InitLivePersonCallBack
import com.liveperson.messaging.sdk.api.LivePerson
import com.liveperson.messaging.sdk.api.model.ConsumerProfile

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
                openFaqsPage()
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
                SampleAppStorage.getInstance(context!!)?.account,
                SampleAppStorage.SDK_SAMPLE_FCM_APP_ID,
                null,
                object : InitLivePersonCallBack {

                    override fun onInitSucceed() {
                        Toast.makeText(context!!, "Init failed", Toast.LENGTH_SHORT).show()

                        SampleAppStorage.getInstance(requireContext())?.sdkMode =
                            (SampleAppStorage.SDKMode.ACTIVITY)
                        SampleAppStorage.getInstance(requireContext())?.firstName = ""
                        SampleAppStorage.getInstance(requireContext())?.lastName = ""
                        SampleAppStorage.getInstance(requireContext())?.phoneNumber = ""
                        SampleAppStorage.getInstance(requireContext())?.authCode = ""
                        SampleAppStorage.getInstance(requireContext())?.publicKey = ""
                        initActivityConversation()
                    }

                    override fun onInitFailed(e: Exception) {
                        Toast.makeText(context!!, "Init failed", Toast.LENGTH_SHORT).show()
                    }
                })
        )
    }

    private fun storeParams() {
        SampleAppStorage.getInstance(requireContext())?.account = "17038977"
        SampleAppStorage.getInstance(requireContext())?.appInstallId = "17038977"
    }

    private fun initActivityConversation() {

        LivePerson.initialize(
            context!!,
            InitLivePersonProperties(
                SampleAppStorage.getInstance(context!!)?.account,
                SampleAppStorage.SDK_SAMPLE_FCM_APP_ID,
                object : InitLivePersonCallBack {
                    override fun onInitSucceed() {
                        activity!!.runOnUiThread(Runnable {
                            openActivity()
                        })
                    }

                    override fun onInitFailed(e: Exception) {
                    }
                })
        )
    }

    private fun openActivity() {

        //storeData()

        val authCode = SampleAppStorage.getInstance(context!!)?.authCode
        val publicKey = SampleAppStorage.getInstance(context!!)?.publicKey

        val authParams = LPAuthenticationParams()
        authParams.authKey = authCode
        authParams.addCertificatePinningKey(publicKey)

        val campaignInfo = getCampaignInfo(context!!)
        val params = ConversationViewParams().setReadOnlyMode(false)
            .setHistoryConversationsStateToDisplay(LPConversationsHistoryStateToDisplay.ALL)
            .setCampaignInfo(campaignInfo).setReadOnlyMode(isReadOnly())
        //        setWelcomeMessage(params);  //This method sets the welcome message with quick replies. Uncomment this line to enable this feature.
        LivePerson.showConversation(requireActivity(), authParams, params)

        val consumerProfile = ConsumerProfile.Builder()
            .setFirstName(SampleAppStorage.getInstance(requireContext())?.firstName)
            .setLastName(SampleAppStorage.getInstance(requireContext())?.lastName)
            .setPhoneNumber(SampleAppStorage.getInstance(requireContext())?.phoneNumber)
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

    /**
     * Get the CampaignInfo stored in the SampleAppStorage (if available). If not available return null
     * @param context
     * @return
     */
    @Nullable
    fun getCampaignInfo(context: Context): CampaignInfo? {
        var campaignInfo: CampaignInfo? = null
        if (SampleAppStorage.getInstance(context)?.campaignId != null || SampleAppStorage.getInstance(
                context
            )?.engagementId != null ||
            SampleAppStorage.getInstance(context)?.sessionId != null || SampleAppStorage.getInstance(
                context
            )?.visitorId != null
        ) {

            try {
                campaignInfo = CampaignInfo(
                    SampleAppStorage.getInstance(context)?.campaignId!!,
                    SampleAppStorage.getInstance(context)?.engagementId!!,
                    SampleAppStorage.getInstance(context)?.interactionContextId!!,
                    SampleAppStorage.getInstance(context)?.sessionId,
                    SampleAppStorage.getInstance(context)?.visitorId
                )
            } catch (e: BadArgumentException) {
                return null
            }

        }
        return campaignInfo
    }

    private fun openFaqsPage() {
        val url = "http://www.example.com"
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(url)
        startActivity(i)
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