package co.yap.modules.dashboard.addionalinfo.states

import android.app.Application
import androidx.databinding.ObservableBoolean
import co.yap.modules.dashboard.addionalinfo.interfaces.ISelectDocument
import co.yap.yapcore.BaseState

class SelectDocumentState(application: Application) : BaseState(), ISelectDocument.State {
    override val valid: ObservableBoolean = ObservableBoolean(false)
}