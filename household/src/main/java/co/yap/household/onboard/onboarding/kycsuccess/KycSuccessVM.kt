package co.yap.household.onboard.onboarding.kycsuccess

import android.os.Bundle
import androidx.navigation.NavController
import co.yap.networking.transactions.TransactionsRepository
import co.yap.yapcore.dagger.base.viewmodel.DaggerBaseViewModel
import javax.inject.Inject

class KycSuccessVM @Inject constructor(override var state :IKycSuccess.State,private val epo: TransactionsRepository) :
    DaggerBaseViewModel<IKycSuccess.State>(), IKycSuccess.ViewModel {



    override fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController?) {
    }
}