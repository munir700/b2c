package co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.viewmodels

import android.app.Application
import co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.interfaces.ITransferType
import co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.states.TransferTypeState
import co.yap.modules.dashboard.yapit.sendmoney.viewmodels.SendMoneyBaseViewModel
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
                    parentViewModel?.transferType?.value =
                        (SendMoneyBeneficiaryType.DOMESTIC.name)
                } else {
                    it.rmtCountry?.let { isRmt ->
                        if (isRmt) {
                            parentViewModel?.transferType?.value =
                                (SendMoneyBeneficiaryType.RMT.name)
                        } else {
                            parentViewModel?.transferType?.value =
                                (SendMoneyBeneficiaryType.SWIFT.name)
                        }
                    }
                }
            }
        }
        clickEvent.setValue(id)
    }

    override fun handlePressOnTypeCashPickUp(id: Int) {
        parentViewModel?.transferType?.value =
            (SendMoneyBeneficiaryType.CASHPAYOUT.name)
        clickEvent.setValue(id)
    }

    override fun onResume() {
        super.onResume()
        setToolBarTitle(getString(Strings.screen_add_beneficiary_display_text_title))
        //toggleAddButtonVisibility(false)
    }
}