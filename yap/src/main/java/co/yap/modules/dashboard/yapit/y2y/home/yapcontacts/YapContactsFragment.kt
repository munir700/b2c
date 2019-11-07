package co.yap.modules.dashboard.yapit.y2y.home.yapcontacts

import android.annotation.SuppressLint
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
        initState()
        initComponents()
        setObservers()
    }

    private fun initComponents() {

        getBinding().recycler.adapter = YapContactsAdaptor( mutableListOf())
        (getBinding().recycler.adapter as YapContactsAdaptor).setItemListener(listener)
    }

    private fun initState() {
        viewModel.getState().observe(this, Observer { state ->
            if ((getBinding().recycler.adapter as YapContactsAdaptor).getDataList().isNullOrEmpty()) {
                getBinding().tvNoResult.visibility = View.GONE
                getBinding().recycler.visibility = View.GONE
                getBinding().txtError.visibility =
                    if (state == PagingState.DONE || state == PagingState.ERROR) View.VISIBLE else View.GONE
                getBinding().btnInvite.visibility =
                    if (state == PagingState.DONE || state == PagingState.ERROR) View.VISIBLE else View.GONE
                getBinding().progressBar.visibility =
                    if (state == PagingState.LOADING) View.VISIBLE else View.GONE

            } else {
                getBinding().tvNoResult.visibility = View.GONE
                getBinding().txtError.visibility = View.GONE
                getBinding().btnInvite.visibility = View.GONE
                getBinding().progressBar.visibility = View.GONE
                getBinding().recycler.visibility = View.VISIBLE
            }
        })
        viewModel.pagingState.value = PagingState.LOADING
    }

    @SuppressLint("SetTextI18n")
    private fun setObservers() {
        viewModel.clickEvent.observe(this, observer)
        viewModel.parentViewModel?.yapContactLiveData?.observe(this, Observer {
            (getBinding().recycler.adapter as YapContactsAdaptor).setList(it)
            getBinding().tvNoResult.visibility = View.GONE
            getBinding().tvContactListDescription.visibility =
                if (it.isEmpty()) View.GONE else View.VISIBLE

            getBinding().tvContactListDescription.text =
                (getBinding().recycler.adapter as YapContactsAdaptor).itemCount.toString() + " YAP contacts"


            //if (!it.isEmpty() && !viewModel.getState() == PagingState.LOADING)
            viewModel.pagingState.value = PagingState.DONE
            //(getBinding().recycler.adapter as YapContactsAdaptor).setState(PagingState.DONE)
            getBinding().tvContactListDescription.visibility =
                if (it.isEmpty()) View.GONE else View.VISIBLE
            getBinding().tvContactListDescription.text =
                if (it.size==1) "${it.size} YAP contact" else "${it.size} YAP contacts"
        })

        viewModel.parentViewModel?.searchQuery?.observe(this, Observer {
            (getBinding().recycler.adapter as YapContactsAdaptor).filter.filter(it)
        })

        (getBinding().recycler.adapter as YapContactsAdaptor).filterCount.observe(this, Observer {

            getBinding().tvContactListDescription.visibility =
                if (it == 0) View.GONE else View.VISIBLE
            getBinding().tvNoResult.visibility = if (it == 0) View.VISIBLE else View.GONE
            getBinding().tvContactListDescription.text =
                if (it == 1) "$it YAP contact" else "$it YAP contacts"
        })
    }

    val listener = object : OnItemClickListener {
        override fun onItemClick(view: View, data: Any, pos: Int) {
            when (view.id) {
                R.id.btnInvite -> {
                    Utils.shareText(requireContext(), getBody())
                }
                R.id.tvInvite -> {
                    Utils.shareText(requireContext(), getBody())
                }
                R.id.lyContact -> {
                    if (data is Contact && data.yapUser!! && data.accountDetailList != null && data.accountDetailList?.isNotEmpty()!!) {
                        if (parentFragment is YapToYapFragment) {
                            (parentFragment as YapToYapFragment).findNavController().navigate(
                                YapToYapFragmentDirections.actionYapToYapHomeToY2YTransferFragment(
                                    data.beneficiaryPictureUrl!!
                                    , data.accountDetailList?.get(0)?.accountUuid!!, data.title!!,pos
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