package co.yap.household.onboarding.onboarding.fragments

import android.graphics.Rect
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.annotation.RequiresApi
import androidx.core.animation.addListener
import androidx.core.view.children
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.household.BR
import co.yap.household.R
import co.yap.household.onboarding.fragments.OnboardingChildFragment
import co.yap.household.onboarding.onboarding.interfaces.INewUserSuccess
import co.yap.household.onboarding.onboarding.viewmodels.NewUserSuccessViewModel
import co.yap.yapcore.helpers.AnimationUtils
import kotlinx.android.synthetic.main.fragment_new_user_success.*

class NewUserSuccessFragment :
    OnboardingChildFragment<INewUserSuccess.ViewModel>() {

    private val windowSize: Rect = Rect() // to hold the size of the visible window

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_new_user_success

    override val viewModel: INewUserSuccess.ViewModel
        get() = ViewModelProviders.of(this).get(NewUserSuccessViewModel::class.java)


    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
//        AnimationUtils.runSequentially(
////            titleAnimation(),
//            // Card Animation
//            AnimationUtils.outOfTheBoxAnimation(ivCard),
//            // Bottom views animation
//            AnimationUtils.runTogether(
//                AnimationUtils.jumpInAnimation(tvIbanTitle),
//                AnimationUtils.jumpInAnimation(tvIban).apply { startDelay = 100 },
//                AnimationUtils.jumpInAnimation(tvMeetingNotes).apply { startDelay = 200 },
//                AnimationUtils.jumpInAnimation(btnCompleteVerification).apply { startDelay = 300 }
//            )
//        ).apply {
//            addListener (onEnd = {
//                setObservers()
//            })
//        }.start()
    }
    override fun onDestroyView() {
//        viewModel.nextButtonPressEvent.removeObservers(this)
//        viewModel.animationStartEvent.removeObservers(this)
        super.onDestroyView()
    }

    private val nextButtonObserver = Observer<Int> {
        when (it) {
//            viewModel.EVENT_NAVIGATE_NEXT -> navigate(R.id.congratulationsFragment)
//            viewModel.EVENT_POST_VERIFICATION_EMAIL -> viewModel.sendVerificationEmail()
//            viewModel.EVENT_POST_DEMOGRAPHIC -> viewModel.postDemographicData()
        }

    }

    private fun startAnimation() {
//        viewModel.stopTimer()
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

    override fun onBackPressed(): Boolean = true
}

