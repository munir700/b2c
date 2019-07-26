package co.yap.modules.kyc.fragments

import android.content.Context
import android.view.MotionEvent
import android.widget.FrameLayout
import com.google.android.gms.maps.SupportMapFragment


class MapDetailViewFragment : SupportMapFragment() {
    private var mListener: OnTouchListener? = null

    fun setListener(listener: () -> Unit) {
//        mListener = listener
    }

    interface OnTouchListener {
        fun onTouch()
    }

    inner class TouchableWrapper(context: Context) : FrameLayout(context) {

        override fun dispatchTouchEvent(event: MotionEvent): Boolean {
            when (event.getAction()) {
                MotionEvent.ACTION_DOWN -> mListener!!.onTouch()
                MotionEvent.ACTION_UP -> mListener!!.onTouch()
            }
            return super.dispatchTouchEvent(event)
        }
    }
}
