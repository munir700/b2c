package co.yap.modules.dashboard.yapit.addmoney.easybanktransfer.topup.topupamount

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import co.yap.BR
import co.yap.R
import co.yap.databinding.FragmentTopupAmountBinding
import co.yap.modules.dashboard.yapit.addmoney.main.AddMoneyBaseFragment
import co.yap.yapcore.helpers.extentions.generateChipViews
import com.google.android.material.chip.Chip

//adjust resize need to be added when required activity is created
class TopupAmountFragment : AddMoneyBaseFragment<ITopupAmount.ViewModel>(),
    ITopupAmount.View {
    override val viewModel: TopupAmountViewModel by viewModels()
    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.fragment_topup_amount

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewDataBinding.lifecycleOwner = this
        viewModel.setAvailableBalance()
        generateChipViews(viewModel.state.denominationChipList.value!!)
        setObservers()
    }

    override fun setObservers() {
        observeClickEvent()
        observeEnteredAmount()
        setDenominationsChipListener()
    }

    private fun setDenominationsChipListener() {
        for (index in 0 until getBinding().cgDenominations.childCount) {
            val chip: Chip = getBinding().cgDenominations.getChildAt(index) as Chip
            chip.setOnCheckedChangeListener { view, isChecked ->
                viewModel.denominationAmountValidator(view.text.toString())
            }
        }
    }

    private fun observeEnteredAmount() {
        viewModel.state.enteredTopUpAmount.observe(viewLifecycleOwner) {topUpAmount->
            //deal with topUpAmount
        }
    }

    private fun observeClickEvent(){
        viewModel.clickEvent.observe(this) { id ->
            when (id) {
                R.id.btnAction -> {
                    //navigate screen
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
    
    private fun generateChipViews(selectedList: List<String>) {
        getBinding().cgDenominations.generateChipViews(
            R.layout.item_denominations_chip,
            selectedList
        )
    }

    private fun getBinding() = viewDataBinding as FragmentTopupAmountBinding

}