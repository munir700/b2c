package co.yap.modules.onboarding.fragments

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.graphics.Rect
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.text.toSpannable
import androidx.core.view.children
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import co.yap.BR
import co.yap.R
import co.yap.modules.kyc.activities.DocumentsDashboardActivityArgs
import co.yap.modules.onboarding.activities.OnboardingActivity
import co.yap.modules.onboarding.interfaces.ICongratulations
import co.yap.modules.onboarding.viewmodels.CongratulationsViewModel
import co.yap.translation.Strings
import co.yap.widgets.AnimatingProgressBar
import co.yap.yapcore.helpers.AnimationUtils
import co.yap.yapcore.helpers.Utils
import kotlinx.android.synthetic.main.fragment_onboarding_congratulations.*


class CongratulationsFragment : OnboardingChildFragment<ICongratulations.ViewModel>() {

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_onboarding_congratulations

    override val viewModel: ICongratulations.ViewModel
        get() = ViewModelProviders.of(this).get(CongratulationsViewModel::class.java)

    private val windowSize: Rect = Rect() // to hold the size of the visible window

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnCompleteVerification.setOnClickListener {
            val action = CongratulationsFragmentDirections.actionCongratulationsFragmentToDocumentsDashboardActivity(
                viewModel.state.nameList[0] ?: ""
            )
            findNavController().navigate(action)
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

        val counter = counterAnimation(1, viewModel.elapsedOnboardingTime.toInt(), tvSubTitle)

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

        val animationStack: ArrayList<Animator> = arrayListOf()
        animationStack.add(moveToCenter)
        animationStack.add(appearance)
        if (viewModel.elapsedOnboardingTime <= 60) animationStack.add(counter)
        animationStack.add(moveFromCenterToTop.apply { startDelay = 300 })
        val array = arrayOfNulls<Animator>(animationStack.size)
        animationStack.toArray(array)
        return AnimationUtils.runSequentially(*array.requireNoNulls())
    }

    private fun counterAnimation(initialValue: Int, finalValue: Int, textview: TextView): ValueAnimator {
        val text = getString(Strings.screen_onboarding_congratulations_display_text_sub_title)
        val parts = text.split("%1s")

        return AnimationUtils.valueCounter(initialValue, finalValue, 1500).apply {
            addUpdateListener { animator ->
                textview.text = SpannableStringBuilder().run {
                    append(parts[0])
                    val counterText = animator.animatedValue.toString() + parts[1]
                    append(counterText.toSpannable().apply {
                        setSpan(
                            ForegroundColorSpan(Utils.getColor(requireContext(), R.color.colorPrimaryDark)),
                            0, counterText.length,
                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                        )
                    })
                }.toSpannable()
            }
        }
    }

    override fun onBackPressed(): Boolean = run { (activity as? OnboardingActivity)?.finish().let { true } }
}