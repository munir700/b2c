package co.yap.modules.kyc.amendments.missinginfo

import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.navigation.NavController
import co.yap.BR
import co.yap.yapcore.BaseRVAdapter
import co.yap.yapcore.BaseViewHolder

class MissingInfoAdapter(val listItems: MutableList<String>, navigation: NavController?) :
    BaseRVAdapter<String, MissingInfoItemViewModel, BaseViewHolder<String, MissingInfoItemViewModel>>(
        listItems,
        navigation
    ) {
    init {
        setHasStableIds(true)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getLayoutId(viewType: Int): Int = getViewModel(viewType).layoutRes()
    override fun getViewHolder(
        view: View,
        viewModel: MissingInfoItemViewModel,
        mDataBinding: ViewDataBinding,
        viewType: Int
    ) = BaseViewHolder(view, viewModel, mDataBinding)

    override fun getViewModel(viewType: Int): MissingInfoItemViewModel = MissingInfoItemViewModel()

    override fun getVariableId(): Int = BR.viewModel
}