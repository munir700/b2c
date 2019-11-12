package co.yap.modules.yapit.sendmoney.addbeneficiary.fragments

import android.os.Bundle
import android.view.View
import android.widget.Spinner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import co.yap.BR
import co.yap.R
import co.yap.modules.yapit.sendmoney.addbeneficiary.interfaces.ISelectCountry
import co.yap.modules.yapit.sendmoney.addbeneficiary.viewmodels.SelectCountryViewModel
import co.yap.modules.yapit.sendmoney.fragments.SendMoneyBaseFragment
import sun.jvm.hotspot.utilities.IntArray
import co.yap.modules.yapit.sendmoney.addbeneficiary.interfaces.ISelectCountry



class SelectCountryFragment : SendMoneyBaseFragment<ISelectCountry.ViewModel>(),
    ISelectCountry.View {

    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.fragment_select_country

    override val viewModel: ISelectCountry.ViewModel
        get() = ViewModelProviders.of(this).get(SelectCountryViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.clickEvent.observe(this, Observer {
            when (it) {
                R.id.nextButton ->
                    findNavController().navigate(R.id.action_selectCountryFragment_to_addBeneficiaryFragment)
            }
        })

    }

    override fun onPause() {
        viewModel.clickEvent.removeObservers(this)
        super.onPause()

    }

    override fun onResume() {
        super.onResume()

//        viewModel.clickEvent.observe(this, Observer {
//            when (it) {
//
//            }
//        })

    }

    override fun onBackPressed(): Boolean {

        return super.onBackPressed()
    }

fun renderSpinner(){


    val myArraySpinner = ArrayList<String>()

    myArraySpinner.add("red")
    myArraySpinner.add("green")
    myArraySpinner.add("blue")

    varSpinnerData = myArraySpinner

    val mySpinner = Spinner(varRoot)

    varSpinner = mySpinner

    val spinnerArrayAdapter =
        ArrayAdapter<String>(varRoot, android.R.layout.simple_spinner_item, myArraySpinner)
    spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item) // The drop down vieww
    mySpinner.setAdapter(spinnerArrayAdapter)
}
}