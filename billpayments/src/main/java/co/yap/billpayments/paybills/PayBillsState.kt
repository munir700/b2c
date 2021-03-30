package co.yap.billpayments.paybills

import co.yap.yapcore.BaseState

class PayBillsState : BaseState(), IPayBills.State {
    override var showBillCategory: Boolean = false
}