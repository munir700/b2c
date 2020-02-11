package co.yap.modules.onboarding.fragments

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.app.Activity
import android.content.Intent
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
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.animation.addListener
import androidx.core.text.toSpannable
import androidx.core.view.children
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import co.yap.BR
import co.yap.R
import co.yap.modules.kyc.activities.DocumentsDashboardActivity
import co.yap.modules.onboarding.interfaces.ICongratulations
import co.yap.modules.onboarding.viewmodels.CongratulationsViewModel
import co.yap.translation.Strings
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.constants.RequestCodes
import co.yap.yapcore.helpers.AnimationUtils
import co.yap.yapcore.helpers.Utils
import co.yap.yapcore.helpers.extentions.ExtraType
import co.yap.yapcore.helpers.extentions.getValue
import co.yap.yapcore.helpers.extentions.launchActivity
import kotlinx.android.synthetic.main.fragment_onboarding_congratulations.*


class CongratulationsFragment : OnboardingChildFragment<ICongratulations.ViewModel>(),
    ICongratulations.View {

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_onboarding_congratulations

    override val viewModel: ICongratulations.ViewModel
        get() = ViewModelProviders.of(this).get(CongratulationsViewModel::class.java)

    private val windowSize: Rect = Rect() // to hold the size of the visible window

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


    override fun setObservers() {
        viewModel.clickEvent.observe(this, Observer {
            when (it) {
                R.id.btnCompleteVerification -> {

                    launchActivity<DocumentsDashboardActivity>(requestCode = RequestCodes.REQUEST_KYC_DOCUMENTS){
                        putExtra(Constants.name, viewModel.state.nameList[0] ?: "")
                        putExtra(Constants.data, false)
                    }

//                    startActivityForResult(
//                        DocumentsDashboardActivity.getIntent(
//                            requireContext(),
//                            viewModel.state.nameList[0] ?: "",
//                            false
//                        ), RequestCodes.REQUEST_KYC_DOCUMENTS
//                    )
                }
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == RequestCodes.REQUEST_KYC_DOCUMENTS) {
                data?.let {
                    val success =
                        data.getValue(
                            Constants.result,
                            ExtraType.BOOLEAN.name
                        ) as? Boolean
                    val skipped =
                        data.getValue(
                            Constants.skipped,
                            ExtraType.BOOLEAN.name
                        ) as? Boolean

                    success?.let {
                        if (it) {
                            val action =
                                CongratulationsFragmentDirections.actionCongratulationsFragmentToYapDashboardActivity()
                            findNavController().navigate(action)
                            activity?.finishAffinity()
                        } else {
                            skipped?.let { skip ->
                                if (skip) {
                                    val action =
                                        CongratulationsFragmentDirections.actionCongratulationsFragmentToYapDashboardActivity()
                                    findNavController().navigate(action)
                                    activity?.finishAffinity()
                                } else {
                                    val action =
                                        CongratulationsFragmentDirections.actionCongratulationsFragmentToYapDashboardActivity()
                                    findNavController().navigate(action)
                                    activity?.finishAffinity()
                                }
                            }
                        }
                    }
                }
            }
        }
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
        ).apply {
            addListener(onEnd = {
                setObservers()
            })
        }.start()
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

    private fun counterAnimation(
        initialValue: Int,
        finalValue: Int,
        textview: TextView
    ): ValueAnimator {
        val text = getString(Strings.screen_onboarding_congratulations_display_text_sub_title)
        val parts = text.split("%1s")

        return AnimationUtils.valueCounter(initialValue, finalValue, 1500).apply {
            addUpdateListener { animator ->
                textview.text = SpannableStringBuilder().run {
                    append(parts[0])
                    val counterText = animator.animatedValue.toString() + parts[1]
                    append(counterText.toSpannable().apply {
                        setSpan(
                            ForegroundColorSpan(
                                Utils.getColor(
                                    requireContext(),
                                    R.color.colorPrimaryDark
                                )
                            ),
                            0, counterText.length,
                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                        )
                    })
                }.toSpannable()
            }
        }
    }

//    override fun onBackPressed(): Boolean =
//        run { (activity as? OnboardingActivity)?.finish().let { true } }

    override fun onBackPressed(): Boolean {
        return true
    }
}