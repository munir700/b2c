package co.yap.modules.transaction_filters.viewmodels

import android.app.Application
import co.yap.modules.transaction_filters.interfaces.ITransactionFilters
import co.yap.modules.transaction_filters.states.TransactionFiltersState
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.helpers.Utils
import com.jaygoo.widget.RangeSeekBar

class TransactionFiltersViewModel(application: Application) :
    BaseViewModel<ITransactionFilters.State>(application),
    ITransactionFilters.ViewModel {


    override val state: ITransactionFilters.State = TransactionFiltersState()
    override val clickEvent: SingleClickEvent = SingleClickEvent()
    override fun handlePressOnView(id: Int) {
        clickEvent.setValue(id)
    }

    override fun handlePressOnBackButton(id: Int) {
        clickEvent.setValue(id)
    }

    override fun updateRangeValue(seekBar: RangeSeekBar) {

        val startRangeValue = Utils.getFormattedCurrency(seekBar.leftSeekBar.progress.toString())
        val endRangeValues = Utils.getFormattedCurrency(seekBar.rightSeekBar.progress.toString())
        state.rangeStartValue.set(startRangeValue)
        state.rangeEndValue.set(endRangeValues)
    }

}