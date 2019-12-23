package co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.fragments

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.navArgs
import co.yap.BR
import co.yap.R
import co.yap.modules.dashboard.yapit.sendmoney.activities.BeneficiaryCashTransferActivity
import co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.interfaces.ITransferSuccess
import co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.viewmodels.TransferSuccessViewModel
import co.yap.modules.dashboard.yapit.sendmoney.fragments.SendMoneyBaseFragment
import co.yap.modules.dashboard.yapit.y2y.home.phonecontacts.InviteBottomSheet
import co.yap.translation.Strings
import co.yap.yapcore.enums.SendMoneyBeneficiaryType
import co.yap.yapcore.helpers.Utils
import com.digitify.identityscanner.camera.engine.offset.Reference


class TransferSuccessFragment : SendMoneyBaseFragment<ITransferSuccess.ViewModel>(),
    ITransferSuccess.View, InviteBottomSheet.OnItemClickListener {
    val args: TransferSuccessFragmentArgs by navArgs()

    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.fragment_transfer_success

    override val viewModel: ITransferSuccess.ViewModel
        get() = ViewModelProviders.of(this).get(TransferSuccessViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity is BeneficiaryCashTransferActivity) {
            setData()
            viewModel.state.amount = "${args.currencyType} ${args.amount}"
            viewModel.state.referenceNumber = args.referenceNumber
            viewModel.state.position = args.position

        }
        viewModel.clickEvent.observe(this, Observer {
            when (it) {
                R.id.confirmButton -> {
                    activity?.finish()
                }
                R.id.tvShareCode -> {
                    showToast(
                        "share code please!"
                    )
                    shareCode("20394848")
                }
            }
        })
    }

    private fun shareCode(referenceCode: String) {
        this.fragmentManager?.let {
            val inviteFriendBottomSheet = InviteBottomSheet(this, referenceCode)
            inviteFriendBottomSheet.show(it, "")
        }
    }

    override fun onPause() {
        viewModel.clickEvent.removeObservers(this)
        super.onPause()

    }

    override fun onResume() {
        super.onResume()
    }

    override fun onBackPressed(): Boolean {
        return true
    }

    private fun setData() {
        if (context is BeneficiaryCashTransferActivity) {
            (context as BeneficiaryCashTransferActivity).viewModel.state.beneficiary?.let { beneficiary ->
                beneficiary.beneficiaryType?.let { beneficiaryType ->
                    if (beneficiaryType.isNotEmpty())
                        when (SendMoneyBeneficiaryType.valueOf(beneficiaryType)) {
                            SendMoneyBeneficiaryType.RMT -> {
                                setDataForRmt()
                            }
                            SendMoneyBeneficiaryType.SWIFT -> {
                                setDataForSwift()
                            }
                            SendMoneyBeneficiaryType.CASHPAYOUT -> {
                                setDataForCashPayout()
                            }
                            SendMoneyBeneficiaryType.DOMESTIC -> {
                                setDataForDomestic()
                            }
                            SendMoneyBeneficiaryType.UAEFTS -> {
                                setDataForUAEFTS()
                            }
                            else -> {
                                //common views

                            }
                        }
                }
            }
        }
    }

    private fun setDataForCashPayout() {
        viewModel.state.flagLayoutVisibility = false
        viewModel.state.locationLayoutVisibility = true
        (activity as BeneficiaryCashTransferActivity).let {
            it.viewModel.state.toolBarVisibility = false
            viewModel.state.name = it.viewModel.state.beneficiary?.fullName()
            viewModel.state.successHeader =
                getString(Strings.screen_cash_pickup_funds_transfer_display_text_title)
            viewModel.state.transferAmountHeading =
                getString(Strings.screen_cash_pickup_funds_transfer_display_text_amount_heading)
            viewModel.state.buttonTitle =
                getString(Strings.screen_cash_pickup_funds_transfer_back_to_dashboard)
        }
    }

    private fun setDataForDomestic() {
        viewModel.state.flagLayoutVisibility = false
        viewModel.state.locationLayoutVisibility = false
        (activity as BeneficiaryCashTransferActivity).let {
            it.viewModel.state.toolBarVisibility = false
            viewModel.state.name = it.viewModel.state.beneficiary?.fullName()
            viewModel.state.successHeader =
                getString(Strings.screen_cash_pickup_funds_success_toolbar_header)
            viewModel.state.successHeader =
                getString(Strings.screen_domestic_funds_transfer_display_text_title)
            viewModel.state.transferAmountHeading =
                getString(Strings.screen_domestic_funds_transfer_display_text_amount_heading)
            viewModel.state.buttonTitle =
                getString(Strings.screen_cash_pickup_funds_transfer_back_to_dashboard)
        }
    }

    private fun setDataForUAEFTS() {
        viewModel.state.flagLayoutVisibility = false
        viewModel.state.locationLayoutVisibility = false
        (activity as BeneficiaryCashTransferActivity).let {
            it.viewModel.state.toolBarVisibility = false
            viewModel.state.name = it.viewModel.state.beneficiary?.fullName()
            viewModel.state.successHeader =
                getString(Strings.screen_cash_pickup_funds_transfer_display_text_title)
            viewModel.state.transferAmountHeading =
                getString(Strings.screen_cash_pickup_funds_transfer_display_text_amount_heading)
            viewModel.state.buttonTitle =
                getString(Strings.screen_cash_pickup_funds_transfer_back_to_dashboard)
        }
    }

    private fun setDataForSwift() {
        viewModel.state.beneficiaryCountry = args.country
        viewModel.state.flagLayoutVisibility = true
        viewModel.state.locationLayoutVisibility = false
        (activity as BeneficiaryCashTransferActivity).let {
            it.viewModel.state.toolBarVisibility = false
            viewModel.state.name = it.viewModel.state.beneficiary?.fullName()
            viewModel.state.successHeader =
                getString(Strings.screen_international_funds_transfer_display_text_success_title)
            viewModel.state.transferAmountHeading =
                getString(Strings.screen_international_funds_transfer_display_text_amount_heading)
            viewModel.state.buttonTitle =
                getString(Strings.screen_international_funds_transfer_back_to_dashboard)
        }
    }

    private fun setDataForRmt() {
        viewModel.state.beneficiaryCountry = args.country
        viewModel.state.locationLayoutVisibility = false
        viewModel.state.flagLayoutVisibility = true
        (activity as BeneficiaryCashTransferActivity).let {
            it.viewModel.state.toolBarVisibility = false
            viewModel.state.name = it.viewModel.state.beneficiary?.fullName()
            viewModel.state.successHeader =
                getString(Strings.screen_international_funds_transfer_display_text_success_title)
            viewModel.state.transferAmountHeading =
                getString(Strings.screen_international_funds_transfer_display_text_amount_heading)
            viewModel.state.buttonTitle =
                getString(Strings.screen_international_funds_transfer_back_to_dashboard)
        }
    }

    override fun onClick(viewId: Int, data: Any) {

        if (data is String)
            when (viewId) {
                R.id.tvChooseEmail -> inviteViaEmail(data)
                R.id.tvChooseSMS -> inviteViaSms(data)
                R.id.tvChooseWhatsapp -> inviteViaWhatsapp(data)
            }

    }

    private fun inviteViaWhatsapp(referenceNumber: String) {
        val url =
            "https://wa.me/?text=${referenceNumber})}"
        val i = Intent(Intent.ACTION_SEND, Uri.fromParts("", "", null))
        i.data = Uri.parse(url)
        if (canHandleIntent(intent = i, activity = activity))
            startActivity(i)
    }

    private fun inviteViaEmail(referenceNumber: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.fromParts("mailto", "", null))
        intent.putExtra(Intent.EXTRA_TEXT, referenceNumber)
        startActivity(Intent.createChooser(intent, "Send mail..."))
    }

    private fun inviteViaSms(referenceNumber: String) {
        val sendIntent = Intent(Intent.ACTION_VIEW)
        sendIntent.data = Uri.parse("sms:")
        sendIntent.putExtra("sms_body", referenceNumber)
        if (canHandleIntent(intent = sendIntent, activity = activity))
            startActivity(sendIntent)
    }

    private fun canHandleIntent(intent: Intent, activity: Activity?): Boolean {
        val packageManager = activity?.packageManager
        packageManager?.let {
            return if (intent.resolveActivity(packageManager) != null) {
                true
            } else {
                showToast("No app available to handle action")
                false
            }
        }
        return false
    }

}