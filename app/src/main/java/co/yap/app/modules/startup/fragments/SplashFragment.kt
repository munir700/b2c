package co.yap.app.modules.startup.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import co.yap.app.R
import co.yap.app.modules.startup.interfaces.ISplash
import co.yap.app.modules.startup.viewmodels.SplashViewModel
import co.yap.modules.kyc.activities.DocumentsDashboardActivity
import co.yap.modules.kyc.activities.MapsActivity
import co.yap.modules.kyc.fragments.LocationSelectionActivity
import co.yap.yapcore.BaseFragment
import co.yap.yapcore.helpers.SharedPreferenceManager

class SplashFragment : BaseFragment<ISplash.ViewModel>(),
    ISplash.View {
    override val viewModel: ISplash.ViewModel
        get() = ViewModelProviders.of(this).get(SplashViewModel::class.java)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.activity_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        startActivity(
            activity?.let { it1 ->
                MapsActivity.newIntent(
                    it1
                )
            }
        )
//        Intent(context, LocationSelectionActivity::class.java)


//        startActivity(
//            activity?.let { it1 ->
//                LocationSelectionActivity.newIntent(
//                    it1
//                )
//            }
//        )

        viewModel.splashComplete.observe(this, Observer {


            val sharedPreferenceManager = SharedPreferenceManager(requireContext())
//            if (sharedPreferenceManager.getValueBoolien(SharedPreferenceManager.KEY_IS_USER_LOGGED_IN, false)) {
//                findNavController().navigate(
//                    R.id.action_splashFragment_to_verifyPasscodeActivity,
//                    Bundle().apply { putString(getString(R.string.arg_username), "") })
//            } else {
//                if (sharedPreferenceManager.getValueBoolien(SharedPreferenceManager.KEY_IS_FIRST_TIME_USER, true)) {
//                    sharedPreferenceManager.save(SharedPreferenceManager.KEY_IS_FIRST_TIME_USER, false)
//                    findNavController().navigate(R.id.action_splashFragment_to_accountSelectionFragment)
//                } else {
//                    findNavController().navigate(R.id.action_splashFragment_to_loginFragment)
//                }
//
//            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.splashComplete.removeObservers(this)
    }
}