package co.yap.modules.dashboard.addionalinfo.viewmodels

import android.app.Application
import co.yap.modules.dashboard.addionalinfo.interfaces.IAdditionalInfoQuestion
import co.yap.modules.dashboard.addionalinfo.states.AdditionalInfoQuestionState

class AdditionalInfoQuestionViewModel(application: Application) :
    AdditionalInfoBaseViewModel<IAdditionalInfoQuestion.State>(application),
    IAdditionalInfoQuestion.ViewModel {
    override val state: IAdditionalInfoQuestion.State = AdditionalInfoQuestionState(application)
    override fun onCreate() {
        super.onCreate()
        showHeader(true)
        setTitle("Final Question")
        setSubTitle(getQuestionList().firstOrNull()?.questionFromCustomer ?: "")
    }

    override fun moveToNext() {
        moveStep()
    }
}