package co.yap.modules.dashboard.cards.addpaymentcard.spare.virtual

import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import co.yap.R
import co.yap.modules.dashboard.cards.addpaymentcard.models.VirtualCardModel
import co.yap.yapcore.BaseListItemViewModel

class VirtualCardItemViewModel  : BaseListItemViewModel<VirtualCardModel>() {

    private lateinit var mItem: VirtualCardModel

    override fun setItem(item: VirtualCardModel, position: Int) {
        mItem = item
    }

    override fun getItem()= mItem

    override fun layoutRes()= R.layout.item_virtual_card

    override fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController?) {
    }

    override fun onItemClick(view: View, data: Any, pos: Int) {
    }
}