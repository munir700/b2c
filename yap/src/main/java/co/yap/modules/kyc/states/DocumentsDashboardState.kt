package co.yap.modules.kyc.states

import androidx.databinding.Bindable
import co.yap.BR
import co.yap.modules.kyc.interfaces.IDocumentsDashboard
import co.yap.yapcore.BaseState

class DocumentsDashboardState : BaseState(), IDocumentsDashboard.State {

    @get:Bindable
    override var name: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.name)
        }
}