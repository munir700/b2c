package co.yap.billpayments.utils

import co.yap.billpayments.R


fun String?.getResId(): Int {
    return try {
        this.let {
            val res = R.drawable::class.java
            val field = res.getField(it.toString())
            field.getInt(null)
        }
    } catch (e: Exception) {
        -1
    }
}
