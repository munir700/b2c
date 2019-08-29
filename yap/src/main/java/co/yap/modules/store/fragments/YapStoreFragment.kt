package co.yap.modules.store.fragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import co.yap.BR
import co.yap.R
import co.yap.modules.store.adaptor.YapStoreAdaptor
import co.yap.modules.store.interfaces.IYapStore
import co.yap.modules.store.viewmodels.YapStoreViewModel
import co.yap.yapcore.BaseBindingFragment
import co.yap.yapcore.helpers.MarginItemDecoration
import kotlinx.android.synthetic.main.fragment_yap_store.*

class YapStoreFragment : BaseBindingFragment<IYapStore.ViewModel>(), IYapStore.View {

    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.fragment_yap_store

    override val viewModel: IYapStore.ViewModel
        get() = ViewModelProviders.of(this).get(YapStoreViewModel::class.java)

    override var testName: String = "Store"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecycleView()
    }

    private fun setupRecycleView() {
        //viewDataBinding.recycler_stores

        val storeAdaptor = YapStoreAdaptor(viewModel.yapStoreData)
        recycler_stores.layoutManager = LinearLayoutManager(context)
        recycler_stores.addItemDecoration(
            MarginItemDecoration(
                resources.getDimension(R.dimen.margin_normal_large).toInt(),
                resources.getDimension(R.dimen.margin_large).toInt()
            )
        )
        recycler_stores.adapter = storeAdaptor
    }
}