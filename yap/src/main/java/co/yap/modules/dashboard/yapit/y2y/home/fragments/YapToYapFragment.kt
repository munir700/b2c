package co.yap.modules.dashboard.yapit.y2y.home.fragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.R
import co.yap.databinding.FragmentYapToYapBinding
import co.yap.modules.dashboard.yapit.y2y.home.adaptors.PHONE_CONTACTS
import co.yap.modules.dashboard.yapit.y2y.home.adaptors.RecentTransferAdaptor
import co.yap.modules.dashboard.yapit.y2y.home.adaptors.TransferLandingAdaptor
import co.yap.modules.dashboard.yapit.y2y.home.adaptors.YAP_CONTACTS
import co.yap.modules.dashboard.yapit.y2y.home.interfaces.IYapToYap
import co.yap.modules.dashboard.yapit.y2y.home.viewmodel.YapToYapViewModel
import co.yap.modules.dashboard.yapit.y2y.main.fragments.Y2YBaseFragment
import co.yap.translation.Strings
import co.yap.translation.Translator
import co.yap.widgets.searchwidget.SearchingListener
import co.yap.yapcore.BR
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_yap_to_yap.*


class YapToYapFragment : Y2YBaseFragment<IYapToYap.ViewModel>() {
    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.fragment_yap_to_yap

    override val viewModel: YapToYapViewModel
        get() = ViewModelProviders.of(this).get(YapToYapViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.recentTransferData.observe(this, Observer {
            layoutRecent?.visibility = if (it)View.VISIBLE else View.GONE

        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.clickEvent.observe(this, clickEventObserver)
        setupAdaptor()
        setupTabs()
        setSearchView()
        viewModel.adapter.set(RecentTransferAdaptor(ArrayList()))
        viewModel.getRecentBeneficiaries()

    }

    private fun setupAdaptor() {
        val adaptor = TransferLandingAdaptor(this)
        getBindingView().viewPager.adapter = adaptor
    }

    private fun setupTabs() {
        TabLayoutMediator(getBindingView().tabLayout, getBindingView().viewPager,
            TabLayoutMediator.TabConfigurationStrategy { tab, position ->
                tab.text = getTabTitle(position)
            }).attach()
        getBindingView().viewPager.isUserInputEnabled = false
        getBindingView().viewPager.offscreenPageLimit = 1
    }

    private fun setSearchView() {
        getBindingView().svContacts.initializeSearch(requireContext(), object : SearchingListener {
            override fun onCancel() {
                svContacts.clearInputField()
            }

            override fun onSearchKeyPressed(search: String?) {

                showToast("start search $search")
            }

        }, false, true)
        getBindingView().svContacts.setOnClickListener { }
    }

    private val clickEventObserver = Observer<Int> {
        when (it) {
            R.id.btnInvite -> {

            }
        }
    }

    private fun getTabTitle(position: Int): String? {
        return when (position) {
            YAP_CONTACTS -> Translator.getString(
                requireContext(),
                Strings.screen_y2y_display_button_yap_contacts
            )
            PHONE_CONTACTS -> Translator.getString(
                requireContext(),
                Strings.screen_y2y_display_button_all_contacts
            )
            else -> null
        }
    }

    private fun getBindingView(): FragmentYapToYapBinding {
        return (viewDataBinding as FragmentYapToYapBinding)
    }
}