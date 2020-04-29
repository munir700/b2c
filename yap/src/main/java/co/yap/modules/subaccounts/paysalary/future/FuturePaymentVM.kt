package co.yap.modules.subaccounts.paysalary.future

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import co.yap.R
import co.yap.networking.customers.responsedtos.SubAccount
import co.yap.yapcore.dagger.base.viewmodel.DaggerBaseViewModel
import co.yap.yapcore.helpers.DateUtils
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import java.util.*
import javax.inject.Inject

class FuturePaymentVM @Inject constructor(override val state: IFuturePayment.State) :
    DaggerBaseViewModel<IFuturePayment.State>(), IFuturePayment.ViewModel {
    private val calendar = Calendar.getInstance()
    override var fragmentManager: FragmentManager? = null

    override fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController?) {
        bundle?.let { state.subAccount.value = it.getParcelable(SubAccount::class.simpleName) }
        state.date.set(DateUtils.dateToString(calendar.time, "MMM dd, yyyy"))
    }

    override fun datePicker(context: Context) {
        val dpd =
            DatePickerDialog.newInstance { view, year, monthOfYear, dayOfMonth ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, monthOfYear)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                state.date.set(DateUtils.dateToString(calendar.time, "MMM dd, yyyy"))
            }
        fragmentManager?.let {
            dpd.accentColor = context.getColor(R.color.colorPrimary)
            dpd.show(it, "")
        }
    }
}