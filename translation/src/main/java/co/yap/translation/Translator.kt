package co.yap.translation

import android.app.Application
import android.content.Context

object Translator {
    //    val context: Context= ctx.applicationContext
    fun getString(context: Context, keyID: Int, vararg args: String): String {
        return context?.getResources()?.getString(keyID, args)!!
    }

    fun getString(context: Application, keyID: Int): String {
        return context?.getResources()?.getString(keyID)!!
    }

    fun getString(context: Application, keyID: String): String {
        val stringResourceId = context?.getResources()?.getIdentifier(keyID, "string", context?.getPackageName())
        return getString(context, stringResourceId!!)

    }

    fun getString(context: Application, keyID: String, value: String): String {
        val stringResourceId = context?.getResources()?.getIdentifier(keyID, "string", context?.getPackageName())
        return context?.getResources()?.getString(stringResourceId!!, value)!!
    }

    fun getString(context: Application, keyID: String, value1: String, value2: String): String {
        val stringResourceId = context?.getResources()
            ?.getIdentifier(keyID, "string", context?.getPackageName())
        return stringResourceId?.let { context?.getResources()?.getString(it, value1, value2) }!!
    }

    fun getString(
        context: Application,
        keyID: String,
        value1: String,
        value2: String,
        value3: String,
        value4: String,
        value5: String,
        value6: String
    ): String {
        val stringResourceId = context?.getResources()?.getIdentifier(keyID, "string", context?.getPackageName())
        return context?.getResources()?.getString(stringResourceId!!, value1, value2, value3, value4, value5, value6)!!
    }
}
