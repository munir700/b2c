package co.yap.modules.onboarding.viewmodels

import android.app.Application
import co.yap.R
import co.yap.modules.onboarding.interfaces.IWelcome
import co.yap.modules.onboarding.models.WelcomeContent
import co.yap.modules.onboarding.states.WelcomeState
import co.yap.translation.Strings
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
        val content1 = WelcomeContent(getString(Strings.screen_welcom_b2b_display_text_page1_title), getString(Strings.screen_welcom_b2b_display_text_page1_details), R.drawable.ic_real_time_banking)
        val content2 = WelcomeContent(getString(Strings.screen_welcom_b2b_display_text_page2_title), getString(Strings.screen_welcom_b2b_display_text_page2_details), R.drawable.ic_real_time_perks)
        val content3 = WelcomeContent(getString(Strings.screen_welcom_b2b_display_text_page3_title), getString(Strings.screen_welcom_b2b_display_text_page3_details), R.drawable.ic_real_time_benefits)
        return arrayListOf(content1, content2, content3)
    }
}