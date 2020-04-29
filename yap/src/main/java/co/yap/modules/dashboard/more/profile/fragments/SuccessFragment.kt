package co.yap.modules.dashboard.more.profile.fragments

import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.view.View
import android.view.View.VISIBLE
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import co.yap.BR
import co.yap.R
import co.yap.modules.dashboard.more.main.activities.MoreActivity
import co.yap.modules.dashboard.more.profile.intefaces.ISuccess
import co.yap.modules.dashboard.more.profile.viewmodels.SuccessViewModel
import co.yap.yapcore.BaseBindingFragment
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.managers.MyUserManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import kotlinx.android.synthetic.main.fragment_success.*

class SuccessFragment : BaseBindingFragment<ISuccess.ViewModel>(),
    ISuccess.View {
    val args: SuccessFragmentArgs by navArgs()
    var successType: String? = null
    var addressField: String = ""

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_success

    override val viewModel: ISuccess.ViewModel
        get() = ViewModelProviders.of(this).get(SuccessViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.buttonClickEvent.observe(this, Observer {
            if (successType == Constants.CHANGE_PASSCODE) {
                findNavController().popBackStack(R.id.profileSettingsFragment, true)
                findNavController().navigate(R.id.profileSettingsFragment)
            } else {
                findNavController().popBackStack(R.id.personalDetailsFragment, true)
                findNavController().navigate(R.id.personalDetailsFragment)
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (context is MoreActivity)
            (context as MoreActivity).goneToolbar()
        loadData()
        if (activity is MoreActivity) {
            (activity as MoreActivity).viewModel.preventTakeDeviceScreenShot.value = false
        }
    }

    override fun onDestroy() {
        viewModel.buttonClickEvent.removeObservers(this)
        super.onDestroy()
    }

    private fun loadData() {
        successType = args.successType
        val fcs =
            ForegroundColorSpan(ContextCompat.getColor(requireContext(), R.color.colorPrimaryDark))

        val separatedPrimary =
            args.staticString.split(args.destinationValue)
        val primaryStr = SpannableStringBuilder(args.staticString + args.destinationValue)

        primaryStr.setSpan(
            fcs,
            separatedPrimary[0].length,
            primaryStr.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        val addressStr = getString(R.string.screen_address_success_display_text_sub_heading_update)

        if (primaryStr.contains(addressStr)) {
            cvLocationCard.visibility = VISIBLE
            addressField =
                MyUserManager.userAddress?.address1 + " " + MyUserManager.userAddress?.address2
            tvSuccessSubHeading.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.greyDark
                )
            )

            tvSuccessSubHeading.text = addressStr
            if (!MyUserManager.userAddress?.address2.isNullOrEmpty()) {
                tvAddressTitle.setText(MyUserManager.userAddress?.address2)
            }
            if (!MyUserManager.userAddress?.address1.isNullOrEmpty()) {
                tvAddressSubTitle.setText(MyUserManager.userAddress?.address1)
            }
            Glide.with(ivLocationPhoto.context)
                .load("")
                .placeholder(R.drawable.location_place_holder)
                .transforms(CenterCrop(), RoundedCorners(15))
                .into(ivLocationPhoto)
        } else {
            tvSuccessSubHeading.text = primaryStr
        }
    }

    override fun onBackPressed(): Boolean {
        return true
    }
}