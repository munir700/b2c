package co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.fragments

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
            (activity as BeneficiaryCashTransferActivity).let {
                it.viewModel.state.leftButtonVisibility =
                    false
                it.viewModel.state.rightButtonVisibility =
                    false
                viewModel.state.name = it.viewModel.state.beneficiary?.fullName()
                it.viewModel.state.toolBarTitle =
                    getString(Strings.screen_cash_pickup_funds_success_toolbar_header)
            }
            viewModel.state.amount = "${args.currencyType} ${args.amount}"

        }
        viewModel.clickEvent.observe(this, Observer {
            when (it) {
                R.id.confirmButton -> {
                    // go back to dashboard
//                    activity!!.recreate()
                    activity?.finish()
//                    findNavController().navigate(R.id.action_beneficiaryOverviewFragment_to_transferSuccessFragment)
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
            "https://api.whatsapp.com/send?text=${referenceNumber})}"
        val i = Intent(Intent.ACTION_SENDTO, Uri.fromParts("", "", null))
        i.data = Uri.parse(url)
        startActivity(i)
    }

    private fun inviteViaEmail(referenceNumber: String) {
        val intent = Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", "", null))
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name))
        intent.putExtra(Intent.EXTRA_TEXT, referenceNumber)
        startActivity(Intent.createChooser(intent, "Send mail..."))

    }

    private fun inviteViaSms(referenceNumber: String) {
        val uri = Uri.parse(
            "sms:"
        )
        val it = Intent(Intent.ACTION_SEND, uri)
        it.putExtra("sms_body", referenceNumber)
        startActivity(it)
    }

    fun setData() {
        if (context is BeneficiaryCashTransferActivity) {
            (context as BeneficiaryCashTransferActivity).viewModel.state.beneficiary?.let { beneficiary ->
                beneficiary.beneficiaryType?.let { beneficiaryType ->
                    if (beneficiaryType.isNotEmpty())
                        when (SendMoneyBeneficiaryType.valueOf(beneficiaryType)) {
                            SendMoneyBeneficiaryType.RMT -> {
                                //Set views for RMT


                            }
                            SendMoneyBeneficiaryType.SWIFT -> {
                                //set views for Swift

                            }
                            SendMoneyBeneficiaryType.CASHPAYOUT -> {
                                //set views for cash payout
                                setDataForCashPayout()
                            }
                            SendMoneyBeneficiaryType.DOMESTIC -> {
                                //set views for domestic
                                setDataForDomestic()
                            }
                            /*SendMoneyBeneficiaryType.INTERNAL_TRANSFER -> {
                                //call service for INTERNAL_TRANSFER

                            }*/
                            SendMoneyBeneficiaryType.UAEFTS -> {
                                //set views for UAEFTS
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

    fun setDataForCashPayout() {
        viewModel.state.locationLayoutVisibility = true
    }

    fun setDataForDomestic() {
        viewModel.state.locationLayoutVisibility = false
    }

    fun setDataForUAEFTS() {
        viewModel.state.locationLayoutVisibility = false
    }

}