package co.yap.modules.dashboard.yapit.y2y.home.yapcontacts

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import co.yap.R
import co.yap.modules.dashboard.yapit.y2y.home.interfaces.IYapToYap
import co.yap.modules.dashboard.yapit.y2y.home.viewmodel.YapToYapViewModel
import co.yap.modules.dashboard.yapit.y2y.main.fragments.Y2YBaseFragment
import co.yap.widgets.searchwidget.SearchingListener
import co.yap.yapcore.BR
import kotlinx.android.synthetic.main.fragment_yap_to_yap.*

class YapToYapFragment : Y2YBaseFragment<IYapToYap.ViewModel>() {
    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.fragment_yap_to_yap

    override val viewModel: YapToYapViewModel
        get() = ViewModelProviders.of(this).get(YapToYapViewModel::class.java)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.clickEvent.observe(this, clickEventObserver)
        tabLayout.addTab(tabLayout.newTab().setText("YAP contacts"))
        tabLayout.addTab(tabLayout.newTab().setText("All contacts"))
        setSearchView()
        svContacts.setOnClickListener { findNavController().navigate(R.id.y2YTransferFragment) }
    }

    private val clickEventObserver = Observer<Int> {
        when (it) {
            R.id.btnInvite -> {
                findNavController().navigate(R.id.y2YTransferFragment)
            }
        }
    }

    private fun setSearchView() {
        svContacts.initializeSearch(requireContext(),object: SearchingListener {
            override fun onCancel() {
                svContacts.clearInputField()
            }

            override fun onSearchKeyPressed(search: String?) {

                showToast("start search $search")
            }

        },false,true)

    }

}