package co.yap.household.onboarding.onboardsuccess

import android.animation.Animator
import android.animation.AnimatorSet
import android.graphics.Rect
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AccelerateInterpolator
import androidx.core.view.children
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import co.yap.household.BR
import co.yap.household.R
import co.yap.household.databinding.FragmentHhonboardingSuccessBinding
import co.yap.yapcore.AdjustEvents.Companion.trackAdjustPlatformEvent
import co.yap.yapcore.adjust.AdjustEvents
import co.yap.yapcore.dagger.base.navigation.BaseNavViewModelFragment
import co.yap.yapcore.helpers.AnimationUtils
import co.yap.yapcore.hilt.base.navigation.BaseNavViewModelFragmentV2
import co.yap.yapcore.leanplum.HHUserOnboardingEvents
import co.yap.yapcore.leanplum.trackEvent
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HHOnBoardingSuccessFragment :
    BaseNavViewModelFragmentV2<FragmentHhonboardingSuccessBinding, IHHOnBoardingSuccess.State, HHOnBoardingSuccessVM>() {
    private val windowSize: Rect = Rect()
    override fun getBindingVariable() = BR.viewModel
    override fun getLayoutId() = R.layout.fragment_hhonboarding_success
    override fun setDisplayHomeAsUpEnabled() = false
    override val viewModel: HHOnBoardingSuccessVM by viewModels()

    override fun postExecutePendingBindings(savedInstanceState: Bundle?) {
        super.postExecutePendingBindings(savedInstanceState)
        setBackButtonDispatcher()
        val display = activity?.windowManager?.defaultDisplay
        display?.getRectSize(windowSize)
        viewDataBinding.rootContainer.children.forEach { it.alpha = 0f }
        Handler(Looper.getMainLooper()).postDelayed({ runAnimations() }, 500)
        trackAdjustPlatformEvent(AdjustEvents.ONBOARDING_NEW_HH_USER_SIGNUP.type)
        trackEvent(HHUserOnboardingEvents.ONBOARDING_NEW_HH_USER_SIGN_UP.type)
    }

    override fun onClick(id: Int) {
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
                AnimationUtils.jumpInAnimation(viewDataBinding.ivCard),
                AnimationUtils.jumpInAnimation(viewDataBinding.btnCompleteVerification).apply { startDelay = 300 }
            )
        ).start()
    }

    private fun titleAnimation(): AnimatorSet {
        val titleOriginalPosition = viewDataBinding.tvTitle.y
        val subTitleOriginalPosition = viewDataBinding.tvSubTitle.y
        val titleMidScreenPosition = (windowSize.height() / 2 - (viewDataBinding.tvTitle.height)).toFloat()
        val subTitleMidScreenPosition = (windowSize.height() / 2 + 40).toFloat()

        // move to center position instantly without animation
        val moveToCenter = AnimationUtils.runTogether(
            AnimationUtils.slideVertical(viewDataBinding.tvTitle, 0, titleOriginalPosition, titleMidScreenPosition),
            AnimationUtils.slideVertical(
                viewDataBinding.tvSubTitle,
                0,
                subTitleOriginalPosition,
                subTitleMidScreenPosition
            )
        )

        // appear with alpha and scale animation
        val appearance = AnimationUtils.runTogether(
            AnimationUtils.outOfTheBoxAnimation(viewDataBinding.tvTitle),
            AnimationUtils.outOfTheBoxAnimation(viewDataBinding.tvSubTitle).apply { startDelay = 100 }
        )

        val moveFromCenterToTop = AnimationUtils.runTogether(
            AnimationUtils.slideVertical(
                view = viewDataBinding.tvTitle,
                from = titleMidScreenPosition,
                to = titleOriginalPosition,
                interpolator = AccelerateInterpolator()
            ),
            AnimationUtils.slideVertical(
                view = viewDataBinding.tvSubTitle,
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
}
