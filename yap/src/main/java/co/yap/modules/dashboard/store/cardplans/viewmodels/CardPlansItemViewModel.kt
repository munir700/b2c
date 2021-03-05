package co.yap.modules.dashboard.store.cardplans.viewmodels

import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import co.yap.R
import co.yap.yapcore.BaseListItemViewModel

class CardPlansItemViewModel : BaseListItemViewModel<Int>() {
    private var mItem: Int? = null

    override fun setItem(item: Int, position: Int) {
        mItem = item
        notifyChange()
    }

    override fun getItem(): Int = mItem ?: 0
    override fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController?) {
    }

    override fun layoutRes(): Int = R.layout.item_card_plans


    override fun onItemClick(view: View, data: Any, pos: Int) {
    }
}