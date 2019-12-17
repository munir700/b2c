package co.yap.modules.others.helper

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.view.Gravity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import co.yap.R
import co.yap.widgets.popmenu.*

fun Context.getCurrencyPopMenu(
    lifecycleOwner: LifecycleOwner?,
    onMenuItemClickListener: OnMenuItemClickListener<PopupMenuItem?>?,
    onDismissedListener: OnDismissedListener?
): PopupMenu {
    return PopupMenu.Builder(this)
        .addItem(PopupMenuItem("Pakistani Rupees", true))
        .addItem(PopupMenuItem("US Dollars", false))
        .setAutoDismiss(true)
        .setLifecycleOwner(lifecycleOwner!!)
        .setAnimation(MenuAnimation.SHOWUP_TOP_LEFT)
        .setCircularEffect(CircularEffect.INNER)
        .setMenuRadius(10f)
        .setMenuShadow(10f)
        .setTextColor(ContextCompat.getColor(this, R.color.greyDark))
        .setTextSize(12).setBackgroundAlpha(0f)
        .setTextGravity(Gravity.CENTER)
        .setSelectedTextColor(Color.WHITE)
        .setMenuColor(Color.WHITE)
        .setSelectedMenuColor(ContextCompat.getColor(this, R.color.greyLight))
        .setOnMenuItemClickListener(onMenuItemClickListener)
        .setOnDismissListener(onDismissedListener)
        .setInitializeRule(Lifecycle.Event.ON_CREATE, 0)
        .build()
}
