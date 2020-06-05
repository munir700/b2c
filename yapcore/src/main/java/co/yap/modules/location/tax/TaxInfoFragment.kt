package co.yap.modules.location.tax

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.yapcore.BR
import co.yap.yapcore.BaseBindingFragment
import co.yap.yapcore.R
import co.yap.yapcore.databinding.FragmentTaxInfoBinding
import co.yap.yapcore.interfaces.OnItemClickListener

class TaxInfoFragment : BaseBindingFragment<ITaxInfo.ViewModel>(), ITaxInfo.View {

    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.fragment_tax_info
    override val viewModel: ITaxInfo.ViewModel
        get() = ViewModelProviders.of(this).get(TaxInfoViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addObservers()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecycleView()
    }

    override fun addObservers() {
        viewModel.clickEvent.observe(this, clickObserver)
    }

    private val clickObserver = Observer<Int> {}

    private fun setupRecycleView() {
        getBinding().recycler.adapter = viewModel.taxInfoAdaptor
        viewModel.taxInfoAdaptor.allowFullItemClickListener = true
        viewModel.taxInfoAdaptor.setItemListener(listener)
    }

    val listener = object : OnItemClickListener {
        override fun onItemClick(view: View, data: Any, pos: Int) {
            when (view.id) {
            }
        }
    }

    override fun removeObservers() {
        viewModel.clickEvent.removeObserver(clickObserver)
    }

    override fun onDestroy() {
        super.onDestroy()
        removeObservers()
    }

    override fun getBinding(): FragmentTaxInfoBinding {
        return (viewDataBinding as FragmentTaxInfoBinding)
    }

}
