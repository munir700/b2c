package co.yap.modules.dashboard.yapit.addmoney.easybanktransfer.banklist

import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import co.yap.R
import co.yap.networking.leanteach.responsedtos.banklistmodels.BankListMainModel
import co.yap.yapcore.BaseListItemViewModel

class BankListItemViewModel : BaseListItemViewModel<BankListMainModel>() {
    private lateinit var mItem: BankListMainModel

    override fun layoutRes(): Int = R.layout.item_bank_list

    override fun getItem(): BankListMainModel = mItem

    override fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController?) {
    }

    override fun onItemClick(view: View, data: Any, pos: Int) {
    }

    override fun setItem(item: BankListMainModel, position: Int) {
        mItem = item
        notifyChange()
    }
}