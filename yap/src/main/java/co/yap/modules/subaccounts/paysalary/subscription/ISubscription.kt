package co.yap.modules.subaccounts.paysalary.subscription

import android.content.Context
import co.yap.yapcore.IBase

interface ISubscription {
    interface View : IBase.View<ViewModel>

    interface ViewModel : IBase.ViewModel<State>{
        fun handlePressOnClick(context: Context)
    }

    interface State : IBase.State
}