package co.yap.modules.subaccounts.paysalary.future

import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import co.yap.R
import co.yap.networking.customers.household.CustomerHHApi
import co.yap.networking.customers.household.CustomersHHRepository
import co.yap.networking.customers.household.requestdtos.SchedulePayment
import co.yap.networking.customers.household.responsedtos.SubAccount
import co.yap.networking.models.RetroApiResponse
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.dagger.base.viewmodel.DaggerBaseViewModel
import co.yap.yapcore.helpers.DateUtils
import co.yap.yapcore.helpers.DateUtils.FORMAT_LONG_INPUT
import co.yap.yapcore.helpers.DateUtils.GMT
import co.yap.yapcore.helpers.DateUtils.dateToString
import co.yap.yapcore.helpers.DateUtils.datetoString
import co.yap.yapcore.helpers.cancelAllSnackBar
import co.yap.yapcore.helpers.extentions.hideKeyboard
import co.yap.yapcore.helpers.extentions.parseToDouble
import co.yap.yapcore.helpers.livedata.GetAccountBalanceLiveData
import co.yap.yapcore.helpers.showTextUpdatedAbleSnackBar
import co.yap.yapcore.helpers.spannables.underline
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import java.util.*
import javax.inject.Inject

class FuturePaymentVM @Inject constructor(override val state: IFuturePayment.State) :
    DaggerBaseViewModel<IFuturePayment.State>(), IFuturePayment.ViewModel {
    private val calendar = Calendar.getInstance()
    private val repository: CustomerHHApi = CustomersHHRepository
    override var fragmentManager: FragmentManager? = null
    override val clickEvent = SingleClickEvent()
    override fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController?) {
    }
    override fun fetchExtras(extras: Bundle?) {
        super.fetchExtras(extras)
        extras?.let { state.subAccount.value = it.getParcelable(SubAccount::class.simpleName) }

    }
    override fun datePicker(view: View) {
        view.hideKeyboard()
        val dpd =
            DatePickerDialog.newInstance { _, year, monthOfYear, dayOfMonth ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, monthOfYear)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                state.date.set(dateToString(calendar.time, DateUtils.FORMAT_DATE_MON_YEAR))
            }
        fragmentManager?.let {
            dpd.accentColor = context.getColor(R.color.colorPrimary)
            dpd.show(it, "")
        }
    }

    override fun onAmountChange(
        amount: CharSequence, start: Int, before: Int,
        count: Int
    ) {
        if (amount.parseToDouble() > GetAccountBalanceLiveData.cardBalance.value?.availableBalance.parseToDouble()) {
            state.isValid.value = false
            context.showTextUpdatedAbleSnackBar(
                msg = "Looks like it’s time to Top Up! Please top up your account to continue with this transaction. ${underline(
                    "Top Up here!"
                )}", clickListener = View.OnClickListener { })
        } else {
            cancelAllSnackBar()
            state.isValid.value = true
        }
    }

    override fun createSchedulePayment(uuid: String?, schedulePayment: SchedulePayment?) {
        launch {
            when (val response = repository.createSchedulePayment(
                uuid,
                schedulePayment
            )) {
                is RetroApiResponse.Success -> {
                    clickEvent.postValue(GO_TO_CONFIRMATION)
                }
                is RetroApiResponse.Error -> {
                }
            }
        }
    }

    override fun handlePressOnClick(id: Int) {
        val time = datetoString(calendar.time, FORMAT_LONG_INPUT, GMT)
        createSchedulePayment(
            state.subAccount.value?.accountUuid,
            SchedulePayment(
                amount = state.amount.value,
                nextProcessingDate = time
            )
        )
    }
}
