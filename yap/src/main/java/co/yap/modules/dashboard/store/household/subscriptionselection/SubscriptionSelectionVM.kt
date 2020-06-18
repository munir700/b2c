package co.yap.modules.dashboard.store.household.subscriptionselection

import android.os.Bundle
import androidx.navigation.NavController
import co.yap.modules.dashboard.cards.addpaymentcard.models.BenefitsModel
import co.yap.networking.household.responsedtos.HouseHoldPlan
import co.yap.networking.models.RetroApiResponse
import co.yap.networking.transactions.TransactionsApi
import co.yap.networking.transactions.TransactionsRepository
import co.yap.networking.transactions.responsedtos.transaction.RemittanceFeeResponse
import co.yap.translation.Strings
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.dagger.base.viewmodel.DaggerBaseViewModel
import co.yap.yapcore.enums.PackageType
import co.yap.yapcore.helpers.extentions.toFormattedAmountWithCurrency
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import javax.inject.Inject

class SubscriptionSelectionVM @Inject constructor(override var state: ISubscriptionSelection.State) :
    DaggerBaseViewModel<ISubscriptionSelection.State>(), ISubscriptionSelection.ViewModel {
    override val repository: TransactionsApi = TransactionsRepository
    override val clickEvent: SingleClickEvent = SingleClickEvent()
    private var monthlyFee: Double? = 0.0
    private var yearlyFee: Double? = 0.0
    override fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController?) {
        fetchHouseholdPackagesFee()
    }

    override fun handlePressOnButton(id: Int) {
        clickEvent.setValue(id)
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
        monthlyFeeResponse: RetroApiResponse<RemittanceFeeResponse>,
        yearlyFeeResponse: RetroApiResponse<RemittanceFeeResponse>
    ) {
        // handle Monthly and yearly Fee Response
        if (monthlyFeeResponse is RetroApiResponse.Success && yearlyFeeResponse is RetroApiResponse.Success) {
            if (monthlyFeeResponse.data.data != null) {
                if (monthlyFeeResponse.data.data?.feeType == Constants.FEE_TYPE_FLAT) {
                    val feeAmount = monthlyFeeResponse.data.data?.tierRateDTOList?.get(0)?.feeAmount
                    val VATAmount = monthlyFeeResponse.data.data?.tierRateDTOList?.get(0)?.vatAmount
                    monthlyFee = feeAmount?.plus(VATAmount ?: 0.0)
                }
            } else {
                monthlyFee = 0.0
            }
            if (yearlyFeeResponse.data.data != null) {
                if (yearlyFeeResponse.data.data?.feeType == Constants.FEE_TYPE_FLAT) {
                    val feeAmount = yearlyFeeResponse.data.data?.tierRateDTOList?.get(0)?.feeAmount
                    val VATAmount = yearlyFeeResponse.data.data?.tierRateDTOList?.get(0)?.vatAmount
                    yearlyFee = feeAmount?.plus(VATAmount ?: 0.0)
                }
            } else {
                yearlyFee = 0.0
            }
            state.monthlyFee.value = monthlyFee.toString().toFormattedAmountWithCurrency()
            state.annuallyFee.value = yearlyFee.toString().toFormattedAmountWithCurrency()
            state.plansList.add(
                HouseHoldPlan(
                    type = PackageType.MONTHLY.type,
                    amount = state.monthlyFee.value
                )
            )
            state.plansList.add(
                HouseHoldPlan(
                    type = "Yearly",
                    amount = state.annuallyFee.value,
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
                state.planDiscount?.value =
                    getString(Strings.screen_yap_house_hold_subscription_selection_display_text_saving).format(
                        "${discountPercent.toString()}%"
                    )
                return discountPercent
            }
        }
        return discountPercent
    }
}
