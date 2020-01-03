package co.yap.household.onboarding.onboarding.fragments

import android.os.Bundle
import android.view.View
import androidx.core.view.ViewCompat
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager2.widget.ViewPager2
import co.yap.household.BR
import co.yap.household.R
import co.yap.household.databinding.FragmentHouseHoldCardSelectionBinding
import co.yap.household.onboarding.onboarding.interfaces.IHouseHoldCardsSelection
import co.yap.household.onboarding.onboarding.viewmodels.HouseHoldCardsSelectionViewModel
import co.yap.yapcore.BaseBindingFragment
import co.yap.yapcore.helpers.Utils
import co.yap.yapcore.interfaces.OnItemClickListener

class HouseHoldCardsSelectionFragment : BaseBindingFragment<IHouseHoldCardsSelection.ViewModel>(),
    IHouseHoldCardsSelection.View {

    lateinit var adapter: HouseHoldCardSelectionAdapter
    override fun getBindingVariable(): Int = BR.houseHoldViewModel

    override fun getLayoutId(): Int = R.layout.fragment_house_hold_card_selection

    override val viewModel: IHouseHoldCardsSelection.ViewModel
        get() = ViewModelProviders.of(this).get(HouseHoldCardsSelectionViewModel::class.java)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupPager()
        setupList()
    }

    private fun setupList() {

        var list: MutableList<Int> = MutableList1(2) { index -> 0 + index }
        adapter.setList(list)
    }

    inline fun <T> MutableList1(size: Int, init: (index: Int) -> T): MutableList<Int> {
        val list = MutableList(2) { index -> 0 + index }
        return list
    }

    private fun setupPager() {
        adapter = HouseHoldCardSelectionAdapter(requireContext(), mutableListOf())
        getBindings().vpCards.adapter = adapter

        with(getBindings().vpCards) {
            clipToPadding = false
            clipChildren = false
            offscreenPageLimit = 3
        }

        val pageMarginPx = Utils.getDimensionInPercent(context!!, true, 14)
        val offsetPx = Utils.getDimensionInPercent(context!!, true, 14)
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

        adapter.setItemListener(object : OnItemClickListener {
            override fun onItemClick(view: View, data: Any, pos: Int) {
//                viewModel.clickEvent.setPayload(
//                    SingleClickEvent.AdaptorPayLoadHolder(
//                        view,
//                        data,
//                        pos
//                    )
//                )
//                viewModel.clickEvent.setValue(view.id)
            }
        })
    }

    fun getBindings(): FragmentHouseHoldCardSelectionBinding {
        return viewDataBinding as FragmentHouseHoldCardSelectionBinding
    }
}