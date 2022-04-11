package co.yap.modules.dashboard.yapit.topup.addcard

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import co.yap.R
import co.yap.databinding.FragmentAddTopupCardBinding
import co.yap.widgets.luhn.CardNumberTextWatcher
import co.yap.widgets.luhn.CvvTextWatcher
import co.yap.widgets.luhn.ExpiringDateTextWatcher
import co.yap.yapcore.BR
import co.yap.yapcore.BaseBindingFragment
import co.yap.yapcore.helpers.extentions.toast
import com.checkout.android_sdk.PaymentForm.PaymentFormCallback
import com.checkout.android_sdk.Response.CardTokenisationFail
import com.checkout.android_sdk.Response.CardTokenisationResponse
import com.checkout.android_sdk.network.NetworkError


class AddTopUpCardFragment : BaseBindingFragment<FragmentAddTopupCardBinding,IAddTopUpCard.ViewModel>() {

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId() = R.layout.fragment_add_topup_card

    override val viewModel: AddTopUpCardViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.validator?.targetViewBinding =
            getDataBindingView<FragmentAddTopupCardBinding>()
        initCheckOut()
    }

    private fun initCheckOut() {
        getDataBindingView<FragmentAddTopupCardBinding>().tilPanNumber.apply {
            editText?.addTextChangedListener(object : CardNumberTextWatcher(this) {
                override fun onValidated(moveToNext: Boolean, cardPan: String?, cardName: String?) {


                }
            })
        }

        getDataBindingView<FragmentAddTopupCardBinding>().tilExpireDate.apply {
            editText?.addTextChangedListener(object : ExpiringDateTextWatcher(this) {
                override fun onValidated(
                    moveToNext: Boolean,
                    expDate: String?,
                    month: Int,
                    year: Int
                ) {

                }
            })
        }
        getDataBindingView<FragmentAddTopupCardBinding>().tilCvv.apply {
            editText?.addTextChangedListener(object : CvvTextWatcher(this) {
                override fun onValidated(moveToNext: Boolean, pin: String?) {

                }
            })
        }
//        val checkoutForm = getDataBindingView<FragmentAddTopupCardBinding>().checkoutForm
//        checkoutForm.setKey(YAPApplication.configManager?.checkoutKey ?: "")
//            .setEnvironment(if (YAPApplication.configManager?.flavor == ProductFlavour.PROD.flavour) Environment.LIVE else Environment.SANDBOX)
//            .includeBilling(false).setFormListener(mFormListener)
    }

    var mFormListener: PaymentFormCallback = object : PaymentFormCallback {
        override fun onFormSubmit() {
        }

        override fun onTokenGenerated(response: CardTokenisationResponse) {
            toast(response.last4)
        }

        override fun onError(response: CardTokenisationFail) {

        }

        override fun onNetworkError(error: NetworkError?) {
        }

        override fun onBackPressed() {
            // the user decided to leave the payment page
//            getDataBindingView<FragmentAddTopupCardBinding>().checkoutForm.clearForm() // this clears the Payment Form
        }
    }

}