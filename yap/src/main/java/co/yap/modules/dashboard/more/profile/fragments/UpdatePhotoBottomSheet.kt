package co.yap.modules.dashboard.more.profile.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import co.yap.R
import co.yap.modules.dashboard.cards.paymentcarddetail.fragments.CardClickListener
import co.yap.modules.dashboard.constants.Constants
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.bottom_sheet_primary_card.view.*
import kotlinx.android.synthetic.main.bottom_sheet_update_card.view.*


class UpdatePhotoBottomSheet(private val mListener: CardClickListener) : BottomSheetDialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.bottom_sheet_update_card, container, false)
        view.tvChoosePhoto.setOnClickListener { mListener.onClick(Constants.EVENT_CHOOSE_PHOTO)}
        view.tvOpenCamera.setOnClickListener { mListener.onClick(Constants.EVENT_ADD_PHOTO)}

        return view
    }
}