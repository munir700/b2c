package co.yap.modules.dashboard.more.profile.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import co.yap.R
import co.yap.modules.dashboard.cards.paymentcarddetail.fragments.CardClickListener
import co.yap.modules.others.helper.Constants
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.bottom_sheet_update_card.view.*


class UpdatePhotoBottomSheet(private val mListener: CardClickListener) : BottomSheetDialogFragment() {
    override fun getTheme(): Int = R.style.AppBottomSheetDialogTheme

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.bottom_sheet_update_card, container, false)
        view.tvChoosePhoto.setOnClickListener { mListener.onClick(Constants.EVENT_CHOOSE_PHOTO)}
        view.tvOpenCamera.setOnClickListener { mListener.onClick(Constants.EVENT_ADD_PHOTO)}
        view.tvRemovePhoto.setOnClickListener { mListener.onClick(Constants.EVENT_REMOVE_PHOTO)}


        return view
    }
}