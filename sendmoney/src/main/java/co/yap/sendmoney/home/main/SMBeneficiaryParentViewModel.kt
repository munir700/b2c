package co.yap.sendmoney.home.main

import android.app.Application
import co.yap.networking.customers.responsedtos.sendmoney.IBeneficiary

class SMBeneficiaryParentViewModel(application: Application) :
    SMBeneficiaryParentBaseViewModel<ISMBeneficiaryParent.State>(application = application),
    ISMBeneficiaryParent.ViewModel {
    override var beneficiariesList: List<IBeneficiary> = arrayListOf()
    override val state: SMBeneficiaryParentState = SMBeneficiaryParentState()
}
