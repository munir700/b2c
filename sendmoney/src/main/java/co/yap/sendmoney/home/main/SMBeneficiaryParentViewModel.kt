package co.yap.sendmoney.home.main

import android.app.Application

class SMBeneficiaryParentViewModel(application: Application) :
    SMBeneficiaryParentBaseViewModel<ISMBeneficiaryParent.State>(application = application),
    ISMBeneficiaryParent.ViewModel {
    override val state: SMBeneficiaryParentState = SMBeneficiaryParentState()
}
