package co.yap.modules.dashboard.more.bankdetails.viewmodel

import android.app.Application
import co.yap.R
import co.yap.modules.dashboard.more.bankdetails.interfaces.IBankDetail
import co.yap.modules.dashboard.more.bankdetails.states.BankDetailStates
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.helpers.Utils
import co.yap.yapcore.managers.MyUserManager

class BankDetailViewModel(application: Application) : BaseViewModel<IBankDetail.State>(application),
    IBankDetail.ViewModel {

    override val clickEvent: SingleClickEvent = SingleClickEvent()
    override val state: BankDetailStates = BankDetailStates()

    init {
        MyUserManager.user?.accountNo?.let { state.account.set(it) }
        MyUserManager.user?.bank?.address?.let { state.addresse.set(it) }
        MyUserManager.user?.bank?.name?.let { state.bank.set(it) }
        MyUserManager.user?.iban?.let { state.iban.set(it) }
        MyUserManager.user?.bank?.swiftCode?.let { state.swift.set(it) }

        state.name.set(MyUserManager.user?.currentCustomer?.getFullName())
        state.title.set(getString(R.string.screen_more_detail_display_text_bank_details))
        MyUserManager.user?.currentCustomer?.getPicture()?.let {
            state.image.set(it)
        }
        state.initials.set(Utils.shortName(state.name.get()!!))
    }

    override fun handlePressOnView(id: Int) {
        clickEvent.setValue(id)
    }

}