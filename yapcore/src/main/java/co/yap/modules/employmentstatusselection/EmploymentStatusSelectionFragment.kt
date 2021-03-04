package co.yap.modules.employmentstatusselection

import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.yapcore.BR
import co.yap.yapcore.BaseBindingFragment
import co.yap.yapcore.R

class EmploymentStatusSelectionFragment :
    BaseBindingFragment<IEmploymentStatusSelection.ViewModel>(),
    IEmploymentStatusSelection.View {
    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_employment_status_selection
    override fun setObservers() {
        viewModel.clickEvent.observe(this, onClickObserver)
    }

    override fun removeObservers() {
        viewModel.clickEvent.removeObservers(this)
    }

    override val viewModel: EmploymentStatusSelectionViewModel
        get() = ViewModelProviders.of(this).get(EmploymentStatusSelectionViewModel::class.java)

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
