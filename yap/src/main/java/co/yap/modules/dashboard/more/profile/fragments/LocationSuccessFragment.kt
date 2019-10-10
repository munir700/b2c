package co.yap.modules.dashboard.more.profile.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import co.yap.R
import co.yap.modules.dashboard.cards.reportcard.activities.ReportLostOrStolenCardActivity
import co.yap.translation.Strings
import co.yap.translation.Translator
import co.yap.yapcore.defaults.DefaultFragment
import co.yap.yapcore.managers.MyUserManager
import kotlinx.android.synthetic.main.fragment_add_physical_spare_card_success.*
import kotlinx.android.synthetic.main.fragment_location_success.*
import kotlinx.android.synthetic.main.layout_add_spare_card_header.view.*

class LocationSuccessFragment : DefaultFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater?.inflate(
            R.layout.fragment_location_success,
            container, false
        )

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvSuccessSubHeading.setText(
            Translator.getString(
                context!!,
                Strings.screen_spare_card_landing_display_text_physical_card
            ) + "\n" + MyUserManager.userAddress!!.address1 + " " + MyUserManager.userAddress!!.address2
        )
        include.labelCardType.setText(
            Translator.getString(
                context!!,
                Strings.screen_spare_card_landing_display_text_physical_card
            )
        )

        btnDone.setOnClickListener {
            // Spare physical card added event
            ReportLostOrStolenCardActivity.reportCardSuccess = true
//            findNavController().navigate(R.id.action_addressSelectionFragment_to_changeAddressSuccessFragment)
            super.onBackPressed()
//            activity!!.finish()
        }
    }

    override fun onBackPressed(): Boolean {
        /*setupActionsIntent()
        activity!!.finish()*/
        return true
    }

}