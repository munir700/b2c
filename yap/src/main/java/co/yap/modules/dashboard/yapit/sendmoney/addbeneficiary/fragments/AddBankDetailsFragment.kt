package co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.BR
import co.yap.R
import co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.adaptor.AddBeneficiariesAdaptor
import co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.adaptor.RAKBankAdaptor
import co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.interfaces.IBankDetails
import co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.viewmodels.BankDetailsViewModel
import co.yap.modules.dashboard.yapit.sendmoney.fragments.SendMoneyBaseFragment
import co.yap.networking.customers.requestdtos.OtherBankQuery
import co.yap.networking.customers.responsedtos.beneficiary.BankParams
import co.yap.networking.customers.responsedtos.sendmoney.Bank
import co.yap.yapcore.interfaces.OnItemClickListener
import kotlinx.android.synthetic.main.fragment_add_bank_detail.*
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext


// UI fields must be made dynamically based upon the response of API
class AddBankDetailsFragment : SendMoneyBaseFragment<IBankDetails.ViewModel>(),
    IBankDetails.View, CoroutineScope {
    private lateinit var mJob: Job
    override val coroutineContext: CoroutineContext
        get() = mJob + Dispatchers.Main

    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.fragment_add_bank_detail

    override val viewModel: BankDetailsViewModel
        get() = ViewModelProviders.of(this).get(BankDetailsViewModel::class.java)

    var adaptor: AddBeneficiariesAdaptor? = null
    var adaptorBanks: RAKBankAdaptor? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addListener()
        mJob = Job()
    }

    private fun addListener() {
        viewModel.clickEvent.observe(this, observer)
        viewModel.bankParams.observe(this, Observer {
            setupAdaptor(it)
        })
        viewModel.bankList.observe(this, Observer {
            setupAdaptorBanks(it)
        })
    }

    private fun setupAdaptor(list: List<BankParams>) {
        adaptor = AddBeneficiariesAdaptor(list as MutableList<BankParams>, watcher)
        adaptor?.setItemListener(listener)
        recycler.adapter = adaptor
    }

    private fun setupAdaptorBanks(list: List<Bank>) {
        adaptorBanks = RAKBankAdaptor(list as MutableList<Bank>)
        adaptorBanks?.setItemListener(listener)
        recycler_banks.adapter = adaptorBanks
    }

    val listener = object : OnItemClickListener {
        override fun onItemClick(view: View, data: Any, pos: Int) {
//            val action =
//                YapStoreFragmentDirections.actionYapStoreFragmentToYapStoreDetailFragment((dataList as Store).id.toString())
//            view.findNavController().navigate(action)
        }
    }

    val observer = Observer<Int> {
        when (it) {
            R.id.confirmButton -> {
                viewModel.searchRMTBanks(otherSearchParams())
            }
            //findNavController().navigate(R.id.action_addBankDetailsFragment_to_beneficiaryAccountDetailsFragment)
        }
    }

    private fun otherSearchParams(): OtherBankQuery {
        val query = OtherBankQuery()
        viewModel.parentViewModel?.selectedCountry?.let { country ->
            query.max_records = 200
            query.other_bank_country = country.value?.isoCountryCode2Digit
            if (adaptor?.getDataList() != null) {
                for (field in adaptor?.getDataList()!!.iterator()) {
                    if (!field.data.isNullOrEmpty()) {
                        val bankParams = OtherBankQuery.Params()
                        bankParams.id = field.id
                        bankParams.value = field.data
                        query.params?.add(bankParams)
                    }
                }
            }
        }
        return query
    }

    private val watcher = object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            launch {
                delay(300)
                if (adaptor?.getDataList() != null) {
                    var isValid = false
                    for (field in adaptor?.getDataList()!!.iterator()) {
                        if (field.isMandatory == "Y") {
                            if (field.minCharacters?.toInt() != null &&
                                field.minCharacters?.toInt()!! > field.data?.length ?: 0
                            ) {
                                isValid = false
                                break
                            } else {
                                isValid = true
                            }
                        }
                    }
                    viewModel.state.valid = isValid
                }
            }
        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }
    }

    override fun onDestroy() {
        viewModel.clickEvent.removeObservers(this)
        super.onDestroy()

    }

    override fun onBackPressed(): Boolean {
        return super.onBackPressed()
    }
}