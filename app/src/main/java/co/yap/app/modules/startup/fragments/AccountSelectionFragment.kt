package co.yap.app.modules.startup.fragments

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import co.yap.app.BR
import co.yap.app.R
import co.yap.app.modules.startup.interfaces.IAccountSelection
import co.yap.app.modules.startup.viewmodels.AccountSelectionViewModel
import co.yap.modules.onboarding.enums.AccountType
import co.yap.yapcore.BaseBindingFragment
import co.yap.yapcore.helpers.extentions.trackEvent
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import kotlinx.android.synthetic.main.fragment_account_selection.*


class AccountSelectionFragment : BaseBindingFragment<IAccountSelection.ViewModel>(),
    IAccountSelection.View {

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_account_selection
    var totalDuration: Long = 0
    var currentDuration: Int = 0
    var stopPosition: Int = 0
    var captionsIndex: Int = 0
    var isPaused = false
    var isVideoFinished = false
    var mediaPlayer: MediaPlayer? = null
    var timer: CountDownTimer? = null
    private var animatorSet: AnimatorSet? = null

    private var captions = listOf(
        "Bank your way", "Get an account in seconds", "Money transfers made simple",
        "Track your spending", "Split bills effortlessly", "Spend locally wherever you go",
        "Instant spending notifications", "An app for everyone", ""

    )
    private var captionDelays = listOf(2000, 1000, 2000, 2000, 3000, 2000, 3000, 3000, 1000)
    override val viewModel: IAccountSelection.ViewModel
        get() = ViewModelProviders.of(this).get(AccountSelectionViewModel::class.java)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val uri: Uri =
            Uri.parse("android.resource://" + requireActivity().packageName + "/" + R.raw.yap_demo_intro)

        videoView?.setVideoURI(uri)
        captionsIndex = 0
        layoutButtons.postDelayed({
            YoYo.with(Techniques.FadeIn).duration(1500)
                .onStart { layoutButtons.visibility = View.VISIBLE }.playOn(layoutButtons)
        }, 1000)
        videoView?.setOnPreparedListener { mp ->
            if (stopPosition == 0 && !isVideoFinished) {

                layoutButtons.postDelayed({ playCaptionAnimation() }, 1000)
            }
            mediaPlayer = mp
            totalDuration = mediaPlayer?.duration?.toLong()!!
            if (!isVideoFinished)
                videoView?.start()


            //playCaptionAnimation()
            initVideoViewProgress(totalDuration)
        }
        videoView?.setOnCompletionListener {
            stopPosition = 0
            isVideoFinished = true
            timer?.cancel()
        }


    }

    private fun initVideoViewProgress(duration: Long) {
        timer = object : CountDownTimer(duration, 100) {
            override fun onFinish() {
                stopPosition = 0
            }

            override fun onTick(millisUntilFinished: Long) {
                //currentDuration = videoView?.currentPosition!! / 1000

            }
        }
        timer?.start()
    }

//    private fun playCaptionAnimation() {
//        if (!isPaused) {
//            tvCaption.text = captions[captionsIndex]
//            val tech = if(captions[captionsIndex].isEmpty()) Techniques.FadeOut else Techniques.FadeIn
//            YoYo.with(tech).duration(captionDelays[captionsIndex].toLong())
//                .onEnd {
//                    captionsIndex += 1
//                    if (captionsIndex < captions.size)
//                        playCaptionAnimation()
//                    //toast("Animation END>> $captionsIndex")
//                }.playOn(tvCaption)
//        }
//
//
//
//    }

    fun playCaptionAnimation() {
        if (!isPaused && captionsIndex != -1) {
            tvCaption?.text = captions[captionsIndex]
            val fadeIn = ObjectAnimator.ofFloat(
                tvCaption,
                View.ALPHA,
                0f, 1f
            )
            fadeIn.interpolator = DecelerateInterpolator() //add this
            fadeIn.duration = 500

            val fadeOut = ObjectAnimator.ofFloat(
                tvCaption,
                View.ALPHA,
                1f, 0f
            )
            fadeOut.interpolator = AccelerateInterpolator() //add this
            fadeOut.duration = 500
            fadeOut.startDelay = captionDelays[captionsIndex].toLong()
            animatorSet = AnimatorSet()
            animatorSet?.interpolator = AccelerateDecelerateInterpolator()
            animatorSet?.playSequentially(fadeIn, fadeOut)
            animatorSet?.addListener(object : Animator.AnimatorListener {
                override fun onAnimationRepeat(animation: Animator?) {
                }

                override fun onAnimationEnd(animation: Animator?) {
                    captionsIndex += 1
                    if (captionsIndex < captions.size)
                        playCaptionAnimation()
                }

                override fun onAnimationCancel(animation: Animator?) {
                }

                override fun onAnimationStart(animation: Animator?) {
                    tvCaption.visibility = View.VISIBLE
                }
            })
            animatorSet?.start()
        }
    }

    override fun onResume() {
        super.onResume()
        if (stopPosition > 0 && !isVideoFinished) {
            videoView.seekTo(stopPosition)
            videoView?.resume()
            initVideoViewProgress(totalDuration - stopPosition)
        }
        isPaused = false
        viewModel.clickEvent.observe(this, Observer {
            when (it) {
                R.id.tvSignIn -> {
                    findNavController().navigate(R.id.action_accountSelectionFragment_to_loginFragment)
                }
                R.id.btnPersonal -> {
                    trackEvent("Get started")
                    findNavController().navigate(
                        R.id.action_accountSelectionFragment_to_welcomeFragment,
                        Bundle().apply {
                            putSerializable(
                                getString(R.string.arg_account_type),
                                AccountType.B2C_ACCOUNT
                            )
                        })
                }
            }
        })
    }

    override fun onPause() {
        super.onPause()
        isPaused = true
        if (!isVideoFinished) {
            stopPosition = videoView.currentPosition //stopPosition is an int
            videoView?.pause()
        }
        timer?.cancel()
        viewModel.clickEvent.removeObservers(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        animatorSet?.cancel()
        animatorSet = null
        stopPosition = 0
        captionsIndex = -1
        isPaused = false
        isVideoFinished = false
    }
}


