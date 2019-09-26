package co.yap.modules.dashboard.more.home.viewmodels

import android.app.Application
import co.yap.modules.dashboard.more.home.interfaces.IMoreHome
import co.yap.modules.dashboard.more.home.states.MoreHomeState
import co.yap.modules.dashboard.more.viewmodels.MoreBaseViewModel
import co.yap.networking.transactions.TransactionsRepository

class MoreHomeViewModel(application: Application) :
    MoreBaseViewModel<IMoreHome.State>(application), IMoreHome.ViewModel/*,
    IRepositoryHolder<CustomersRepository>*/ {
    override fun handlePressOnBackButton() {
    }

    private val transactionRepository: TransactionsRepository = TransactionsRepository
    override val state: MoreHomeState =
        MoreHomeState()


    override fun onResume() {
        super.onResume()
        setToolBarTitle("More")
    }
}