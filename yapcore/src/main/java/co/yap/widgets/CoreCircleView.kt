package co.yap.widgets

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import co.yap.yapcore.R
import co.yap.yapcore.binders.UIBinder
import kotlinx.android.synthetic.main.core_circle_view.view.*

class CoreCircleView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) :
    ConstraintLayout(context, attrs) {
    var icon: Drawable? = null
        set(value) {
            field = value
            icon?.let {
                UIBinder.setImageResId(ivIcon, value)
            }
        }

    var topLefticon: Drawable? = null
        set(value) {
            field = value
            topLefticon?.let {
                ivTopLeft.visibility = VISIBLE
                UIBinder.setImageResId(ivTopLeft, value)
            }

        }

    var bottomRightIcon: Drawable? = null
        set(value) {
            field = value
            bottomRightIcon?.let {
                ivBottomRight.visibility = VISIBLE
                UIBinder.setImageResId(ivBottomRight, value)
            }

        }

    var iconWidth: Float? = null
        set(value) {
            field = value
            iconWidth?.let {
                ivIcon.layoutParams.width = it.toInt()
            }
        }

    var iconHeight: Float? = null
        set(value) {
            field = value
            iconHeight?.let {
                ivIcon.layoutParams.height = it.toInt()
            }
        }

    var iconBgHeight: Float? = null
        set(value) {
            field = value
            iconBgHeight?.let {
                clBgIcon.layoutParams.height = it.toInt()
            }
        }

    var iconBgWidth: Float? = null
        set(value) {
            field = value
            iconBgWidth?.let {
                clBgIcon.layoutParams.width = it.toInt()
            }
        }

    var iconBgTint: Int? = null
        set(value) {
            field = value
            iconBgTint?.let {
                clBgIcon.backgroundTintList =
                    ContextCompat.getColorStateList(clBgIcon.context, it)
            }
        }

    var iconTint: Int? = null
        set(value) {
            field = value
            iconTint?.let {
                ivIcon.imageTintList =
                    ContextCompat.getColorStateList(clBgIcon.context, it)
            }
        }

    var view: View = LayoutInflater.from(context)
        .inflate(R.layout.core_circle_view, this, true)

    init {
        attrs?.let {
            val typedArray =
                context.obtainStyledAttributes(it, R.styleable.CoreCircleView, 0, 0)
            typedArray.recycle()
        }
    }
}