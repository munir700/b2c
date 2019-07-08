package co.yap.modules.onboarding.fragments

import android.animation.AnimatorSet
import android.graphics.Rect
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.core.view.children
import androidx.lifecycle.ViewModelProviders
import co.yap.BR
import co.yap.R
import co.yap.modules.onboarding.activities.OnboardingActivity
import co.yap.modules.onboarding.interfaces.ICongratulations
import co.yap.modules.onboarding.viewmodels.CongratulationsViewModel
import co.yap.widgets.AnimatingProgressBar
import co.yap.yapcore.helpers.AnimationUtils
import kotlinx.android.synthetic.main.fragment_onboarding_congratulations.*


class CongratulationsFragment : OnboardingChildFragment<ICongratulations.ViewModel>() {

    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.fragment_onboarding_congratulations

    override val viewModel: ICongratulations.ViewModel
        get() = ViewModelProviders.of(this).get(CongratulationsViewModel::class.java)

    private val windowSize: Rect = Rect()


    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.state.ibanNumber = "AE07 0331 2345 6789 01** ***"

        btnCompleteVerification.setOnClickListener {
            navigate(R.id.action_congratulationsFragment_to_liteDashboardActivity)
        }
        hideAll()

        val display = activity!!.windowManager.defaultDisplay
        display.getRectSize(windowSize)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Handler(Looper.getMainLooper()).postDelayed({ runAnimations() }, 500)
    }

    private fun hideAll() {
        rootContainer.children.forEach { it.alpha = 0f }
    }

    private fun runAnimations() {
        AnimationUtils.runSequentially(
            toolbarAnimation(),
            titleAnimation(),
            // Card Animation
            AnimationUtils.enterScaleAnimation(ivCard),
            // Bottom views animation
            AnimationUtils.runTogether(
                AnimationUtils.enterSlideAnimation(tvIbanTitle),
                AnimationUtils.enterSlideAnimation(tvIban).apply { startDelay = 100 },
                AnimationUtils.enterSlideAnimation(tvMeetingNotes).apply { startDelay = 200 },
                AnimationUtils.enterSlideAnimation(btnCompleteVerification).apply { startDelay = 300 }
            )
        ).start()
    }

    private fun titleAnimation(): AnimatorSet = AnimationUtils.runTogether(
        AnimationUtils.enterSlideAnimation(tvTitle, 500, (windowSize.height() / 2 - (tvTitle.height)).toFloat(), tvTitle.y, AccelerateInterpolator()),
        AnimationUtils.enterSlideAnimation(
            tvSubTitle,
            500,
            (windowSize.height() / 2 + (tvSubTitle.height)).toFloat(),
            tvSubTitle.y,
            AccelerateInterpolator()
        ).apply { startDelay = 50 }
    )

    private fun toolbarAnimation(): AnimatorSet {
        val checkButton = (activity as OnboardingActivity).findViewById<ImageView>(R.id.tbBtnCheck)
        val backButton = (activity as OnboardingActivity).findViewById<ImageView>(R.id.tbBtnBack)
        val progressbar = (activity as OnboardingActivity).findViewById<AnimatingProgressBar>(R.id.tbProgressBar)

        val checkBtnEndPosition = (windowSize.width() / 2) - (checkButton.width / 2)

        return AnimationUtils.runSequentially(
            AnimationUtils.bounce(checkButton),
            AnimationUtils.runTogether(
                AnimationUtils.fadeOut(backButton, 200),
                AnimationUtils.fadeOut(progressbar, 200)
            ),
            AnimationUtils.translateX(view = checkButton, from = checkButton.x, to = checkBtnEndPosition.toFloat(), duration = 500)
        )

    }

}