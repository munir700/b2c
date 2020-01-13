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
import co.yap.translation.Strings
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.enums.PackageType
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

    override fun onCreate() {
        super.onCreate()
        getPackageFee(PackageType.MONTHLY.type)
        getPackageFee(PackageType.YEARLY.type)
//        aysuctest()
    }

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

    override fun getPackageFee(type: String) {
        launch {
            when (val response = repository.getHousholdFeePackage(type)) {
                is RetroApiResponse.Success -> {
                    when (type) {
                        PackageType.MONTHLY.type -> {
                            monthlyFee = response.data.data?.amount?.toDoubleOrNull()
                            state.monthlyFee =
                                response.data.data?.currency + " " + response.data.data?.amount
                            plansList.add(
                                HouseHoldPlan(
                                    type = type,
                                    amount = state.monthlyFee
                                )
                            )
                        }
                        PackageType.YEARLY.type -> {
                            yearlyFee = response.data.data?.amount?.toDoubleOrNull()
                            state.annuallyFee =
                                response.data.data?.currency + " " + response.data.data?.amount
                            plansList.add(
                                HouseHoldPlan(
                                    type = "Yearly",
                                    amount = state.annuallyFee,
                                    discount = getDiscount()
                                )
                            )
                        }
                    }
                }
                is RetroApiResponse.Error -> {
                    state.toast = response.error.message
                }
            }
        }
    }
//
//    private fun aysuctest() {
//        launch {
//            state.loading = true
//            val monthly = viewModelBGScope.async {
//                getPackageFee(
//                    PackageType.MONTHLY.type
//                )
//            }
//            val yearly = viewModelBGScope.async {
//                getPackageFee(
//                    PackageType.YEARLY.type
//                )
//            }
//
//            state.loading = false
//        }
//
//    }

    private fun getDiscount(): Int? {
        var discountPercent: Int? = null
        monthlyFee?.let { monthlyAmt ->
            val actualYearlyAmount = monthlyAmt.times(12)
            yearlyFee?.let { yearlyAmt ->
                val discountPrice = actualYearlyAmount - yearlyAmt
                discountPercent = (discountPrice / actualYearlyAmount).times(100).toInt()
                state.planDiscount =
                    getString(Strings.screen_yap_house_hold_subscription_selection_display_text_saving).format(
                        "${discountPercent.toString()} %"
                    )
                return discountPercent
            }
        }
        return discountPercent
    }

}