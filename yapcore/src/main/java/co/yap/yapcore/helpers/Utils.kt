package co.yap.yapcore.helpers

import android.app.Activity
import android.app.AlertDialog
import android.content.*
import android.content.Intent.ACTION_VIEW
import android.content.res.Resources
import android.net.Uri
import android.os.Build
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.view.inputmethod.InputMethodManager
import android.widget.ProgressBar
import androidx.annotation.ColorRes
import co.yap.yapcore.R
import com.google.i18n.phonenumbers.PhoneNumberUtil
import java.text.DecimalFormat
import java.util.*
import java.util.regex.Pattern


object Utils {
    fun getColor(context: Context, @ColorRes color: Int) =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            context.resources.getColor(color, null)
        } else {
            context.resources.getColor(color)
        }

    fun requestKeyboard(view: View, request: Boolean, forced: Boolean) {
        view.requestFocus()
        if (forced) {
            (view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).toggleSoftInput(
                InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY
            )
        } else if (request) {
            (view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).toggleSoftInput(
                InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_IMPLICIT_ONLY
            )
        }

    }

    fun hideKeyboard(view: View?) {
        view?.let { v ->
            val imm =
                view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            imm?.hideSoftInputFromWindow(v.windowToken, 0)
        }
    }

    fun createProgressDialog(context: Context): AlertDialog {
        val layoutInflater: LayoutInflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = layoutInflater.inflate(R.layout.progress_dialogue_fragment, null)
        view.findViewById<ProgressBar>(R.id.progressBar2).indeterminateDrawable.setColorFilter(
            getColor(
                context,
                R.color.colorPrimaryDark
            ), android.graphics.PorterDuff.Mode.SRC_IN
        )
        return AlertDialog.Builder(context).run {
            setView(view)
            setCancelable(false)
            create()
        }.apply {
            window?.setBackgroundDrawableResource(android.R.color.transparent)
            requestWindowFeature(Window.FEATURE_NO_TITLE)
        }
    }

    fun copyToClipboard(context: Context, text: CharSequence) {
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("YapAccount", text)
        clipboard.primaryClip = clip
    }

    fun getFormattedCurrency(num: String?): String {
        return if ("" != num && null != num) {
            val m = java.lang.Double.parseDouble(num)
            val formatter = DecimalFormat("###,###,##0.00")
            formatter.format(m)
        } else {
            ""
        }
    }

    fun getFormattedCurrencyWithoutDecimal(num: String?): String {
        return if ("" != num && null != num) {
            val m = java.lang.Double.parseDouble(num)
            val formatter = DecimalFormat("#,###")
            formatter.format(m)
        } else {
            ""
        }
    }

    fun getFormattedCurrencyWithoutComma(num: String?): String {
        return if ("" != num && null != num) {
            val m = java.lang.Double.parseDouble(num)
            val formatter = DecimalFormat("########0.00")
            formatter.format(m)
        } else {
            ""
        }
    }

    fun convertDpToPx(context: Context, dp: Float): Float {
        return dp * context.resources.displayMetrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT
    }

    fun convertPxToDp(context: Context, px: Float): Float {
        return px / context.resources.displayMetrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT
    }

    fun getNavigationBarHeight(activity: Activity): Int {
        val metrics = DisplayMetrics()
        activity.windowManager.defaultDisplay.getMetrics(metrics)
        val usableHeight = metrics.heightPixels
        activity.windowManager.defaultDisplay.getRealMetrics(metrics)
        val realHeight = metrics.heightPixels
        return if (realHeight > usableHeight)
            realHeight - usableHeight
        else
            0
    }

    fun getCardDimensions(context: Context, width: Int, height: Int): IntArray {
        val dimensions = IntArray(2)
        dimensions[0] = getDimensionInPercent(context, true, width)
        dimensions[1] = getDimensionInPercent(context, false, height)
        return dimensions
    }

    fun getDimensionInPercent(context: Context, isWidth: Boolean, percent: Int): Int {
        val displayMetrics = context.resources.displayMetrics
        return if (isWidth) {
            ((displayMetrics.widthPixels.toDouble() / 100) * percent).toInt()
        } else {
            if (hasNavBar(context.resources)) {
                //val h = getNavBarHeight(context.resources)
                //val bottom = convertDpToPx(context, 56f)
                (((displayMetrics.heightPixels.toDouble() - getNavBarHeight(context.resources)) / 100) * percent).toInt()
            } else {
                ((displayMetrics.heightPixels.toDouble() / 100) * percent).toInt()
            }
        }
    }

    private fun hasNavBar(resources: Resources): Boolean {
        val id = resources.getIdentifier("config_showNavigationBar", "bool", "android")
        return id > 0 && resources.getBoolean(id)
    }

    private fun getNavBarHeight(resources: Resources): Int {

        val resourceId: Int = resources.getIdentifier("navigation_bar_height", "dimen", "android")
        if (resourceId > 0) {
            return resources.getDimensionPixelSize(resourceId)
        }
        return 0
    }

    fun isEmulator(): Boolean {
        return (Build.FINGERPRINT.startsWith("generic")
                || Build.FINGERPRINT.startsWith("unknown")
                || Build.MODEL.contains("google_sdk")
                || Build.MODEL.contains("Emulator")
                || Build.MODEL.contains("Android SDK built for x86")
                || Build.MANUFACTURER.contains("Genymotion")
                || Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic")
                || "google_sdk" == Build.PRODUCT)
    }

    fun validateEmail(email: String): Boolean {
        var isValidEmail = false
        if ("" == email.trim { it <= ' ' }) {
            isValidEmail = false
        } else if (isValidEmail(email)) {
            isValidEmail = true
        } else {
            return isValidEmail
        }
        return isValidEmail
    }

    private fun isValidEmail(email: String): Boolean {
        var inputStr: CharSequence = ""
        var isValid = false
        val expression =
            //   "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"
            "^[a-zA-Z0-9._-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)+\$"
        // with plus       String expression = "^[_A-Za-z0-9-+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        inputStr = email
        val pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE)
        val matcher = pattern.matcher(inputStr)

        if (matcher.matches()) {
            isValid = true
        }
        return isValid
    }

    fun setSpan(
        startIndex: Int,
        endIndex: Int,
        wordtoSpan: SpannableString,
        color: Int
    ): SpannableString {
        wordtoSpan.setSpan(
            ForegroundColorSpan(color),
            startIndex,
            endIndex,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        wordtoSpan.setSpan(
            android.text.style.StyleSpan(android.graphics.Typeface.BOLD),
            startIndex,
            endIndex,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        return wordtoSpan
    }

    fun shortName(cardFullName: String): String {
        var cardFullName = cardFullName
        cardFullName = cardFullName.trim { it <= ' ' }
        var shortName = ""
        if (cardFullName.isNotEmpty() && cardFullName.contains(" ")) {
            val nameStr =
                cardFullName.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            val firstName = nameStr[0]
            val lastName = nameStr[nameStr.size - 1]
            shortName = firstName.substring(0, 1) + lastName.substring(0, 1)
            return shortName.toUpperCase()
        } else if (cardFullName.length > 0) {
            val nameStr =
                cardFullName.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            val firstName = nameStr[0]
            shortName = firstName.substring(0, 1)
            return shortName.toUpperCase()
        }
        return shortName.toUpperCase()
    }

    fun formatePhoneWithPlus(phoneNumber: String): String {
        if (phoneNumber.startsWith("00")) {
            return phoneNumber.replaceRange(
                0,
                2,
                "+"
            )
        } else {
            return phoneNumber.replaceRange(
                0,
                0,
                "+"
            )
        }
    }

    fun getFormattedMobileNumber(countryCode: String, mobile: String): String {
        return countryCode.trim() + " " + mobile.trim().replace(countryCode.trim(), "")
    }

    fun openTwitter(context: Context) {
        var intent: Intent?
        try {
            context.packageManager.getPackageInfo("com.twitter.android", 0)
            intent = Intent(
                ACTION_VIEW,
                Uri.parse("twitter.com/intent/follow?screen_name=YapTweets")
            )
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        } catch (e: Exception) {
            // no Twitter app, revert to browser
            context.startActivity(
                Intent(
                    ACTION_VIEW,
                    Uri.parse("https://twitter.com/intent/follow?screen_name=YapTweets")
                )
            )
        }

    }

    fun openInstagram(context: Context) {
        val uri = Uri.parse("https://www.instagram.com/yapnow/")
        val likeIng = Intent(ACTION_VIEW, uri)
        likeIng.setPackage("com.instagram.android")

        try {
            context.startActivity(likeIng)
        } catch (e: ActivityNotFoundException) {
            context.startActivity(
                Intent(
                    ACTION_VIEW,
                    Uri.parse("https://www.instagram.com/yapnow/")
                )
            )
        }

    }

    fun getOpenFacebookIntent(context: Context): Intent {

        return try {
            context.packageManager.getPackageInfo("com.facebook.katana", 0)
            Intent(ACTION_VIEW, Uri.parse("fb://page/288432705359181"))
        } catch (e: Exception) {
            Intent(
                ACTION_VIEW,
                Uri.parse("https://www.facebook.com/Yap-Now-288432705359181/")
            )
        }

    }

    fun getFormattedPhone(mobileNo: String): String {
        val pnu = PhoneNumberUtil.getInstance()
        val pn = pnu.parse(mobileNo, Locale.getDefault().country)
        return pnu.format(pn, PhoneNumberUtil.PhoneNumberFormat.INTERNATIONAL)
    }

    fun getPhoneNumberCountryCode(mobileNo: String): String {
        return try {
            val phoneUtil = PhoneNumberUtil.getInstance()
            val pn = phoneUtil.parse(mobileNo, Locale.getDefault().country)
            //didt find any other way to get number zero
            return """00${pn.countryCode}"""
        } catch (e: Exception) {
            e.printStackTrace()
            ""
        }
    }

    fun getPhoneWithoutCountryCode(mobileNo: String): String {
        return try {
            val phoneUtil = PhoneNumberUtil.getInstance()
            val pn = phoneUtil.parse(mobileNo, Locale.getDefault().country)
            pn.nationalNumber.toString()
        } catch (e: Exception) {
            e.printStackTrace()
            ""
        }
    }

    fun shareText(context: Context, body: String) {
        val sharingIntent = Intent(Intent.ACTION_SEND)
        sharingIntent.type = "text/plain"
        // not set because ios team is not doing this.
        //sharingIntent.putExtra(Intent.EXTRA_SUBJECT, viewModel.state.title.get())
        sharingIntent.putExtra(Intent.EXTRA_TEXT, body)
        context.startActivity(Intent.createChooser(sharingIntent, "Share"))
    }
}