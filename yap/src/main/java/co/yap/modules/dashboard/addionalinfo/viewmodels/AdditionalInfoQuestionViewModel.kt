package co.yap.modules.dashboard.addionalinfo.viewmodels

import android.app.Application
import co.yap.modules.dashboard.addionalinfo.interfaces.IAdditionalInfoEmployment
import co.yap.modules.dashboard.addionalinfo.interfaces.IAdditionalInfoQuestion
import co.yap.modules.dashboard.addionalinfo.states.AdditionalInfoQuestionState

class AdditionalInfoQuestionViewModel(application: Application) :
    AdditionalInfoBaseViewModel<IAdditionalInfoQuestion.State>(application),
    IAdditionalInfoQuestion.ViewModel  {
    override val state: IAdditionalInfoQuestion.State= AdditionalInfoQuestionState(application)
    override fun onCreate() {
        super.onCreate()
        setTitle("Final Question")
        setSubTitle("Please tell us the name of the company you are currently employed with")
    }

    override fun moveToNext() {
        moveStep()
    }
}