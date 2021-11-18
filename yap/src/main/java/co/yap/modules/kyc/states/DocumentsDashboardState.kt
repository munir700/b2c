package co.yap.modules.kyc.states

import androidx.databinding.Bindable
import androidx.databinding.ObservableField
import co.yap.BR
import co.yap.modules.kyc.interfaces.IDocumentsDashboard
import co.yap.yapcore.BaseState

class DocumentsDashboardState : BaseState(), IDocumentsDashboard.State {

    @get:Bindable
    override var totalProgress: Int = 100
        set(value) {
            field = value
            notifyPropertyChanged(BR.totalProgress)
        }

    @get:Bindable
    override var currentProgress: Int = 0
        set(value) {
            field = value
            notifyPropertyChanged(BR.currentProgress)
        }
    override var firstName: ObservableField<String> = ObservableField()
    override var middleName: ObservableField<String> = ObservableField()
    override var lastName: ObservableField<String> = ObservableField()
    override var nationality: ObservableField<String> = ObservableField()
    override var identityNo: ObservableField<String> = ObservableField()
}