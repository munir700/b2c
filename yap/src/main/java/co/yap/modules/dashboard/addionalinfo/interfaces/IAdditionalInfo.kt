package co.yap.modules.dashboard.addionalinfo.interfaces

import androidx.databinding.Observable
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import co.yap.networking.customers.models.additionalinfo.AdditionalDocument
import co.yap.networking.customers.models.additionalinfo.AdditionalQuestion
import co.yap.networking.customers.responsedtos.additionalinfo.AdditionalInfoResponse
import co.yap.yapcore.IBase

interface IAdditionalInfo {
    interface View : IBase.View<ViewModel> {
        fun setObserver()
    }

    interface ViewModel : IBase.ViewModel<State> {
        val stepCount: MutableLiveData<Int>
        val additionalInfoResponse: MutableLiveData<AdditionalInfoResponse>
    }

    interface State : IBase.State {
        val steps: ObservableField<Int>
        val showHeader: ObservableBoolean
        var documentList: ArrayList<AdditionalDocument>
        var questionList: ArrayList<AdditionalQuestion>
        var screenType: ObservableField<String>
    }
}