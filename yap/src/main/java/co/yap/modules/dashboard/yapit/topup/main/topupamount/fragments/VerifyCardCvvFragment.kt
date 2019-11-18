package co.yap.modules.dashboard.yapit.topup.main.topupamount.fragments

import `in`.aabhasjindal.otptextview.OtpTextView
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import co.yap.BR
import co.yap.R
import co.yap.databinding.FragmentVerifyCardCvvBinding
import co.yap.modules.dashboard.yapit.topup.main.topupamount.activities.TopUpCardActivity
import co.yap.modules.dashboard.yapit.topup.main.topupamount.interfaces.IVerifyCardCvv
import co.yap.modules.dashboard.yapit.topup.main.topupamount.viewModels.VerifyCardCvvViewModel
import co.yap.yapcore.BaseBindingFragment

class VerifyCardCvvFragment : BaseBindingFragment<IVerifyCardCvv.ViewModel>(), IVerifyCardCvv.View {
    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_verify_card_cvv

    override val viewModel: IVerifyCardCvv.ViewModel
        get() = ViewModelProviders.of(this).get(VerifyCardCvvViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.clickEvent.observe(this, clickEvent)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (context is TopUpCardActivity)
            if ((context as TopUpCardActivity).cardInfo?.logo.equals("Visa", true)) {
                showToast("visa")
            } else if ((context as TopUpCardActivity).cardInfo?.logo.equals("MASTERCARD", true)) {
                getBindings().cvvView.visibility = View.VISIBLE
                getBindings().cvvAmericanView.visibility = View.GONE
            } else {
                getBindings().cvvView.visibility = View.GONE
                getBindings().cvvAmericanView.visibility = View.VISIBLE
            }
        showToast((context as TopUpCardActivity).topUpTransactionModel?.value?.cardId.toString())
    }

    var clickEvent = Observer<Int> {

        when (it) {
            R.id.btnAction ->
                if(context is TopUpCardActivity){
                viewModel.topUpTransactionRequest((context as TopUpCardActivity).topUpTransactionModel?.value)

                }
               // findNavController().navigate(R.id.action_verifyCardCvvFragment_to_topUpCardSuccessFragment)
        }
    }

    fun getBindings(): FragmentVerifyCardCvvBinding {
        return viewDataBinding as FragmentVerifyCardCvvBinding
    }

}