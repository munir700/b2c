package co.yap.modules.dashboard.fragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.BR
import co.yap.R
import co.yap.modules.dashboard.interfaces.IYapStore
import co.yap.modules.dashboard.viewmodels.YapStoreViewModel
import co.yap.yapcore.BaseBindingFragment

class YapStoreFragment : BaseBindingFragment<IYapStore.ViewModel>(), IYapStore.View {
    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_yap_store

    override val viewModel: IYapStore.ViewModel
        get() = ViewModelProviders.of(this).get(YapStoreViewModel::class.java)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.clickEvent.observe(this, Observer {

        })
    }
}