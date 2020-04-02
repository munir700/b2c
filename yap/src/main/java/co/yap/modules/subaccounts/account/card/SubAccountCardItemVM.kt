package co.yap.modules.subaccounts.account.card

import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import co.yap.R
import co.yap.networking.models.ApiResponse
import co.yap.yapcore.BaseListItemViewModel

class SubAccountCardItemVM : BaseListItemViewModel<ApiResponse>() {
    private lateinit var mItem: ApiResponse
    override fun setItem(item: ApiResponse, position: Int) {
        mItem = item
    }

    override fun getItem() = mItem

    override fun layoutRes() = R.layout.item_sub_account_card

    override fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController?) {
    }

    override fun onItemClick(view: View, data: Any, pos: Int) {
    }
}