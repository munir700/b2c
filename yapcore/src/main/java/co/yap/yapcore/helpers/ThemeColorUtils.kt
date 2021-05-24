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
        }.data
    }

    fun colorPrimaryDarkAttribute(context: Context): Int {
        return TypedValue().also {
            context.theme.resolveAttribute(
                R.attr.colorPrimaryDarkAttr,
                it,
                true
            )
        }.data
    }

    fun colorAccentAttribute(context: Context): Int {
        return TypedValue().also {
            context.theme.resolveAttribute(
                R.attr.colorPrimaryAccentAttr,
                it,
                true
            )
        }.data
    }

    fun colorPrimaryWarmAttribute(context: Context): Int {
        return TypedValue().also {
            context.theme.resolveAttribute(
                R.attr.colorPrimaryWarmAttr,
                it,
                true
            )
        }.data
    }


    fun colorPrimaryLightAttribute(context: Context): Int {
        return TypedValue().also {
            context.theme.resolveAttribute(
                R.attr.colorPrimaryLightAttr,
                it,
                true
            )
        }.data
    }


    fun colorPrimaryAltAttribute(context: Context): Int {
        return TypedValue().also {
            context.theme.resolveAttribute(
                R.attr.colorPrimaryAltAttr,
                it,
                true
            )
        }.data
    }


    fun colorPrimarySoftAttribute(context: Context): Int {
        return TypedValue().also {
            context.theme.resolveAttribute(
                R.attr.colorPrimarySoftAttr,
                it,
                true
            )
        }.data
    }


    fun colorPrimaryDisabledBtnAttribute(context: Context): Int {
        return TypedValue().also {
            context.theme.resolveAttribute(
                R.attr.colorPrimaryDisabledBtnAttr,
                it,
                true
            )
        }.data
    }


    fun colorPressedBtnStateAttribute(context: Context): Int {
        return TypedValue().also {
            context.theme.resolveAttribute(
                R.attr.colorPressedBtnStateAttr,
                it,
                true
            )
        }.data
    }

    fun colorIconsTintAttrAttribute(context: Context): Int {
        return TypedValue().also {
            context.theme.resolveAttribute(
                R.attr.colorIconsTintAttr,
                it,
                true
            )
        }.data
    }

    fun colorPrimAttribute(context: Context): Int {
        return TypedValue().also {
            context.theme.resolveAttribute(
                R.attr.colorPrimAttr,
                it,
                true
            )
        }.data
    }

    fun colorSendMoneyToolBarAttribute(context: Context): Int {
        return TypedValue().also {
            context.theme.resolveAttribute(
                R.attr.colorSendMoneyToolBarAttr,
                it,
                true
            )
        }.data
    }

    fun colorSearchViewHintAttribute(context: Context): Int {
        return TypedValue().also {
            context.theme.resolveAttribute(
                R.attr.colorSearchViewHintAttr,
                it,
                true
            )
        }.data
    }

    fun colorSearchViewEditTexAttribute(context: Context): Int {
        return TypedValue().also {
            context.theme.resolveAttribute(
                R.attr.colorSearchViewEditTexAttr,
                it,
                true
            )
        }.data
    }


    fun colorDisabledLightAttribute(context: Context): Int {
        return TypedValue().also {
            context.theme.resolveAttribute(
                R.attr.colorDisabledLightAttr,
                it,
                true
            )
        }.data
    }

    fun colorSecondaryAttribute(context: Context): Int {
        return TypedValue().also {
            context.theme.resolveAttribute(
                R.attr.colorSecondaryAttr,
                it,
                true
            )
        }.data
    }

    fun colorDisabledLightSecondaryAttribute(context: Context): Int {
        return TypedValue().also {
            context.theme.resolveAttribute(
                R.attr.colorDisabledLightSecondaryAttr,
                it,
                true
            )
        }.data
    }

    fun colorStatusBarSuccessAttribute(context: Context): Int {
        return TypedValue().also {
            context.theme.resolveAttribute(
                R.attr.colorStatusBarSuccessAttr,
                it,
                true
            )
        }
            .data

    }


    fun colorCircularTextAttribute(context: Context): Int {
        return TypedValue().also {
            context.theme.resolveAttribute(
                R.attr.colorCircularTextAttr,
                it,
                true
            )
        }.data
    }


    fun colorCircularBgAttribute(context: Context): Int {
        return TypedValue().also {
            context.theme.resolveAttribute(
                R.attr.colorCircularBgAttr,
                it,
                true
            )
        }.data
    }

}
