package co.yap.modules.dashboard.store.young.benefits.adapter

import android.view.View
import co.yap.BR
import androidx.databinding.ViewDataBinding
import androidx.navigation.NavController
import co.yap.yapcore.BaseRVAdapter
import co.yap.yapcore.BaseViewHolder
import javax.inject.Inject

class YoungBenefitsAdapter @Inject constructor(
    mBenefitList: MutableList<YoungBenefitsModel>,
    navigation: NavController?
) :
    BaseRVAdapter<YoungBenefitsModel, YoungBenefitsItemVM, BaseViewHolder<YoungBenefitsModel, YoungBenefitsItemVM>>(
        mBenefitList,
        navigation
    ) {
    override fun getLayoutId(viewType: Int) = getViewModel(viewType).layoutRes()
    override fun getViewHolder(
        view: View,
        viewModel: YoungBenefitsItemVM,
        mDataBinding: ViewDataBinding,
        viewType: Int
    ) = BaseViewHolder(view, viewModel, mDataBinding)

    override fun getViewModel(viewType: Int) = YoungBenefitsItemVM()
    override fun getVariableId() = BR.viewModel
}

