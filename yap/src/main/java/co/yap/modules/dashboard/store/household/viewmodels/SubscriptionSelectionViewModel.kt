package co.yap.modules.dashboard.store.household.viewmodels

import android.app.Application
import co.yap.R
import co.yap.modules.dashboard.cards.addpaymentcard.models.BenefitsModel
import co.yap.modules.dashboard.store.household.interfaces.IHouseHoldSubscription
import co.yap.modules.dashboard.store.household.states.HouseHoldSubscriptionState
import co.yap.modules.onboarding.models.WelcomeContent
import co.yap.translation.Strings
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleClickEvent

class SubscriptionSelectionViewModel(application: Application) :
    BaseViewModel<IHouseHoldSubscription.State>(application),
    IHouseHoldSubscription.ViewModel {

    override val clickEvent: SingleClickEvent = SingleClickEvent()
    override var benefitsList: ArrayList<BenefitsModel> = ArrayList<BenefitsModel>()
    override val state: HouseHoldSubscriptionState = HouseHoldSubscriptionState()

    override fun handlePressOnCloseIcon(id: Int) {
        clickEvent.setValue(id)
    }

    override fun handlePressOnGetStarted(id: Int) {
        clickEvent.setValue(id)
    }

    override fun handlePressOnMonthlyPackage(id: Int) {
        clickEvent.setValue(id)
    }

    override fun handlePressOnYearlyPackage(id: Int) {
        clickEvent.setValue(id)
    }

    override fun onResume() {
        super.onResume()
    }

    override fun getPages(): ArrayList<WelcomeContent> = generateB2CPages()

    private fun generateB2CPages(): ArrayList<WelcomeContent> {
        val content1 = WelcomeContent(
            getString(Strings.screen_welcome_b2c_display_text_page1_title),
            getString(Strings.screen_welcome_b2c_display_text_page1_details),
            R.drawable.gold
        )
        val content2 = WelcomeContent(
            getString(Strings.screen_welcome_b2c_display_text_page2_title),
            getString(Strings.screen_welcome_b2c_display_text_page2_details),
            R.drawable.rose_gold
        )
        val content3 = WelcomeContent(
            getString(Strings.screen_welcome_b2c_display_text_page3_title),
            getString(Strings.screen_welcome_b2c_display_text_page3_details),
            R.drawable.card_spare
        )
        return arrayListOf(content1, content2, content3)
    }

    override fun loadDummyData(): ArrayList<BenefitsModel> {

        val benefitsTitle: ArrayList<String> = arrayListOf(
            getString(Strings.screen_yap_house_hold_success_display_text_pager_color),
            getString(Strings.screen_yap_house_hold_success_display_text_pager_schedule_payments),
            getString(Strings.screen_yap_house_hold_success_display_text_pager_schedule_pots)
        )

        val benefitsDescription: ArrayList<String> = arrayListOf(" ", " ", " ", " ")

        val benefitsModelList: ArrayList<BenefitsModel> = ArrayList<BenefitsModel>()

        for (i in 0 until 4) {
            var benfitTitle: String = benefitsTitle.get(i)
            var benfitDetail: String = benefitsDescription.get(i)

            val benefitsModel: BenefitsModel = BenefitsModel(
                benfitTitle,
                benfitDetail
            )
            benefitsModelList.add(benefitsModel)
        }


        return benefitsModelList
    }

}