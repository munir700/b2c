package co.yap.modules.dashboard.yapit.sendmoney.home.fragments

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import co.yap.BR
import co.yap.R
import co.yap.databinding.ActivitySendMoneyLandingBinding
import co.yap.modules.dashboard.yapit.sendmoney.fragments.SendMoneyBaseFragment
import co.yap.modules.dashboard.yapit.sendmoney.home.adapters.AllBeneficiriesAdapter
import co.yap.modules.dashboard.yapit.sendmoney.home.interfaces.ISendMoneyHome
import co.yap.modules.dashboard.yapit.sendmoney.home.viewmodels.SendMoneyHomeScreenViewModel
import co.yap.modules.dashboard.yapit.y2y.home.adaptors.RecentTransferAdaptor
import co.yap.modules.dashboard.yapit.y2y.home.fragments.YapToYapFragment
import co.yap.networking.customers.requestdtos.Contact
import co.yap.networking.customers.responsedtos.sendmoney.Beneficiary
import co.yap.translation.Translator
import co.yap.widgets.swipe_lib.SwipeCallBack
import co.yap.yapcore.helpers.RecyclerTouchListener
import co.yap.yapcore.helpers.Utils
import co.yap.yapcore.interfaces.OnItemClickListener
import co.yap.yapcore.toast
import kotlinx.android.synthetic.main.fragment_yap_to_yap.*
import kotlinx.android.synthetic.main.layout_send_beneficiaries_toolbar.*


class SendMoneyHomeFragment : SendMoneyBaseFragment<ISendMoneyHome.ViewModel>(),
    ISendMoneyHome.View, SwipeCallBack {
    var positionToDelete = 0
    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.activity_send_money_landing

    override val viewModel: ISendMoneyHome.ViewModel
        get() = ViewModelProviders.of(this).get(SendMoneyHomeScreenViewModel::class.java)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initComponents()
//        setObservers()

        activity?.tbBtnAddBeneficiary?.setOnClickListener { findNavController().navigate(R.id.action_sendMoneyHomeFragment_to_selectCountryFragment) }
    }

    override fun onPause() {
        viewModel.clickEvent.removeObservers(this)
        viewModel.onDeleteSuccess.removeObservers(this)
        super.onPause()

    }

    override fun onResume() {
        super.onResume()
//        setupRecent()

        viewModel.clickEvent.observe(this, Observer {
            when (it) {
                R.id.addContactsButton ->
                    findNavController().navigate(R.id.action_sendMoneyHomeFragment_to_selectCountryFragment)

                R.id.tbBtnAddBeneficiary ->
                    findNavController().navigate(R.id.action_sendMoneyHomeFragment_to_selectCountryFragment)
            }
        })

        viewModel.onDeleteSuccess.observe(this, Observer {
            (getBinding().layoutBeneficiaries.rvAllBeneficiaries.adapter as AllBeneficiriesAdapter).removeItemAt(
                positionToDelete
            )
        })

    }

//    private fun setupRecent() {
//        if (viewModel.adapter.get() == null) {
//            viewModel.requestRecentBeneficiaries()
//            viewModel.recentTransferData.observe(this, Observer {
//                if (it.isEmpty()) {
//                    layoutRecent?.visibility = View.GONE
//                } else {
//                    viewModel.adapter.set(
//                        RecentTransferAdaptor(
//                            it.toMutableList(),
//                            findNavController()
//                        )
//                    )
//                    layoutRecent?.visibility = View.VISIBLE
//                }
//            })
//        } else {
//            if (viewModel.recentTransferData.value != null && viewModel.recentTransferData.value!!.isNotEmpty()) {
//                viewModel.adapter.set(
//                    RecentTransferAdaptor(
//                        viewModel.recentTransferData.value?.toMutableList()!!,
//                        findNavController()
//                    )
//                )
//                layoutRecent?.visibility = View.VISIBLE
//            }
//        }
//
//    }



    private fun initComponents() {
        initSwipeListener()
        getBinding().layoutBeneficiaries.rvAllBeneficiaries.adapter =
            AllBeneficiriesAdapter(mutableListOf())
//        (getBinding().layoutBeneficiaries.rvAllBeneficiaries.adapter as AllBeneficiriesAdapter).setItemListener(
//            listener
//        )


    }

//    @SuppressLint("SetTextI18n")
//    private fun setObservers() {
//        viewModel.clickEvent.observe(this, observer)
//
//        viewModel.allBeneficiariesLiveData.observe(this, Observer {
//            (getBinding().layoutBeneficiaries.rvAllBeneficiaries.adapter as AllBeneficiriesAdapter).setList(
//                it
//            )
//
//        })
//
//
//        (getBinding().layoutBeneficiaries.rvAllBeneficiaries.adapter as AllBeneficiriesAdapter).filterCount.observe(
//            this,
//            Observer {
//
//            })
//    }

//    val listener = object : OnItemClickListener {
//        override fun onItemClick(view: View, data: Any, pos: Int) {
//            when (view.id) {
//                R.id.tvInvite -> {
//                    Utils.shareText(requireContext(), getBody())
//                }
//
//                R.id.lyContact -> {
//                    if (data is Contact && data.yapUser!! && data.accountDetailList != null && data.accountDetailList?.isNotEmpty()!!) {
//                        if (parentFragment is YapToYapFragment) {
//                            (parentFragment as YapToYapFragment).findNavController().navigate(
//                                YapToYapFragmentDirections.actionYapToYapHomeToY2YTransferFragment(
//                                    data.beneficiaryPictureUrl!!
//                                    ,
//                                    data.accountDetailList?.get(0)?.accountUuid!!,
//                                    data.title!!,
//                                    pos
//                                )
//                            )
//                        }
//                    }
//                }
//            }
//        }
//    }


    private fun getBinding(): ActivitySendMoneyLandingBinding {
        return (viewDataBinding as ActivitySendMoneyLandingBinding)
    }

    override fun onSwipeEdit(beneficiary: Beneficiary) {
        toast(beneficiary.title + " onSwipeEdit")
        // using navigation controller go to edit beneficiary passing data beneficiary
        val action =
            SendMoneyHomeFragmentDirections.actionSendMoneyHomeFragmentToBeneficiaryOverviewFragment(
                beneficiary
            )
        findNavController().navigate(action)
    }

    override fun onSwipeDelete(beneficiary: Beneficiary, position: Int) {
        positionToDelete = position
        ConfirmDeleteBeneficiary(beneficiary)
    }

    private fun initSwipeListener() {
        RecyclerTouchListener(
            requireContext(),
            true,
            getBinding().layoutBeneficiaries.rvAllBeneficiaries,
            rvSwipeListener
        )
    }

    //            .setClickable(object : RecyclerTouchListener.OnRowClickListener {
//                override fun onRowClicked(position: Int) {
//                    onPressEdit(position)
//                }
//
//                override fun onIndependentViewClicked(independentViewID: Int, position: Int) {}
//            }).setSwipeOptionViews(R.id.edit, R.id.delete)
//            .setSwipeable(
//                R.id.rowFG, R.id.rowBG
//            ) { viewID, position ->
//                if (viewID == R.id.delete)
//                    onPressDelete(position)
//                else if (viewID == R.id.edit) {
//                    onPressEdit(position)
//                }
//            }
    private val rvSwipeListener = object : RecyclerTouchListener.ClickListener {
        override fun onClick(view: View, position: Int) {
        }

        override fun onLeftSwipe(view: View, position: Int) {
        }

        override fun onRightSwipe(view: View, position: Int) {
        }

    }


    fun ConfirmDeleteBeneficiary(beneficiary: Beneficiary) {
        androidx.appcompat.app.AlertDialog.Builder(activity!!)
            .setTitle(
                Translator.getString(
                    activity!!,
                    R.string.screen_send_money_display_text_delete
                )
            )
            .setMessage(
                Translator.getString(
                    activity!!,
                    R.string.screen_send_money_display_text_delete_message
                )
            )
            .setPositiveButton(
                Translator.getString(
                    activity!!,
                    R.string.common_button_yes
                ),
                DialogInterface.OnClickListener { dialog, which ->
                    viewModel.requestDeleteBeneficiary(beneficiary.id)
                })

            .setNegativeButton(
                Translator.getString(
                    activity!!,
                    R.string.common_button_cancel
                ),
                null
            )
            .show()
    }


}