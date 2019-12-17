package co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.fragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import co.yap.BR
import co.yap.R
import co.yap.modules.dashboard.yapit.sendmoney.adapters.CountryAdapter
import co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.interfaces.ISelectCountry
import co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.viewmodels.SelectCountryViewModel
import co.yap.modules.dashboard.yapit.sendmoney.fragments.SendMoneyBaseFragment
import co.yap.yapcore.interfaces.OnItemClickListener
import kotlinx.android.synthetic.main.fragment_select_country.*


class SelectCountryFragment : SendMoneyBaseFragment<ISelectCountry.ViewModel>(),
    ISelectCountry.View {

    private var countryAdapter: CountryAdapter? = null

    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.fragment_select_country

    override val viewModel: ISelectCountry.ViewModel
        get() = ViewModelProviders.of(this).get(SelectCountryViewModel::class.java)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.populateSpinnerData.observe(this, Observer {
            countriesSpinner.adapter = getCountryAdapter()
            getCountryAdapter()?.setItemListener(listener)
        })
    }

    val listener = object : OnItemClickListener {
        override fun onItemClick(view: View, data: Any, pos: Int) {
            countriesSpinner.setSelection(pos.toInt())
            viewModel.onCountrySelected(pos)
        }
    }

    override fun onPause() {
        viewModel.clickEvent.removeObservers(this)
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
        viewModel.clickEvent.observe(this, Observer {
            when (it) {
                R.id.nextButton -> {
                    viewModel.state.selectedCountry?.let { it ->
                        if (viewModel.state.isDomestic.get() == true) {
                            findNavController().navigate(R.id.action_selectCountryFragment_to_DomesticFragment)
                        } else {
                            it.cashPickUp?.let { cashPickup ->
                                if (cashPickup) {
                                    moveToTransferType()
                                } else {
                                    moveToAddBeneficiary()
                                }
                            }
                        }
                    }
                }

                R.id.viewTriggerSpinnerClick -> {
                    countriesSpinner.performClick()
                }
            }
        })
    }

    private fun moveToAddBeneficiary(){
        findNavController().navigate(R.id.action_selectCountryFragment_to_addBeneficiaryFragment)
    }

    private fun moveToTransferType(){
        findNavController().navigate(R.id.action_selectCountryFragment_to_transferTypeFragment)
    }

    private fun getCountryAdapter(): CountryAdapter? {

        if (countryAdapter == null)
            countryAdapter =
                context?.let {
                    CountryAdapter(
                        it, R.layout.item_country,
                        viewModel.countries
                    )
                }

        return countryAdapter
    }

    override fun onBackPressed(): Boolean {

        return super.onBackPressed()
    }
}