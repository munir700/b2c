package co.yap.modules.dashboard.store.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.BR
import co.yap.R
import co.yap.databinding.FragmentYapStoreBinding
import co.yap.modules.dashboard.main.fragments.YapDashboardChildFragment
import co.yap.modules.dashboard.store.cardplans.activities.CardPlansActivity
import co.yap.networking.store.responsedtos.Store
import co.yap.widgets.guidedtour.OnTourItemClickListener
import co.yap.widgets.guidedtour.TourSetup
import co.yap.widgets.guidedtour.models.GuidedTourViewDetail
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.dagger.base.navigation.host.NAVIGATION_Graph_ID
import co.yap.yapcore.dagger.base.navigation.host.NAVIGATION_Graph_START_DESTINATION_ID
import co.yap.yapcore.dagger.base.navigation.host.NavHostPresenterActivity
import co.yap.yapcore.helpers.TourGuideManager
import co.yap.yapcore.helpers.TourGuideType
import co.yap.yapcore.helpers.extentions.launchActivity
import co.yap.yapcore.helpers.extentions.launchTourGuide
import co.yap.yapcore.interfaces.OnItemClickListener
import co.yap.yapcore.managers.SessionManager
import com.liveperson.infra.configuration.Configuration.getDimension
import kotlinx.android.synthetic.main.fragment_yap_store.*

class YapStoreFragment : YapDashboardChildFragment<IYapStore.ViewModel>(), IYapStore.View {

    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.fragment_yap_store
    private var tourStep: TourSetup? = null

    override val viewModel: YapStoreViewModel
        get() = ViewModelProviders.of(this).get(YapStoreViewModel::class.java)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getStoreList()
        initComponents()
        setObservers()
    }

    private fun initComponents() {
        recycler_stores.adapter = YapStoreAdaptor(mutableListOf())
        (recycler_stores.adapter as YapStoreAdaptor).allowFullItemClickListener = true
        (recycler_stores.adapter as YapStoreAdaptor).setItemListener(listener)

    }

    private fun setObservers() {
        viewModel.clickEvent.observe(viewLifecycleOwner, Observer { onClick(it) })
        viewModel.storesLiveData.observe(viewLifecycleOwner, Observer {
            (recycler_stores.adapter as YapStoreAdaptor).setList(it)
        })
        viewModel.parentViewModel?.isYapStoreFragmentVisible?.observe(
            viewLifecycleOwner,
            Observer { isStoreFragmentVisible ->
                if (isStoreFragmentVisible) {
                    tourStep = requireActivity().launchTourGuide(TourGuideType.STORE_SCREEN) {
                        this.addAll(setViewsArray())
                    }
                } else {
                    tourStep?.let {
                        if (it.isShowing)
                            it.dismiss()
                    }
                }
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
                            Constants.ITEM_STORE_CARD_PLANS -> {
                                launchActivity<CardPlansActivity> { }
                            }
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

    override fun onToolBarClick(id: Int) {
        when (id) {
            R.id.ivRightIcon -> {
                Toast.makeText(requireContext(), "Coming Soon", Toast.LENGTH_SHORT).show()
//                val tour = TourSetup(requireActivity(), setViewsArray())
//                tour.startTour()
            }
        }
    }


    private fun setViewsArray(): ArrayList<GuidedTourViewDetail> {
        val list = ArrayList<GuidedTourViewDetail>()
        list.add(
            GuidedTourViewDetail(
                getBindings().toolbar.findViewById(R.id.ivRightIcon),
                getString(R.string.screen_dashboard_tour_guide_display_text_packages),
                getString(R.string.screen_dashboard_tour_guide_display_text_packages_des),
                padding = 0f,
                circleRadius = getDimension(R.dimen._60sdp),
                showSkip = false,
                showPageNo = false,
                btnText = getString(R.string.screen_dashboard_tour_guide_display_text_got_it),
                callBackListener = tourItemListener
            )
        )
        return list
    }

    private val tourItemListener = object : OnTourItemClickListener {
        override fun onTourCompleted(pos: Int) {
            TourGuideManager.lockTourGuideScreen(
                TourGuideType.STORE_SCREEN,
                completed = true
            )
        }

        override fun onTourSkipped(pos: Int) {
            TourGuideManager.lockTourGuideScreen(
                TourGuideType.STORE_SCREEN,
                skipped = true
            )
        }
    }

    private fun getBindings(): FragmentYapStoreBinding {
        return viewDataBinding as FragmentYapStoreBinding
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.clickEvent.removeObservers(this)
        viewModel.parentViewModel?.isYapStoreFragmentVisible?.removeObservers(this)
    }
}