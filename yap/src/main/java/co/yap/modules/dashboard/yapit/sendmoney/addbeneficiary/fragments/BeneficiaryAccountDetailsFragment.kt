package co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.BR
import co.yap.R
import co.yap.modules.dashboard.yapit.sendmoney.activities.BeneficiaryCashTransferActivity
import co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.interfaces.IBeneficiaryAccountDetails
import co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.viewmodels.BeneficiaryAccountDetailsViewModel
import co.yap.modules.dashboard.yapit.sendmoney.fragments.SendMoneyBaseFragment
import co.yap.translation.Translator
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.constants.RequestCodes
import co.yap.yapcore.helpers.Utils
import co.yap.yapcore.interfaces.OnItemClickListener

class BeneficiaryAccountDetailsFragment :
    SendMoneyBaseFragment<IBeneficiaryAccountDetails.ViewModel>(),
    IBeneficiaryAccountDetails.View {

    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.fragment_beneficiary_account_detail

    override val viewModel: BeneficiaryAccountDetailsViewModel
        get() = ViewModelProviders.of(this).get(BeneficiaryAccountDetailsViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.clickEvent.observe(this, observer)
        viewModel.success.observe(this, Observer {
            if (it) {
                context?.let { it ->
                    Utils.confirmationDialog(it,
                        Translator.getString(
                            it,
                            R.string.screen_add_beneficiary_detail_display_text_alert_title
                        ),
                        Translator.getString(
                            it,
                            R.string.screen_add_beneficiary_detail_display_button_block_alert_description
                        ), Translator.getString(
                            it,
                            R.string.screen_add_beneficiary_detail_display_button_block_alert_yes
                        ), Translator.getString(
                            it,
                            R.string.screen_add_beneficiary_detail_display_button_block_alert_no
                        ),
                        object : OnItemClickListener {
                            override fun onItemClick(view: View, data: Any, pos: Int) {
                                if (data is Boolean) {
                                    if (data) {
                                        startMoneyTransfer()
                                    } else {
                                        setIntentResult()
                                    }
                                }
                            }
                        })
                }
            } else {
                // error while creating beneficiary
            }
        })
    }

    private fun startMoneyTransfer() {
        viewModel.beneficiary?.let { beneficiary ->
            requireActivity().startActivityForResult(
                BeneficiaryCashTransferActivity.newIntent(
                    requireActivity(),
                    beneficiary,
                    isNewBeneficiary = true
                ), RequestCodes.REQUEST_TRANSFER_MONEY
            )
        }
    }

    private fun setIntentResult() {
        activity?.let { it ->
            val intent = Intent()
            intent.putExtra(Constants.BENEFICIARY_CHANGE, true)
            it.setResult(Activity.RESULT_OK, intent)
            it.finish()
        }
    }


    private val observer = Observer<Int> {
        when (it) {
            R.id.confirmButton ->
                viewModel.createBeneficiaryRequest()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.clickEvent.removeObservers(this)

    }

    override fun onBackPressed(): Boolean {
        return super.onBackPressed()
    }

}