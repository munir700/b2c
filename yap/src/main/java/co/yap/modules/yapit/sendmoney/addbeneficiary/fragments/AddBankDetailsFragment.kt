package co.yap.modules.yapit.sendmoney.addbeneficiary.fragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.BR
import co.yap.R
import co.yap.modules.yapit.sendmoney.addbeneficiary.interfaces.IBankDetails
import co.yap.modules.yapit.sendmoney.addbeneficiary.viewmodels.BankDetailsViewModel
import co.yap.modules.yapit.sendmoney.fragments.SendMoneyBaseFragment
import co.yap.yapcore.helpers.Utils

class AddBankDetailsFragment : SendMoneyBaseFragment<IBankDetails.ViewModel>(),
    IBankDetails.View {

    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.fragment_add_bank_detail

    override val viewModel: IBankDetails.ViewModel
        get() = ViewModelProviders.of(this).get(BankDetailsViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Utils.ConfirmAddBeneficiary(this.activity!!)

        viewModel.clickEvent.observe(this, Observer {
            when (it) {
                R.id.confirmButton ->
                    Utils.ConfirmAddBeneficiary(this.activity!!)
            }
        })
    }

    override fun onPause() {
        viewModel.clickEvent.removeObservers(this)
        super.onPause()

    }

    override fun onResume() {
        super.onResume()


    }

    override fun onBackPressed(): Boolean {

        return super.onBackPressed()
    }

//    fun ConfirmAddBeneficiary() {
//        AlertDialog.Builder(this!!.activity!!)
//            .setTitle(getString(R.string.screen_add_beneficiary_detail_display_text_alert_title))
//            .setMessage(getString(R.string.screen_add_beneficiary_detail_display_button_block_alert_description))
//            .setPositiveButton(getString(R.string.screen_add_beneficiary_detail_display_button_block_alert_yes),
//                DialogInterface.OnClickListener { dialog, which ->
//                    //                    doLogout()
//                })
//
//            .setNegativeButton(
//                getString(R.string.screen_add_beneficiary_detail_display_button_block_alert_no),
//                null
//            )
//            .show()
//    }

}