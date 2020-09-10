package co.yap.modules.dashboard.store.young.landing.benefits.adapter

import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import co.yap.R
import co.yap.yapcore.BaseListItemViewModel


class YoungBenefitsItemVM : BaseListItemViewModel<YoungBenefitsModel>(){
    private lateinit var mItem: YoungBenefitsModel
    override fun setItem(item: YoungBenefitsModel, position: Int) {
        mItem = item
    }
    override fun getItem() = mItem
    override fun layoutRes() = R.layout.layout_item_young_benefits
    override fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController?) {
    }
    override fun onItemClick(view: View, data: Any, pos: Int) {
    }

}
