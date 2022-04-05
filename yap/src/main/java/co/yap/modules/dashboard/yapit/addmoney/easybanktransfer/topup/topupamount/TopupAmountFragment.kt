package co.yap.modules.dashboard.yapit.addmoney.easybanktransfer.topup.topupamount

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import co.yap.BR
import co.yap.R
import co.yap.modules.dashboard.yapit.addmoney.main.AddMoneyBaseFragment

//adjust resize need to be added when required activity is created
class TopupAmountFragment : AddMoneyBaseFragment<ITopupAmount.ViewModel>(),
    ITopupAmount.View {
    override val viewModel: TopupAmountViewModel by viewModels()
    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.fragment_topup_amount

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewDataBinding.lifecycleOwner = this
        setObservers()
        viewModel.setAvailableBalance()
    }

    override fun setObservers() {
        observeClickEvent()
        observeEnteredAmount()
    }

    private fun observeEnteredAmount() {
        viewModel.state.enteredTopUpAmount.observe(viewLifecycleOwner, Observer {

        })
    }

    private fun observeClickEvent(){
        viewModel.clickEvent.observe(this, Observer {id->
            when(id){
                R.id.tvDominationFirstAmount->{
                    viewModel.denominationAmountValidator(viewModel.state.denominationFirstAmount)
                }

                R.id.tvDominationSecondAmount->{
                    viewModel.denominationAmountValidator(viewModel.state.denominationSecondAmount)
                }

                R.id.tvDominationThirdAmount->{
                    viewModel.denominationAmountValidator(viewModel.state.denominationThirdAmount)
                }
            }
        })
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
}