package co.yap.modules.kyc.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import co.yap.BR
import co.yap.R
import co.yap.modules.kyc.interfaces.IDocumentsDashboard
import co.yap.modules.kyc.viewmodels.DocumentsDashboardViewModel
import co.yap.yapcore.BaseBindingActivity
import co.yap.yapcore.IFragmentHolder
import co.yap.yapcore.defaults.DefaultNavigator
import co.yap.yapcore.defaults.INavigator
import co.yap.yapcore.interfaces.BackPressImpl
import co.yap.yapcore.interfaces.IBaseNavigator

class DocumentsDashboardActivity : BaseBindingActivity<IDocumentsDashboard.ViewModel>(), INavigator, IFragmentHolder {
    companion object {


        fun newIntent(context: Context): Intent = Intent(context, DocumentsDashboardActivity::class.java)

    }

    override val viewModel: IDocumentsDashboard.ViewModel
        get() = ViewModelProviders.of(this).get(DocumentsDashboardViewModel::class.java)

    override val navigator: IBaseNavigator
        get() = DefaultNavigator(this, R.id.kyc_host_fragment)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        viewModel.onboardingData.accountType = getAccountType()
//        viewModel.backButtonPressEvent.observe(this, backButtonObserver)
//        val mapFragment =
//            supportFragmentManager.findFragmentById(R.id.map) as MyMapFragment
//        /*mapFragment.initial_latitude = -10.0
//        mapFragment.initial_longitude = 115.0
//        mapFragment.initial_marker = "Inishol mawker"*/
//        mapFragment.getMapAsync(mapFragment)

    }

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.activity_documents_dashboard


//    private val backButtonObserver = Observer<Boolean> { onBackPressed() }

    override fun onDestroy() {
//        viewModel.backButtonPressEvent.removeObservers(this)
        super.onDestroy()
    }

    override fun onBackPressed() {
        val fragment = supportFragmentManager.findFragmentById(R.id.my_nav_host_fragment)
        if (!BackPressImpl(fragment).onBackPressed()) {
            super.onBackPressed()
        }
    }

    override fun onFragmentAttached() {

    }

    override fun onFragmentDetached(tag: String) {
    }
}