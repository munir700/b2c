package co.yap.modules.kyc.amendments.missinginfo

import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import co.yap.R
import co.yap.networking.notification.responsedtos.HomeNotification
import co.yap.yapcore.BaseListItemViewModel

class MissingInfoItemViewModel :
    BaseListItemViewModel<String>() {
    private lateinit var mItem: String
    private var position: Int = 0
    override fun setItem(item: String, position: Int) {
        mItem = item
        this.position = position
    }

    override fun getItem() = mItem

    override fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController?) {

    }

    override fun layoutRes() = R.layout.item_misssing_info

    override fun onItemClick(view: View, data: Any, pos: Int) {
    }

    fun onDeleteClick(view: View) {
    }

    fun getTextForItem() = "$position. $mItem"

}