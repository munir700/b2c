package co.yap.modules.dashboard.addionalinfo.viewmodels

import android.app.Application
import co.yap.modules.dashboard.addionalinfo.interfaces.IAdditionalInfo
import co.yap.sendmoney.base.SMFeeViewModel
import co.yap.sendmoney.fundtransfer.interfaces.IBeneficiaryFundTransfer
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.IBase

abstract class AdditionalInfoBaseViewModel <S : IBase.State>(application: Application) :
    BaseViewModel<S>(application)  {
    var parentViewModel: IAdditionalInfo.ViewModel? = null

}