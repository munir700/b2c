package co.yap.modules.kyc.amendments.passportactivity

import androidx.lifecycle.ViewModelProvider
import co.yap.R
import co.yap.yapcore.BR
import co.yap.yapcore.BaseBindingActivity
import co.yap.yapcore.IFragmentHolder
import co.yap.yapcore.defaults.DefaultNavigator
import co.yap.yapcore.defaults.INavigator
import co.yap.yapcore.interfaces.IBaseNavigator

class PassportActivity : BaseBindingActivity<IPassport.ViewModel>(), IPassport.View,
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
}