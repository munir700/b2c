package co.yap.modules.yapit.sendmoney.addbeneficiary.fragments

import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.view.Gravity
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.R
import co.yap.modules.yapit.sendmoney.addbeneficiary.interfaces.ICashTransfer
import co.yap.modules.yapit.sendmoney.addbeneficiary.viewmodels.CashTransferViewModel
import co.yap.modules.yapit.sendmoney.fragments.SendMoneyBaseFragment
import co.yap.translation.Strings
import co.yap.yapcore.BR
import co.yap.yapcore.helpers.CustomSnackbar
import co.yap.yapcore.helpers.DecimalDigitsInputFilter
import co.yap.yapcore.helpers.Utils
import co.yap.yapcore.managers.MyUserManager
import kotlinx.android.synthetic.main.fragment_y2y_funds_transfer.*
import kotlinx.android.synthetic.main.layout_y_to_y_transfer_initial_image.view.*

class CashTransferFragment : SendMoneyBaseFragment<ICashTransfer.ViewModel>(), ICashTransfer.View {

//    val args: Y2YTransferFragmentArgs by navArgs()

    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.fragment_cash_transfer

    override val viewModel: CashTransferViewModel
        get() = ViewModelProviders.of(this).get(CashTransferViewModel::class.java)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.state.availableBalance = MyUserManager.cardBalance.value?.availableBalance
        setObservers()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpData()
    }

    override fun setObservers() {
        viewModel.clickEvent.observe(this, Observer {
//            val action =
//                Y2YTransferFragmentDirections.actionY2YTransferFragmentToY2YFundsTransferSuccessFragment(
//                    viewModel.state.fullName,
//                    "AED",
//                    viewModel.state.amount, args.position
//                )
//            findNavController().navigate(action)
        })

        viewModel.errorEvent.observe(this, Observer {
            showErrorSnackBar()
        })


    }

    private fun setUpData() {

//        viewModel.state.fullName = args.beneficiaryName
//        viewModel.receiverUUID = args.receiverUUID
//        viewModel.state.imageUrl = args.imagePath
//        getBinding().lyUserImage.tvNameInitials.background = Utils.getContactBackground(
//            getBinding().lyUserImage.tvNameInitials.context,
//            args.position
//        )

        lyUserImage.tvNameInitials.setTextColor(
            Utils.getContactColors(
                lyUserImage.tvNameInitials.context, 1
            )
        )

        viewModel.state.availableBalanceText =
            " " + getString(Strings.common_text_currency_type) + " " + Utils.getFormattedCurrency(
                viewModel.state.availableBalance
            )

        etAmount.filters =
            arrayOf(InputFilter.LengthFilter(7), DecimalDigitsInputFilter(2))
        etAmount.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0?.length!! > 0) {
                    etAmount.gravity = Gravity.CENTER
                } else {
                    etAmount.gravity = Gravity.START or Gravity.CENTER_VERTICAL
                }
            }
        })
    }

    private fun showErrorSnackBar() {
        CustomSnackbar.showErrorCustomSnackbar(
            context = requireContext(),
            layout = clFTSnackbar,
            message = viewModel.state.errorDescription
        )
    }

    override fun onDestroy() {
        viewModel.clickEvent.removeObservers(this)
        super.onDestroy()

    }


    override fun onBackPressed(): Boolean {
        viewModel.parentViewModel?.state?.rightButtonVisibility = View.VISIBLE
        return super.onBackPressed()
    }

}