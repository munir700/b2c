package co.yap.household.onboarding.cardselection

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.view.ViewGroup
import androidx.lifecycle.Observer
import co.yap.household.BR
import co.yap.household.R
import co.yap.household.databinding.FragmentHhonBoardingCardSelectionBinding
import co.yap.modules.kyc.activities.DocumentsDashboardActivity
import co.yap.modules.kyc.enums.KYCAction
import co.yap.modules.location.activities.LocationSelectionActivity
import co.yap.networking.cards.responsedtos.Address
import co.yap.widgets.CircleView
import co.yap.widgets.viewpager.SimplePageOffsetTransformer
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.dagger.base.navigation.BaseNavViewModelFragment
import co.yap.yapcore.helpers.extentions.ExtraType
import co.yap.yapcore.helpers.extentions.dimen
import co.yap.yapcore.helpers.extentions.getValue
import co.yap.yapcore.helpers.extentions.launchActivityForResult
import co.yap.yapcore.leanplum.HHUserOnboardingEvents
import co.yap.yapcore.leanplum.trackEvent
import co.yap.yapcore.managers.MyUserManager
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_hhon_boarding_card_selection.*
import javax.inject.Inject


class HHOnBoardingCardSelectionFragment :
    BaseNavViewModelFragment<FragmentHhonBoardingCardSelectionBinding, IHHOnBoardingCardSelection.State, HHOnBoardingCardSelectionVM>(),
    TabLayout.OnTabSelectedListener {
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
        viewModel.clickEvent.observe(this, Observer { onClick(it) })
        viewModel.adapter?.set(adapter)
        viewPager?.adapter = adapter
        setupPager()
    }

    private fun setupPager() {

        viewPager?.apply {
            this.setPageTransformer(
                SimplePageOffsetTransformer(
                    resources.getDimensionPixelOffset(R.dimen._30sdp),
                    resources.getDimensionPixelOffset(R.dimen._40sdp)
                )
            )
            state.cardDesigns?.observe(this@HHOnBoardingCardSelectionFragment, Observer {
                TabLayoutMediator(tabLayout, this,
                    TabLayoutMediator.TabConfigurationStrategy { tab, position ->
                        val view =
                            layoutInflater.inflate(R.layout.item_circle_view, null) as CircleView
                        view.layoutParams = ViewGroup.LayoutParams(
                            dimen(R.dimen._24sdp),
                            dimen(R.dimen._24sdp)
                        )
                        try {
                            view.circleColor = Color.parseColor(it[position].designColorCode)
                        } catch (e: Exception) {
                        }
                        tabViews.add(view)
                        tab.customView = view
                    }).attach()
                tabLayout?.addOnTabSelectedListener(this@HHOnBoardingCardSelectionFragment)
                this.currentItem = 0
            })
        }
    }

    private fun onClick(id: Int) {
        when (id) {
            R.id.btnCompleteSetup -> {
                launchActivityForResult<DocumentsDashboardActivity>(
                    init = {
                        putExtra(
                            Constants.name,
                            MyUserManager.user?.currentCustomer?.firstName.toString()
                        )
                        putExtra(Constants.data, false)
                    }, completionHandler = { resultCode, data ->
                        data?.run {
                            val status = getStringExtra("status")
                            if (getBooleanExtra(Constants.result, false)) {
                                trackEvent(HHUserOnboardingEvents.ONBOARDING_NEW_HH_USER_EID.type)
                                launchAddressSelection()
                            } else if (getBooleanExtra(Constants.skipped, false)) {
                                trackEvent(HHUserOnboardingEvents.ONBOARDING_NEW_HH_USER_EID_DECLINED.type)
                                if (status == KYCAction.ACTION_EID_FAILED.name)
                                    navigateForward(
                                        HHOnBoardingCardSelectionFragmentDirections.toHHOnBoardingInvalidEidFragment(),
                                        arguments
                                    )
                            }
                        }

                    })
            }
            R.id.tvChangeLocation -> {
            }
            R.id.btnConfirmLocation -> {
            }
        }
    }

    private fun launchAddressSelection() {
        launchActivityForResult<LocationSelectionActivity>(init = {
            putExtra(LocationSelectionActivity.HEADING, "Your Card is ready to be sent out!")
            putExtra(
                LocationSelectionActivity.SUB_HEADING,
                "Make sure you are available at the below address"
            )
            putExtra(Constants.ADDRESS, state.address?.value)
            putExtra(LocationSelectionActivity.IS_ON_BOARDING, false)
        }, completionHandler = { resultCode, data ->
            if (resultCode == Activity.RESULT_OK) {
                val address =
                    data?.getValue(Constants.ADDRESS, ExtraType.PARCEABLE.name) as? Address
                val success =
                    data?.getValue(Constants.ADDRESS_SUCCESS, ExtraType.BOOLEAN.name) as? Boolean
                address?.let { selectedAddress ->
                    success?.let { success ->
                        if (success) {
                            //selectedAddress.designCode = viewModel.state.designCode
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
            tabViews[it.position].borderWidth = 6f
            tabViews[it.position].borderColor = Color.parseColor("#88848D")
        }
    }
}
