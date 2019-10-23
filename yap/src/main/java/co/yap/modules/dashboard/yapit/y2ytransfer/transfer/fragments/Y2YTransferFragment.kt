package co.yap.modules.dashboard.yapit.y2ytransfer.transfer.fragments

import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.view.Gravity
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.R
import co.yap.modules.dashboard.yapit.y2ytransfer.fragments.Y2YBaseFragment
import co.yap.modules.dashboard.yapit.y2ytransfer.transfer.interfaces.IY2YTransfer
import co.yap.modules.dashboard.yapit.y2ytransfer.transfer.viewmodels.Y2YTransferViewModel
import co.yap.yapcore.BR
import co.yap.yapcore.helpers.CustomSnackbar
import co.yap.yapcore.helpers.DecimalDigitsInputFilter
import co.yap.yapcore.helpers.Utils
import kotlinx.android.synthetic.main.fragment_yap_to_yap_transfer.*

class Y2YTransferFragment : Y2YBaseFragment<IY2YTransfer.ViewModel>(), IY2YTransfer.View {

    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.fragment_yap_to_yap_transfer

    override val viewModel: Y2YTransferViewModel
        get() = ViewModelProviders.of(this).get(Y2YTransferViewModel::class.java)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setObservers()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpData()
    }

    override fun setObservers() {
        viewModel.clickEvent.observe(this, Observer {
            showToast("success state")
        })
        viewModel.errorEvent.observe(this, Observer {
            showErrorSnackBar()
        })


    }

    private fun setUpData() {

        viewModel.state.availableBalanceText =
            " " + getString(Strings.common_text_currency_type) + " " + Utils.getFormattedCurrency(
                viewModel.state.availableBalance
            )
        etAmount.filters =
            arrayOf<InputFilter>(InputFilter.LengthFilter(7), DecimalDigitsInputFilter(2))
        etAmount.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0?.length!! > 0) {
                    // puts the caret after the text when unempty
                    etAmount.setGravity(Gravity.CENTER)
                } else {
                    etAmount.setGravity(Gravity.START or Gravity.CENTER_VERTICAL)
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


}