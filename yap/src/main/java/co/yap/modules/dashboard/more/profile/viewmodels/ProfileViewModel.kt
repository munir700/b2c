package co.yap.modules.dashboard.more.profile.viewmodels

import android.app.Application
import co.yap.modules.dashboard.more.profile.intefaces.IProfile
import co.yap.modules.dashboard.more.profile.states.ProfileStates
import co.yap.modules.dashboard.more.viewmodels.MoreBaseViewModel
import co.yap.networking.transactions.TransactionsRepository

class ProfileViewModel(application: Application) :
    MoreBaseViewModel<IProfile.State>(application), IProfile.ViewModel/*,
    IRepositoryHolder<CustomersRepository>*/ {
    override fun handlePressOnBackButton() {
    }

    private val transactionRepository: TransactionsRepository = TransactionsRepository
    override val state: ProfileStates =
        ProfileStates()


    override fun onResume() {
        super.onResume()
        setToolBarTitle("Profile")
    }
}