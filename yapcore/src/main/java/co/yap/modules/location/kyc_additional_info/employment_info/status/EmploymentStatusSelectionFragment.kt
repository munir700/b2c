package co.yap.modules.location.kyc_additional_info.employment_info.status

import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import co.yap.modules.location.fragments.LocationChildFragment
import co.yap.translation.Strings
import co.yap.yapcore.BR
import co.yap.yapcore.R
import co.yap.yapcore.databinding.FragmentEmploymentStatusSelectionBinding
import co.yap.yapcore.enums.EmploymentStatus
import co.yap.yapcore.helpers.ButtonType
import co.yap.yapcore.helpers.ExtraKeys
import co.yap.yapcore.helpers.extentions.showInfoDialog

class EmploymentStatusSelectionFragment :
    LocationChildFragment<FragmentEmploymentStatusSelectionBinding , IEmploymentStatusSelection.ViewModel>(),
    IEmploymentStatusSelection.View {
    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_employment_status_selection
    override val viewModel: EmploymentStatusSelectionViewModel
        get() = ViewModelProvider(this).get(EmploymentStatusSelectionViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(viewModel.parentViewModel?.amendmentMap?.isNullOrEmpty()?.not()==true){
            viewModel.getAmendmentsEmploymentInfo()
        }
//        if (viewModel.canSkipFragment()) {
//            skipEmploymentStatusSelectionFragment()
//        } else {
//            viewModel.getAmendmentsEmploymentInfo()
            setObservers()
//        }
    }

    override fun setObservers() {
        viewModel.clickEvent.observe(this, onClickObserver)
    }

    override fun removeObservers() {
        viewModel.clickEvent.removeObservers(this)
    }

    private fun skipEmploymentStatusSelectionFragment() {
        val navOptions = NavOptions.Builder()
            .setPopUpTo(
                R.id.employmentStatusSelectionFragment,
                true
            ) // starting destination skipped
            .build()

        findNavController().navigate(
            R.id.action_employmentStatusSelectionFragment_to_employmentQuestionnaireFragment,
            null,
            navOptions
        )
    }


    private val onClickObserver = Observer<Int> {
        when (it) {
            R.id.btnNext -> {
                val status =
                    (viewModel.employmentStatusAdapter.getDataForPosition(viewModel.lastItemCheckedPosition)).employmentStatus
                if (status == EmploymentStatus.SELF_EMPLOYED) showAdditionalInfoDialog(status) else navigateToQuestionAir(status)
            }
        }
    }

    private fun navigateToQuestionAir(status: EmploymentStatus) {
        navigate(
            R.id.action_employmentStatusSelectionFragment_to_employmentQuestionnaireFragment
            ,
            args = bundleOf(ExtraKeys.EMPLOYMENT_STATUS.name to status)
        )
    }

    override fun onBackPressed(): Boolean = false

    override fun onDestroy() {
        super.onDestroy()
        removeObservers()
    }

    private fun showAdditionalInfoDialog(status: EmploymentStatus) {
        requireContext().showInfoDialog(
            getString(Strings.screen_employee_information_additional_information_dialog_title),
            getString(Strings.screen_employee_information_additional_information_dialog_text),
            arrayListOf(ButtonType.CONTINUE, ButtonType.BACK)
        ) {
            when (it.id) {
                R.id.btnNext -> {
                    navigateToQuestionAir(status)
                }
            }
        }
    }
}
