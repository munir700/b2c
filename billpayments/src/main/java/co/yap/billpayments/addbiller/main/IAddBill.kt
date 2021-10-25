package co.yap.billpayments.addbiller.main

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import co.yap.networking.customers.responsedtos.billpayment.BillProviderModel
import co.yap.networking.customers.responsedtos.billpayment.BillerCatalogModel
import co.yap.yapcore.IBase

interface IAddBill {
    interface State : IBase.State {
        var toolbarVisibility: ObservableBoolean
        var rightIconVisibility: ObservableBoolean
        var leftIconVisibility: ObservableBoolean
        var toolbarTitleString: ObservableField<String>
        var selectedBillerPosition: ObservableInt
    }

    interface ViewModel : IBase.ViewModel<State> {
        var billerCatalogs: MutableList<BillerCatalogModel>
        var selectedBillProvider: BillProviderModel?
        var selectedBillerCatalog: BillerCatalogModel?
    }

    interface View : IBase.View<ViewModel>
}
