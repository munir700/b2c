package co.yap.app.modules.startup.fragments

import android.animation.Animator
import android.animation.AnimatorSet
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import co.yap.app.BR
import co.yap.app.BuildConfig
import co.yap.app.R
import co.yap.app.databinding.FragmentSplashBinding
import co.yap.app.main.MainChildFragment
import co.yap.app.modules.startup.interfaces.ISplash
import co.yap.app.modules.startup.viewmodels.SplashViewModel
import co.yap.modules.onboarding.models.CountryCode
import co.yap.modules.onboarding.models.LoadConfig
import co.yap.yapcore.animations.animators.ScaleAnimator
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.constants.Constants.KEY_COUNTRY_CODE
import co.yap.yapcore.constants.Constants.KEY_IMAGE_LOADING_TIME
import co.yap.yapcore.constants.Constants.KEY_IS_FIRST_TIME_USER
import co.yap.yapcore.constants.Constants.KEY_MOBILE_NO
import co.yap.yapcore.helpers.SharedPreferenceManager
import co.yap.yapcore.helpers.Utils
import co.yap.yapcore.helpers.alert
import co.yap.yapcore.helpers.countryCodeForRegion
import co.yap.yapcore.helpers.extentions.openPlayStore
import com.yap.ghana.ui.auth.main.GhAuthenticationActivity
import com.yap.yappakistan.ui.auth.main.AuthenticationActivity
import kotlinx.coroutines.delay

class SplashFragment : MainChildFragment<FragmentSplashBinding, ISplash.ViewModel>(), ISplash.View {
    private var animatorSet: AnimatorSet? = null

    override fun getBindingVariable() = BR.viewModel
    override fun getLayoutId() = R.layout.fragment_splash

    private lateinit var pkIntent: Intent
    private lateinit var ghIntent: Intent

    override val viewModel: SplashViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        animatorSet = AnimatorSet()
        viewModel.state.downTime.observe(viewLifecycleOwner, Observer { downTime ->
            requireActivity().alert(
                message = downTime.downTimeMessage ?: "",
                positiveButton = if (downTime.isDown == true) getString(android.R.string.ok) else getString(
                    R.string.common_display_text_retry
                ),
                cancelable = false
            ) {
                if (downTime.isDown == true)
                    requireActivity().finish()
                else viewModel.loadCookies()
            }
        })
        viewModel.splashComplete.observe(this, Observer {
            if (it) viewModel.getAppConfigurations()
        })
        SharedPreferenceManager.getInstance(requireContext()).save(
            KEY_IMAGE_LOADING_TIME,
            System.currentTimeMillis().toString()
        )
        viewModel.appUpdate?.observe(this, {
            if (it != null && it.androidForceUpdate && Utils.checkForUpdate(
                    BuildConfig.VERSION_NAME,
                    it.androidAppVersionNumber
                )
            ) {
                requireContext().alert(
                    getString(R.string.screen_splash_display_text_force_update),
                    getString(R.string.screen_splash_button_force_update),
                    getString(R.string.screen_splash_button_force_update),
                    false
                ) {
                    requireContext().openPlayStore()
                    requireActivity().finish()
                }
            } else {
                playAnimationAndMoveNext()
            }
        })
    }

    private fun playAnimationAndMoveNext() {
        initPkGhana()
        val scaleLogo =
            ScaleAnimator(
                1.0f,
                150.0f,
                AccelerateDecelerateInterpolator()
            ).with(viewDataBinding.ivLogo, 1500)
        val scaleDot =
            ScaleAnimator(
                1.0f,
                150.0f,
                AccelerateDecelerateInterpolator()
            ).with(viewDataBinding.ivDot, 1500)
        scaleDot.startDelay = 400

        animatorSet?.play(scaleLogo)?.with(scaleDot)
        animatorSet?.interpolator = AccelerateDecelerateInterpolator()
        animatorSet?.start()

        animatorSet?.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {}
            override fun onAnimationEnd(animation: Animator?) {
                moveNext()
            }

            override fun onAnimationCancel(animation: Animator?) {}
            override fun onAnimationStart(animation: Animator?) {}

        })
    }

    private fun moveNext() {
        if (SharedPreferenceManager.getInstance(requireContext()).getValueBoolien(
                KEY_IS_FIRST_TIME_USER,
                true
            )
        ) {
            SharedPreferenceManager.getInstance(requireContext()).save(
                KEY_IS_FIRST_TIME_USER,
                false
            )
            findNavController().navigate(R.id.action_splashFragment_to_accountSelectionFragment)
        } else {
            val sharedPreferenceManager = SharedPreferenceManager.getInstance(requireContext())
            if (sharedPreferenceManager.getValueBoolien(
                    Constants.KEY_IS_USER_LOGGED_IN,
                    false
                )
            ) {
                val savedCountryCode = sharedPreferenceManager.getValueString(KEY_COUNTRY_CODE)
                val countryCode = savedCountryCode?.run {
                    viewModel.countriesList.find {
                        it.isoCountryCode2Digit?.countryCodeForRegion() == this
                    }?.isoCountryCode2Digit?.countryCodeForRegion()
                }

                countryCode?.let {
                    if (it != CountryCode.UAE.countryCode) {
                        launchPkGhana(
                            it
                        )
                    } else {
                        //sharedPreferenceManager.save(Constants.KEY_IS_USER_LOGGED_IN, false)
                    }
                } ?: run {
                    // sharedPreferenceManager.save(Constants.KEY_IS_USER_LOGGED_IN, false)
                    sharedPreferenceManager.save(KEY_MOBILE_NO, "")
                    sharedPreferenceManager.save(KEY_COUNTRY_CODE, null)
                }
            }
            launch {
                delay(10)
                findNavController().navigate(R.id.action_splashFragment_to_loginFragment)
            }
        }
    }


    private fun initPkGhana() {
        val sharedPreferenceManager = SharedPreferenceManager.getInstance(requireContext())
        if (sharedPreferenceManager.getValueBoolien(
                Constants.KEY_IS_USER_LOGGED_IN,
                false
            )
        ) {
            sharedPreferenceManager.getValueString(
                KEY_COUNTRY_CODE, CountryCode.UAE.countryCode ?: ""
            )?.let {
                if (it != CountryCode.UAE.countryCode) {
                    val mobileNo = sharedPreferenceManager.getValueString(Constants.KEY_MOBILE_NO)
                        ?.replace(" ", "")

                    when (it) {
                        CountryCode.PAK.countryCode -> {
                            LoadConfig(requireContext()).initYapRegion(it)
                            pkIntent = Intent(requireContext(), AuthenticationActivity::class.java)
                            pkIntent.putExtra("countryCode", it)
                            pkIntent.putExtra("mobileNo", mobileNo ?: "")
                            pkIntent.putExtra("isAccountBlocked", false)
                        }
                        CountryCode.GHANA.countryCode -> {
                            LoadConfig(requireContext()).initYapRegion(it)
                            ghIntent =
                                Intent(requireContext(), GhAuthenticationActivity::class.java)
                            ghIntent.putExtra("countryCode", it)
                            ghIntent.putExtra("mobileNo", mobileNo ?: "")
                            ghIntent.putExtra("isAccountBlocked", false)
                        }
                    }
                }
            }
        }
    }

    private fun launchPkGhana(countryCode: String) {
        when (countryCode) {
            CountryCode.GHANA.countryCode -> {
                startActivity(ghIntent)
            }
            CountryCode.PAK.countryCode -> {
                startActivity(pkIntent)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        animatorSet?.resume()
    }

    override fun onPause() {
        super.onPause()
        animatorSet?.pause()
    }

    override fun onDestroyView() {
        animatorSet?.cancel()
        animatorSet = null
        viewModel.splashComplete.removeObservers(this)
        super.onDestroyView()
    }
}