package co.yap.modules.onboarding.fragments


import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import co.yap.BR
import co.yap.R
import co.yap.modules.onboarding.interfaces.IMeetingConfirmation
import co.yap.modules.onboarding.viewmodels.MeetingConfirmationViewModel
import co.yap.yapcore.BaseBindingActivity
import co.yap.yapcore.BaseBindingFragment

class MeetingConfirmationFragment : BaseBindingFragment<IMeetingConfirmation.viewModel>() {

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_meeting_confirmation

    override val viewModel: IMeetingConfirmation.viewModel
        get() = ViewModelProviders.of(this).get(MeetingConfirmationViewModel::class.java)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.goToDashboardButtonPressEvent.observe(this, Observer {
            findNavController().navigate(R.id.action_goto_DashboardActivity)
            activity?.finish()
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.goToDashboardButtonPressEvent.removeObservers(this)
    }
 
    override fun onBackPressed(): Boolean {
        return true
    }

}