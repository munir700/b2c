package co.yap.household.onboarding.onboardsuccess

import android.animation.Animator
import android.animation.AnimatorSet
import android.graphics.Rect
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AccelerateInterpolator
import androidx.core.view.children
import androidx.lifecycle.Observer
import co.yap.household.BR
import co.yap.household.R
import co.yap.household.databinding.FragmentHhonboardingSuccessBinding
import co.yap.yapcore.AdjustEvents.Companion.trackAdjustPlatformEvent
import co.yap.yapcore.adjust.AdjustEvents
import co.yap.yapcore.dagger.base.navigation.BaseNavViewModelFragment
import co.yap.yapcore.helpers.AnimationUtils
import co.yap.yapcore.leanplum.HHUserOnboardingEvents
import co.yap.yapcore.leanplum.trackEvent
import kotlinx.android.synthetic.main.fragment_hhonboarding_success.*

class HHOnBoardingSuccessFragment :
    BaseNavViewModelFragment<FragmentHhonboardingSuccessBinding, IHHOnBoardingSuccess.State, HHOnBoardingSuccessVM>() {
    private val windowSize: Rect = Rect()
    override fun getBindingVariable() = BR.viewModel
    override fun getLayoutId() = R.layout.fragment_hhonboarding_success
    override fun setDisplayHomeAsUpEnabled() = false
    override fun postExecutePendingBindings(savedInstanceState: Bundle?) {
        super.postExecutePendingBindings(savedInstanceState)
        setBackButtonDispatcher()
        val display = activity?.windowManager?.defaultDisplay
        display?.getRectSize(windowSize)
        rootContainer.children.forEach { it.alpha = 0f }
        viewModel.clickEvent.observe(this, Observer { onClick(it) })
        Handler(Looper.getMainLooper()).postDelayed({ runAnimations() }, 500)
        trackAdjustPlatformEvent(AdjustEvents.ONBOARDING_NEW_HH_USER_SIGNUP.type)
        trackEvent(HHUserOnboardingEvents.ONBOARDING_NEW_HH_USER_SIGN_UP.type)
    }

    private fun onClick(id: Int) {
        when (id) {
            R.id.btnCompleteVerification ->
                navigateForwardWithAnimation(
                    HHOnBoardingSuccessFragmentDirections.toHHOnBoardingCardSelectionFragment(),
                    arguments,
                    null
                )
        }
    }


    private fun runAnimations() {
        AnimationUtils.runSequentially(
            titleAnimation(),
            AnimationUtils.runTogether(
                AnimationUtils.jumpInAnimation(ivCard),
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
            AnimationUtils.slideVertical(
                tvSubTitle,
                0,
                subTitleOriginalPosition,
                subTitleMidScreenPosition
            )
        )

        // appear with alpha and scale animation
        val appearance = AnimationUtils.runTogether(
            AnimationUtils.outOfTheBoxAnimation(tvTitle),
            AnimationUtils.outOfTheBoxAnimation(tvSubTitle).apply { startDelay = 100 }
        )

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
        animationStack.add(moveFromCenterToTop.apply { startDelay = 300 })
        val array = arrayOfNulls<Animator>(animationStack.size)
        animationStack.toArray(array)
        return AnimationUtils.runSequentially(*array.requireNoNulls()).apply {
            duration = 500
            startDelay = 1000
        }
    }

    override fun onDestroyView() {
        viewModel.clickEvent.removeObservers(this)
        super.onDestroyView()
    }
}
