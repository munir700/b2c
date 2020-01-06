package co.yap.household.onboarding.onboarding.fragments

import android.graphics.Rect
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import co.yap.household.BR
import co.yap.household.R
import co.yap.household.onboarding.fragments.OnboardingChildFragment
import co.yap.household.onboarding.onboarding.interfaces.IEmail
import co.yap.household.onboarding.onboarding.viewmodels.DialerViewModel
import co.yap.household.onboarding.onboarding.viewmodels.EmailHouseHoldViewModel
import kotlinx.android.synthetic.main.dialer_layout.*
import kotlinx.android.synthetic.main.passcode.*

class DialerFragment  : OnboardingChildFragment<IEmail.ViewModel>() {

    private val windowSize: Rect = Rect() // to hold the size of the visible window

    override fun getBindingVariable(): Int = BR.dialerViewModel

    override fun getLayoutId(): Int = R.layout.dialer_layout

    override val viewModel: IEmail.ViewModel
        get() = ViewModelProviders.of(this).get(DialerViewModel::class.java)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        val display = activity!!.windowManager.defaultDisplay
//        display.getRectSize(windowSize)

        viewModel.nextButtonPressEvent.observe(this, nextButtonObserver)
        viewModel.animationStartEvent.observe(this, Observer { startAnimation() })

        containerDialer.setOnClickListener(View.OnClickListener {
            findNavController().navigate(R.id.action_dialerFragment_to_passCodeFragment)


        })
    }

    override fun onDestroyView() {
        viewModel.nextButtonPressEvent.removeObservers(this)
        viewModel.animationStartEvent.removeObservers(this)
        super.onDestroyView()
    }

    private val nextButtonObserver = Observer<Int> {
        when (it) {
//            viewModel.EVENT_NAVIGATE_NEXT -> navigate(R.id.congratulationsFragment)
            viewModel.EVENT_POST_VERIFICATION_EMAIL -> viewModel.sendVerificationEmail()
//            viewModel.EVENT_POST_DEMOGRAPHIC -> viewModel.postDemographicData()
        }

    }

    private fun startAnimation() {
        viewModel.stopTimer()
//        Handler(Looper.getMainLooper()).postDelayed({
//            toolbarAnimation().apply {
//                addListener(onEnd = {
//                })
//            }.start()
//        }, 500)
    }

//    private fun toolbarAnimation(): AnimatorSet {
//        val checkButton = (activity as OnboardingActivity).findViewById<ImageView>(R.id.tbBtnCheck)
//        val backButton = (activity as OnboardingActivity).findViewById<ImageView>(R.id.tbBtnBack)
//        val progressbar =
//            (activity as OnboardingActivity).findViewById<AnimatingProgressBar>(R.id.tbProgressBar)
//
//        val checkBtnEndPosition = (windowSize.width() / 2) - (checkButton.width / 2)
//
//        checkButton.isEnabled = true
//        return AnimationUtils.runSequentially(
//            AnimationUtils.pulse(checkButton),
//            AnimationUtils.runTogether(
//                AnimationUtils.fadeOut(backButton, 200),
//                AnimationUtils.fadeOut(progressbar, 200)
//            ),
//            AnimationUtils.slideHorizontal(
//                view = checkButton,
//                from = checkButton.x,
//                to = checkBtnEndPosition.toFloat(),
//                duration = 500
//            )
//        )
//    }

    override fun onBackPressed(): Boolean = viewModel.state.verificationCompleted
}

