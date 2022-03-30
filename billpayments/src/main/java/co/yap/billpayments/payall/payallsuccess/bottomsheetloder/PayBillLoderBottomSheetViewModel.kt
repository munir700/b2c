package co.yap.billpayments.payall.payallsuccess.bottomsheetloder

import android.app.Application
import co.yap.yapcore.BaseViewModel

class PayBillLoderBottomSheetViewModel(application: Application) :
    BaseViewModel<IPayBillLoderBottomSheet.State>(application),
    IPayBillLoderBottomSheet.ViewModel {
    override val state: PayBillLoderBottomSheetState = PayBillLoderBottomSheetState()
}