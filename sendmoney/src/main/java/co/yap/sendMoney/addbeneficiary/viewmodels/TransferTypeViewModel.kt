package co.yap.sendMoney.addbeneficiary.viewmodels

import android.app.Application
import co.yap.sendMoney.addbeneficiary.interfaces.ITransferType
import co.yap.sendMoney.addbeneficiary.states.TransferTypeState
import co.yap.sendMoney.viewmodels.SendMoneyBaseViewModel
import co.yap.networking.customers.CustomersRepository
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.translation.Strings
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.enums.SendMoneyBeneficiaryType

class TransferTypeViewModel(application: Application) :
    SendMoneyBaseViewModel<ITransferType.State>(application), ITransferType.ViewModel,
    IRepositoryHolder<CustomersRepository> {

    override val repository: CustomersRepository = CustomersRepository

    override val state: TransferTypeState = TransferTypeState()

    override var clickEvent: SingleClickEvent = SingleClickEvent()

    override fun handlePressOnTypeBankTransfer(id: Int) {
        parentViewModel?.selectedCountry?.value?.let {
            it.isoCountryCode2Digit?.let { code ->
                if (code.equals("ae", true)) {
                    parentViewModel?.beneficiary?.value?.beneficiaryType =
                        SendMoneyBeneficiaryType.DOMESTIC.name
                    clickEvent.setValue(id)
                } else {
                    it.getCurrency()?.rmtCountry?.let { isRmt ->
                        if (isRmt) {
                            parentViewModel?.beneficiary?.value?.beneficiaryType =
                                SendMoneyBeneficiaryType.RMT.name
                            clickEvent.setValue(id)
                        } else {
                            parentViewModel?.beneficiary?.value?.beneficiaryType =
                                SendMoneyBeneficiaryType.SWIFT.name
                            clickEvent.setValue(id)
                        }
                    }
                }
            }
        }
    }

    override fun handlePressOnTypeCashPickUp(id: Int) {
        parentViewModel?.beneficiary?.value?.beneficiaryType =
            SendMoneyBeneficiaryType.CASHPAYOUT.name
        clickEvent.setValue(id)
    }

    override fun onResume() {
        super.onResume()
        setToolBarTitle(getString(Strings.screen_add_beneficiary_display_text_title))
        parentViewModel?.state?.toolbarVisibility?.set(true)
        parentViewModel?.state?.leftIcon?.set(true)
        //toggleAddButtonVisibility(false)
    }
}
