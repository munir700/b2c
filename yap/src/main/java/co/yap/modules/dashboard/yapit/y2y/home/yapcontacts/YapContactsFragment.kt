package co.yap.modules.dashboard.yapit.y2y.home.yapcontacts

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import co.yap.R
import co.yap.databinding.FragmentYapContactsBinding
import co.yap.modules.dashboard.yapit.y2y.home.fragments.YapToYapFragment
import co.yap.modules.dashboard.yapit.y2y.home.fragments.YapToYapFragmentDirections
import co.yap.modules.dashboard.yapit.y2y.main.fragments.Y2YBaseFragment
import co.yap.networking.customers.requestdtos.Contact
import co.yap.yapcore.BR
import co.yap.yapcore.helpers.PagingState
import co.yap.yapcore.helpers.Utils
import co.yap.yapcore.interfaces.OnItemClickListener

class YapContactsFragment : Y2YBaseFragment<IYapContact.ViewModel>() {
    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.fragment_yap_contacts

    override val viewModel: YapContactViewModel
        get() = ViewModelProviders.of(this).get(YapContactViewModel::class.java)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setObservers()
        initState()
        initComponents()
    }

    private fun initComponents() {
        getBinding().recycler.adapter = YapContactsAdaptor(requireContext(), mutableListOf())
        (getBinding().recycler.adapter as YapContactsAdaptor).setItemListener(listener)
    }

    private fun initState() {
        //retryBtn.setOnClickListener { viewModel.retry() }
        viewModel.getState().observe(this, Observer { state ->
            if (viewModel.listIsEmpty()) {
                getBinding().recycler.visibility = View.GONE
                getBinding().txtError.visibility =
                    if (state == PagingState.DONE || state == PagingState.ERROR) View.VISIBLE else View.GONE
                getBinding().progressBar.visibility =
                    if (state == PagingState.LOADING) View.VISIBLE else View.GONE
            } else {
                getBinding().txtError.visibility = View.GONE
                getBinding().progressBar.visibility = View.GONE
                getBinding().recycler.visibility = View.VISIBLE
                // (getBinding().recycler.adapter as YapContactsAdaptor)?.setState(state)
            }
        })
    }

    private fun setObservers() {
        viewModel.clickEvent.observe(this, observer)
        viewModel.parentViewModel?.yapContactLiveData?.observe(this, Observer {
            (getBinding().recycler.adapter as YapContactsAdaptor).setList(it)
            //(getBinding().recycler.adapter as YapContactsAdaptor).setState(PagingState.DONE)
        })
    }

    val listener = object : OnItemClickListener {
        override fun onItemClick(view: View, data: Any, pos: Int) {
            when (view.id) {
                R.id.userPackageType -> {

                }
                R.id.tvInvite -> {
                    Utils.shareText(requireContext(), getBody())
                }
                R.id.lyContact -> {
                    if (data is Contact && data.yapUser!!) {
                        if (parentFragment is YapToYapFragment) {
                            (parentFragment as YapToYapFragment).findNavController().navigate(
                                YapToYapFragmentDirections.actionYapToYapHomeToY2YTransferFragment(
                                    data.beneficiaryPictureUrl!!
                                    , data.accountDetailList?.get(0)?.accountUuid!!, data.title!!
                                )
                            )
                        }
                    }
                }
            }
        }
    }

    private fun getBody(): String {
        return "App LInk"
    }

    private val observer = Observer<Int> {
        when (it) {
            R.id.imgStoreShopping -> {
            }
        }
    }

    private fun getBinding(): FragmentYapContactsBinding {
        return (viewDataBinding as FragmentYapContactsBinding)
    }
}