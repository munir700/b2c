package co.yap.modules.dashboard.store.young.card

import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.navigation.NavController
import co.yap.yapcore.BaseRVAdapter
import co.yap.yapcore.BaseViewHolder

class YoungCardEditAdapter(mValue: MutableList<YoungCardsDesign>, navigation: NavController?) :
    BaseRVAdapter<YoungCardsDesign, YoungCardItemVM, YoungCardEditAdapter.ViewHolder>(
        mValue,
        navigation
    ) {


    override fun getLayoutId(viewType: Int): Int {
        TODO("Not yet implemented")
    }

    override fun getViewHolder(
        view: View,
        viewModel: YoungCardItemVM,
        mDataBinding: ViewDataBinding,
        viewType: Int
    ): ViewHolder {
        TODO("Not yet implemented")
    }

    override fun getViewModel(viewType: Int): YoungCardItemVM {
        TODO("Not yet implemented")
    }

    override fun getVariableId(): Int {
        TODO("Not yet implemented")
    }

    class ViewHolder(view: View, viewModel: YoungCardItemVM, mDataBinding: ViewDataBinding) :
        BaseViewHolder<YoungCardsDesign, YoungCardItemVM>(view, viewModel, mDataBinding) {

    }
}