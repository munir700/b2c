package co.yap.modules.dashboard.more.profile.fragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import co.yap.BR
import co.yap.R
import co.yap.modules.dashboard.more.activities.MoreActivity
import co.yap.modules.dashboard.more.fragments.MoreBaseFragment
import co.yap.modules.dashboard.more.profile.intefaces.IPersonalDetail
import co.yap.modules.dashboard.more.profile.viewmodels.PersonalDetailsViewModel

class PersonalDetailsFragment : MoreBaseFragment<IPersonalDetail.ViewModel>(),
    IPersonalDetail.View {

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_personal_detail

    override val viewModel: IPersonalDetail.ViewModel
        get() = ViewModelProviders.of(this).get(PersonalDetailsViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.clickEvent.observe(this, Observer {
            when (it) {

                R.id.tvEditPhoneNumber -> {
                    findNavController().navigate(R.id.action_personalDetailsFragment_to_change_phone_number_navigation)
                }

                R.id.tvEditEmail -> {
                    findNavController().navigate(R.id.action_personalDetailsFragment_to_change_email_navigation)
                }

                R.id.tvEditAddress -> {

                }

                R.id.cvCard -> {
                    if (!viewModel.state.errorVisibility) {
                        val action =
                            PersonalDetailsFragmentDirections.actionPersonalDetailsFragmentToDocumentsDashboardActivity(
                                viewModel.state.fullName,true
                            )
                        findNavController().navigate(action)
                    }

                }
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (context as MoreActivity).visibleToolbar()
    }

    override fun onDestroy() {
        viewModel.clickEvent.removeObservers(this)
        super.onDestroy()
    }
}