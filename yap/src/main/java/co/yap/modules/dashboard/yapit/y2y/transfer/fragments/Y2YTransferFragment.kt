package co.yap.modules.dashboard.yapit.y2y.transfer.fragments

import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.view.Gravity
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import co.yap.R
import co.yap.modules.dashboard.yapit.y2y.main.fragments.Y2YBaseFragment
import co.yap.modules.dashboard.yapit.y2y.transfer.interfaces.IY2YFundsTransfer
import co.yap.modules.dashboard.yapit.y2y.transfer.viewmodels.Y2YFundsTransferViewModel
import co.yap.translation.Strings
import co.yap.yapcore.BR
import co.yap.yapcore.helpers.CustomSnackbar
import co.yap.yapcore.helpers.DecimalDigitsInputFilter
import co.yap.yapcore.helpers.Utils
import co.yap.yapcore.managers.MyUserManager
import kotlinx.android.synthetic.main.fragment_y2y_funds_transfer.*

class Y2YTransferFragment : Y2YBaseFragment<IY2YFundsTransfer.ViewModel>(), IY2YFundsTransfer.View {
    val args: Y2YTransferFragmentArgs by navArgs()

    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.fragment_y2y_funds_transfer

    override val viewModel: Y2YFundsTransferViewModel
        get() = ViewModelProviders.of(this).get(Y2YFundsTransferViewModel::class.java)


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
            val action =
                Y2YTransferFragmentDirections.actionY2YTransferFragmentToY2YFundsTransferSuccessFragment(
                    "Sufyan",
                    "AED",
                    viewModel.state.amount
                )
            findNavController().navigate(action)
        })
        viewModel.errorEvent.observe(this, Observer {
            showErrorSnackBar()
        })


    }

    private fun setUpData() {

        viewModel.state.fullName = args.beneficiaryName
        viewModel.receiverUUID = args.receiverUUID

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

}