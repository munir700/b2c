package co.yap.sendmoney.y2y.home.fragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.sendmoney.R
import co.yap.sendmoney.databinding.FragmentY2YSearchContactsBinding
import co.yap.sendmoney.y2y.home.adaptors.PHONE_CONTACTS
import co.yap.sendmoney.y2y.home.adaptors.TransferLandingAdaptor
import co.yap.sendmoney.y2y.home.adaptors.YAP_CONTACTS
import co.yap.sendmoney.y2y.home.interfaces.IY2YSearchContacts
import co.yap.sendmoney.y2y.home.viewmodel.Y2YSearchContactsViewModel
import co.yap.sendmoney.y2y.main.fragments.Y2YBaseFragment
import co.yap.translation.Strings
import co.yap.translation.Translator
import co.yap.yapcore.BR
import co.yap.yapcore.helpers.extentions.afterTextChanged
import co.yap.yapcore.helpers.extentions.hideKeyboard
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_y_2_y_search_contacts.view.*
import kotlinx.android.synthetic.main.fragment_yap_to_yap.*

class Y2YSearchContactsFragment : Y2YBaseFragment<IY2YSearchContacts.ViewModel>() {
    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.fragment_y_2_y_search_contacts

    override val viewModel: Y2YSearchContactsViewModel
        get() = ViewModelProviders.of(this).get(Y2YSearchContactsViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.clickEvent.observe(this, clickEventObserver)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdaptor()
        setupTabs()
        setupSearch()
    }

    private fun setupSearch() {
        getBindingView().lySearchView.etSearch.afterTextChanged {
            viewModel.parentViewModel?.searchQuery?.value = it
        }
        viewModel.parentViewModel?.isSearching?.value = true
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
        viewModel.parentViewModel?.selectedTabPos?.value?.let {
            tabLayout.getTabAt(it)?.select()
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


    private val clickEventObserver = Observer<Int> {
        when (it) {
            R.id.tvCancel -> {
                activity?.onBackPressed()
            }
        }
    }

    override fun onBackPressed(): Boolean {
        viewModel.parentViewModel?.searchQuery?.value = ""
        getBindingView().lySearchView.etSearch.hideKeyboard()
        viewModel.parentViewModel?.isSearching?.value = false
        return super.onBackPressed()
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.clickEvent.removeObservers(this)
    }

    private fun getBindingView(): FragmentY2YSearchContactsBinding {
        return (viewDataBinding as FragmentY2YSearchContactsBinding)
    }
}