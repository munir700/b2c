package co.yap.modules.employmentstatusselection

import android.app.Application
import android.view.View
import android.widget.CheckedTextView
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.interfaces.OnItemClickListener

class EmploymentStatusSelectionViewModel(application: Application) :
    BaseViewModel<IEmploymentStatusSelection.State>(application),
    IEmploymentStatusSelection.ViewModel {
    override val state: EmploymentStatusSelectionState =
        EmploymentStatusSelectionState()
    override var clickEvent: SingleClickEvent = SingleClickEvent()
    override var employmentStatusSelectionAdapter: EmploymentStatusSelectionAdapter =
        EmploymentStatusSelectionAdapter(
            context,
            mutableListOf()
        )
    override var lastItemCheckedPosition = -1;
    override var employmentStatusSelectionList: MutableList<EmploymentStatusSelectionModel> =
        mutableListOf()

    override fun onCreate() {
        super.onCreate()
        employmentStatusSelectionList = getEmploymentStatusList()
        employmentStatusSelectionAdapter.setList(employmentStatusSelectionList)
        state.enableNextButton.set(false)
        employmentStatusSelectionAdapter.onItemClickListener = onItemClickListener
    }

    override fun handleOnPressNext(id: Int) {
        clickEvent.setValue(id)
    }

    private fun getEmploymentStatusList(): MutableList<EmploymentStatusSelectionModel> {
        val employmentStatus = mutableListOf<EmploymentStatusSelectionModel>()
        employmentStatus.add(
            EmploymentStatusSelectionModel(
                "Employed",
                false
            )
        )
        employmentStatus.add(
            EmploymentStatusSelectionModel(
                "Self-Employed",
                false
            )
        )
        employmentStatus.add(
            EmploymentStatusSelectionModel(
                "Salaried & Self-Employed",
                false
            )
        )
        employmentStatus.add(
            EmploymentStatusSelectionModel(
                "Other",
                false
            )
        )
        return employmentStatus
    }

    val onItemClickListener = object : OnItemClickListener {
        override fun onItemClick(view: View, data: Any, pos: Int) {
            if ((data is EmploymentStatusSelectionModel) && (view is CheckedTextView)) {

                if (data.isSelected == false && lastItemCheckedPosition == -1) {
                    data.isSelected = true
                    lastItemCheckedPosition = pos
                    employmentStatusSelectionAdapter.notifyItemChanged(lastItemCheckedPosition)
                    state.enableNextButton.set(true)
                } else if (data.isSelected == false && lastItemCheckedPosition != pos) {
                    data.isSelected = true
                    employmentStatusSelectionList.get(lastItemCheckedPosition).isSelected = false
                    employmentStatusSelectionAdapter.notifyItemChanged(pos)
                    employmentStatusSelectionAdapter.notifyItemChanged(lastItemCheckedPosition)
                    lastItemCheckedPosition = pos
                } else {
                    data.isSelected = false
                    employmentStatusSelectionAdapter.notifyItemChanged(pos)
                    lastItemCheckedPosition = -1;
                    state.enableNextButton.set(false)
                }
            }
        }
    }
}
