package co.yap.modules.yap_to_yap.fragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProviders
import co.yap.R
import co.yap.modules.yap_to_yap.interfaces.IYapToYap
import co.yap.modules.yap_to_yap.viewmodels.YapToYapViewModel
import co.yap.yapcore.BR
import co.yap.yapcore.BaseBindingFragment

class YapToYapFragment : BaseBindingFragment<IYapToYap.ViewModel>() {
    override fun getBindingVariable():Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.fragment_yap_to_yap

    override val viewModel: YapToYapViewModel
        get() = ViewModelProviders.of(this).get(YapToYapViewModel::class.java)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}