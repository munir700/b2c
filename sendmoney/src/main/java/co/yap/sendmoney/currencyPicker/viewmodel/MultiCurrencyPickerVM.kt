package co.yap.sendmoney.currencyPicker.viewmodel

import android.app.Application
import co.yap.sendmoney.addbeneficiary.states.MainStateCurrencyPickerDialog
import co.yap.sendmoney.currencyPicker.interfaces.IMultiCurrencyPickerDialog
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.helpers.SharedPreferenceManager

class MultiCurrencyPickerVM (application: Application) : BaseViewModel<IMultiCurrencyPickerDialog.State>(application),
    IMultiCurrencyPickerDialog.ViewModel {
      override val state: IMultiCurrencyPickerDialog.State = MainStateCurrencyPickerDialog()
    override val shardPrefs: SharedPreferenceManager =
        SharedPreferenceManager.getInstance(application)
}