package co.yap.modules.dashboard.store.young.landing.contact

import android.os.Bundle
import androidx.navigation.NavController
import co.yap.networking.customers.CustomersApi
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.dagger.base.viewmodel.DaggerBaseViewModel
import co.yap.yapcore.helpers.validation.IValidator
import co.yap.yapcore.helpers.validation.Validator
import javax.inject.Inject

class YoungContactDetailsVM @Inject constructor(
    override val state: IYoungContactDetails.State,
    override var validator: Validator?, private val repository: CustomersApi
) : DaggerBaseViewModel<IYoungContactDetails.State>(), IYoungContactDetails.ViewModel, IValidator {
    override val clickEvent: SingleClickEvent
        get() = TODO("Not yet implemented")

    override fun handlePressOnClick(id: Int) {
        TODO("Not yet implemented")
    }

    override fun verifyHouseholdEmail(apiResponse: ((Boolean) -> Unit?)?) {
        TODO("Not yet implemented")
    }

    override fun verifyMobileNumber(apiResponse: ((Boolean?) -> Unit?)?) {
        TODO("Not yet implemented")
    }

    override fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController?) {
        TODO("Not yet implemented")
    }
}