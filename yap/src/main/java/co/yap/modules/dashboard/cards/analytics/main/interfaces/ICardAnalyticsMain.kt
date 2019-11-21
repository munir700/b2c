package co.yap.modules.dashboard.cards.analytics.main.interfaces

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import co.yap.modules.dashboard.cards.analytics.models.AnalyticsItem
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface ICardAnalyticsMain {

    interface View : IBase.View<ViewModel>
    interface ViewModel : IBase.ViewModel<State> {
        val clickEvent: SingleClickEvent
        fun handlePressOnView(id: Int)
        val categoryAnalyticsItemLiveData: MutableLiveData<ArrayList<AnalyticsItem>>
        val merchantAnalyticsItemLiveData: MutableLiveData<ArrayList<AnalyticsItem>>
        val selectedItemPosition: MutableLiveData<Int>
    }

    interface State : IBase.State {
        var toolBarTitle: ObservableField<String>
        var toolBarVisibility: ObservableField<Boolean>
        var leftButtonVisibility: ObservableField<Boolean>

    }

}