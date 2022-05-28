package co.yap.sendmoney.y2y.home.phonecontacts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import co.yap.sendmoney.R
import co.yap.sendmoney.databinding.BottomSheetInviteFriendBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class InviteBottomSheet(
    private val mListener: OnItemClickListener,
    private val T: Any
) : BottomSheetDialogFragment() {

    override fun getTheme(): Int = R.style.AppBottomSheetDialogTheme
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = DataBindingUtil.inflate<BottomSheetInviteFriendBinding>(
            inflater,
            R.layout.bottom_sheet_invite_friend,
            container,
            false
        )

        view.tvChooseEmail.setOnClickListener { mListener.onClick(view.tvChooseEmail.id, T) }
        view.tvChooseSMS.setOnClickListener { mListener.onClick(view.tvChooseSMS.id, T) }
        view.tvChooseWhatsapp.setOnClickListener {
            mListener.onClick(
                view.tvChooseWhatsapp.id,
                T
            )
        }
        return view.root
    }

    interface OnItemClickListener {
        fun onClick(viewId: Int, T: Any)
    }
}