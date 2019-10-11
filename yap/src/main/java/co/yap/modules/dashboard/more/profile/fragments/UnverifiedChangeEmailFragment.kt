package co.yap.modules.dashboard.more.profile.fragments

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import co.yap.R
import co.yap.modules.dashboard.more.profile.intefaces.IChangeEmail
import co.yap.modules.dashboard.more.profile.viewmodels.UnverifiedChangeEmailViewModel

class UnverifiedChangeEmailFragment : ChangeEmailFragment() {
    override val viewModel: IChangeEmail.ViewModel
        get() = ViewModelProviders.of(this).get(UnverifiedChangeEmailViewModel::class.java)


    override fun setObservers() {

        viewModel.success.observe(this, Observer {
            if (it) {
                val action =
                    UnverifiedChangeEmailFragmentDirections.actionUnverifiedChangeEmailFragmentToUnverifiedChangeEmailSuccessFragment()
                findNavController().navigate(action)
            }
        })
        viewModel.clickEvent.observe(this, Observer {
            when (it) {
                R.id.tbBtnBack -> {

                }
            }
        })

    }
}