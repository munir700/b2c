package co.yap.modules.dashboard.more.help.fragments

import android.os.Bundle
import android.view.MotionEvent
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
import co.yap.widgets.spinneradapter.searchable.IStatusListener
import co.yap.widgets.spinneradapter.searchable.OnItemSelectedListener
import co.yap.widgets.spinneradapter.searchable.SimpleListAdapter
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.helpers.extentions.*
import co.yap.yapcore.managers.MyUserManager
import com.liveperson.infra.*
import com.liveperson.infra.callbacks.InitLivePersonCallBack
import com.liveperson.messaging.sdk.api.LivePerson
import com.liveperson.messaging.sdk.api.model.ConsumerProfile
import kotlinx.android.synthetic.main.fragment_help_support.*


class HelpSupportFragment : MoreBaseFragment<IHelpSupport.ViewModel>(), IHelpSupport.View,
    View.OnTouchListener {

    lateinit var adapter: HelpSupportAdaptor

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_help_support

    private val brandId = "17038977"
    private val appInstallId = MyUserManager.user?.uuid
    private lateinit var mSimpleListAdapter: SimpleListAdapter

    override val viewModel: IHelpSupport.ViewModel
        get() = ViewModelProviders.of(this).get(HelpSupportViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setObservers()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getHelpDeskPhone()
        mSimpleListAdapter = SimpleListAdapter(requireContext(), getSpinnerList())
        searchableSpinner?.setAdapter(mSimpleListAdapter)
        searchableSpinner?.setOnItemSelectedListener(mOnItemSelectedListener)
        searchableSpinner?.setStatusListener(object : IStatusListener
        {
            override fun spinnerIsOpening() {

            }

            override fun spinnerIsClosing() {
            }
        })
        view.setOnTouchListener(this)
    }

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        if (!searchableSpinner.isInsideSearchEditText(event)) {
            searchableSpinner.hideEdit()
            return true
        }
        return false
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
                if (requireContext().isWhatsAppInstalled()) {
                    requireContext().openWhatsApp()
                } else {
                    requireContext().openUrl("https://web.whatsapp.com/")
                }
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
//        val monitoringParams = MonitoringInitParams(appInstallId)
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
        authParams.hostAppJWT = viewModel.authRepository.getJwtToken()
//        authParams.hostAppJWT = CookiesManager.jwtToken
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

    /////////////////////////////////////////////////////////////////
    private var mOnItemSelectedListener: OnItemSelectedListener = object : OnItemSelectedListener {
        override fun onItemSelected(view: View?, position: Int, id: Long) {
            toast("Item on position " + position + " : " + mSimpleListAdapter?.getItem(position) + " Selected")
        }

        override fun onNothingSelected() {
            toast("Nothing Selected")
        }
    }

    private fun getSpinnerList(): ArrayList<String> {
        val list: ArrayList<String> = ArrayList()
        list.add("Brigida Kurz")
        list.add("Tracy Mckim")
        list.add("Iesha Davids")
        list.add("Ozella Provenza")
        list.add("Florentina Carriere")
        list.add("Geri Eiler")
        list.add("Tammara Belgrave")
        list.add("Ashton Ridinger")
        list.add("Jodee Dawkins")
        list.add("Florine Cruzan")
        list.add("Latia Stead")
        list.add("Kai Urbain")
        list.add("Liza Chi")
        list.add("Clayton Laprade")
        list.add("Wilfredo Mooney")
        list.add("Roseline Cain")
        list.add("Chadwick Gauna")
        list.add("Carmela Bourn")
        list.add("Valeri Dedios")
        list.add("Calista Mcneese")
        list.add("Willard Cuccia")
        list.add("Ngan Blakey")
        list.add("Reina Medlen")
        list.add("Fabian Steenbergen")
        list.add("Edmond Pine")
        list.add("Teri Quesada")
        list.add("Vernetta Fulgham")
        list.add("Winnifred Kiefer")
        list.add("Chiquita Lichty")
        list.add("Elna Stiltner")
        return list
    }
}