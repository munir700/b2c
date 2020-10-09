package co.yap.modules.dashboard.more.help.fragments

import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import androidx.annotation.Nullable
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.BR
import co.yap.R
import co.yap.countryutils.country.Country
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
        requireActivity().getWindow()?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
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
            ), toolBarTitle = viewModel.state.title.get() ?: "", showToolBar = false
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

    private fun getSpinnerList(): ArrayList<Country> {
        val list: ArrayList<Country> = ArrayList()
        list.add(Country(name = "Brigida Kurz"))
        list.add(Country(name = "Tracy Mckim"))
        list.add(Country(name = "Iesha Davids"))
        list.add(Country(name = "Ozella Provenza"))
        list.add(Country(name = "Florentina Carriere"))
        list.add(Country(name = "Geri Eiler"))
        list.add(Country(name = "Tammara Belgrave"))
        list.add(Country(name = "Ashton Ridinger"))
        list.add(Country(name = "Jodee Dawkins"))
        list.add(Country(name = "Florine Cruzan"))
        list.add(Country(name = "Latia Stead"))
        list.add(Country(name = "Kai Urbain"))
        list.add(Country(name = "Liza Chi"))
        list.add(Country(name = "Clayton Laprade"))
        list.add(Country(name = "Wilfredo Mooney"))
        list.add(Country(name = "Roseline Cain"))
        list.add(Country(name = "Chadwick Gauna"))
        list.add(Country(name = "Carmela Bourn"))
        list.add(Country(name = "Valeri Dedios"))
        list.add(Country(name = "Calista Mcneese"))
        list.add(Country(name = "Willard Cuccia"))
        list.add(Country(name = "Ngan Blakey"))
        list.add(Country(name = "Reina Medlen"))
        list.add(Country(name = "Fabian Steenbergen"))
        list.add(Country(name = "Edmond Pine"))
        list.add(Country(name = "Teri Quesada"))
        list.add(Country(name = "Vernetta Fulgham"))
        list.add(Country(name = "Winnifred Kiefer"))
        list.add(Country(name = "Chiquita Lichty"))
        list.add(Country(name = "Elna Stiltner"))
        return list
    }
}