package co.yap.modules.kyc.viewmodels

import android.app.Application
import android.view.View
import android.widget.CheckedTextView
import co.yap.modules.kyc.adapters.EmpInfoSelectionAdapter
import co.yap.modules.kyc.interfaces.IEmpInfoSelection
import co.yap.modules.kyc.models.EmpInfoStatusModel
import co.yap.modules.kyc.states.EmploymentInformationSelectionState
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.interfaces.OnItemClickListener

class EmpInfoSelectionViewModel(application: Application) :
    KYCChildViewModel<IEmpInfoSelection.State>(application),
    IEmpInfoSelection.ViewModel {
    override val state: EmploymentInformationSelectionState = EmploymentInformationSelectionState()
    override var clickEvent: SingleClickEvent = SingleClickEvent()
    override var empInfoSelectionAdapter: EmpInfoSelectionAdapter =
        EmpInfoSelectionAdapter(
            context,
            mutableListOf()
        )
    override var lastItemCheckedPosition = -1;
    override var empInfoStatusList: MutableList<EmpInfoStatusModel> = mutableListOf()

    override fun onCreate() {
        super.onCreate()
        empInfoStatusList = getEmploymentStatusList()
        empInfoSelectionAdapter.setList(empInfoStatusList)
        state.enableNextButton.set(false)
        empInfoSelectionAdapter.onItemClickListener = onItemClickListener
    }

    override fun handleOnPressNext(id: Int) {
        clickEvent.setValue(id)
    }

    private fun getEmploymentStatusList(): MutableList<EmpInfoStatusModel> {
        val employmentStatus = mutableListOf<EmpInfoStatusModel>()
        employmentStatus.add(EmpInfoStatusModel("Employed", false))
        employmentStatus.add(EmpInfoStatusModel("Self-Employed", false))
        employmentStatus.add(EmpInfoStatusModel("Salaried & Self-Employed", false))
        employmentStatus.add(EmpInfoStatusModel("Other", false))
        return employmentStatus
    }

    val onItemClickListener = object : OnItemClickListener {
        override fun onItemClick(view: View, data: Any, pos: Int) {
            if ((data is EmpInfoStatusModel) && (view is CheckedTextView)) {

                if (data.isSelected == false && lastItemCheckedPosition == -1) {
                    data.isSelected = true
                    lastItemCheckedPosition = pos
                    empInfoSelectionAdapter.notifyItemChanged(lastItemCheckedPosition)
                    state.enableNextButton.set(true)
                } else if (data.isSelected == false && lastItemCheckedPosition != pos) {
                    data.isSelected = true
                    empInfoStatusList.get(lastItemCheckedPosition).isSelected = false
                    empInfoSelectionAdapter.notifyItemChanged(pos)
                    empInfoSelectionAdapter.notifyItemChanged(lastItemCheckedPosition)
                    lastItemCheckedPosition = pos
                } else {
                    data.isSelected = false
                    empInfoSelectionAdapter.notifyItemChanged(pos)
                    lastItemCheckedPosition = -1;
                    state.enableNextButton.set(false)
                }
            }
        }
    }
}
