package co.yap.modules.onboarding.fragments

import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.graphics.Rect
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.widget.ImageView
import android.widget.TextView
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
import ru.bullyboo.text_animation.TextCounter
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min
import kotlin.math.roundToLong


class CongratulationsFragment : OnboardingChildFragment<ICongratulations.ViewModel>() {

    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.fragment_onboarding_congratulations

    override val viewModel: ICongratulations.ViewModel
        get() = ViewModelProviders.of(this).get(CongratulationsViewModel::class.java)

    private val windowSize: Rect = Rect() // to hold the size of the visible window


    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.state.ibanNumber = "AE07 0331 2345 6789 01** ***"

        btnCompleteVerification.setOnClickListener {
            navigate(R.id.action_congratulationsFragment_to_liteDashboardActivity)
        }

        val display = activity!!.windowManager.defaultDisplay
        display.getRectSize(windowSize)

        // hide all in the beginning
        rootContainer.children.forEach { it.alpha = 0f }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Handler(Looper.getMainLooper()).postDelayed({ runAnimations() }, 500)
    }

    private fun runAnimations() {
        AnimationUtils.runSequentially(
            toolbarAnimation(),
            titleAnimation(),
            // Card Animation
            AnimationUtils.outOfTheBoxAnimation(ivCard),
            // Bottom views animation
            AnimationUtils.runTogether(
                AnimationUtils.jumpInAnimation(tvIbanTitle),
                AnimationUtils.jumpInAnimation(tvIban).apply { startDelay = 100 },
                AnimationUtils.jumpInAnimation(tvMeetingNotes).apply { startDelay = 200 },
                AnimationUtils.jumpInAnimation(btnCompleteVerification).apply { startDelay = 300 }
            )
        ).start()
    }

    private fun titleAnimation(): AnimatorSet {
        val titleOriginalPosition = tvTitle.y
        val subTitleOriginalPosition = tvSubTitle.y
        val titleMidScreenPosition = (windowSize.height() / 2 - (tvTitle.height)).toFloat()
        val subTitleMidScreenPosition = (windowSize.height() / 2 + 40).toFloat()


        // move to center position instantly without animation
        val moveToCenter = AnimationUtils.runTogether(
            AnimationUtils.slideVertical(tvTitle, 0, titleOriginalPosition, titleMidScreenPosition),
            AnimationUtils.slideVertical(tvSubTitle, 0, subTitleOriginalPosition, subTitleMidScreenPosition)
        )

        // appear with alpha and scale animation
        val appearance = AnimationUtils.runTogether(
            AnimationUtils.outOfTheBoxAnimation(tvTitle),
            AnimationUtils.outOfTheBoxAnimation(tvSubTitle).apply { startDelay = 100 }
        )

        val counter = handleTextViewWithAnimatedValue(100, 30, tvSubTitle)

        val moveFromCenterToTop = AnimationUtils.runTogether(
            AnimationUtils.slideVertical(
                view = tvTitle,
                from = titleMidScreenPosition,
                to = titleOriginalPosition,
                interpolator = AccelerateInterpolator()
            ),
            AnimationUtils.slideVertical(
                view = tvSubTitle,
                from = subTitleMidScreenPosition,
                to = subTitleOriginalPosition,
                interpolator = AccelerateInterpolator()
            ).apply { startDelay = 50 }
        )

        return AnimationUtils.runSequentially(moveToCenter, appearance, counter, moveFromCenterToTop)
    }

    private fun toolbarAnimation(): AnimatorSet {
        val checkButton = (activity as OnboardingActivity).findViewById<ImageView>(R.id.tbBtnCheck)
        val backButton = (activity as OnboardingActivity).findViewById<ImageView>(R.id.tbBtnBack)
        val progressbar = (activity as OnboardingActivity).findViewById<AnimatingProgressBar>(R.id.tbProgressBar)

        val checkBtnEndPosition = (windowSize.width() / 2) - (checkButton.width / 2)

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

    private fun handleTextViewWithAnimatedValue(initialValue: Int, finalValue: Int, textview: TextView): ValueAnimator =
        ValueAnimator.ofInt(initialValue, finalValue).apply {
            duration = 1500
            addUpdateListener { animator -> textview.text = animator.animatedValue.toString() }
        }


    private fun handleTextView(initialValue: Int, finalValue: Int, targetTextview: TextView) {
        val decelerateInterpolator = DecelerateInterpolator(1f)
        val newInitialValue = min(initialValue, finalValue)
        val newFinalValue = max(initialValue, finalValue)
        val difference = abs(finalValue - initialValue)
        val handler = Handler()
        for (count in newInitialValue..newFinalValue) {
            //Time to display the current value to the user.
            val time =
                (decelerateInterpolator.getInterpolation(count.toFloat() / difference) * 100).roundToLong() * count
            val finalCount = if (initialValue > finalValue) initialValue - count else count
            handler.postDelayed({ targetTextview.text = finalCount.toString() }, time.toLong())
        }
    }

    private fun handleTextCounter(view: TextView) {
        TextCounter.newBuilder()
            .setTextView(view)
            .setType(TextCounter.LONG)
            // .setCustomAnimation(modeBuilder)
            .setMode(TextCounter.ACCELERATION_DECELERATION_MODE)
            .from(100L)
            .to(1000L)
            .build()
            .start()
    }


}