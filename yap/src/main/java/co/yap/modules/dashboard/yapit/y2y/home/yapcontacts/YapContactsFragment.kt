package co.yap.modules.dashboard.yapit.y2y.home.yapcontacts

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import co.yap.R
import co.yap.modules.dashboard.yapit.y2y.home.phonecontacts.IPhoneContact
import co.yap.modules.dashboard.yapit.y2y.home.phonecontacts.PhoneContactViewModel
import co.yap.modules.dashboard.yapit.y2y.main.fragments.Y2YBaseFragment
import co.yap.yapcore.BR

class YapContactsFragment : Y2YBaseFragment<IPhoneContact.ViewModel>() {
    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.fragment_yap_contacts

    override val viewModel: PhoneContactViewModel
        get() = ViewModelProviders.of(this).get(PhoneContactViewModel::class.java)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private val clickEventObserver = Observer<Int> {
        when (it) {
            R.id.btnInvite -> {
                findNavController().navigate(R.id.y2YTransferFragment)
            }
        }
    }

}