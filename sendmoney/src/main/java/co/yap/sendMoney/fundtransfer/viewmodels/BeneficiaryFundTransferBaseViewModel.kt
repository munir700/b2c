package co.yap.sendMoney.fundtransfer.viewmodels

import android.app.Application
import co.yap.sendMoney.base.SMFeeViewModel
import co.yap.sendMoney.fundtransfer.interfaces.IBeneficiaryFundTransfer
import co.yap.yapcore.IBase

abstract class BeneficiaryFundTransferBaseViewModel<S : IBase.State>(application: Application) :
    SMFeeViewModel<S>(application) {
    var parentViewModel: IBeneficiaryFundTransfer.ViewModel? = null

    fun setToolBarTitle(title: String) {
        parentViewModel?.state?.toolbarTitle = title
    }

    fun toggleToolBarVisibility(visibility: Boolean) {
        parentViewModel?.state?.toolbarVisibility?.set(visibility)
    }
}