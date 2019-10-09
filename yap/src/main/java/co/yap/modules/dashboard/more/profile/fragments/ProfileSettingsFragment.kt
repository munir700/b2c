package co.yap.modules.dashboard.more.profile.fragments

import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import co.yap.BR
import co.yap.R
import co.yap.modules.dashboard.cards.paymentcarddetail.fragments.CardClickListener
import co.yap.modules.dashboard.constants.Constants
import co.yap.modules.dashboard.more.activities.MoreActivity
import co.yap.modules.dashboard.more.fragments.MoreBaseFragment
import co.yap.modules.dashboard.more.profile.intefaces.IProfile
import co.yap.modules.dashboard.more.profile.viewmodels.ProfileSettingsViewModel
import co.yap.networking.cards.responsedtos.CardBalance
import co.yap.yapcore.helpers.AuthUtils
import co.yap.yapcore.managers.MyUserManager

class ProfileSettingsFragment : MoreBaseFragment<IProfile.ViewModel>(), IProfile.View,
    CardClickListener {

    private lateinit var updatePhotoBottomSheet: UpdatePhotoBottomSheet

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_profile

    override val viewModel: IProfile.ViewModel
        get() = ViewModelProviders.of(this).get(ProfileSettingsViewModel::class.java)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.clickEvent.observe(this, Observer {
            when (it) {

                R.id.tvPersonalDetailView -> {
                    findNavController().navigate(R.id.action_profileSettingsFragment_to_personalDetailsFragment)
                }

                R.id.tvPrivacyView -> {

                }

                R.id.tvChangePasscode -> {

                    findNavController().navigate(R.id.action_profileSettingsFragment_to_change_pascode_navigation)

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

                R.id.tvLogOut -> {

                    logoutAlert()

                }

                R.id.rlAddNewProfilePic -> {
                    updatePhotoBottomSheet = UpdatePhotoBottomSheet(this)
                    updatePhotoBottomSheet.show(this.fragmentManager!!, "")
                }
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (context is MoreActivity)
            (context as MoreActivity).visibleToolbar()
    }

    override fun onClick(eventType: Int) {

        updatePhotoBottomSheet.dismiss()

        when (eventType) {
// add update photo
            Constants.EVENT_ADD_PHOTO -> {
                showToast(Constants.EVENT_ADD_PHOTO.toString())
            }

            Constants.EVENT_CHOOSE_PHOTO -> {
                showToast(Constants.EVENT_CHOOSE_PHOTO.toString())
            }
        }
    }

    override fun onDestroy() {
        viewModel.clickEvent.removeObservers(this)
        super.onDestroy()

    }

    fun logoutAlert() {
        AlertDialog.Builder(this!!.activity!!)
            .setTitle(getString(R.string.screen_profile_settings_logout_display_text_alert_title))
            .setMessage(getString(R.string.screen_profile_settings_logout_display_text_alert_message))
            .setPositiveButton(getString(R.string.screen_profile_settings_logout_display_text_alert_logout),
                DialogInterface.OnClickListener { dialog, which ->
                    doLogout()
                })

            .setNegativeButton(
                getString(R.string.screen_profile_settings_logout_display_text_alert_cancel),
                null
            )
            .show()
    }

    private fun doLogout() {
        AuthUtils.navigateToHardLogin(requireContext())
        MyUserManager.cardBalance.value = CardBalance()
        MyUserManager.cards.value?.clear()
        activity?.finish()
    }
}