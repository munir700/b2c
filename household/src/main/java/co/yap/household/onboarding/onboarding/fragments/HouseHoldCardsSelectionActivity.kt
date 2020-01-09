package co.yap.household.onboarding.onboarding.fragments

import android.os.Bundle
import androidx.core.view.ViewCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager2.widget.ViewPager2
import co.yap.household.BR
import co.yap.household.R
import co.yap.household.databinding.FragmentHouseHoldCardSelectionBinding
import co.yap.household.onboarding.onboarding.interfaces.IHouseHoldCardsSelection
import co.yap.household.onboarding.onboarding.viewmodels.HouseHoldCardsSelectionViewModel
import co.yap.yapcore.BaseBindingActivity
import co.yap.yapcore.helpers.Utils
import co.yap.yapcore.helpers.toast

class HouseHoldCardsSelectionActivity : BaseBindingActivity<IHouseHoldCardsSelection.ViewModel>(),
    IHouseHoldCardsSelection.View {

    override fun getBindingVariable(): Int = BR.houseHoldViewModel

    override fun getLayoutId(): Int = R.layout.fragment_house_hold_card_selection

    override val viewModel: IHouseHoldCardsSelection.ViewModel
        get() = ViewModelProviders.of(this).get(HouseHoldCardsSelectionViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setObservers()
        setupPager()
    }

    override fun setObservers() {
        viewModel.clickEvent.observe(this, clickEvent)
    }

    private var clickEvent = Observer<Int> {
        when (it) {
            R.id.btnNext -> toast("Go next")

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
    }


    private fun getBindings(): FragmentHouseHoldCardSelectionBinding {
        return viewDataBinding as FragmentHouseHoldCardSelectionBinding
    }

    override fun onDestroy() {
        viewModel.clickEvent.removeObservers(this)
        super.onDestroy()
    }
}