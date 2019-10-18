package co.yap.modules.yapit.y2ytransfer.fragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.R
import co.yap.modules.yapit.y2ytransfer.interfaces.IYapToYap
import co.yap.modules.yapit.y2ytransfer.viewmodels.YapToYapViewModel
import co.yap.yapcore.BR
import co.yap.yapcore.BaseBindingFragment
import kotlinx.android.synthetic.main.fragment_yap_to_yap.*

class YapToYapFragment : BaseBindingFragment<IYapToYap.ViewModel>() {
    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.fragment_yap_to_yap

    override val viewModel: YapToYapViewModel
        get() = ViewModelProviders.of(this).get(YapToYapViewModel::class.java)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.clickEvent.observe(this, clickEventObserver)
        tabLayout.addTab(tabLayout.newTab().setText("YAP contacts"));
        tabLayout.addTab(tabLayout.newTab().setText("All contacts"));
    }

    private val clickEventObserver = Observer<Int> {
//        when (it) {
//            R.id.btnInvite ->{
//                showToast("Invitie Button Clicked")
//            }
//        }
    }
}