package co.yap.yapcore.helpers

import android.content.Context
import android.os.Build
import androidx.annotation.ColorRes
import co.yap.yapcore.R

object Utils {
    fun getColor(context: Context, @ColorRes color: Int) =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            context.resources.getColor(color, null)
        } else {
            context.resources.getColor(color)
        }

}