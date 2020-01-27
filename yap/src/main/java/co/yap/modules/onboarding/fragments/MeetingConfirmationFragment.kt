package co.yap.modules.onboarding.fragments


import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.BR
import co.yap.R
import co.yap.modules.dashboard.main.activities.YapDashboardActivity
import co.yap.modules.kyc.activities.DocumentsDashboardActivity
import co.yap.modules.onboarding.interfaces.IMeetingConfirmation
import co.yap.modules.onboarding.viewmodels.MeetingConfirmationViewModel
import co.yap.modules.others.fragmentpresenter.activities.FragmentPresenterActivity
import co.yap.yapcore.BaseBindingFragment
import co.yap.yapcore.interfaces.OnBackPressedListener

class MeetingConfirmationFragment : BaseBindingFragment<IMeetingConfirmation.viewModel>() {

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_meeting_confirmation

    override val viewModel: IMeetingConfirmation.viewModel
        get() = ViewModelProviders.of(this).get(MeetingConfirmationViewModel::class.java)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.goToDashboardButtonPressEvent.observe(this, Observer {
            if (activity is DocumentsDashboardActivity)
                (activity as DocumentsDashboardActivity).goToDashBoard(
                    success = true,
                    skippedPress = false
                )
            else if (activity is FragmentPresenterActivity) {
                startActivity(Intent(requireContext(),YapDashboardActivity::class.java))
                activity?.finish()

            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.goToDashboardButtonPressEvent.removeObservers(this)
    }

    override fun onBackPressed(): Boolean {
        if (activity is FragmentPresenterActivity) {
            return true
        }
        return false
    }

}