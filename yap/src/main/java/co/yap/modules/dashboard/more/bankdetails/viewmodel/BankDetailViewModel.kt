package co.yap.modules.dashboard.more.bankdetails.viewmodel

import android.app.Application
import co.yap.modules.dashboard.more.bankdetails.interfaces.IBankDetail
import co.yap.modules.dashboard.more.bankdetails.states.BankDetailStates
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.managers.MyUserManager

class BankDetailViewModel(application: Application) : BaseViewModel<IBankDetail.State>(application),
    IBankDetail.ViewModel {

    override val clickEvent: SingleClickEvent = SingleClickEvent()
    override val state: BankDetailStates = BankDetailStates()

    init {
        state.account.set(MyUserManager.user?.accountNo)
        state.addresse.set(MyUserManager.user?.bank?.address)
        state.bank.set(MyUserManager.user?.bank?.name)
        state.iban.set(MyUserManager.user?.iban)
        state.swift.set(MyUserManager.user?.bank?.swiftCode)
        state.name.set(MyUserManager.user?.customer?.firstName + " " + MyUserManager.user?.customer?.lastName)
        state.title.set("YAP Bank details")
        state.image.set("")
        state.initials.set(co.yap.yapcore.helpers.Utils.shortName(state.name.get()!!))
    }

    override fun handlePressOnView(id: Int) {
        clickEvent.setValue(id)
    }

}