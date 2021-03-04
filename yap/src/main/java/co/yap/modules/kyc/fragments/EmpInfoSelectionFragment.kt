package co.yap.modules.kyc.fragments

import android.os.Bundle
import android.view.View
import android.widget.CheckedTextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.BR
import co.yap.R
import co.yap.modules.kyc.interfaces.IEmpInfoSelection
import co.yap.modules.kyc.models.EmpInfoStatusModel
import co.yap.modules.kyc.viewmodels.EmpInfoSelectionViewModel
import co.yap.yapcore.interfaces.OnItemClickListener

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


    var lastItemCheckedPosition = -1;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.empInfoSelectionAdapter.onItemClickListener = onItemClickListener
        setObservers()
    }

    val onItemClickListener = object : OnItemClickListener {
        override fun onItemClick(view: View, data: Any, pos: Int) {
            if ((data is EmpInfoStatusModel) && (view is CheckedTextView)) {

                if (data.isSelected == false && lastItemCheckedPosition == -1) {
                    data.isSelected = true
                    lastItemCheckedPosition = pos
                    viewModel.empInfoSelectionAdapter.notifyItemChanged(lastItemCheckedPosition)
                    viewModel.state.enableNextButton.set(true)
                } else if (data.isSelected == false && lastItemCheckedPosition != pos) {
                    data.isSelected = true
                    viewModel.empInfoStatusList.get(lastItemCheckedPosition).isSelected = false
                    viewModel.empInfoSelectionAdapter.notifyItemChanged(pos)
                    viewModel.empInfoSelectionAdapter.notifyItemChanged(lastItemCheckedPosition)
                    lastItemCheckedPosition = pos
                } else {
                    data.isSelected = false
                    viewModel.empInfoSelectionAdapter.notifyItemChanged(pos)
                    lastItemCheckedPosition = -1;
                    viewModel.state.enableNextButton.set(false)
                }
            }
        }
    }
    private val onClickObserver = Observer<Int> {
        when (it) {
            R.id.btnNext -> {
                Toast.makeText(
                    requireContext(),
                    "Last Item Clicked is $lastItemCheckedPosition", Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        removeObservers()
    }
}
