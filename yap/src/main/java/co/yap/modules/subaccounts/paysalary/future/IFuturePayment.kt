package co.yap.modules.subaccounts.paysalary.future

import android.content.Context
import androidx.databinding.ObservableField
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.MutableLiveData
import co.yap.networking.customers.household.responsedtos.SubAccount
import co.yap.yapcore.IBase

interface IFuturePayment {
    interface View : IBase.View<ViewModel>
    interface ViewModel : IBase.ViewModel<State>{
        fun datePicker(context: Context)
        var fragmentManager: FragmentManager?
    }
    interface State : IBase.State{
        var subAccount: MutableLiveData<SubAccount>
        var date: ObservableField<String>
    }
}