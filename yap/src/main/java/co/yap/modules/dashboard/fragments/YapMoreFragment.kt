package co.yap.modules.dashboard.fragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.BR
import co.yap.R
import co.yap.modules.dashboard.interfaces.IYapMore
import co.yap.modules.dashboard.viewmodels.YapMoreViewModel
import co.yap.yapcore.BaseBindingFragment

class YapMoreFragment : BaseBindingFragment<IYapMore.ViewModel>(), IYapMore.View {
    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_yap_more

    override val viewModel: IYapMore.ViewModel
        get() = ViewModelProviders.of(this).get(YapMoreViewModel::class.java)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.clickEvent.observe(this, Observer {

        })
    }
}