package co.yap.household.dashboard.home

import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import co.yap.household.R
import co.yap.networking.models.ApiResponse
import co.yap.yapcore.BaseListItemViewModel

class HomeTransactionChildItemVM : BaseListItemViewModel<ApiResponse>() {
    private lateinit var mItem: ApiResponse
    override fun setItem(item: ApiResponse, position: Int) {
        this.mItem = item
    }

    override fun getItem() = mItem

    override fun layoutRes() = R.layout.item_home_transaction_child

    override fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController?) {}

    override fun onItemClick(view: View, data: Any, pos: Int) {}
}