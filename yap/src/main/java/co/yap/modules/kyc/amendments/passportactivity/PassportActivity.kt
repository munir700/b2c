package co.yap.modules.kyc.amendments.passportactivity

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import co.yap.R
import co.yap.databinding.ActivityPassportBinding
import co.yap.yapcore.BR
import co.yap.yapcore.BaseBindingActivity
import co.yap.yapcore.IFragmentHolder
import co.yap.yapcore.defaults.DefaultNavigator
import co.yap.yapcore.defaults.INavigator
import co.yap.yapcore.interfaces.IBaseNavigator

class PassportActivity : BaseBindingActivity<ActivityPassportBinding,IPassport.ViewModel>(), IPassport.View,
    INavigator, IFragmentHolder {
    override val viewModel: IPassport.ViewModel
        get() = ViewModelProvider(this).get(PassportVM::class.java)
    override val navigator: IBaseNavigator
        get() = DefaultNavigator(
            this@PassportActivity,
            R.id.passport_amendment_navigation
        )

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.activity_passport
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        navController.setGraph(
            R.navigation.passport_amendment_navigation,
            intent.extras
        )
        setObservers()
    }

    private val onDestinationChangedListener =
        NavController.OnDestinationChangedListener { _, destination, argument ->
            getDataBindingView<ActivityPassportBinding>().toolBar.visibility =
                if (destination.id == R.id.missingInfoConfirmationFragment) View.GONE else View.VISIBLE
        }

    override fun onResume() {
        super.onResume()
        findNavController(R.id.nav_host_fragment).addOnDestinationChangedListener(
            onDestinationChangedListener
        )
    }

    override fun onPause() {
        super.onPause()
        findNavController(R.id.nav_host_fragment).removeOnDestinationChangedListener(
            onDestinationChangedListener
        )
    }

    fun setObservers() {
        viewModel.clickEvent.observe(this, onClickObserver)
    }

    private val onClickObserver = Observer<Int> {
        when (it) {
            R.id.tbBtnBack -> {
                onBackPressed()
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        viewModel.clickEvent.removeObservers(this)
    }
}
