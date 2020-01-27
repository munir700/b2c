package co.yap.modules.dashboard.store.household.viewmodels

import android.app.Application
import co.yap.R
import co.yap.modules.dashboard.cards.addpaymentcard.models.BenefitsModel
import co.yap.modules.dashboard.store.household.interfaces.IHouseHoldSubscription
import co.yap.modules.dashboard.store.household.states.HouseHoldSubscriptionState
import co.yap.modules.onboarding.models.WelcomeContent
import co.yap.networking.household.responsedtos.HouseHoldPlan
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.networking.models.RetroApiResponse
import co.yap.networking.transactions.TransactionsRepository
import co.yap.networking.transactions.responsedtos.CardFeeResponse
import co.yap.translation.Strings
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.enums.PackageType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async

class SubscriptionSelectionViewModel(application: Application) :
    BaseViewModel<IHouseHoldSubscription.State>(application),
    IHouseHoldSubscription.ViewModel, IRepositoryHolder<TransactionsRepository> {

    override val repository: TransactionsRepository = TransactionsRepository
    override val clickEvent: SingleClickEvent = SingleClickEvent()
    override var benefitsList: ArrayList<BenefitsModel> = ArrayList()
    override var plansList: ArrayList<HouseHoldPlan> = ArrayList()
    override val state: HouseHoldSubscriptionState = HouseHoldSubscriptionState()
    var monthlyFee: Double? = 0.0
    var yearlyFee: Double? = 0.0

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

    override fun getPages(): ArrayList<WelcomeContent> = generateB2CPages()

    private fun generateB2CPages(): ArrayList<WelcomeContent> {
        val content1 = WelcomeContent(
            getString(Strings.screen_welcome_b2c_display_text_page1_title),
            getString(Strings.screen_yap_house_hold_success_display_text_pager_color),
            R.drawable.image_yap_household_card
        )
        val content2 = WelcomeContent(
            getString(Strings.screen_welcome_b2c_display_text_page2_title),
            getString(Strings.screen_yap_house_hold_success_display_text_pager_schedule_payments),
            R.drawable.image_yap_household_salary
        )
        val content3 = WelcomeContent(
            getString(Strings.screen_welcome_b2c_display_text_page3_title),
            getString(Strings.screen_yap_house_hold_success_display_text_pager_schedule_pots),
            R.drawable.image_hosue_hold_track_expenses
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
    
    override fun fetchHouseholdPackagesFee() {
        launch {
            val monthly = viewModelBGScope.async(Dispatchers.IO) {
                repository.getHousholdFeePackage(PackageType.MONTHLY.type)
            }

            val yearly = viewModelBGScope.async(Dispatchers.IO) {
                repository.getHousholdFeePackage(PackageType.YEARLY.type)
            }
            state.loading = true
            handlePackageFeeResponse(monthly.await(), yearly.await())
        }
    }

    private fun handlePackageFeeResponse(
        monthlyFeeResponse: RetroApiResponse<CardFeeResponse>,
        yearlyFeeResponse: RetroApiResponse<CardFeeResponse>
    ) {
        // handle Monthly and yearly Fee Response
        if (monthlyFeeResponse is RetroApiResponse.Success && yearlyFeeResponse is RetroApiResponse.Success) {
            monthlyFee = monthlyFeeResponse.data.data?.amount?.toDoubleOrNull()
            yearlyFee = yearlyFeeResponse.data.data?.amount?.toDoubleOrNull()
            state.monthlyFee =
                monthlyFeeResponse.data.data?.currency + " " + monthlyFeeResponse.data.data?.amount
            state.annuallyFee =
                yearlyFeeResponse.data.data?.currency + " " + yearlyFeeResponse.data.data?.amount

            plansList.add(
                HouseHoldPlan(
                    type = PackageType.MONTHLY.type,
                    amount = state.monthlyFee
                )
            )
            plansList.add(
                HouseHoldPlan(
                    type = "Yearly",
                    amount = state.annuallyFee,
                    discount = getDiscount()
                )
            )
        } else if (monthlyFeeResponse is RetroApiResponse.Error) {
            state.error = monthlyFeeResponse.error.message
        } else if (yearlyFeeResponse is RetroApiResponse.Error)
            state.error = yearlyFeeResponse.error.message

        state.loading = false
    }

    private fun getDiscount(): Int? {
        var discountPercent: Int? = null
        monthlyFee?.let { monthlyAmt ->
            val actualYearlyAmount = monthlyAmt.times(12)
            yearlyFee?.let { yearlyAmt ->
                val discountPrice = actualYearlyAmount - yearlyAmt
                discountPercent = (discountPrice / actualYearlyAmount).times(100).toInt()
                state.planDiscount =
                    getString(Strings.screen_yap_house_hold_subscription_selection_display_text_saving).format(
                        "${discountPercent.toString()}%"
                    )
                return discountPercent
            }
        }
        return discountPercent
    }

}