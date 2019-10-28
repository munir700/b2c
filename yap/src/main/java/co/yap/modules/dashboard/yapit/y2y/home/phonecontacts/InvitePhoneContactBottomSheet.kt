package co.yap.modules.dashboard.yapit.y2y.home.phonecontacts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import co.yap.R
import co.yap.networking.customers.requestdtos.Contact
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.bottom_sheet_invite_friend.view.*

class InvitePhoneContactBottomSheet(
    private val mListener: OnItemClickListener,
    private val contact: Contact
) : BottomSheetDialogFragment() {

    override fun getTheme(): Int = R.style.AppBottomSheetDialogTheme
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.bottom_sheet_invite_friend, container, false)

        view.tvChooseEmail.setOnClickListener { mListener.onClick(view.tvChooseEmail.id, contact) }
        view.tvChooseSMS.setOnClickListener { mListener.onClick(view.tvChooseSMS.id, contact) }
        view.tvChooseWhatsapp.setOnClickListener {
            mListener.onClick(
                view.tvChooseWhatsapp.id,
                contact
            )
        }
        return view
    }

    interface OnItemClickListener {
        fun onClick(viewId: Int, contact: Contact)
    }
}