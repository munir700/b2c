package co.yap.modules.dashboard.yapit.y2y.home.fragments

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.R
import co.yap.databinding.FragmentYapToYapBinding
import co.yap.modules.dashboard.yapit.y2y.home.activities.YapToYapDashboardActivity
import co.yap.modules.dashboard.yapit.y2y.home.adaptors.PHONE_CONTACTS
import co.yap.modules.dashboard.yapit.y2y.home.adaptors.TransferLandingAdaptor
import co.yap.modules.dashboard.yapit.y2y.home.adaptors.YAP_CONTACTS
import co.yap.modules.dashboard.yapit.y2y.home.interfaces.IYapToYap
import co.yap.modules.dashboard.yapit.y2y.home.viewmodel.YapToYapViewModel
import co.yap.modules.dashboard.yapit.y2y.main.fragments.Y2YBaseFragment
import co.yap.translation.Strings
import co.yap.translation.Translator
import co.yap.yapcore.BR
import com.google.android.material.tabs.TabLayoutMediator


class YapToYapFragment : Y2YBaseFragment<IYapToYap.ViewModel>() {
    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.fragment_yap_to_yap

    override val viewModel: YapToYapViewModel
        get() = ViewModelProviders.of(this).get(YapToYapViewModel::class.java)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.clickEvent.observe(this, clickEventObserver)
        setupAdaptor()
        setupTabs()
        setSearchView()
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

        if (!viewModel.parentViewModel?.isSearching?.value!!) {
            getBindingView().svContacts.isIconified = true
            getBindingView().run { svContacts.setIconifiedByDefault(true) }
        } else {
            getBindingView().svContacts.setOnQueryTextListener(object :
                SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    viewModel.parentViewModel?.searchQuery?.value = query
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return true
                }
            })
            getBindingView().svContacts.isIconified = false
            getBindingView().run { svContacts.setIconifiedByDefault(false) }
        }

    }

    private val clickEventObserver = Observer<Int> {
        when (it) {
            R.id.svContacts -> {
                if (!viewModel.parentViewModel?.isSearching?.value!!) {
                    startActivity(
                        YapToYapDashboardActivity.getIntent(
                            requireContext(),
                            true,
                            null
                        )
                    )
                }
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