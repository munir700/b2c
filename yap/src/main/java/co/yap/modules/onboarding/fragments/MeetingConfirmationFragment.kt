package co.yap.modules.onboarding.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.BR
import co.yap.R
import co.yap.modules.onboarding.interfaces.IMeetingConfirmation
import co.yap.modules.onboarding.viewmodels.MeetingConfirmationViewModel
import co.yap.yapcore.BaseBindingActivity

class MeetingConfirmationActivity : BaseBindingActivity<IMeetingConfirmation.viewModel>() {

    companion object {
        fun newIntent(context: Context): Intent = Intent(context, MeetingConfirmationActivity::class.java)
    }

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_meeting_confirmation

    override val viewModel: IMeetingConfirmation.viewModel
        get() = ViewModelProviders.of(this).get(MeetingConfirmationViewModel::class.java)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.goToDashboardButtonPressEvent.observe(this, gotoDashboardPressEvent)
    }

    private val gotoDashboardPressEvent = Observer<Boolean> {
    }

}