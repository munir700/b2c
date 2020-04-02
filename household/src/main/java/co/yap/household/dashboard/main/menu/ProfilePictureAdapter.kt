package co.yap.household.dashboard.main.menu

import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.navigation.NavController
import co.yap.BR
import co.yap.networking.customers.responsedtos.AccountInfo
import co.yap.yapcore.BaseRVAdapter
import co.yap.yapcore.BaseViewHolder

class ProfilePictureAdapter(mValue: MutableList<AccountInfo>, navigation: NavController?) :
    BaseRVAdapter<AccountInfo, ProfilePictureItemVM, ProfilePictureAdapter.ViewHolder>(
        mValue,
        navigation
    ) {
    override fun getLayoutId(viewType: Int) = getViewModel().layoutRes()
    override fun getViewHolder(
        view: View,
        viewModel: ProfilePictureItemVM,
        mDataBinding: ViewDataBinding, viewType: Int
    ): ViewHolder {
        return ViewHolder(
            view,
            viewModel,
            mDataBinding
        )
    }

    override fun getViewModel() = ProfilePictureItemVM()
    override fun getVariableId() = BR.profilePictureItemVM
    class ViewHolder(view: View, viewModel: ProfilePictureItemVM, mDataBinding: ViewDataBinding) :
        BaseViewHolder<AccountInfo, ProfilePictureItemVM>(view, viewModel, mDataBinding)
}