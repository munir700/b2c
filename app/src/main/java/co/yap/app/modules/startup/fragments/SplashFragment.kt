package co.yap.app.modules.startup.fragments

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
import co.yap.modules.others.helper.Constants.BUILD_TYPE
import co.yap.modules.others.helper.Constants.FLAVOR
import co.yap.modules.others.helper.Constants.VERSION_CODE
import co.yap.modules.others.helper.Constants.VERSION_NAME
import co.yap.yapcore.BaseFragment
import co.yap.yapcore.helpers.SharedPreferenceManager
import kotlinx.android.synthetic.main.fragment_splash.*
import java.lang.Exception

class SplashFragment : BaseFragment<ISplash.ViewModel>(), ISplash.View {

    override val viewModel: ISplash.ViewModel
        get() = ViewModelProviders.of(this).get(SplashViewModel::class.java)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.splashComplete.observe(this, Observer {
            val sharedPreferenceManager = SharedPreferenceManager(requireContext())
            if (sharedPreferenceManager.getValueBoolien(
                    SharedPreferenceManager.KEY_IS_USER_LOGGED_IN,
                    false
                )
            ) {
                val action =
                    SplashFragmentDirections.actionSplashFragmentToVerifyPasscodeFragment("")
                findNavController().navigate(action)
            } else {
                if (sharedPreferenceManager.getValueBoolien(
                        SharedPreferenceManager.KEY_IS_FIRST_TIME_USER,
                        true
                    )
                ) {
                    sharedPreferenceManager.save(
                        SharedPreferenceManager.KEY_IS_FIRST_TIME_USER,
                        false
                    )
                    findNavController().navigate(R.id.action_splashFragment_to_accountSelectionFragment)
                } else {
                    findNavController().navigate(R.id.action_splashFragment_to_loginFragment)
                }
            }
        })
        setAppVersion()
    }

    private fun setAppVersion() {
        try {
            VERSION_NAME = BuildConfig.VERSION_NAME
            VERSION_CODE = BuildConfig.VERSION_CODE
            FLAVOR = BuildConfig.FLAVOR
            BUILD_TYPE = BuildConfig.BUILD_TYPE

        } catch (e: Exception) {

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.splashComplete.removeObservers(this)
    }
}