package co.yap.modules.dashboard.store.yapstore

import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import co.yap.R
import co.yap.networking.store.responsedtos.Store
import co.yap.yapcore.BaseListItemViewModel

class YapStoreItemVM : BaseListItemViewModel<Store>() {
    private lateinit var mItem: Store
    override fun setItem(item: Store, position: Int) {
        mItem = item
    }
    override fun getItem() = mItem
    override fun layoutRes() = R.layout.item_yap_store
    override fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController?) {
    }
    override fun onItemClick(view: View, data: Any, pos: Int) {
    }
}
