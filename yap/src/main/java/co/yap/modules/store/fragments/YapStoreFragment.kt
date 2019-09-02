package co.yap.modules.store.fragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import co.yap.BR
import co.yap.R
import co.yap.modules.store.adaptor.YapStoreAdaptor
import co.yap.modules.store.interfaces.IYapStore
import co.yap.modules.store.viewmodels.YapStoreViewModel
import co.yap.networking.store.responsedtos.Store
import co.yap.yapcore.BaseBindingFragment
import co.yap.yapcore.BaseBindingRecyclerAdapter
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
        setObservers()
    }

    private fun setObservers() {
        viewModel.clickEvent.observe(this, observer)
        viewModel.yapStoreData.observe(this, Observer {
            setupRecycleView(it)
        })
    }

    private fun setupRecycleView(list: MutableList<Store>) {
        val storeAdaptor = YapStoreAdaptor(list)
        recycler_stores.layoutManager = LinearLayoutManager(context)
        recycler_stores.addItemDecoration(
            MarginItemDecoration(
                resources.getDimension(R.dimen.margin_normal_large).toInt(),
                resources.getDimension(R.dimen.margin_large).toInt()
            )
        )
        recycler_stores.adapter = storeAdaptor
        storeAdaptor.setItemListener(listener)
    }

    val listener = object : BaseBindingRecyclerAdapter.OnItemClickListener {
        override fun onItemClick(view: View, data: Any, pos: Int) {
            val action =
                YapStoreFragmentDirections.actionYapStoreFragmentToYapStoreDetailFragment((data as Store).id.toString())
            findNavController().navigate(action)
        }
    }

    private val observer = Observer<Int> {
        when (it) {
            R.id.imgStoreShopping -> {
                showToast("Shopping Button Clicked")
            }
        }
    }

    override fun onDestroyView() {
        viewModel.clickEvent.removeObservers(this)
        super.onDestroyView()
    }

}