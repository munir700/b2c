package co.yap.modules.kyc.viewmodels

import android.app.Application
import co.yap.modules.kyc.adapters.EmpInfoSelectionAdapter
import co.yap.modules.kyc.interfaces.IEmpInfoSelection
import co.yap.modules.kyc.models.EmpInfoStatusModel
import co.yap.modules.kyc.states.EmploymentInformationSelectionState
import co.yap.yapcore.SingleClickEvent

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
    override var empInfoStatusList: MutableList<EmpInfoStatusModel> = mutableListOf()

    override fun onCreate() {
        super.onCreate()
        empInfoStatusList = getEmploymentStatusList()
        empInfoSelectionAdapter.setList(empInfoStatusList)
        state.enableNextButton.set(false)
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
}
