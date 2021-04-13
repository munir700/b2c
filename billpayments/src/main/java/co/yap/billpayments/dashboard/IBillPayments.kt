package co.yap.billpayments.dashboard

import android.graphics.drawable.Drawable
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import co.yap.networking.customers.responsedtos.billpayment.BillProviderModel
import co.yap.networking.customers.responsedtos.billpayment.BillerCatalogModel
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IBillPayments {
    interface State : IBase.State {
        var toolbarVisibility: ObservableBoolean
        var toolbarTitleString: ObservableField<String>
        var leftIconVisibility: ObservableBoolean
        var rightIconDrawable: ObservableField<Drawable>
        var sortIconVisibility: ObservableBoolean
        var rightIconVisibility: ObservableBoolean
    }

    interface ViewModel : IBase.ViewModel<State> {
        var billerCatalogs: MutableList<BillerCatalogModel>
        var billcategories: MutableList<BillProviderModel>
        var selectedBillerCatalog: BillerCatalogModel?
        var onToolbarClickEvent: SingleClickEvent
        fun onToolbarClick(id: Int)
    }

    interface View : IBase.View<ViewModel>
}
