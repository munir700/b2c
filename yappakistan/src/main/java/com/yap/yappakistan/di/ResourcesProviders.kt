package com.yap.yappakistan.di

import android.content.Context
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.PluralsRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import com.yap.core.extensions.getColors
import com.yap.core.utils.getText
import com.yap.yappakistan.localization.getFormattedQuantityString
import com.yap.yappakistan.localization.getString
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ResourcesProviders @Inject constructor(@ApplicationContext val context: Context) {
    fun getColor(@ColorRes id: Int) = context.getColors(id)

    fun getDrawable(@DrawableRes id: Int) = ContextCompat.getDrawable(context, id)

    fun getString(@StringRes resId: Int, vararg args: String) = context.getString(resId, args)
    fun getString(keyID: String, vararg args: String) =
        context.getString(keyID = keyID, args = *args)

    fun getSpannableText(id: String, vararg formatArgs: Any?): CharSequence =
        context.resources.getText(id = id, formatArgs = *arrayOf(formatArgs))

    fun getString(keyID: String) = context.getString(keyID = keyID)
    fun getString(@StringRes resId: Int) = context.getString(resId)
    fun getFormattedQuantityString(@PluralsRes id: Int, quantity: Int, formatValue: String) =
        context.getFormattedQuantityString(id, quantity, formatValue)
}