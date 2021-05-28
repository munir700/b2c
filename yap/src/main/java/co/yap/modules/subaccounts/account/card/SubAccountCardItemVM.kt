package co.yap.modules.subaccounts.account.card

import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import co.yap.R
import co.yap.networking.customers.household.responsedtos.SubAccount
import co.yap.yapcore.BaseListItemViewModel
import co.yap.yapcore.enums.CardDeliveryStatus
import co.yap.yapcore.enums.PartnerBankStatus
import co.yap.yapcore.helpers.DateUtils.dayDiffFromCurrent
import java.util.*

class SubAccountCardItemVM : BaseListItemViewModel<SubAccount>() {
    private var mItem: SubAccount =
        SubAccount()
    var position: Int = 0
    var status: String? = "Add new card"
    var statusColorResId: Int = R.color.colorPrimary

    //    1 cardStatus if REJECTED then ineligible for card
//    4: salaryTransferred if true show manage
//      2 if deliveryStatue is  shiped and pinCreated then show active
//
//    3 salaryTransferred  && salaryDueDate<5 salary in due 5 days
    override fun setItem(item: SubAccount, position: Int) {
        mItem = item
        this.position = position
//        item.pinCreated = true
        item.accountType?.let {
            if (item.cardStatus == PartnerBankStatus.REJECTED.status) {
//                status = "Ineligible for a card"
                status = item.cardStatus
                statusColorResId = R.color.error
            } else if (item.salaryTransferred == true)  status = item.cardStatus /*status = "Manage"*/
            else if (item.pinCreated == true) status  = item.cardStatus /*"Card is active!"*/
            else if (item.pinCreated == false && item.deliveryStatue == CardDeliveryStatus.SHIPPED.name) status =   item.cardStatus
                /*"Card is on the way"*/
            else if (item.salaryTransferred == true && dayDiffFromCurrent(Calendar.getInstance().time) > 5) {
//                status = "Salary due in 5 days"
                status = item.cardStatus
                statusColorResId = R.color.error
            }
            else status = item.cardStatus

        }
    }

    override fun getItem() = mItem
    override fun layoutRes() = R.layout.item_sub_account_card
    override fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController?) {
    }

    override fun onItemClick(view: View, data: Any, pos: Int) {
    }

}