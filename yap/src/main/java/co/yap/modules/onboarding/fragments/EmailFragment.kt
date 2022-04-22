package co.yap.modules.onboarding.fragments

import android.animation.AnimatorSet
import android.graphics.Rect
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ImageView
import androidx.core.animation.addListener
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import co.yap.BR
import co.yap.R
import co.yap.databinding.FragmentEmailBinding
import co.yap.modules.onboarding.activities.OnboardingActivity
import co.yap.modules.onboarding.enums.OnboardingPhase
import co.yap.modules.onboarding.interfaces.IEmail
import co.yap.modules.onboarding.viewmodels.EmailViewModel
import co.yap.widgets.AnimatingProgressBar
import co.yap.yapcore.firebase.FirebaseEvent
import co.yap.yapcore.firebase.trackEventWithScreenName
import co.yap.yapcore.helpers.AnimationUtils
import co.yap.yapcore.helpers.ExtraKeys


class EmailFragment : OnboardingChildFragment<FragmentEmailBinding, IEmail.ViewModel>() {

    private val windowSize: Rect = Rect() // to hold the size of the visible window

    override fun getBindingVariable(): Int = BR.emailViewModel

    override fun getLayoutId(): Int = R.layout.fragment_email

    override val viewModel: EmailViewModel by viewModels()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val display = activity?.windowManager?.defaultDisplay
        display?.getRectSize(windowSize)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addObservers()
    }

    private fun addObservers() {
        viewModel.nextButtonPressEvent.observe(this, nextButtonObserver)
        viewModel.animationStartEvent.observe(this, Observer { startAnimation() })
        viewModel.parentViewModel?.state?.notificationAction?.value?.let {
            viewModel.nextButtonPressEvent.postValue(it.id)
        }
        if (viewModel.parentViewModel?.emailError?.value.isNullOrBlank()
                .not()
        ) viewModel.state.emailError = viewModel.parentViewModel?.emailError?.value ?: ""
    }

    override fun onDestroyView() {
        viewModel.nextButtonPressEvent.removeObservers(this)
        viewModel.animationStartEvent.removeObservers(this)
        super.onDestroyView()
    }

    private val nextButtonObserver = Observer<Int> {
        when (it) {
            OnboardingPhase.NOTIFICATION_KFS_FLOW.id -> navigate(R.id.action_emailFragment_to_kfsNotificationFragment)
            OnboardingPhase.NOTIFICATION_SELECTED.id -> if (viewModel.isAnyNotificationSelected() || viewModel.parentViewModel?.state?.noNotificationAccepted?.value == true) viewModel.setVerificationLabel()
            OnboardingPhase.EVENT_NAVIGATE_NEXT.id -> {
                trackEventWithScreenName(FirebaseEvent.SIGNUP_EMAIL_SUCCESS)
                val bundle = bundleOf(ExtraKeys.IS_WAITING.name to viewModel.state.isWaiting)
                navigate(
                    destinationId = R.id.congratulationsFragment,
                    args = bundle
                )
            }
            OnboardingPhase.EVENT_POST_VERIFICATION_EMAIL.id -> viewModel.sendVerificationEmail()
            OnboardingPhase.EVENT_POST_DEMOGRAPHIC.id -> viewModel.postDemographicData()

        }
    }

    private fun startAnimation() {
        viewModel.stopTimer()
        Handler(Looper.getMainLooper()).postDelayed({
            toolbarAnimation().apply {
                addListener(onEnd = {
                })
            }.start()
        }, 500)
    }

    private fun toolbarAnimation(): AnimatorSet {
        val checkButton = (activity as OnboardingActivity).findViewById<ImageView>(R.id.tbBtnCheck)
        val backButton = (activity as OnboardingActivity).findViewById<ImageView>(R.id.tbBtnBack)
        val progressbar =
            (activity as OnboardingActivity).findViewById<AnimatingProgressBar>(R.id.tbProgressBar)

        val checkBtnEndPosition = (windowSize.width() / 2) - (checkButton.width / 2)

        checkButton.isEnabled = true
        return AnimationUtils.runSequentially(
            AnimationUtils.pulse(checkButton),
            AnimationUtils.runTogether(
                AnimationUtils.fadeOut(backButton, 200),
                AnimationUtils.fadeOut(progressbar, 200)
            ),
            AnimationUtils.slideHorizontal(
                view = checkButton,
                from = checkButton.x,
                to = checkBtnEndPosition.toFloat(),
                duration = 500
            )
        )
    }

    override fun onBackPressed(): Boolean = viewModel.state.verificationCompleted


}