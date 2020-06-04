package co.yap.modules.location.tax

import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.databinding.FragmentTaxInfoBinding

interface ITaxInfo {

    interface View : IBase.View<ViewModel> {
        fun addObservers()
        fun removeObservers()
        fun getBinding(): FragmentTaxInfoBinding
    }

    interface ViewModel : IBase.ViewModel<State> {
        fun handleOnPressView(id: Int)
        var clickEvent: SingleClickEvent
        var taxModel: TaxModel
        var taxInfoAdaptor: TaxInfoAdaptor

    }

    interface State : IBase.State {
        var valid: Boolean
    }
}