package co.yap.household.onboarding.cardselection

import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.navigation.NavController
import co.yap.household.BR
import co.yap.household.R
import co.yap.household.databinding.ItemCardSelectionBinding
import co.yap.networking.customers.responsedtos.HouseHoldCardsDesign
import co.yap.yapcore.BaseRVAdapter
import co.yap.yapcore.BaseViewHolder
import co.yap.yapcore.helpers.extentions.dimen
import javax.inject.Inject

class CardSelectionAdapter @Inject constructor(mValue: MutableList<HouseHoldCardsDesign>, navigation: NavController?) :
    BaseRVAdapter<HouseHoldCardsDesign, CardSelectionItemVM, CardSelectionAdapter.ViewHolder>(
        mValue,
        navigation
    ) {
    override fun getLayoutId(viewType: Int) = getViewModel(viewType).layoutRes()
    override fun getViewHolder(
        view: View,
        viewModel: CardSelectionItemVM,
        mDataBinding: ViewDataBinding, viewType: Int
    ) = ViewHolder(view, viewModel, mDataBinding)

    override fun getViewModel(viewType: Int) = CardSelectionItemVM()
    override fun getVariableId() = BR.cardSelectionItemVM
    class ViewHolder(view: View, viewModel: CardSelectionItemVM, mDataBinding: ViewDataBinding) :
        BaseViewHolder<HouseHoldCardsDesign, CardSelectionItemVM>(view, viewModel, mDataBinding) {
        init {
            val binding = mDataBinding as ItemCardSelectionBinding
            val params = binding.ivCard.layoutParams
            params.width = binding.ivCard.context.dimen(R.dimen._204sdp)
            params.height = binding.ivCard.context.dimen(R.dimen._216sdp)
            binding.ivCard.layoutParams = params
        }
    }
}