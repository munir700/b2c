package co.yap.modules.dashboard.store.young.sendmoney

import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import co.yap.yapcore.dagger.base.viewmodel.DaggerBaseViewModel
import co.yap.yapcore.helpers.cancelAllSnackBar
import co.yap.yapcore.helpers.extentions.parseToDouble
import co.yap.yapcore.helpers.livedata.GetAccountBalanceLiveData
import co.yap.yapcore.helpers.showTextUpdatedAbleSnackBar
import co.yap.yapcore.helpers.spannables.underline
import co.yap.yapcore.helpers.validation.IValidator
import co.yap.yapcore.helpers.validation.Validator
import co.yap.yapcore.hilt.base.viewmodel.HiltBaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class YoungSendMoneyVM @Inject constructor(
    override val state: YoungSendMoneyState
) : HiltBaseViewModel<IYoungSendMoney.State>(), IYoungSendMoney.ViewModel,
    IValidator {
    override var validator: Validator? = Validator(null)
    override fun onAmountChange(amount: CharSequence, start: Int, before: Int, count: Int) {
        if (amount.parseToDouble() > GetAccountBalanceLiveData.cardBalance.value?.availableBalance.parseToDouble()) {
            context.showTextUpdatedAbleSnackBar(
                msg = "Looks like it’s time to Top Up! Please top up your account to continue with this transaction. ${underline(
                    "Top Up here!"
                )}", clickListener = View.OnClickListener { })
        } else {
            cancelAllSnackBar()
        }
    }

    override fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController?) {
    }

    override fun handleOnClick(id: Int) {
    }
}
