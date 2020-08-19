package co.yap.modules.dashboard.store.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.BR
import co.yap.R
import co.yap.modules.dashboard.store.adaptor.YapStoreAdaptor
import co.yap.modules.dashboard.store.interfaces.IYapStore
import co.yap.modules.dashboard.store.viewmodels.YapStoreViewModel
import co.yap.networking.store.responsedtos.Store
import co.yap.yapcore.BaseBindingFragment
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.constants.RequestCodes
import co.yap.yapcore.dagger.base.navigation.host.NAVIGATION_Graph_ID
import co.yap.yapcore.dagger.base.navigation.host.NAVIGATION_Graph_START_DESTINATION_ID
import co.yap.yapcore.dagger.base.navigation.host.NavHostPresenterActivity
import co.yap.yapcore.helpers.extentions.ExtraType
import co.yap.yapcore.helpers.extentions.getValue
import co.yap.yapcore.helpers.extentions.launchActivity
import co.yap.yapcore.interfaces.OnItemClickListener
import co.yap.yapcore.managers.MyUserManager
import kotlinx.android.synthetic.main.fragment_yap_store.*

class YapStoreFragment : BaseBindingFragment<IYapStore.ViewModel>(), IYapStore.View {

    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.fragment_yap_store

    override val viewModel: IYapStore.ViewModel
        get() = ViewModelProviders.of(this).get(YapStoreViewModel::class.java)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getStoreList()
        initComponents()
        setObservers()
    }

    private fun initComponents() {
        YapStoreAdaptor(mutableListOf())?.apply {
            viewModel.mAdapter?.set(this)
            allowFullItemClickListener = true
            setItemListener(listener)
        }
    }

    private fun setObservers() {
        viewModel.clickEvent.observe(this, observer)
    }

    val listener = object : OnItemClickListener {
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
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RequestCodes.REQUEST_ADD_HOUSE_HOLD) {
            if (resultCode == Activity.RESULT_OK) {
                data?.let {
                    val finishScreen =
                        data.getValue(
                            RequestCodes.REQUEST_CODE_FINISH,
                            ExtraType.BOOLEAN.name
                        ) as? Boolean
                    finishScreen?.let { it ->
                        if (it) {
                            //finish()  // Transaction fragment
                        } else {
                            // other things?
                        }
                    }
                }
            }
        }
    }

    private val observer = Observer<Int> {
        when (it) {
            R.id.imgStoreShopping -> {
            }
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