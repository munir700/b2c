package co.yap.modules.dashboard.store.household.activities.subscriptionselection

import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import co.yap.R
import co.yap.modules.onboarding.models.WelcomeContent
import co.yap.yapcore.BaseListItemViewModel

class SubscriptionSelectionItemVM : BaseListItemViewModel<WelcomeContent>() {
    public lateinit var mItem: WelcomeContent
    override fun setItem(item: WelcomeContent, position: Int) {
        mItem = item
    }

    override fun getItem() = mItem

    override fun layoutRes() = R.layout.content_subscription_selection_pager_v2

    override fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController?) {}

    override fun onItemClick(view: View, data: Any, pos: Int) {
    }
}
