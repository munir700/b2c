package co.yap.modules.yap_to_yap.fragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import co.yap.R
import co.yap.modules.yap_to_yap.interfaces.IYapToYap
import co.yap.modules.yap_to_yap.viewmodels.YapToYapViewModel
import co.yap.yapcore.BR
import co.yap.yapcore.BaseBindingFragment

class YapToYapFragment : BaseBindingFragment<IYapToYap.ViewModel>() {
    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.fragment_yap_to_yap

    override val viewModel: YapToYapViewModel
        get() = ViewModelProviders.of(this).get(YapToYapViewModel::class.java)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.clickEvent.observe(this, clickEventObserver)
    }

    private val clickEventObserver = Observer<Int> {
        when (it) {
            R.id.tbIvClose -> {
                findNavController().navigateUp()
                showToast("Cross Button Clicked")
            }
            R.id.tbIvGift -> {
                showToast("Gift Button Clicked")
            }
            R.id.btnInvite ->{
                showToast("Invitie Button Clicked")
            }
        }
    }
}