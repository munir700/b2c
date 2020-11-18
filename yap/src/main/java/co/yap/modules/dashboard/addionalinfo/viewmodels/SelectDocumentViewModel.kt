package co.yap.modules.dashboard.addionalinfo.viewmodels

import android.app.Application
import co.yap.modules.dashboard.addionalinfo.interfaces.ISelectDocument
import co.yap.modules.dashboard.addionalinfo.states.SelectDocumentState
import co.yap.sendmoney.fundtransfer.interfaces.ICashTransfer
import co.yap.sendmoney.fundtransfer.viewmodels.BeneficiaryFundTransferBaseViewModel

class SelectDocumentViewModel (application: Application) :
    AdditionalInfoBaseViewModel<ISelectDocument.State>(application),
    ISelectDocument.ViewModel {
    override val state: ISelectDocument.State = SelectDocumentState(application)
}