package co.yap.modules.subaccounts.paysalary.recurringpayment

import android.content.Context
import androidx.databinding.ObservableField
import androidx.fragment.app.FragmentManager
import co.yap.yapcore.IBase

interface IRecurringPayment {
    interface View : IBase.View<ViewModel>
    interface ViewModel : IBase.ViewModel<State> {
        fun datePicker(context: Context)
        var fragmentManager: FragmentManager?
    }

    interface State : IBase.State {
        var date: ObservableField<String>
    }
}