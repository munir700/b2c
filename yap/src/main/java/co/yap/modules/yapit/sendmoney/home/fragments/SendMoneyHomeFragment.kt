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
import co.yap.modules.dashboard.yapit.y2y.home.fragments.YapToYapFragment
import co.yap.modules.dashboard.yapit.y2y.home.fragments.YapToYapFragmentDirections
import co.yap.modules.yapit.sendmoney.activities.SendMoneyHomeActivity
import co.yap.modules.yapit.sendmoney.fragments.SendMoneyBaseFragment
import co.yap.modules.yapit.sendmoney.home.adapters.AllBeneficiriesAdapter
import co.yap.modules.yapit.sendmoney.home.interfaces.ISendMoneyHome
import co.yap.modules.yapit.sendmoney.home.viewmodels.SendMoneyHomeScreenViewModel
import co.yap.networking.customers.requestdtos.Contact
import co.yap.networking.customers.responsedtos.sendmoney.Beneficiary
import co.yap.widgets.swipe_lib.SwipeCallBack
import co.yap.yapcore.helpers.Utils
import co.yap.yapcore.helpers.toast
import co.yap.yapcore.interfaces.OnItemClickListener
import co.yap.yapcore.toast
import kotlinx.android.synthetic.main.layout_send_beneficiaries_toolbar.*


class SendMoneyHomeFragment : SendMoneyBaseFragment<ISendMoneyHome.ViewModel>(),
    ISendMoneyHome.View, SwipeCallBack {

    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.fragment_send_money_home

    override val viewModel: ISendMoneyHome.ViewModel
        get() = ViewModelProviders.of(this).get(SendMoneyHomeScreenViewModel::class.java)

    lateinit var adaptor: AllBeneficiriesAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initComponents()
        setObservers()

        activity!!.tbBtnAddBeneficiary.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                findNavController().navigate(R.id.action_sendMoneyHomeFragment_to_selectCountryFragment)

            }
        })
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
                    findNavController().navigate(R.id.action_sendMoneyHomeFragment_to_selectCountryFragment)

                R.id.tbBtnAddBeneficiary ->
                    findNavController().navigate(R.id.action_sendMoneyHomeFragment_to_selectCountryFragment)
            }
        })

    }

    override fun onBackPressed(): Boolean {

        return super.onBackPressed()
    }


    private fun initComponents() {
        getBinding().layoutBeneficiaries.rvAllBeneficiaries.adapter =
            AllBeneficiriesAdapter(mutableListOf(), this )
        (getBinding().layoutBeneficiaries.rvAllBeneficiaries.adapter as AllBeneficiriesAdapter).setItemListener(
            listener
        )
    }

    @SuppressLint("SetTextI18n")
    private fun setObservers() {
        viewModel.clickEvent.observe(this, observer)

        viewModel.yapContactLiveData?.observe(this, Observer {
            (getBinding().layoutBeneficiaries.rvAllBeneficiaries.adapter as AllBeneficiriesAdapter).setList(
                it
            )

        })


        (getBinding().layoutBeneficiaries.rvAllBeneficiaries.adapter as AllBeneficiriesAdapter).filterCount.observe(
            this,
            Observer {

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

    override fun onSwipeEdit(beneficiary: Beneficiary) {
        toast(beneficiary.title +" onSwipeEdit")
    }

    override fun onSwipeDelete(beneficiary: Beneficiary) {
        toast(beneficiary.title +" onSwipeDelete")
    }
}