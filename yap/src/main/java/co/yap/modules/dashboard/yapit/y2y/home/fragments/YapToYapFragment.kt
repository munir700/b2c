package co.yap.modules.dashboard.yapit.y2y.home.fragments

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavOptions
import co.yap.R
import co.yap.databinding.FragmentYapToYapBinding
import co.yap.modules.dashboard.yapit.y2y.home.activities.YapToYapDashboardActivity
import co.yap.modules.dashboard.yapit.y2y.home.adaptors.PHONE_CONTACTS
import co.yap.modules.dashboard.yapit.y2y.home.adaptors.TransferLandingAdaptor
import co.yap.modules.dashboard.yapit.y2y.home.adaptors.YAP_CONTACTS
import co.yap.modules.dashboard.yapit.y2y.home.interfaces.IYapToYap
import co.yap.modules.dashboard.yapit.y2y.home.viewmodel.YapToYapViewModel
import co.yap.modules.dashboard.yapit.y2y.main.fragments.Y2YBaseFragment
import co.yap.networking.customers.responsedtos.sendmoney.Beneficiary
import co.yap.translation.Strings
import co.yap.translation.Translator
import co.yap.yapcore.BR
import co.yap.yapcore.enums.FeatureSet
import co.yap.yapcore.helpers.extentions.hideKeyboard
import co.yap.yapcore.helpers.extentions.launchActivity
import co.yap.yapcore.interfaces.OnItemClickListener
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_yap_to_yap.*


class YapToYapFragment : Y2YBaseFragment<IYapToYap.ViewModel>(), OnItemClickListener {
    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.fragment_yap_to_yap

    override val viewModel: YapToYapViewModel
        get() = ViewModelProviders.of(this).get(YapToYapViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.parentViewModel?.beneficiary?.let {
            skipYapHomeFragment()
        } ?: viewModel.clickEvent.observe(this, clickEventObserver)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdaptor()
        setupTabs()
        setSearchView(viewModel.parentViewModel?.isSearching?.value == true)
        setupRecent()
    }

    private fun setupRecent() {
        if (viewModel.parentViewModel?.isSearching?.value == true) {
            layoutRecent.visibility = View.GONE
        } else {
            viewModel.getRecentBeneficiaries()
        }
    }

    override fun onItemClick(view: View, data: Any, pos: Int) {
        if (data is Beneficiary) {
            viewModel.parentViewModel?.beneficiary = data
            navigateToTransferFunds()
        }
    }

    private fun setupAdaptor() {
        val adaptor = TransferLandingAdaptor(this)
        getBindingView().viewPager.adapter = adaptor
        viewModel.recentsAdapter.allowFullItemClickListener = true
        viewModel.recentsAdapter.setItemListener(this)
    }

    private fun setupTabs() {
        TabLayoutMediator(getBindingView().tabLayout, getBindingView().viewPager,
            TabLayoutMediator.TabConfigurationStrategy { tab, position ->
                tab.text = getTabTitle(position)
            }).attach()
        getBindingView().viewPager.isUserInputEnabled = false
        getBindingView().viewPager.offscreenPageLimit = 1
    }

    private fun setSearchView(show: Boolean) {
        getBindingView().layoutSearchView.ivSearch.visibility =
            if (!show) View.VISIBLE else View.GONE
        getBindingView().layoutSearchView.tvSearch.visibility =
            if (!show) View.VISIBLE else View.GONE
        getBindingView().layoutSearchView.svContacts.visibility =
            if (!show) View.GONE else View.VISIBLE

        if (!show) {
            getBindingView().layoutSearchView.svContacts.isIconified = true
            getBindingView().run { layoutSearchView.svContacts.setIconifiedByDefault(false) }
            getBindingView().tvCancel.visibility = View.GONE
        } else {
            getBindingView().tvCancel.visibility = View.VISIBLE
            getBindingView().layoutSearchView.svContacts.isIconified = false
            getBindingView().run { layoutSearchView.svContacts.setIconifiedByDefault(false) }
            getBindingView().layoutSearchView.svContacts.setOnQueryTextListener(object :
                SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    viewModel.parentViewModel?.searchQuery?.value = query
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    viewModel.parentViewModel?.searchQuery?.value = newText
                    return true
                }
            })
            getBindingView().layoutSearchView.svContacts.onFocusChangeListener =
                View.OnFocusChangeListener { view, hasFoucs -> if (!hasFoucs) view.hideKeyboard() }
        }
    }

    private val clickEventObserver = Observer<Int> {
        when (it) {
            R.id.layoutSearchView -> {
                if (viewModel.parentViewModel?.isSearching?.value == false) {
                    openY2YScreen()
                }
            }
            R.id.tvCancel -> {
                activity?.finish()
            }
            R.id.tvHideRecents, R.id.recents -> {
                viewModel.state.isRecentsVisible.set(getBindingView().layoutRecent.recyclerView.visibility == View.VISIBLE)
            }
        }
    }

    private fun openY2YScreen() {
        launchActivity<YapToYapDashboardActivity>(type = FeatureSet.YAP_TO_YAP) {
            putExtra(YapToYapDashboardActivity.searching, true)
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

    private fun skipYapHomeFragment() {
        val navOptions = NavOptions.Builder()
            .setPopUpTo(R.id.yapToYapHome, true) // starting destination skiped
            .build()
        navigateToTransferFunds(navOptions)
    }

    private fun navigateToTransferFunds(navOptions: NavOptions? = null) {
        navigate(
            YapToYapFragmentDirections.actionYapToYapHomeToY2YTransferFragment(
                viewModel.parentViewModel?.beneficiary?.beneficiaryPictureUrl ?: "",
                viewModel.parentViewModel?.beneficiary?.beneficiaryUuid ?: "",
                viewModel.parentViewModel?.beneficiary?.title ?: "", 0
            ), screenType = FeatureSet.Y2Y_TRANSFER, navOptions = navOptions
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.clickEvent.removeObservers(this)
    }

    private fun getBindingView(): FragmentYapToYapBinding {
        return (viewDataBinding as FragmentYapToYapBinding)
    }

}