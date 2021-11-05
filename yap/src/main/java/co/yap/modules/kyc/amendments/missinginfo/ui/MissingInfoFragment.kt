package co.yap.modules.kyc.amendments.missinginfo.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.BR
import co.yap.R
import co.yap.modules.kyc.amendments.missinginfo.interfaces.IMissingInfo
import co.yap.yapcore.BaseBindingFragment

class MissingInfoFragment : BaseBindingFragment<IMissingInfo.ViewModel>(), IMissingInfo.View {
    override fun getBindingVariable() = BR.viewModel
    override fun getLayoutId() = R.layout.fragment_missinginfo

    override val viewModel: IMissingInfo.ViewModel
        get() = ViewModelProviders.of(this).get(MissingInfoFragmentViewModel::class.java)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.missingInfoItems.observe(viewLifecycleOwner, missingInfoItemsObserver)
        viewModel.getMissingInfoItems()
    }

    private val missingInfoItemsObserver = Observer<ArrayList<String>> {
        it.map { item -> Log.e("Item", item) }
    }
}