package co.yap.app.modules.startup.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import co.yap.app.R
import co.yap.app.modules.startup.interfaces.ISplash
import co.yap.app.modules.startup.viewmodels.SplashViewModel
import co.yap.yapcore.BaseFragment
import co.yap.yapcore.helpers.SharedPreferenceManager
import java.text.SimpleDateFormat
import java.util.*


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
//
//        val stringVal: String = "2017-07-25"
//
//        val currDate = SimpleDateFormat("yyyy-mm-dd").format(Calendar.getInstance().time)
//        println(currDate)
//
//        var sdf = SimpleDateFormat("yyyy-mm-dd")
//        val date = sdf.parse(stringVal)
//
//        val outputString = sdf.format(date)
//        println(outputString)

//        getExpiryDate()

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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.splashComplete.removeObservers(this)
    }


    fun getExpiryDate() {
//        val expiryDateString: String = "2017-07-25"
        val expiryDateString: String = "2019-05-25"

        val currDate = SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().time)
        val prevDate = SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().time)
//        println(currDate)


        var sdf = SimpleDateFormat("yyyy-MM-dd")
        val expireyDate = sdf.parse(expiryDateString)

        val expiryDateValue = sdf.format(expireyDate)
        println(expiryDateValue)

        val cal = Calendar.getInstance()

        cal.add(Calendar.DAY_OF_YEAR, -1)

        val sdfN = SimpleDateFormat("yyyy-MM-dd")
        val prevDay = sdfN.format(cal.time)
        val previousDayDate = sdfN.parse(prevDay)

        if (expireyDate >= previousDayDate) {
            // hide red icon
            Log.i(
                "checkz",
                "isNotExpired " + previousDayDate + " prevdate , expireyDate " + expireyDate
            )

        } else {
            // show red icon
            Log.i(
                "checkz",
                "hasExpired " + previousDayDate + " prevdate , expireyDate " + expireyDate
            )

        }
    }

}