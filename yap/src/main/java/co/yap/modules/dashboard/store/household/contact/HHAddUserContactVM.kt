package co.yap.modules.dashboard.store.household.contact

import android.os.Bundle
import androidx.navigation.NavController
import co.yap.networking.customers.CustomersApi
import co.yap.networking.customers.CustomersRepository
import co.yap.networking.customers.requestdtos.HouseholdOnboardRequest
import co.yap.networking.customers.requestdtos.VerifyHouseholdMobileRequest
import co.yap.networking.models.RetroApiResponse
import co.yap.networking.transactions.responsedtos.transaction.HomeTransactionListData
import co.yap.widgets.State
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.dagger.base.viewmodel.DaggerBaseViewModel
import co.yap.yapcore.helpers.validation.IValidator
import co.yap.yapcore.helpers.validation.Validator
import javax.inject.Inject

class HHAddUserContactVM @Inject constructor(
    override val state: IHHAddUserContact.State,
    override var validator: Validator?
) :
    DaggerBaseViewModel<IHHAddUserContact.State>(), IHHAddUserContact.ViewModel, IValidator {
    override val clickEvent = SingleClickEvent()
    private val repository: CustomersApi = CustomersRepository
    override fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController?) {
    }

    override fun fetchExtras(extras: Bundle?) {
        super.fetchExtras(extras)
        extras?.let {
            state.request?.value = it.getParcelable(HouseholdOnboardRequest::class.java.name)
        }
    }

    override fun handlePressOnClick(id: Int) {
        clickEvent.postValue(id)
    }

    override fun verifyMobileNumber(apiResponse: ((Boolean?) -> Unit?)?) {
        launch {
            state.loading = true
            val request = VerifyHouseholdMobileRequest(
                countryCode = "00${state.countryCode.value?.replace("+", "")}",
                mobileNo = state.phone.value?.replace(" ", "") ?: ""
            )
            when (val response = repository.verifyHouseholdMobile(request)) {
                is RetroApiResponse.Success -> {
                    apiResponse?.invoke(true)
                }
                is RetroApiResponse.Error -> {
                    apiResponse?.invoke(false)
                }
            }
            state.loading = false
        }
    }
}
