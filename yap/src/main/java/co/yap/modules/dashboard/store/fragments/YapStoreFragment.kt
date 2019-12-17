package co.yap.modules.dashboard.store.fragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.BR
import co.yap.R
import co.yap.modules.dashboard.store.adaptor.YapStoreAdaptor
import co.yap.modules.dashboard.store.household.activities.HouseHoldLandingActivity
import co.yap.modules.dashboard.store.household.onboarding.HouseHoldOnboardingActivity
import co.yap.modules.dashboard.store.interfaces.IYapStore
import co.yap.modules.dashboard.store.viewmodels.YapStoreViewModel
import co.yap.yapcore.BaseBindingFragment
import co.yap.yapcore.helpers.PagingState
import co.yap.yapcore.interfaces.OnItemClickListener
import kotlinx.android.synthetic.main.fragment_yap_store.*

class YapStoreFragment : BaseBindingFragment<IYapStore.ViewModel>(), IYapStore.View {

    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.fragment_yap_store

    override val viewModel: IYapStore.ViewModel
        get() = ViewModelProviders.of(this).get(YapStoreViewModel::class.java)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setObservers()
        initState()
        initComponents()
//        startActivity(HouseHoldLandingActivity.newIntent(activity!!))
        startActivity(HouseHoldOnboardingActivity.newIntent(activity!!))

    }

    private fun initComponents() {
        recycler_stores.adapter = YapStoreAdaptor { viewModel.retry() }
        (recycler_stores.adapter as YapStoreAdaptor).setItemListener(listener)
    }

    private fun setObservers() {
        viewModel.clickEvent.observe(this, observer)
        viewModel.storesLiveData.observe(this, Observer {
            (recycler_stores.adapter as YapStoreAdaptor).submitList(it)
            getRecycleViewAdaptor()?.setState(PagingState.DONE)
        })
    }

    private fun initState() {
        //retryBtn.setOnClickListener { viewModel.retry() }
        viewModel.getState().observe(this, Observer { state ->
            if (viewModel.listIsEmpty()) {
                recycler_stores.visibility = View.GONE
                txt_error.visibility =
                    if (state == PagingState.DONE || state == PagingState.ERROR) View.VISIBLE else View.GONE
                progress_bar.visibility =
                    if (state == PagingState.LOADING) View.VISIBLE else View.GONE
            } else {
                txt_error.visibility = View.GONE
                progress_bar.visibility = View.GONE
                recycler_stores.visibility = View.VISIBLE
                getRecycleViewAdaptor()?.setState(state)
            }
        })
    }

    val listener = object : OnItemClickListener {
        override fun onItemClick(view: View, data: Any, pos: Int) {
            startActivity(HouseHoldLandingActivity.newIntent(activity!!))

//            val action =
//                YapStoreFragmentDirections.actionYapStoreFragmentToYapStoreDetailFragment((dataList as Store).id.toString())
//            view.findNavController().navigate(action)
        }
    }

    private val observer = Observer<Int> {
        when (it) {
            R.id.imgStoreShopping -> {
            }
        }
    }

    private fun getRecycleViewAdaptor(): YapStoreAdaptor? {
        return if (recycler_stores.adapter is YapStoreAdaptor) {
            (recycler_stores.adapter as YapStoreAdaptor)
        } else {
            null
        }
    }

    override fun onDestroyView() {
        viewModel.clickEvent.removeObservers(this)
        super.onDestroyView()
    }
}