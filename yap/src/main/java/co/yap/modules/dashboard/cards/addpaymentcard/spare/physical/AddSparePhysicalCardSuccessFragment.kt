package co.yap.modules.dashboard.cards.addpaymentcard.spare.physical

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import co.yap.R
import kotlinx.android.synthetic.main.layout_add_spare_physical_card_confirm_purchase.*
 import kotlinx.android.synthetic.main.layout_add_spare_physical_card_success.view.*


class AddSparePhysicalCardSuccessFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater?.inflate(
            R.layout.fragment_add_physical_spare_card_success,
            container, false
        )



        return view


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        layoutPhysicalCardOnSuccess.btnDoneAddingSparePhysicalCard.setOnClickListener {
            findNavController().navigate(R.id.action_addSparePhysicalCardSuccessFragment_to_spareCardLandingFragment)
        }
    }
}