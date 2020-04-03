package co.yap.modules.subaccounts.paysalary.recurringpayment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import co.yap.R
import co.yap.yapcore.dagger.base.viewmodel.DaggerBaseViewModel
import co.yap.yapcore.helpers.DateUtils.dateToString
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import java.util.*
import javax.inject.Inject

class RecurringPaymentVM @Inject constructor(
    override var state: IRecurringPayment.State

) :
    DaggerBaseViewModel<IRecurringPayment.State>(), IRecurringPayment.ViewModel {
    private val calendar = Calendar.getInstance()
    override var fragmentManager: FragmentManager? = null
    override fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController?) {
        state.date.set(dateToString(calendar.time, "MMM dd, yyyy"))
    }

    override fun datePicker(context: Context) {
        val dpd =
            DatePickerDialog.newInstance { view, year, monthOfYear, dayOfMonth ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, monthOfYear)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                state.date.set(dateToString(calendar.time, "MMM dd, yyyy"))
            }
        fragmentManager?.let {
            dpd.accentColor = context.getColor(R.color.colorPrimary)
            dpd.show(it, "")
        }
    }
}