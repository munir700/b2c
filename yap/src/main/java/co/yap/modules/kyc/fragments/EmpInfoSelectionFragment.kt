package co.yap.modules.kyc.fragments

import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.BR
import co.yap.R
import co.yap.modules.kyc.interfaces.IEmpInfoSelection
import co.yap.modules.kyc.viewmodels.EmpInfoSelectionViewModel

class EmpInfoSelectionFragment :
    KYCChildFragment<IEmpInfoSelection.ViewModel>(),
    IEmpInfoSelection.View {
    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_employment_information_selection
    override fun setObservers() {
        viewModel.clickEvent.observe(this, onClickObserver)
    }

    override fun removeObservers() {
        viewModel.clickEvent.removeObservers(this)
    }

    override val viewModel: EmpInfoSelectionViewModel
        get() = ViewModelProviders.of(this).get(EmpInfoSelectionViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setObservers()
    }


    private val onClickObserver = Observer<Int> {
        when (it) {
            R.id.btnNext -> {
                Toast.makeText(
                    requireContext(),
                    "Last Item Clicked is ${viewModel.lastItemCheckedPosition}", Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        removeObservers()
    }
}
