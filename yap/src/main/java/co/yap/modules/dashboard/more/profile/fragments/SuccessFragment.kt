package co.yap.modules.dashboard.more.profile.fragments

import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import co.yap.BR
import co.yap.R
import co.yap.modules.dashboard.more.activities.MoreActivity
import co.yap.modules.dashboard.more.profile.intefaces.ISuccess
import co.yap.modules.dashboard.more.profile.viewmodels.SuccessViewModel
import co.yap.translation.Strings.screen_address_success_display_text_sub_heading
import co.yap.translation.Translator
import co.yap.yapcore.BaseBindingFragment
import co.yap.yapcore.managers.MyUserManager
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
            if (successType == "CHANGE_PASSCODE") {
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
    }

    override fun onDestroy() {
        viewModel.buttonClickEvent.removeObservers(this)
        super.onDestroy()
    }

    private fun loadData() {
        successType = args.successType
        val fcs = ForegroundColorSpan(ContextCompat.getColor(context!!, R.color.colorPrimaryDark))

        val separatedPrimary =
            args.staticString.split(args.destinationValue)
        val primaryStr = SpannableStringBuilder(args.staticString + args.destinationValue)

        primaryStr.setSpan(
            fcs,
            separatedPrimary[0].length,
            primaryStr.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
val addressStr=getString(R.string.screen_address_success_display_text_sub_heading)
//        if (primaryStr.contains(getString(R.string.screen_address_success_display_text_sub_heading))) {
        if (primaryStr.contains( addressStr )){
            addressField =
                MyUserManager.userAddress!!.address1 + " " + MyUserManager.userAddress!!.address2
            tvSuccessSubHeading.text = primaryStr.append("\n" + addressField)

        } else {
            tvSuccessSubHeading.text = primaryStr

        }

    }
}