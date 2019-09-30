package co.yap.modules.dashboard.more.profile.fragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.BR
import co.yap.R
import co.yap.modules.dashboard.more.fragments.MoreBaseFragment
import co.yap.modules.dashboard.more.profile.intefaces.IProfile
import co.yap.modules.dashboard.more.profile.viewmodels.ProfileSettingsViewModel

class ProfileSettingsFragment : MoreBaseFragment<IProfile.ViewModel>(), IProfile.View {

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_profile

    override val viewModel: IProfile.ViewModel
        get() = ViewModelProviders.of(this).get(ProfileSettingsViewModel::class.java)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.clickEvent.observe(this, Observer {
            when (it) {

                R.id.tvPersonalDetailView -> {
//                    findNavController().navigate(R.string.screen_spare_card_landing_display_text_physical_card)
                }

                R.id.tvPrivacyView -> {

                }

                R.id.tvChangePasscode -> {

                }

                R.id.tvTermsAndConditionView -> {

                }

                R.id.tvFollowOnInstagram -> {

                }

                R.id.tvFollowOnTwitter -> {

                }

                R.id.tvLikeUsOnFaceBook -> {

                }

                R.id.ivProfilePic -> {
                    // change profile picture
                }

                R.id.rlAddNewProfilePic -> {
                    // add new profile picture

                }
            }
        })

    }

    override fun onDestroy() {
        viewModel.clickEvent.removeObservers(this)
        super.onDestroy()
    }
}