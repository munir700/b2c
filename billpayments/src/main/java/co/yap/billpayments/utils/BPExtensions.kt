package co.yap.billpayments.utils

import android.content.Context
import androidx.annotation.Keep
import co.yap.billpayments.R

@Keep
fun String?.getResId(context:Context): Int {
    return try {
        this.let {
            context.resources.getIdentifier(this, "drawable", context.packageName)
//            val res = R.drawable::class.java
//            val field = res.getField(it.toString())
//            field.getInt(null)
        }
    } catch (e: Exception) {
        -1
    }
}
