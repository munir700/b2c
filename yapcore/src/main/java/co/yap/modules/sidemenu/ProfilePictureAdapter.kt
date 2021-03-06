package co.yap.modules.sidemenu

import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.navigation.NavController
import co.yap.networking.customers.responsedtos.AccountInfo
import co.yap.yapcore.BR
import co.yap.yapcore.BaseRVAdapter
import co.yap.yapcore.BaseViewHolder

class ProfilePictureAdapter(mValue: MutableList<AccountInfo>, navigation: NavController?) :
    BaseRVAdapter<AccountInfo, ProfilePictureItemVM, BaseViewHolder<AccountInfo, ProfilePictureItemVM>>(
        mValue,
        navigation
    ) {
    override fun getLayoutId(viewType: Int) = getViewModel(viewType).layoutRes()
    override fun getViewHolder(
        view: View,
        viewModel: ProfilePictureItemVM,
        mDataBinding: ViewDataBinding, viewType: Int
    ) = BaseViewHolder(view, viewModel, mDataBinding)

    override fun getViewModel(viewType: Int) = ProfilePictureItemVM()
    override fun getVariableId() = BR.profilePictureItemVM
}