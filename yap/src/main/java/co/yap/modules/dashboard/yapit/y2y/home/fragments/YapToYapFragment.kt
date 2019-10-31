package co.yap.modules.dashboard.yapit.y2y.home.fragments

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import co.yap.R
import co.yap.databinding.FragmentYapToYapBinding
import co.yap.modules.dashboard.yapit.y2y.home.activities.YapToYapDashboardActivity
import co.yap.modules.dashboard.yapit.y2y.home.adaptors.PHONE_CONTACTS
import co.yap.modules.dashboard.yapit.y2y.home.adaptors.RecentTransferAdaptor
import co.yap.modules.dashboard.yapit.y2y.home.adaptors.TransferLandingAdaptor
import co.yap.modules.dashboard.yapit.y2y.home.adaptors.YAP_CONTACTS
import co.yap.modules.dashboard.yapit.y2y.home.interfaces.IYapToYap
import co.yap.modules.dashboard.yapit.y2y.home.viewmodel.YapToYapViewModel
import co.yap.modules.dashboard.yapit.y2y.main.fragments.Y2YBaseFragment
import co.yap.translation.Strings
import co.yap.translation.Translator
import co.yap.yapcore.BR
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
        viewModel.clickEvent.observe(this, clickEventObserver)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdaptor()
        setupTabs()
        setSearchView(viewModel.parentViewModel?.isSearching?.value!!)
        setupRecent()
    }

    private fun setupRecent() {
        if (viewModel.parentViewModel?.isSearching?.value!!) {
            layoutRecent.visibility = View.GONE
        } else {
            //val adapter = RecentTransferAdaptor(ArrayList(),findNavController())
            // viewModel.adapter.set(adapter)
            //viewModel.adapter.get()?.onItemClickListener = this
            //adapter.onItemClickListener = this
            viewModel.getRecentBeneficiaries()
            viewModel.recentTransferData.observe(this, Observer {
                layoutRecent?.visibility = if (it) View.VISIBLE else View.GONE
            })
            viewModel.adapter.set(RecentTransferAdaptor(ArrayList(), findNavController()))
        }
    }

    override fun onItemClick(view: View, data: Any, pos: Int) {
        findNavController().navigate(
            YapToYapFragmentDirections.actionYapToYapHomeToY2YTransferFragment(
//                data.beneficiaryPictureUrl!!
//                , data.accountDetailList?.get(0)?.accountUuid!!, data.title!!,pos
            )
        )
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
        }
    }

    private val clickEventObserver = Observer<Int> {
        when (it) {
            R.id.layoutSearchView -> {
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
            R.id.tvCancel -> {
              activity?.finish()
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

    override fun onDestroy() {
        super.onDestroy()
        viewModel.clickEvent.observe(this, clickEventObserver)
    }


    private fun getBindingView(): FragmentYapToYapBinding {
        return (viewDataBinding as FragmentYapToYapBinding)
    }
}