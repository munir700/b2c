package co.yap.modules.kyc.amendments.missinginfo

import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.navigation.NavController
import co.yap.BR
import co.yap.yapcore.BaseRVAdapter
import co.yap.yapcore.BaseViewHolder

class MissingInfoAdapter(val listItems: MutableList<String>, navigation: NavController?) :
    BaseRVAdapter<String, MissingInfoItemViewModel, MissingInfoAdapter.ViewHolder>(
        listItems,
        navigation
    ) {

    override fun getLayoutId(viewType: Int): Int = getViewModel().layoutRes()
    override fun getViewHolder(
        view: View,
        viewModel: MissingInfoItemViewModel,
        mDataBinding: ViewDataBinding,
        viewType: Int
    ): ViewHolder = ViewHolder(
        view,
        viewModel,
        mDataBinding
    )

    override fun getViewModel(): MissingInfoItemViewModel = MissingInfoItemViewModel()

    override fun getVariableId(): Int = BR.viewModel
    class ViewHolder(
        view: View,
        viewModel: MissingInfoItemViewModel,
        mDataBinding: ViewDataBinding
    ) :
        BaseViewHolder<String, MissingInfoItemViewModel>(view, viewModel, mDataBinding)
}