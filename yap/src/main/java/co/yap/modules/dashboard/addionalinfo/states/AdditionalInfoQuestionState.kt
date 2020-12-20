package co.yap.modules.dashboard.addionalinfo.states

import android.app.Application
import androidx.databinding.ObservableField
import co.yap.modules.dashboard.addionalinfo.interfaces.IAdditionalInfoQuestion
import co.yap.yapcore.BaseState

class AdditionalInfoQuestionState(application: Application) : BaseState(),
    IAdditionalInfoQuestion.State {
    override val answer: ObservableField<String> = ObservableField("")
}