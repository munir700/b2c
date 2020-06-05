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
        fun getReasonsList()
        fun createModel(
            reasons: ArrayList<String>,
            options: ArrayList<String>
        )
        var clickEvent: SingleClickEvent
        var taxInfoList: MutableList<TaxModel>
        var reasonsList: ArrayList<String>
        var options: ArrayList<String>
        var taxInfoAdaptor: TaxInfoAdaptor

    }

    interface State : IBase.State {
        var valid: Boolean
    }
}