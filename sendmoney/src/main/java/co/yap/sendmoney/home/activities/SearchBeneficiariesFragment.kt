package co.yap.sendmoney.home.activities

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.networking.customers.requestdtos.Contact
import co.yap.networking.customers.responsedtos.sendmoney.Beneficiary
import co.yap.sendmoney.BR
import co.yap.sendmoney.R
import co.yap.sendmoney.databinding.FragmentSearchBeneficiaryBinding
import co.yap.sendmoney.editbeneficiary.activity.EditBeneficiaryActivity
import co.yap.sendmoney.fundtransfer.activities.BeneficiaryFundTransferActivity
import co.yap.sendmoney.home.interfaces.ISMSearchBeneficiary
import co.yap.sendmoney.home.main.SMBeneficiaryParentBaseFragment
import co.yap.sendmoney.viewmodels.SMSearchBeneficiaryViewModel
import co.yap.sendmoney.y2y.home.activities.YapToYapDashboardActivity
import co.yap.translation.Strings
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.constants.RequestCodes
import co.yap.yapcore.enums.FeatureSet
import co.yap.yapcore.helpers.ExtraKeys
import co.yap.yapcore.helpers.Utils
import co.yap.yapcore.helpers.confirm
import co.yap.yapcore.helpers.extentions.afterTextChanged
import co.yap.yapcore.helpers.extentions.getBeneficiaryTransferType
import co.yap.yapcore.helpers.extentions.launchActivity
import co.yap.yapcore.helpers.extentions.showBlockedFeatureAlert
import co.yap.yapcore.managers.SessionManager
import com.nikhilpanju.recyclerviewenhanced.RecyclerTouchListener
import kotlinx.android.synthetic.main.layout_item_beneficiary.*

class SearchBeneficiariesFragment :
    SMBeneficiaryParentBaseFragment<ISMSearchBeneficiary.ViewModel>(),
    ISMSearchBeneficiary.View {
    private lateinit var onTouchListener: RecyclerTouchListener
    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.fragment_search_beneficiary

    override val viewModel: SMSearchBeneficiaryViewModel
        get() = ViewModelProviders.of(this).get(SMSearchBeneficiaryViewModel::class.java)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setObservers()
        initSwipeListener()
    }

    override fun setObservers() {
        viewModel.clickEvent.observe(this, clickListener)
        getBindings().etSearch.afterTextChanged {
            viewModel.adapter.filter.filter(it)
        }
    }

    private fun initSwipeListener() {
        activity?.let { activity ->
            onTouchListener =
                RecyclerTouchListener(activity, getBindings().rvAllBeneficiaries)
                    .setClickable(
                        object : RecyclerTouchListener.OnRowClickListener {
                            override fun onRowClicked(position: Int) {
                                viewModel.clickEvent.setPayload(
                                    SingleClickEvent.AdaptorPayLoadHolder(
                                        foregroundContainer,
                                        viewModel.adapter.getDataForPosition(position),
                                        position
                                    )
                                )
                                viewModel.clickEvent.setValue(foregroundContainer.id)
                            }

                            override fun onIndependentViewClicked(
                                independentViewID: Int,
                                position: Int
                            ) {
                            }
                        }).setSwipeOptionViews(R.id.btnEdit, R.id.btnDelete)
                    .setSwipeable(
                        R.id.foregroundContainer, R.id.swipe
                    )
                    { viewID, position ->
                        viewModel.clickEvent.setPayload(
                            SingleClickEvent.AdaptorPayLoadHolder(
                                activity.findViewById(viewID),
                                viewModel.adapter.getDataForPosition(position),
                                position
                            )
                        )
                        viewModel.clickEvent.setValue(viewID)
                    }
        }
    }

    private val clickListener = Observer<Int> {
        when (it) {
            R.id.tvCancel -> {
                Utils.hideKeyboard(getBindings().etSearch)
                navigateBack()
            }
            R.id.foregroundContainer -> {
                viewModel.clickEvent.getPayload()?.let { payload ->
                    when (payload.itemData) {
                        is Beneficiary -> {
                            startMoneyTransfer(payload.itemData as Beneficiary, payload.position)
                        }
                        is Contact -> {
                            startY2YTransfer(
                                viewModel.getBeneficiaryFromContact(payload.itemData as Contact),
                                payload.position
                            )
                        }
                    }
                }
                viewModel.clickEvent.setPayload(null)

            }
            R.id.btnEdit -> {
                viewModel.clickEvent.getPayload()?.let { payload ->
                    if (payload.itemData is Beneficiary) {
                        openEditBeneficiary(payload.itemData as Beneficiary)
                    }
                }
                viewModel.clickEvent.setPayload(null)
            }

            R.id.btnDelete -> {
                viewModel.clickEvent.getPayload()?.let { payload ->
                    if (payload.itemData is Beneficiary) deleteBeneficiary(payload.itemData as Beneficiary)

                }
                viewModel.clickEvent.setPayload(null)
            }
        }
    }

    private fun deleteBeneficiary(beneficiary: Beneficiary) {
        if (SessionManager.user?.otpBlocked == true) {
            showBlockedFeatureAlert(
                requireActivity(),
                FeatureSet.DELETE_SEND_MONEY_BENEFICIARY
            )
        } else {
            confirmDeleteBeneficiary(beneficiary.beneficiaryId.toString())
        }
    }

    private fun confirmDeleteBeneficiary(beneficiaryId: String) {
        confirm(
            message = getString(Strings.screen_send_money_display_text_delete_message),
            title = getString(Strings.screen_send_money_display_text_delete),
            positiveButton = getString(Strings.common_button_yes),
            negativeButton = getString(Strings.common_button_cancel)
        ) {
            viewModel.requestDeleteBeneficiary(beneficiaryId) {
            }
        }
    }

    private fun startMoneyTransfer(beneficiary: Beneficiary?, position: Int) {
        Utils.hideKeyboard(getBindings().etSearch)
        launchActivity<BeneficiaryFundTransferActivity>(
            requestCode = RequestCodes.REQUEST_TRANSFER_MONEY,
            type = beneficiary.getBeneficiaryTransferType()
        ) {
            putExtra(Constants.BENEFICIARY, beneficiary)
            putExtra(Constants.POSITION, position)
            putExtra(Constants.IS_NEW_BENEFICIARY, false)
        }
    }

    private fun startY2YTransfer(
        beneficiary: Beneficiary?,
        position: Int = 0
    ) {
        launchActivity<YapToYapDashboardActivity>(type = FeatureSet.Y2Y_TRANSFER) {
            putExtra(Beneficiary::class.java.name, beneficiary)
            putExtra(ExtraKeys.Y2Y_BENEFICIARY_POSITION.name, position)
        }
    }

    private fun openEditBeneficiary(beneficiary: Beneficiary?) {
        Utils.hideKeyboard(getBindings().etSearch)
        beneficiary?.let {
            val bundle = Bundle()
            bundle.putBoolean(Constants.OVERVIEW_BENEFICIARY, false)
            bundle.putString(Constants.IS_IBAN_NEEDED, "loadFromServer")
            bundle.putParcelable(Beneficiary::class.java.name, beneficiary)
            launchActivity<EditBeneficiaryActivity>(
                requestCode = RequestCodes.REQUEST_NOTIFY_BENEFICIARY_LIST,
                type = FeatureSet.EDIT_SEND_MONEY_BENEFICIARY
            ) {
                putExtra(Constants.EXTRA, bundle)
            }
        }
    }

    override fun onPause() {
        super.onPause()
        getBindings().rvAllBeneficiaries.removeOnItemTouchListener(onTouchListener)

    }

    override fun onResume() {
        super.onResume()
        getBindings().rvAllBeneficiaries.addOnItemTouchListener(onTouchListener)
    }

    override fun removeObservers() {
        viewModel.clickEvent.removeObservers(this)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        removeObservers()
    }

    private fun getBindings(): FragmentSearchBeneficiaryBinding {
        return viewDataBinding as FragmentSearchBeneficiaryBinding
    }

}