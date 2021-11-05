package co.yap.modules.kyc.amendments.missinginfo.ui

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import co.yap.BR
import co.yap.R
import co.yap.databinding.FragmentMissinginfoBinding
import co.yap.modules.kyc.amendments.missinginfo.adapters.MissingInfoAdapter
import co.yap.modules.kyc.amendments.missinginfo.interfaces.IMissingInfo
import co.yap.translation.Strings
import co.yap.yapcore.BaseBindingFragment
import co.yap.yapcore.managers.SessionManager

class MissingInfoFragment : BaseBindingFragment<IMissingInfo.ViewModel>(), IMissingInfo.View {
    override fun getBindingVariable() = BR.viewModel
    override fun getLayoutId() = R.layout.fragment_missinginfo

    override val viewModel: IMissingInfo.ViewModel
        get() = ViewModelProviders.of(this).get(MissingInfoFragmentViewModel::class.java)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeComponents()
        viewModel.missingInfoItems.observe(viewLifecycleOwner, missingInfoItemsObserver)
        viewModel.onClickEvent.observe(viewLifecycleOwner, onClickView)
        SessionManager.getAccountInfo {
            getDataBindingView<FragmentMissinginfoBinding>().tvTitle.text =
                getString(Strings.screen_missing_info_title).format(
                    SessionManager.user?.currentCustomer?.firstName
                )
        }
        viewModel.getMissingInfoItems()
    }

    private fun initializeComponents() {
        getDataBindingView<FragmentMissinginfoBinding>().missingItemsRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = MissingInfoAdapter(mutableListOf())
        }
    }

    private fun getRecycleViewAdaptor(): MissingInfoAdapter? {
        return if (getDataBindingView<FragmentMissinginfoBinding>().missingItemsRecyclerView.adapter is MissingInfoAdapter) {
            getDataBindingView<FragmentMissinginfoBinding>().missingItemsRecyclerView.adapter as MissingInfoAdapter
        } else {
            null
        }
    }

    private val missingInfoItemsObserver = Observer<ArrayList<String>> {
        it?.let { items ->
            getRecycleViewAdaptor()?.setList(items)
        }
    }

    private val onClickView = Observer<Int> {
        when (it) {
            R.id.btnGetStarted -> {
            }
            R.id.tvDoItLater -> {
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.onClickEvent.removeObserver(onClickView)
        viewModel.missingInfoItems.removeObserver(missingInfoItemsObserver)
    }

}