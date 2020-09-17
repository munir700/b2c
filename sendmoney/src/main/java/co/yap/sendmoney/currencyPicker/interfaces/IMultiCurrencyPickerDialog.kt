package co.yap.sendmoney.currencyPicker.interfaces

import co.yap.yapcore.IBase
import co.yap.yapcore.helpers.SharedPreferenceManager

interface IMultiCurrencyPickerDialog {
    interface View : IBase.View<ViewModel>

    interface ViewModel : IBase.ViewModel<State> {

    }

    interface State : IBase.State
}