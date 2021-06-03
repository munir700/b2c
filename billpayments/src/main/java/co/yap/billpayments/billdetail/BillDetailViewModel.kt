package co.yap.billpayments.billdetail

import android.app.Application
import co.yap.networking.customers.responsedtos.billpayment.ViewBillModel
import co.yap.yapcore.BaseViewModel

class BillDetailViewModel(application: Application) :
    BaseViewModel<IBillDetail.State>(application),
    IBillDetail.ViewModel {
    override var selectedBill: ViewBillModel? = null
    override var selectedBillPosition: Int? = 0
    override val state: IBillDetail.State = BillDetailState()
    override fun isPrepaid(): Boolean? {
        return selectedBill?.billerInfo?.skuInfos?.first()?.isPrepaid
    }
}
