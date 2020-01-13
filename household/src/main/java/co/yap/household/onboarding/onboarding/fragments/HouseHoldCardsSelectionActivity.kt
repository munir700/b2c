package co.yap.household.onboarding.onboarding.fragments

//import androidx.viewpager2.widget.ViewPager2
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import co.yap.household.BR
import co.yap.household.R
import co.yap.household.databinding.FragmentHouseHoldCardSelectionBinding
import co.yap.household.onboarding.kycsuccess.KycSuccessActivity
import co.yap.household.onboarding.onboarding.interfaces.IHouseHoldCardsSelection
import co.yap.household.onboarding.onboarding.viewmodels.HouseHoldCardsSelectionViewModel
import co.yap.modules.location.activities.LocationSelectionActivity
import co.yap.networking.cards.requestdtos.OrderCardRequest
import co.yap.networking.cards.responsedtos.Address
import co.yap.translation.Strings
import co.yap.yapcore.BaseBindingActivity
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.constants.RequestCodes
import co.yap.yapcore.helpers.Utils
import co.yap.yapcore.helpers.extentions.ExtraType
import co.yap.yapcore.helpers.extentions.getValue
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.android.material.tabs.TabLayoutMediator

class HouseHoldCardsSelectionActivity : BaseBindingActivity<IHouseHoldCardsSelection.ViewModel>(),
    IHouseHoldCardsSelection.View {

    companion object {
        const val data = "isFromExisting"
        fun newIntent(context: Context, isFromExisting: Boolean?): Intent {
            val intent = Intent(context, HouseHoldCardsSelectionActivity::class.java)
            intent.putExtra(data, isFromExisting)
            return intent
        }
    }

    override fun getBindingVariable(): Int = BR.houseHoldViewModel

    override fun getLayoutId(): Int = R.layout.fragment_house_hold_card_selection

    override val viewModel: IHouseHoldCardsSelection.ViewModel
        get() = ViewModelProviders.of(this).get(HouseHoldCardsSelectionViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setObservers()
        setupPager()
        setUpUI()
    }

    override fun setObservers() {
        viewModel.clickEvent.observe(this, clickEvent)
        viewModel.changedPosition.observe(this, Observer {
            if (getBindings().vpCards.currentItem != it) {
                getBindings().vpCards.setCurrentItem(it, true)
            }
        })
        viewModel.orderCardRequestSuccess.observe(this, Observer {
            startActivity(Intent(this, KycSuccessActivity::class.java))
        })
    }

    override fun setUpUI() {
        if (getIntentData()) {
            viewModel.state.locationVisibility = true
            viewModel.state.buttonVisibility = false
            viewModel.state.cardsHeading =
                getString(Strings.screen_house_hold_card_color_selection_display_text_heading_existing_user)
        } else {
            viewModel.state.buttonVisibility = true
            viewModel.state.locationVisibility = false
            viewModel.state.cardsHeading =
                getString(Strings.screen_house_hold_card_color_selection_display_text_heading)
        }
    }

    private var clickEvent = Observer<Int> {
        when (it) {
            R.id.btnNext -> {
                openLocationScreen(
                    viewModel.state.address ?: Address("", "")
                )
            }
            R.id.tvChangeLocation -> {
                openLocationScreen(
                    viewModel.state.address ?: Address("", "")
                )
            }
            R.id.btnConfirmLocation -> {
                viewModel.orderHouseHoldPhysicalCardRequest(
                    OrderCardRequest(
                        address1 = viewModel.state.address?.address1,
                        address2 = viewModel.state.address?.address2,
                        latitude = viewModel.state.address?.latitude,
                        longitude = viewModel.state.address?.longitude
                    )
                )
                //startActivity(Intent(this, KycSuccessActivity::class.java))
            }
        }
    }

    private fun setupPager() {
        getBindings().vpCards.adapter = viewModel.adapter

        with(getBindings().vpCards) {
            clipToPadding = false
            clipChildren = false
            offscreenPageLimit = 3
        }

        val pageMarginPx = Utils.getDimensionInPercent(this, true, 14)
        val offsetPx = Utils.getDimensionInPercent(this, true, 14)
        getBindings().vpCards.setPageTransformer { page, position ->

            val viewPager = page.parent.parent as ViewPager2
            val offset = position * -(2 * offsetPx + pageMarginPx)
            if (viewPager.orientation == ViewPager2.ORIENTATION_HORIZONTAL) {
                if (ViewCompat.getLayoutDirection(viewPager) == ViewCompat.LAYOUT_DIRECTION_RTL) {
                    page.translationX = -offset
                } else {
                    page.translationX = offset
                }
            } else {
                page.translationY = offset
            }
        }
        getBindings().vpCards.registerOnPageChangeCallback(object : OnPageChangeCallback() {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                viewModel.state.position = position
            }

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
            }

            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
            }
        })
        TabLayoutMediator(getBindings().tabLayout, getBindings().vpCards,
            TabLayoutMediator.TabConfigurationStrategy { tab, position ->
                tab.icon =
                    ContextCompat.getDrawable(this, backgrounds[position])
            }).attach()

        getBindings().tabLayout.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                getBindings().vpCards.currentItem = tab.position
                if (tab.isSelected) {
                    tab.icon = ContextCompat.getDrawable(
                        this@HouseHoldCardsSelectionActivity,
                        R.drawable.circle
                    )
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
                tab.icon =
                    ContextCompat.getDrawable(
                        this@HouseHoldCardsSelectionActivity,
                        backgrounds[tab.position]
                    )
            }

            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
    }

    private val backgrounds = intArrayOf(
        co.yap.yapcore.R.drawable.bg_round_light_red,
        co.yap.yapcore.R.drawable.bg_round_light_blue,
        co.yap.yapcore.R.drawable.bg_round_light_green
    )

    private fun getIntentData(): Boolean {

        return intent.getBooleanExtra("isFromExisting", false)
//        return true
    }


    private fun getBindings(): FragmentHouseHoldCardSelectionBinding {
        return viewDataBinding as FragmentHouseHoldCardSelectionBinding
    }

    override fun onDestroy() {
        viewModel.clickEvent.removeObservers(this)
        super.onDestroy()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            val address = data?.getValue(Constants.ADDRESS, ExtraType.PARCEABLE.name) as? Address
            val success =
                data?.getValue(Constants.ADDRESS_SUCCESS, ExtraType.BOOLEAN.name) as? Boolean
            address?.let {
                success?.let { success ->
                    if (success) {
                        viewModel.state.locationVisibility = true
                        viewModel.state.buttonVisibility = false
                        populateAddressFields(it)
                    }
                }
            }
        }
    }

    private fun populateAddressFields(address: Address?) {
        address.let {
            viewModel.state.address = address
        }
    }

    private fun openLocationScreen(address: Address) {
        startActivityForResult(
            LocationSelectionActivity.newIntent(
                context = this,
                address = address,
                headingTitle = "Your Card is ready to be sent out!",
                subHeadingTitle = "Make sure you are available at the below address"
            ), RequestCodes.REQUEST_FOR_LOCATION
        )
    }
}
