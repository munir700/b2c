package co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.fragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.BR
import co.yap.R
import co.yap.modules.dashboard.yapit.sendmoney.adapters.ReasonListAdapter
import co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.interfaces.IInternationalFundsTransfer
import co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.viewmodels.InternationalFundsTransferViewModel
import co.yap.modules.dashboard.yapit.sendmoney.fragments.SendMoneyBaseFragment
import co.yap.networking.transactions.responsedtos.InternationalFundsTransferReasonList
import co.yap.yapcore.interfaces.OnItemClickListener
import co.yap.yapcore.toast
import kotlinx.android.synthetic.main.fragment_beneficiary_overview.*
import kotlinx.android.synthetic.main.fragment_international_funds_transfer.*


class InternationalFundsTransferFragment :
    SendMoneyBaseFragment<IInternationalFundsTransfer.ViewModel>(),
    IInternationalFundsTransfer.View {

    private var mReasonListAdapter: ReasonListAdapter? = null
    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.fragment_international_funds_transfer
    override val viewModel: IInternationalFundsTransfer.ViewModel
        get() = ViewModelProviders.of(this).get(InternationalFundsTransferViewModel::class.java)

    val reasonList = charArrayOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setObservers()
    }

    private fun setObservers() {
        viewModel.populateSpinnerData.observe(this, Observer {
            if (it == null) return@Observer
            reasonsSpinner.adapter = getReasonListAdapter(it)
            mReasonListAdapter?.setItemListener(listener)
        })

    }

    val listener = object : OnItemClickListener {
        override fun onItemClick(view: View, data: Any, pos: Int) {
            reasonsSpinner.setSelection(pos)
//            viewModel.onCountrySelected(pos)

        }
    }

    private fun papulateReasonsList(reasons: List<InternationalFundsTransferReasonList.ReasonList>?) {
        toast(reasons.toString())

    }
    private fun editBeneficiaryScreen() {
        etnickName.isEnabled = true
        etFirstName.isEnabled = true
        etLastName.isEnabled = true
        etAccountIbanNumber.isEnabled = true
        etnickName.isEnabled = true
        etSwiftCode.isEnabled = true
        etBankREquiredFieldCode.isEnabled = true
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onPause() {
        viewModel.clickEvent.removeObservers(this)
        super.onPause()

    }


    fun getReasonListAdapter(it: List<InternationalFundsTransferReasonList.ReasonList>): ReasonListAdapter {
        if (mReasonListAdapter == null)
            mReasonListAdapter = ReasonListAdapter(
                requireContext(), R.layout.item_reason_list, it
            )
        return this.mReasonListAdapter!!
    }


    override fun onBackPressed(): Boolean {

        return super.onBackPressed()
    }


}