package co.yap.modules.yapit.sendmoney.home.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import co.yap.BR
import co.yap.R
import co.yap.databinding.FragmentSendMoneyHomeBinding
import co.yap.databinding.FragmentYapContactsBinding
import co.yap.modules.dashboard.yapit.y2y.home.fragments.YapToYapFragment
import co.yap.modules.dashboard.yapit.y2y.home.fragments.YapToYapFragmentDirections
import co.yap.modules.yapit.sendmoney.fragments.SendMoneyBaseFragment
import co.yap.modules.yapit.sendmoney.home.adapters.AllBeneficiriesAdapter
import co.yap.modules.yapit.sendmoney.home.interfaces.ISendMoneyHome
import co.yap.modules.yapit.sendmoney.home.viewmodels.SendMoneyHomeScreenViewModel
import co.yap.networking.customers.requestdtos.Contact
import co.yap.yapcore.helpers.PagingState
import co.yap.yapcore.helpers.Utils
import co.yap.yapcore.interfaces.OnItemClickListener


class SendMoneyHomeFragment : SendMoneyBaseFragment<ISendMoneyHome.ViewModel>(),
    ISendMoneyHome.View {

    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.fragment_send_money_home

    override val viewModel: ISendMoneyHome.ViewModel
        get() = ViewModelProviders.of(this).get(SendMoneyHomeScreenViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initState()
        initComponents()
        setObservers()

    }

    override fun onPause() {
        viewModel.clickEvent.removeObservers(this)
        super.onPause()

    }

    override fun onResume() {
        super.onResume()

        viewModel.clickEvent.observe(this, Observer {
            when (it) {
                R.id.addContactsButton ->
                    findNavController().navigate(R.id.action_selectCountryFragment_to_transferTypeFragment)
            }
        })

    }

    override fun onBackPressed(): Boolean {

        return super.onBackPressed()
    }


    private fun initComponents() {

        getBinding().layoutBeneficiaries.rvAllBeneficiaries.adapter = AllBeneficiriesAdapter(mutableListOf())
        (getBinding().layoutBeneficiaries.rvAllBeneficiaries.adapter as AllBeneficiriesAdapter).setItemListener(listener)
    }

    private fun initState() {

        viewModel.getState().observe(this, Observer { state ->
            if ((getBinding().layoutBeneficiaries.rvAllBeneficiaries.adapter as AllBeneficiriesAdapter).getDataList().isNullOrEmpty()) {
                getBinding().layoutBeneficiaries.rvAllBeneficiaries.visibility = View.GONE
//                getBinding().txtError.visibility =
//                    if (state == PagingState.DONE || state == PagingState.ERROR) View.VISIBLE else View.GONE
////                getBinding().btnInvite.visibility =
////                    if (state == PagingState.DONE || state == PagingState.ERROR) if (viewModel.parentViewModel?.isSearching?.value!!) View.GONE else View.VISIBLE else View.GONE
//                getBinding().progressBar.visibility =
//                    if (state == PagingState.LOADING) View.VISIBLE else View.GONE

            } else {
//                getBinding().txtError.visibility = View.GONE
//                getBinding().btnInvite.visibility = View.GONE
//                getBinding().progressBar.visibility = View.GONE
                getBinding().layoutBeneficiaries.rvAllBeneficiaries.visibility = View.VISIBLE
            }
        })
        viewModel.pagingState.value = PagingState.LOADING
    }

    @SuppressLint("SetTextI18n")
    private fun setObservers() {
        viewModel.clickEvent.observe(this, observer)
        viewModel.yapContactLiveData?.observe(this, Observer {
            (getBinding().layoutBeneficiaries.rvAllBeneficiaries.adapter as AllBeneficiriesAdapter).setList(it)
//            getBinding().txtError.visibility = View.GONE
//            getBinding().tvContactListDescription.visibility =
                if (it.isEmpty()) View.GONE else View.VISIBLE

            viewModel.pagingState.value = PagingState.DONE
//            getBinding().tvContactListDescription.text =
//                if (it.size == 1) "${it.size} YAP contact" else "${it.size} YAP contacts"

//            getBinding().txtError.text =
//                if (viewModel.parentViewModel?.isSearching?.value!!) "No result" else Translator.getString(
//                    requireContext(),
//                    Strings.screen_y2y_display_text_no_yap_contacts
//                )
        })

//        viewModel.parentViewModel?.searchQuery?.observe(this, Observer {
//            (getBinding().layoutBeneficiaries.rvAllBeneficiaries.adapter as AllBeneficiriesAdapter).filter.filter(it)
//        })

        (getBinding().layoutBeneficiaries.rvAllBeneficiaries.adapter as AllBeneficiriesAdapter).filterCount.observe(
            this,
            Observer {

//                getBinding().tvContactListDescription.visibility =
//                    if (it == 0) View.GONE else View.VISIBLE
//                getBinding().txtError.visibility = if (it == 0) View.VISIBLE else View.GONE
//            getBinding().txtError.text =
//                if (viewModel.parentViewModel?.isSearching?.value!!) "No result" else Translator.getString(
//                    requireContext(),
//                    Strings.screen_y2y_display_text_no_yap_contacts
//                )
//                getBinding().tvContactListDescription.text =
//                    if (it == 1) "$it YAP contact" else "$it YAP contacts"
            })
    }

    val listener = object : OnItemClickListener {
        override fun onItemClick(view: View, data: Any, pos: Int) {
            when (view.id) {
                R.id.tvInvite -> {
                    Utils.shareText(requireContext(), getBody())
                }
                R.id.lyContact -> {
                    if (data is Contact && data.yapUser!! && data.accountDetailList != null && data.accountDetailList?.isNotEmpty()!!) {
                        if (parentFragment is YapToYapFragment) {
                            (parentFragment as YapToYapFragment).findNavController().navigate(
                                YapToYapFragmentDirections.actionYapToYapHomeToY2YTransferFragment(
                                    data.beneficiaryPictureUrl!!
                                    ,
                                    data.accountDetailList?.get(0)?.accountUuid!!,
                                    data.title!!,
                                    pos
                                )
                            )
                        }
                    }
                }
            }
        }
    }

    private fun getBody(): String {
        return "App link"
    }

    private val observer = Observer<Int> {
        when (it) {
            R.id.btnInvite -> {
                Utils.shareText(requireContext(), getBody())
            }
        }
    }

    private fun getBinding(): FragmentSendMoneyHomeBinding {
        return (viewDataBinding as FragmentSendMoneyHomeBinding)
    }
}