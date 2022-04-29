package co.yap.modules.dashboard.yapit.addmoney.easybanktransfer.topup.topupamountscreen

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import co.yap.BR
import co.yap.R
import co.yap.databinding.FragmentTopupAmountBinding
import co.yap.modules.dashboard.yapit.addmoney.main.AddMoneyBaseFragment
import co.yap.networking.leanteach.responsedtos.accountlistmodel.LeanCustomerAccounts
import co.yap.networking.leanteach.responsedtos.banklistmodels.BankListMainModel
import co.yap.translation.Strings
import co.yap.yapcore.helpers.cancelAllSnackBar
import co.yap.yapcore.helpers.extentions.generateChipViews
import co.yap.yapcore.helpers.extentions.getValueWithoutComa
import co.yap.yapcore.helpers.extentions.parseToDouble
import co.yap.yapcore.helpers.extentions.toFormattedCurrency
import co.yap.yapcore.helpers.showTextUpdatedAbleSnackBar
import co.yap.yapcore.managers.SessionManager
import com.google.android.material.chip.Chip
import com.google.android.material.snackbar.Snackbar
import com.uxcam.UXCam
import me.leantech.link.android.Lean

//adjust resize need to be added when required activity is created
class TopupAmountFragment :
    AddMoneyBaseFragment<FragmentTopupAmountBinding, ITopupAmount.ViewModel>(),
    ITopupAmount.View {
    override val viewModel: TopupAmountViewModel by viewModels()
    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.fragment_topup_amount

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getDataArguments()
        lifecycle.addObserver(viewModel.leanSdkInitializer)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewDataBinding.lifecycleOwner = this
        generateChipViews(viewModel.state.denominationChipList.value!!)
        setObservers()
        viewModel.getLimitOfAmount()
    }

    override fun setObservers() {
        observeClickEvent()
        observeValues()
        setDenominationsChipListener()
        balanceObserver()
        leanPaymentSuccessObserver()
    }

    private fun getDataArguments() {
        arguments?.let { bundle ->
            bundle.getString(co.yap.yapcore.constants.Constants.CUSTOMER_ID_LEAN)?.let {
                viewModel.customerId = it
                viewModel.getPaymentIntentModel.customerId = it
                viewModel.getPaymentIntentModel.currency = SessionManager.getDefaultCurrency()
            }
            bundle.getString(co.yap.yapcore.constants.Constants.DESTINATION_ID_LEAN)?.let {
                viewModel.getPaymentIntentModel.paymentDestinationId = it
            }
            bundle.getParcelable<LeanCustomerAccounts>(co.yap.yapcore.constants.Constants.MODEL_LEAN)
                ?.let {
                    viewModel.leanCustomerAccounts = it
                }
            bundle.getParcelable<BankListMainModel>(co.yap.yapcore.constants.Constants.MODEL_BANK_LEAN)
                ?.let {
                    viewModel.bankListMainModel = it
                }
        }
    }

    private fun setDenominationsChipListener() {
        for (index in 0 until viewDataBinding.cgDenominations.childCount) {
            val chip: Chip = viewDataBinding.cgDenominations.getChildAt(index) as Chip
            chip.setOnCheckedChangeListener { view, isChecked ->
                viewModel.denominationAmountValidator(view.text.toString())
            }
        }
    }

    private fun balanceObserver() {
        SessionManager.cardBalance.observe(viewLifecycleOwner) { value ->
            viewModel.setAvailableBalance(value.availableBalance.toString())
        }
    }

    private fun observeValues() {
        viewModel.state.enteredTopUpAmount.observe(viewLifecycleOwner) { topUpAmount ->
            cancelAllSnackBar()
            if (topUpAmount.isNotBlank() && topUpAmount.parseToDouble() > 0.0)
                viewModel.getPaymentIntentModel.amount =
                    topUpAmount.getValueWithoutComa().toDouble()
        }
        viewModel.paymentIntentId.observe(viewLifecycleOwner) {
            if (it.isNullOrEmpty().not())
                viewModel.startTopUpJourney(it, requireActivity())
        }
    }

    private fun leanPaymentSuccessObserver() {
        viewModel.leanPaymentStatus.observe(viewLifecycleOwner) {
            if (it)
                openPaymentSuccessScreen()
        }
    }

    private fun openPaymentSuccessScreen() {
        navigate(R.id.action_topUpAmountFragment_to_paymentSuccessfulFragment)
    }

    private fun observeClickEvent() {
        viewModel.clickEvent.observe(this) { id ->
            when (id) {
                R.id.btnAction -> {
                    if (viewModel.isMaxMinLimitReached()) showUpperLowerLimitError()
                    else viewModel.getPaymentIntentId()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        removeObservers()
    }

    override fun removeObservers() {
        viewModel.clickEvent.removeObservers(this)
        viewModel.state.enteredTopUpAmount.removeObservers(this)
    }

    override fun onToolBarClick(id: Int) {
        when (id) {
            R.id.ivLeftIcon -> {
                activity?.finish()
            }
        }
    }

    private fun generateChipViews(chipsTextList: List<String>) {
        viewDataBinding.cgDenominations.generateChipViews(
            R.layout.item_denominations_chip,
            chipsTextList
        )
    }

    fun showUpperLowerLimitError() {
        showTextUpdatedAbleSnackBar(
            getString(
                Strings.screen_lean_topup_text_min_max_error,
                viewModel.getLimitOfAmount()?.min.toString().toFormattedCurrency(),
                viewModel.getLimitOfAmount()?.max.toString().toFormattedCurrency()
            ),
            Snackbar.LENGTH_INDEFINITE
        )
    }
}