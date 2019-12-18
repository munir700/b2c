package co.yap.modules.dashboard.store.household.viewmodels

import android.app.Application
import co.yap.modules.dashboard.cards.addpaymentcard.models.BenefitsModel
import co.yap.modules.dashboard.store.household.interfaces.IHouseHoldSubscription
import co.yap.modules.dashboard.store.household.states.HouseHoldSubscriptionState
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

    override fun handlePressOnMonthlyPackage(id: Int) {
        clickEvent.setValue(id)
    }

    override fun handlePressOnYearlyPackage(id: Int) {
        clickEvent.setValue(id)
    }

    override fun onResume() {
        super.onResume()
    }


    override fun loadDummyData(): ArrayList<BenefitsModel> {

        val benefitsTitle: ArrayList<String> = arrayListOf(
            getString(Strings.screen_yap_house_hold_subscription_selection_display_text_benefit_send_salaries),
            getString(Strings.screen_yap_house_hold_subscription_selection_display_text_benefit_allocate_budget),
            getString(Strings.screen_yap_house_hold_subscription_selection_display_text_benefit_setup_payments),
            getString(Strings.screen_yap_house_hold_subscription_selection_display_text_benefit_track_expense)
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