package co.yap.modules.dashboard.store.cardplans.viewmodels

import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import co.yap.R
import co.yap.yapcore.BaseListItemViewModel

class PlanBenefitsItemViewModel : BaseListItemViewModel<String>() {
    private lateinit var cardBenefits: String
    override fun setItem(item: String, position: Int) {
        cardBenefits = item
        notifyChange()
    }

    override fun getItem(): String = cardBenefits

    override fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController?) {
    }

    override fun layoutRes(): Int = R.layout.item_card_benefits


    override fun onItemClick(view: View, data: Any, pos: Int) {
    }

}