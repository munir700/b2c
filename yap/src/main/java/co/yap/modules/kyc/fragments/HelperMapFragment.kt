package co.yap.modules.kyc.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.google.android.gms.maps.SupportMapFragment


class HelperMapFragment : SupportMapFragment() {
    private var mListener: OnTouchListener? = null

    override fun onCreateView(layoutInflater: LayoutInflater, viewGroup: ViewGroup?, savedInstance: Bundle?): View? {
        val layout = super.onCreateView(layoutInflater, viewGroup, savedInstance)

//        val frameLayout = activity?.let { TouchableWrapper(it) }
//
//        if (frameLayout != null) {
//            frameLayout.setBackgroundColor(resources.getColor(android.R.color.transparent))
//        }

        assert(layout != null)
//        (layout as ViewGroup).addView(
//            frameLayout,
//            ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
//        )

        return layout
    }

    fun setListener(listener: Unit) {
        mListener = listener as OnTouchListener
    }

    interface OnTouchListener {
      public abstract fun onTouch()
    }

    inner class TouchableWrapper(context: Context) : FrameLayout(context) {

        override fun dispatchTouchEvent(event: MotionEvent): Boolean {
            when (event.action) {
                MotionEvent.ACTION_DOWN -> mListener!!.onTouch()
                MotionEvent.ACTION_UP -> mListener!!.onTouch()
            }
            return super.dispatchTouchEvent(event)
        }
    }
}