package co.yap.app.modules.startup.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import co.yap.app.BuildConfig
import co.yap.app.R
import co.yap.app.modules.startup.interfaces.ISplash
import co.yap.app.modules.startup.viewmodels.SplashViewModel
import co.yap.yapcore.BaseFragment
import co.yap.yapcore.constants.Constants.KEY_IS_FIRST_TIME_USER
import co.yap.yapcore.helpers.SharedPreferenceManager
import co.yap.yapcore.helpers.Utils
import co.yap.yapcore.helpers.alert
import co.yap.yapcore.helpers.extentions.openPlayStore

class SplashFragment : BaseFragment<ISplash.ViewModel>(), ISplash.View {

    var appUpdate: Boolean = false

    override val viewModel: ISplash.ViewModel
        get() = ViewModelProviders.of(this).get(SplashViewModel::class.java)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_splash, container, false)

    }

    override fun performDataBinding(savedInstanceState: Bundle?) {

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.splashComplete.observe(this, Observer {
            if (it) {
                if (BuildConfig.DEBUG) {
                    moveNext()
                } else {
                    viewModel.getAppUpdate()
                }
            }
        })

        viewModel.appUpdate.observe(this, Observer {
            if (it != null && it.androidForceUpdate && Utils.checkForUpdate(
                    BuildConfig.VERSION_NAME,
                    it.androidAppVersionNumber
                )
            ) {
                activity?.alert(
                    getString(R.string.screen_splash_display_text_force_update),
                    getString(R.string.screen_splash_button_force_update),
                    getString(R.string.screen_splash_button_force_update),
                    false
                ) {
                    appUpdate = true
                    requireContext().openPlayStore()
                    activity?.finish()
                }
            } else {
                moveNext()
            }
        })
    }

    private fun moveNext() {
        val sharedPreferenceManager = SharedPreferenceManager(requireContext())
        if (sharedPreferenceManager.getValueBoolien(
                KEY_IS_FIRST_TIME_USER,
                true
            )
        ) {
            sharedPreferenceManager.save(
                KEY_IS_FIRST_TIME_USER,
                false
            )
            findNavController().navigate(R.id.action_splashFragment_to_accountSelectionFragment)
        } else {
            findNavController().navigate(R.id.action_splashFragment_to_loginFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.splashComplete.removeObservers(this)
    }
}