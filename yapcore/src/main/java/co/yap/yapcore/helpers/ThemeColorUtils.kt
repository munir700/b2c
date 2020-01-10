package co.yap.yapcore.helpers

import android.annotation.SuppressLint
import android.content.Context
import android.util.TypedValue
import co.yap.yapcore.R


@SuppressLint("StaticFieldLeak")
object ThemeColorUtils {

// default colors

    fun colorPrimaryDefaultAttribute(context: Context): Int {
        return TypedValue().also { context.theme.resolveAttribute(R.attr.colorPrimary, it, true) }
            .data

    }

    fun colorPrimaryDarkDefaultAttributeTheme(context: Context): Int {
        return TypedValue().also {
            context.theme.resolveAttribute(
                R.attr.colorPrimaryDark,
                it,
                true
            )
        }.data

    }

    // customized theme based pallet

    fun colorPrimaryAttribute(context: Context): Int {
        return TypedValue().also {
            context.theme.resolveAttribute(
                R.attr.colorPrimaryAttr,
                it,
                true
            )
        }
            .data
    }

    fun colorPrimaryDarkAttribute(context: Context): Int {
        return TypedValue().also {
            context.theme.resolveAttribute(
                R.attr.colorPrimaryDarkAttr,
                it,
                true
            )
        }.resourceId
    }

    fun colorAccentAttribute(context: Context): Int {
        return TypedValue().also {
            context.theme.resolveAttribute(
                R.attr.colorPrimaryAccentAttr,
                it,
                true
            )
        }.resourceId
    }

}