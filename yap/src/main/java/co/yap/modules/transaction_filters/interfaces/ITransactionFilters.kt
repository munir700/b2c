package co.yap.modules.transaction_filters.interfaces

import androidx.databinding.Observable
import androidx.databinding.ObservableField
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
    }

    interface State : IBase.State {
        var rangeStartValue: ObservableField<String>
        var rangeEndValue: ObservableField<String>
    }
}