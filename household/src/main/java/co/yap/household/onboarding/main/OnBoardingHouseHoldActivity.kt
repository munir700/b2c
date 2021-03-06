package co.yap.household.onboarding.main

import android.animation.AnimatorSet
import android.graphics.Rect
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.viewModels
import androidx.core.animation.addListener
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import co.yap.household.BR
import co.yap.household.R
import co.yap.household.databinding.ActivityOnboardingHoueHoldBinding
import co.yap.yapcore.constants.Constants.INDEX
import co.yap.yapcore.dagger.base.navigation.host.NAVIGATION_Graph_ID
import co.yap.yapcore.dagger.base.navigation.host.NAVIGATION_Graph_START_DESTINATION_ID
import co.yap.yapcore.dagger.base.navigation.host.NavHostPresenterActivity
import co.yap.yapcore.enums.AccountStatus
import co.yap.yapcore.helpers.AnimationUtils
import co.yap.yapcore.helpers.extentions.dimen
import co.yap.yapcore.helpers.extentions.launchActivity
import co.yap.yapcore.hilt.base.navigation.BaseNavViewModelActivityV2
import co.yap.yapcore.managers.SessionManager
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.migration.OptionalInject

@OptionalInject
@AndroidEntryPoint
class OnBoardingHouseHoldActivity :
    BaseNavViewModelActivityV2<ActivityOnboardingHoueHoldBinding, IOnBoardingHouseHold.State, OnBoardingHouseHoldVM>(),
    IOnBoardingHouseHold.View {

    override val viewModel: OnBoardingHouseHoldVM by viewModels()

    override val navigationGraphId: Int
        get() = intent?.getIntExtra(NAVIGATION_Graph_ID, 0) ?: 0
    override val navigationGraphStartDestination: Int
        get() {
            SessionManager.user?.let {
                if (!it.notificationStatuses.isBlank()) {
                    when (AccountStatus.valueOf(it.notificationStatuses)) {
                        AccountStatus.PARNET_MOBILE_VERIFICATION_PENDING -> {
                            extrasBundle.putInt(INDEX, 20)
                            return R.id.HHOnBoardingWelcomeFragment
                        }
                        AccountStatus.EMAIL_PENDING -> {
                            extrasBundle.putInt(INDEX, 80)
                            return R.id.HHOnBoardingEmailFragment
                        }
                        AccountStatus.PASS_CODE_PENDING -> {
                            extrasBundle.putInt(INDEX, 50)
                            return R.id.HHOnBoardingPassCodeFragment
                        }
                        else -> {
                            if (it.fssRequestRefNo.isNullOrEmpty()) {
                                extrasBundle.putInt(INDEX, 100)
                                return R.id.HHOnBoardingSuccessFragment
                            } else {
                                launchActivity<NavHostPresenterActivity> {
                                    putExtra(
                                        NAVIGATION_Graph_ID,
                                        R.navigation.hh_main_nav_graph
                                    )
                                }
                            }
                            finish()
                            return@let
                        }
                    }
                }
            }
            return intent?.getIntExtra(NAVIGATION_Graph_START_DESTINATION_ID, 0) ?: 0
        }

    override fun init(savedInstanceState: Bundle?) {
        if (SessionManager.isExistingUser()) {
            var destination: Int = R.id.HHOnBoardingExistingFragment
            SessionManager.user?.let {
                if (!it.notificationStatuses.isBlank()) {
                    destination = when (AccountStatus.valueOf(it.notificationStatuses)) {
                        AccountStatus.INVITE_PENDING, AccountStatus.INVITATION_PENDING -> R.id.HHOnBoardingExistingFragment
                        AccountStatus.INVITE_ACCEPTED -> R.id.HHOnBoardingExistingSuccessFragment
                        else -> if (it.fssRequestRefNo.isNullOrBlank()) R.id.HHOnBoardingCardSelectionFragment else R.id.HHOnBoardingMobileFragment
                    }
                }
            }
            launchActivity<NavHostPresenterActivity>() {
                putExtra(
                    NAVIGATION_Graph_ID, R.navigation.hh_existing_user_onboarding_navigation
                )
                putExtra(NAVIGATION_Graph_START_DESTINATION_ID, destination)
            }
            finish()
        } else
            super.init(savedInstanceState)
    }

    override fun getLayoutId() = R.layout.activity_onboarding_houe_hold
    override fun getBindingVariable() = BR.viewModel

    override fun onClick(id: Int) {
        when (id) {
            R.id.ivBack -> onBackPressed()
        }
    }

    override fun onVerificationComplete(isCompleted: Boolean) {
        viewDataBinding.tbProgressBar.progress = 100
    }

    override fun onDestinationChanged(
        controller: NavController?,
        destination: NavDestination?,
        arguments: Bundle?
    ) {
        arguments?.let {
            viewModel.state.currentProgress.value = it.getInt(INDEX, 0)
            viewDataBinding.tbProgressBar.post { viewDataBinding.tbProgressBar.progress = viewModel.state.currentProgress.value!! }

        }
        if (destination?.id == R.id.HHOnBoardingSuccessFragment) {
            viewDataBinding.toolbar.setBackgroundColor(getColor(R.color.colorLightPinkBackground))
            Handler(Looper.getMainLooper()).postDelayed({
                toolbarAnimation().apply {
                    addListener(onEnd = {
                    })
                }.start()
            }, 500)
        }
    }


    private fun toolbarAnimation(): AnimatorSet {
        val windowSize = Rect()
        windowManager.defaultDisplay.getRectSize(windowSize)
        val checkBtnEndPosition =
            (windowSize.width() / 2) - (viewDataBinding.tbBtnCheck.width / 2) - dimen(R.dimen._10sdp)
        viewDataBinding.tbBtnCheck.isEnabled = true
        return AnimationUtils.runSequentially(
            AnimationUtils.pulse(viewDataBinding.tbBtnCheck),
            AnimationUtils.runTogether(
                AnimationUtils.fadeOut(viewDataBinding.ivBack, 200),
                AnimationUtils.fadeOut(viewDataBinding.tbProgressBar, 200)
            ),
            AnimationUtils.slideHorizontal(
                view = viewDataBinding.tbBtnCheck,
                from = viewDataBinding.tbBtnCheck.x,
                to = checkBtnEndPosition.toFloat(),
                duration = 500
            )
        )
    }
}