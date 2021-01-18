package co.yap.modules.dashboard.store.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.BR
import co.yap.R
import co.yap.networking.store.responsedtos.Store
import co.yap.yapcore.BaseBindingFragment
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.dagger.base.navigation.host.NAVIGATION_Graph_ID
import co.yap.yapcore.dagger.base.navigation.host.NAVIGATION_Graph_START_DESTINATION_ID
import co.yap.yapcore.dagger.base.navigation.host.NavHostPresenterActivity
import co.yap.yapcore.helpers.extentions.launchActivity
import co.yap.yapcore.interfaces.OnItemClickListener
import co.yap.yapcore.managers.SessionManager
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
        recycler_stores.adapter =
            YapStoreAdaptor(
                mutableListOf()
            )
        (recycler_stores.adapter as YapStoreAdaptor).allowFullItemClickListener = true
        (recycler_stores.adapter as YapStoreAdaptor).setItemListener(listener)
    }

    private fun setObservers() {
        viewModel.clickEvent.observe(this, Observer { onClick(it) })
        viewModel.storesLiveData.observe(viewLifecycleOwner, Observer {
            (recycler_stores.adapter as YapStoreAdaptor).setList(it)
        })
    }

    val listener = object : OnItemClickListener {
        override fun onItemClick(view: View, data: Any, pos: Int) {
            if (data is Store) {
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
                    if (it.itemData is Store) {
                        when ((it.itemData as Store).id) {
                            R.id.youngStore -> {
                                launchActivity<NavHostPresenterActivity> {
                                    putExtra(
                                        NAVIGATION_Graph_ID,
                                        R.navigation.young_parent_side_navigation
                                    )
                                    putExtra(
                                        NAVIGATION_Graph_START_DESTINATION_ID,
                                        R.id.youngLandingFragment
                                    )
                                }
                            }
                            R.id.houseHoldStore -> {
                                var navGraphId = 0
                                var startDescription = 0
                                SessionManager.user?.let {
                                    if (it.noOfSubAccounts == null || it.noOfSubAccounts == 0) {
                                        navGraphId = R.navigation.add_house_hold_user_navigation
                                        startDescription = R.id.houseHoldLandingFragment
                                    } else {
                                        navGraphId = R.navigation.iban_subaccount_navigation
                                        startDescription = R.id.subAccountDashBoardFragment
                                    }
                                }
                                if (navGraphId > 0)
                                    launchActivity<NavHostPresenterActivity> {
                                        putExtra(NAVIGATION_Graph_ID, navGraphId)
                                        putExtra(
                                            NAVIGATION_Graph_START_DESTINATION_ID,
                                            startDescription
                                        )
                                    }
                            }
                        }
                    }

                    viewModel.clickEvent.setPayload(null)
                }
                viewModel.clickEvent.setPayload(null)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.clickEvent.removeObservers(this)
    }

    override fun onToolBarClick(id: Int) {
        when (id) {
            R.id.ivRightIcon -> {
                Toast.makeText(requireContext(), "Coming Soon", Toast.LENGTH_SHORT).show()
            }
        }
    }
}