package co.yap.modules.dashboard.home.filters.interfaces

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import co.yap.networking.transactions.responsedtos.TransactionFilters
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent
import com.jaygoo.widget.RangeSeekBar

interface ITransactionFilters {
    interface View : IBase.View<ViewModel>
    interface ViewModel : IBase.ViewModel<State> {
        val clickEvent: SingleClickEvent
        fun handlePressOnView(id: Int)
        fun handlePressOnBackButton(id: Int)
        fun updateRangeValue(seekBar: RangeSeekBar)
        val transactionFilters: MutableLiveData<TransactionFilters>
    }

    interface State : IBase.State {
        var rangeStartValue: ObservableField<String>
        var rangeEndValue: ObservableField<String>
    }
}