package co.yap.modules.dashboard.more.changepasscode.fragments

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.BR
import co.yap.R
import co.yap.modules.dashboard.more.changepasscode.interfaces.IChangePassCodeSuccess
import co.yap.modules.dashboard.more.changepasscode.viewmodels.ChangePasscodeSuccessViewModel

class ChangePasscodeSuccessFragment :
    ChangePasscodeBaseFragment<IChangePassCodeSuccess.ViewModel>(),
    IChangePassCodeSuccess.View {

    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.fragment_success
    override val viewModel: ChangePasscodeSuccessViewModel
        get() = ViewModelProviders.of(this).get(ChangePasscodeSuccessViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.buttonClickEvent.observe(this, Observer {
            activity?.finish()
        })
    }

    override fun onDestroy() {
        viewModel.buttonClickEvent.removeObservers(this)
        super.onDestroy()
    }

//    private fun loadData() {
//        successType = args.successType
//        val fcs =
//            ForegroundColorSpan(ContextCompat.getColor(requireContext(), R.color.colorPrimaryDark))
//
//        val separatedPrimary =
//            args.staticString.split(args.destinationValue)
//        val primaryStr = SpannableStringBuilder(args.staticString + args.destinationValue)
//
//        primaryStr.setSpan(
//            fcs,
//            separatedPrimary[0].length,
//            primaryStr.length,
//            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
//        )
//        val addressStr = getString(R.string.screen_address_success_display_text_sub_heading_update)
//
//        if (primaryStr.contains(addressStr)) {
//            cvLocationCard.visibility = VISIBLE
//            addressField =
//                MyUserManager.userAddress?.address1 + " " + MyUserManager.userAddress?.address2
//            tvSuccessSubHeading.setTextColor(
//                ContextCompat.getColor(
//                    requireContext(),
//                    R.color.greyDark
//                )
//            )
//
//            tvSuccessSubHeading.text = addressStr
//            if (!MyUserManager.userAddress?.address2.isNullOrEmpty()) {
//                tvAddressTitle.setText(MyUserManager.userAddress?.address2)
//            }
//            if (!MyUserManager.userAddress?.address1.isNullOrEmpty()) {
//                tvAddressSubTitle.setText(MyUserManager.userAddress?.address1)
//            }
//            Glide.with(ivLocationPhoto.context)
//                .load("")
//                .placeholder(R.drawable.location_place_holder)
//                .transforms(CenterCrop(), RoundedCorners(15))
//                .into(ivLocationPhoto)
//        } else {
//            tvSuccessSubHeading.text = primaryStr
//        }
//    }

    override fun onBackPressed(): Boolean {
        return true
    }
}