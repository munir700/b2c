package co.yap.modules.onboarding.viewmodels

import android.app.Application
import co.yap.R
import co.yap.modules.onboarding.interfaces.IWelcome
import co.yap.modules.onboarding.models.WelcomeContent
import co.yap.modules.onboarding.states.WelcomeState
import co.yap.yapcore.BaseViewModel

class WelcomeViewModel(application: Application) : BaseViewModel<IWelcome.State>(application), IWelcome.ViewModel {

    override val state: IWelcome.State
        get() = WelcomeState()

    override val pages: ArrayList<WelcomeContent>
        get() = generatePages()

    override fun handlePressOnGetStarted() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun generatePages(): ArrayList<WelcomeContent> {
        val pages: ArrayList<String> = arrayListOf()
        val content: WelcomeContent = WelcomeContent("Title1", "Subtitle1", R.drawable.ic_real_time_banking)
        val content1: WelcomeContent = WelcomeContent("Title1", "Subtitle1", R.drawable.ic_real_time_perks)
        val content2: WelcomeContent = WelcomeContent("Title1", "Subtitle1", R.drawable.ic_real_time_benefits)
    }
}