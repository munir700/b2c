package co.yap.modules.dashboard.store.yapstore

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import co.yap.BR
import co.yap.R
import co.yap.databinding.FragmentYapStoreBinding
import co.yap.networking.store.responsedtos.Store
import co.yap.yapcore.BaseRVAdapter
import co.yap.yapcore.BaseViewHolder
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.dagger.base.BaseRecyclerViewFragment
import co.yap.yapcore.dagger.base.navigation.host.NAVIGATION_Graph_ID
import co.yap.yapcore.dagger.base.navigation.host.NAVIGATION_Graph_START_DESTINATION_ID
import co.yap.yapcore.dagger.base.navigation.host.NavHostPresenterActivity
import co.yap.yapcore.helpers.extentions.launchActivity
import co.yap.yapcore.managers.MyUserManager

class YapStoreFragment :
    BaseRecyclerViewFragment<FragmentYapStoreBinding, IYapStore.State, YapStoreVM, YapStoreFragment.Adapter, Store>() {
    override fun getBindingVariable() = BR.viewModel
    override fun getLayoutId() = R.layout.fragment_yap_store
    override fun postExecutePendingBindings(savedInstanceState: Bundle?) {
        super.postExecutePendingBindings(savedInstanceState)
        setRefreshEnabled(false)
        viewModel.clickEvent.observe(this, Observer { onClick(it) })
    }

    override fun onItemClick(view: View, data: Any, pos: Int) {
        if (data is Store) {
            if (data.name == "YAP Household") {
                viewModel.clickEvent.setPayload(
                    SingleClickEvent.AdaptorPayLoadHolder(
                        view,
                        data,
                        pos
                    )
                )
                viewModel.clickEvent.setValue(view.id)
            }
        }
    }

    private fun onClick(id: Int) {
        when (id) {
            R.id.cvStore -> {
                viewModel.clickEvent.getPayload()?.let {
                    var navGraphId = 0
                    var startDescription = 0
                    MyUserManager.user?.let {
                        if (it.noOfSubAccounts == null || it.noOfSubAccounts == 0) {
                            navGraphId = R.navigation.add_house_hold_user_navigation
                            startDescription = R.id.houseHoldLandingFragment
                        } else {
                            navGraphId = R.navigation.iban_subaccount_navigation
                            startDescription = R.id.subAccountDashBoardFragment
                        }
                    }
                    launchActivity<NavHostPresenterActivity> {
                        putExtra(NAVIGATION_Graph_ID, navGraphId)
                        putExtra(NAVIGATION_Graph_START_DESTINATION_ID, startDescription)
                    }
                    viewModel.clickEvent.setPayload(null)
                }
                viewModel.clickEvent.setPayload(null)
            }
        }
    }

    override fun onDestroyView() {
        viewModel.clickEvent.removeObservers(this)
        super.onDestroyView()
    }

    class Adapter(mValue: MutableList<Store>, navigation: NavController?) :
        BaseRVAdapter<Store, YapStoreItemVM, BaseViewHolder<Store, YapStoreItemVM>>(
            mValue,
            navigation
        ) {
        override fun getLayoutId(viewType: Int) = getViewModel(viewType).layoutRes()
        override fun getViewHolder(
            view: View,
            viewModel: YapStoreItemVM,
            mDataBinding: ViewDataBinding,
            viewType: Int
        ) = BaseViewHolder(view, viewModel, mDataBinding)

        override fun getViewModel(viewType: Int) = YapStoreItemVM()
        override fun getVariableId() = BR.viewModel
    }
}