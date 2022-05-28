package co.yap.household.onboarding.cardselection

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import co.yap.household.BR
import co.yap.household.R
import co.yap.household.databinding.FragmentHhonBoardingCardSelectionBinding
import co.yap.modules.kyc.activities.DocumentsDashboardActivity
import co.yap.modules.kyc.enums.KYCAction
import co.yap.modules.location.activities.LocationSelectionActivity
import co.yap.networking.cards.responsedtos.Address
import co.yap.networking.customers.household.requestdtos.SignUpFss
import co.yap.widgets.CircleView
import co.yap.widgets.viewpager.SimplePageOffsetTransformer
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.dagger.base.navigation.BaseNavViewModelFragment
import co.yap.yapcore.dagger.base.navigation.host.NAVIGATION_Graph_ID
import co.yap.yapcore.dagger.base.navigation.host.NAVIGATION_Graph_START_DESTINATION_ID
import co.yap.yapcore.dagger.base.navigation.host.NavHostPresenterActivity
import co.yap.yapcore.helpers.extentions.*
import co.yap.yapcore.hilt.base.navigation.BaseNavViewModelFragmentV2
import co.yap.yapcore.leanplum.HHUserOnboardingEvents
import co.yap.yapcore.leanplum.trackEvent
import co.yap.yapcore.managers.SessionManager
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class HHOnBoardingCardSelectionFragment :
    BaseNavViewModelFragmentV2<FragmentHhonBoardingCardSelectionBinding, IHHOnBoardingCardSelection.State, HHOnBoardingCardSelectionVM>(),
    TabLayout.OnTabSelectedListener {
    override val viewModel: HHOnBoardingCardSelectionVM by viewModels()

    @Inject
    lateinit var adapter: CardSelectionAdapter
    private var tabViews = ArrayList<CircleView>()
    override fun getBindingVariable() = BR.viewModel
    override fun getLayoutId() = R.layout.fragment_hhon_boarding_card_selection
    override fun setDisplayHomeAsUpEnabled() = false
    override fun toolBarVisibility() = false
    override fun postExecutePendingBindings(savedInstanceState: Bundle?) {
        super.postExecutePendingBindings(savedInstanceState)
        // setBackButtonDispatcher()
        viewModel.adapter?.set(adapter)
        viewDataBinding.viewPager.adapter = adapter
        setupPager()
    }

    private fun setupPager() {

        viewDataBinding.viewPager.apply {
            this.setPageTransformer(
                SimplePageOffsetTransformer(
                    resources.getDimensionPixelOffset(R.dimen._30sdp),
                    resources.getDimensionPixelOffset(R.dimen._40sdp)
                )
            )
            viewModel.state.cardDesigns?.observe(this@HHOnBoardingCardSelectionFragment, Observer {
                TabLayoutMediator(viewDataBinding.tabLayout, this,
                    TabLayoutMediator.TabConfigurationStrategy { tab, position ->
                        val view =
                            layoutInflater.inflate(R.layout.item_circle_view, null) as CircleView
                        view.layoutParams = ViewGroup.LayoutParams(
                            dimen(R.dimen._24sdp),
                            dimen(R.dimen._24sdp)
                        )
                        try {
                            view.circleColorStart = Color.parseColor(it[position].designColorCode)
                            view.circleColorEnd = Color.parseColor(it[position].designColorCode)
                            view.circleColorDirection = CircleView.GradientDirection.TOP_TO_BOTTOM
                            //tab.tag = it[position]
                        } catch (e: Exception) {
                        }
                        viewDataBinding.tabLayout.addOnTabSelectedListener(this@HHOnBoardingCardSelectionFragment)
                        tabViews.add(view)
                        onTabSelected(viewDataBinding.tabLayout.getTabAt(0))
                        viewModel.state.designCode?.value =
                            this@HHOnBoardingCardSelectionFragment.adapter.getData()[0].designCode
                        tab.customView = view
                    }).attach()

            })
        }
    }

    override fun onClick(id: Int) {
        when (id) {
            R.id.btnCompleteSetup -> {
                viewModel.signupToFss(
                    SignUpFss(
                        designCode = adapter.getData()[viewDataBinding.tabLayout.selectedTabPosition].designCode,
                        productCode = adapter.getData()[viewDataBinding.tabLayout.selectedTabPosition].productCode
                    )
                ) {
                    launchActivityForResult<DocumentsDashboardActivity>(
                        init = {
                            putExtra(
                                Constants.name,
                                SessionManager.user?.currentCustomer?.firstName.toString()
                            )
                            putExtra(Constants.data, false)
                        }, completionHandler = { resultCode, data ->
                            data?.let {
                                val status = it.getStringExtra("status")
                                if (it.getBooleanExtra(Constants.result, false)) {
                                    trackEvent(HHUserOnboardingEvents.ONBOARDING_NEW_HH_USER_EID.type)
                                    Handler().post { launchAddressSelection(true) }
                                    return@let
                                } else if (it.getBooleanExtra(Constants.skipped, false)) {
                                    trackEvent(HHUserOnboardingEvents.ONBOARDING_NEW_HH_USER_EID_DECLINED.type)
                                    if (status == KYCAction.ACTION_EID_FAILED.name)
                                       /* navigateForward(
                                            HHOnBoardingCardSelectionFragmentDirections.toHHOnBoardingInvalidEidFragment(),
                                            arguments
                                        )*/
                                    else
                                        launchActivity<NavHostPresenterActivity>(clearPrevious = true) {
                                            putExtra(
                                                NAVIGATION_Graph_ID,
                                                R.navigation.hh_main_nav_graph
                                            )
                                            putExtra(
                                                NAVIGATION_Graph_START_DESTINATION_ID,
                                                R.id.householdDashboardFragment
                                            )
                                        }
                                }
                            }

                        })
                }
            }
            R.id.tvChangeLocation -> {
                launchAddressSelection(false)
            }
            R.id.btnConfirmLocation -> {
                viewModel.state.address?.value?.let { address ->
                    viewModel.signupToFss(
                        SignUpFss(
                            designCode = adapter.getData()[viewDataBinding.tabLayout.selectedTabPosition].designCode,
                            productCode = adapter.getData()[viewDataBinding.tabLayout.selectedTabPosition].productCode
                        )
                    ) {
                        viewModel.orderHouseHoldPhysicalCardRequest(address) {
                            if (it) {
                                navigateForward(
                                    HHOnBoardingCardSelectionFragmentDirections.toKycSuccessFragment(),
                                    arguments?.plus(bundleOf(Constants.ADDRESS to address))
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    private fun launchAddressSelection(gotoNext: Boolean) {
        launchActivityForResult<LocationSelectionActivity>(init = {
            putExtra(LocationSelectionActivity.HEADING, "Your Card is ready to be sent out!")
            putExtra(
                LocationSelectionActivity.SUB_HEADING,
                "Make sure you are available at the below address"
            )
            putExtra(Constants.ADDRESS, viewModel.state.address?.value ?: Address())
            putExtra(LocationSelectionActivity.IS_ON_BOARDING, false)
        }, completionHandler = { resultCode, data ->
            if (resultCode == Activity.RESULT_OK) {
                data?.getParcelableExtra<Address>(Constants.ADDRESS)?.apply {
                    viewModel.state.address?.value = this
                }
                val success =
                    data?.getValue(Constants.ADDRESS_SUCCESS, ExtraType.BOOLEAN.name) as? Boolean
                viewModel.state.address?.value?.let { selectedAddress ->
                    success?.let { success ->
                        if (success && gotoNext) {
                            //selectedAddress.designCode = viewModel.viewModel.state.designCode
                            viewModel.orderHouseHoldPhysicalCardRequest(selectedAddress) {
                                if (it) {
                                    navigateForward(
                                        HHOnBoardingCardSelectionFragmentDirections.toKycSuccessFragment(),
                                        arguments
                                    )
                                }
                            }
                        }
                    }
                }
            }
        })
    }

    override fun onTabReselected(tab: TabLayout.Tab?) {
    }

    override fun onTabUnselected(tab: TabLayout.Tab?) {
        tab?.let {
            tabViews[it.position].borderWidth = 0f
            tabViews[it.position].borderColor = Color.parseColor("#88848D")
        }
    }

    override fun onTabSelected(tab: TabLayout.Tab?) {
        tab?.let {
            viewModel.state.designCode?.value =
                adapter.getData()[it.position].designCode// (tab.tag as HouseHoldCardsDesign).designCode
            tabViews[it.position].borderWidth = 6f
            tabViews[it.position].borderColor = Color.parseColor("#88848D")
        }
    }
}
