package co.yap.household.onboarding

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.household.R
import co.yap.household.databinding.ActivityMainBinding

class OnboardingActivity : AppCompatActivity() {

    companion object {
        fun newIntent(context: Context): Intent {
            val intent = Intent(context, OnboardingActivity::class.java)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewDataBinding: ActivityMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)

        val viewModel: OnboardingViewModel =
            ViewModelProviders.of(this).get(OnboardingViewModel::class.java)

        viewDataBinding.viewModel = viewModel
        viewDataBinding.executePendingBindings()
    }
}

//class OnboardingActivity : BaseBindingActivity<IOnboarding.ViewModel>() {
//
//    companion object {
//        fun newIntent(context: Context): Intent {
//            val intent = Intent(context, OnboardingActivity::class.java)
//            return intent
//        }
//    }
//
//    override fun getBindingVariable(): Int = 0
//
//    override fun getLayoutId(): Int = R.layout.activity_main
//
//    override val viewModel: IOnboarding.ViewModel
//        get() = ViewModelProviders.of(this).get(OnboardingViewModel::class.java)
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        viewModel.clickEvent.observe(this, Observer { })
//    }
//
//    override fun onDestroy() {
//        viewModel.clickEvent.removeObservers(this)
//        super.onDestroy()
//    }
//}