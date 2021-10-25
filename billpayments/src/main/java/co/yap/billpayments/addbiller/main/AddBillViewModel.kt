package co.yap.billpayments.addbiller.main

import android.app.Application
import co.yap.networking.customers.responsedtos.billpayment.BillProviderModel
import co.yap.networking.customers.responsedtos.billpayment.BillerCatalogModel
import co.yap.yapcore.BaseViewModel

class AddBillViewModel(application: Application) :
    BaseViewModel<IAddBill.State>(application),
    IAddBill.ViewModel {
    override var billerCatalogs: MutableList<BillerCatalogModel> = mutableListOf()
    override var selectedBillProvider: BillProviderModel? = null
    override var selectedBillerCatalog: BillerCatalogModel? = null
    override val state: IAddBill.State =
        AddBillState()
}
