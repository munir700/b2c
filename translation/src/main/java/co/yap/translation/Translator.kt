package co.yap.translation

import android.app.Application
import android.content.Context

object Translator {
    fun getString(context: Context, keyID: Int, vararg args: String): String {
        return context.resources.getString(keyID, *args)
    }

    fun getString(context: Context, keyID: String, vararg args: String): String {
        val stringResourceId = context.resources.getIdentifier(keyID, "string", context.packageName)
        return getString(context, stringResourceId, *args)
    }

    fun getString(context: Context, keyID: Int): String {
        return context.resources.getString(keyID)
    }

    fun getString(context: Context, keyID: String): String {
        val stringResourceId = context.resources.getIdentifier(keyID, "string", context.packageName)
        return getString(context, stringResourceId)

    }

    fun getString(context: Context, keyID: String, value: String): String {
        val stringResourceId = context.resources.getIdentifier(keyID, "string", context.packageName)
        return context.resources.getString(stringResourceId, value)
    }

    fun getString(context: Application, keyID: String, value1: String, value2: String): String {
        val stringResourceId = context.resources.getIdentifier(keyID, "string", context.packageName)
        return stringResourceId.let { context.resources.getString(it, value1, value2) }
    }

    fun getString(
        context: Context,
        keyID: String,
        value1: String,
        value2: String,
        value3: String,
        value4: String,
        value5: String,
        value6: String
    ): String {
        val stringResourceId = context.resources.getIdentifier(keyID, "string",context.packageName)
        return context.resources.getString(stringResourceId, value1, value2, value3, value4, value5, value6)
    }
}
