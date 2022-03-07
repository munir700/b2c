package co.yap.widgets.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import co.yap.yapcore.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class TakePhotoBottomSheet(
    private val mListener: OnTakePhotoBottomSheetItemClickListener? = null,
    private val headingLabel: String? = null

) : BottomSheetDialogFragment() {

    override fun getTheme(): Int = R.style.AppBottomSheetTakePhotoTheme
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.take_photo_bottom_sheet, container, false)
        val tvHeading: TextView = view.findViewById(R.id.tvHeadingTitle)
        val tvTakePhoto: TextView = view.findViewById(R.id.tvTakePhoto)
        val tvbrowseFiles: TextView = view.findViewById(R.id.tvbrowseFiles)
        val btnActionCancel: Button = view.findViewById(R.id.btnActionCancel)

        headingLabel?.let {
            tvHeading.text = it
        }
        btnActionCancel.setOnClickListener {
            dismiss()
        }
        tvTakePhoto.setOnClickListener {
            mListener?.onItemClick(it.id)
            dismiss()
        }
        tvbrowseFiles.setOnClickListener {
            mListener?.onItemClick(it.id)
            dismiss()
        }
        return view
    }

    interface OnTakePhotoBottomSheetItemClickListener {
        fun onItemClick(viewId: Int)
    }
}