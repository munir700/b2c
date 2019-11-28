package co.yap.yapcore.helpers

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.*
import android.content.Intent.ACTION_VIEW
import android.content.res.Resources
import android.icu.util.TimeZone
import android.net.Uri
import android.os.Build
import android.telephony.TelephonyManager
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.style.AbsoluteSizeSpan
import android.text.style.ForegroundColorSpan
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.view.inputmethod.InputMethodManager
import android.widget.ProgressBar
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import co.yap.networking.customers.requestdtos.Contact
import co.yap.translation.Strings
import co.yap.translation.Translator
import co.yap.yapcore.R
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.managers.MyUserManager
import com.google.i18n.phonenumbers.PhoneNumberUtil
import java.math.RoundingMode
import java.text.DecimalFormat
import java.util.*
import java.util.regex.Pattern


object Utils {
    var context: Context? = null

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
        return try {
            if ("" != num && null != num) {
                val m = java.lang.Double.parseDouble(num)
                val formatter = DecimalFormat("###,###,##0.00")
                formatter.format(m)
            } else {
                ""
            }
        } catch (e: Exception) {
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
        return try {
            if ("" != num && null != num) {
                val m = java.lang.Double.parseDouble(num)
                val formatter = DecimalFormat("########0.00")
                formatter.format(m)
            } else {
                ""
            }
        } catch (ex: Exception) {
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
        return try {
            val pnu = PhoneNumberUtil.getInstance()
            val pn = pnu.parse(mobileNo, Locale.getDefault().country)
            return pnu.format(pn, PhoneNumberUtil.PhoneNumberFormat.INTERNATIONAL)
        } catch (e: Exception) {
            e.printStackTrace()
            ""
        }
    }

    fun getFormattedPhoneNumber(context: Context, mobileNo: String): String {
        return try {
            val pnu = PhoneNumberUtil.getInstance()
            val pn = pnu.parse(mobileNo, getDefaultCountryCode(context))
            return pnu.format(pn, PhoneNumberUtil.PhoneNumberFormat.INTERNATIONAL)
        } catch (e: Exception) {
            e.printStackTrace()
            ""
        }
    }

    fun getSppnableStringForAmount(
        context: Context,
        staticString: String,
        currencyType: String,
        amount: String
    ): SpannableStringBuilder? {
        return try {
            val fcs = ForegroundColorSpan(ContextCompat.getColor(context, R.color.colorPrimaryDark))
            val separated = staticString.split(currencyType)
            val str = SpannableStringBuilder(staticString)

            str.setSpan(
                fcs,
                separated[0].length,
                separated[0].length + currencyType.length + getFormattedCurrency(amount).length + 1,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            str
        } catch (e: Exception) {
            return null
        }


    }

    fun getSpannableString(
        context: Context,
        staticString: String?,
        startDestination: String?
    ): SpannableStringBuilder? {
        return try {
            val fcs = ForegroundColorSpan(ContextCompat.getColor(context, R.color.colorPrimaryDark))
            val separated = staticString?.split(startDestination!!)
            val str = SpannableStringBuilder(staticString)

            str.setSpan(
                fcs,
                separated?.get(0)!!.length,
                separated[0].length + startDestination!!.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            str
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }

    }

    fun getSpannableStringForLargerBalance(
        context: Context,
        staticString: String,
        currencyType: String,
        amount: String
    ): SpannableStringBuilder? {
        return try {
            var textSize = context.resources.getDimensionPixelSize(R.dimen.text_size_h4)
            val fcs = ForegroundColorSpan(ContextCompat.getColor(context, R.color.colorPrimaryDark))
            val fcsLarge = AbsoluteSizeSpan(textSize)
            val separated = staticString.split(currencyType)
            val str = SpannableStringBuilder(staticString)

            str.setSpan(
                fcs,
                separated[0].length,
                separated[0].length + currencyType.length + getFormattedCurrency(amount).length + 1,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            str.setSpan(
                fcsLarge,
                separated[0].length,
                separated[0].length + currencyType.length + getFormattedCurrency(amount).length + 1,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            str
        } catch (e: Exception) {
            return null
        }


    }

    @SuppressLint("DefaultLocale")
    fun getCountryCodeFromTelephony(context: Context): String {
        val tm = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        return tm.networkCountryIso.toUpperCase()
    }

    fun getPhoneNumberCountryCodeForAPI(
        defaultCountryCode: String,
        mobileNo: String
    ): String {
        return try {
            val phoneUtil = PhoneNumberUtil.getInstance()
            val pn = phoneUtil.parse(mobileNo, defaultCountryCode)
            //didt find any other way to get number zero
            return """00${pn.countryCode}"""
        } catch (e: Exception) {
            e.printStackTrace()
            ""
        }
    }

    fun getPhoneWithoutCountryCode(defaultCountryCode: String, mobileNo: String): String {
        return try {
            val phoneUtil = PhoneNumberUtil.getInstance()
            val pn = phoneUtil.parse(mobileNo, defaultCountryCode)
            pn.nationalNumber.toString()
        } catch (e: Exception) {
            e.printStackTrace()
            ""
        }
    }

//    fun getCountryIsoCode(number: String): String {
//        val validatedNumber = "+$number"
//        val pnu = PhoneNumberUtil.getInstance()
//        val phoneNumber = try {
//            pnu.parse(validatedNumber, null)
//        } catch (e: NumberParseException) {
//            Log.e("UTILS", "error during parsing a number")
//            return ""
//        }
//            ?: return ""
//
//        return pnu.getRegionCodeForCountryCode(phoneNumber.countryCode)
//    }

    fun getDefaultCountryCode(context: Context): String {
        val countryCode = getCountryCodeFromTimeZone(context)
        return if (countryCode == "") "AE" else countryCode
    }

    private fun getCountryCodeFromTimeZone(context: Context): String {
        val curTimeZoneId = Calendar.getInstance().timeZone.id
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            TimeZone.getRegion(curTimeZoneId)
        } else {
            getCountryCodeFromTelephony(context)
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


    fun getContactColors(context: Context, position: Int): Int {
        return ContextCompat.getColor(context, contactColors[position % contactColors.size])
    }

    fun getContactBackground(context: Context, position: Int) =
        ContextCompat.getDrawable(context, backgrounds[position % backgrounds.size])


    fun getBackgroundColor(context: Context, position: Int) =
        ContextCompat.getColor(context, backgroundColors[position % backgroundColors.size])

    private val backgrounds = intArrayOf(
        R.drawable.bg_round_light_red,
        R.drawable.bg_round_light_blue,
        R.drawable.bg_round_light_green,
        R.drawable.bg_round_light_orange
    )

    private val backgroundColors = intArrayOf(
        R.color.bg_round_light_red,
        R.color.bg_round_light_blue,
        R.color.bg_round_light_green,
        R.color.bg_round_light_orange
    )

    private val contactColors = intArrayOf(
        R.color.colorSecondaryMagenta,
        R.color.colorSecondaryBlue,
        R.color.colorSecondaryGreen,
        R.color.colorSecondaryOrange
    )

    fun getTwoDecimalPlaces(value: Double): Double {
        val df = DecimalFormat("#.##")
        df.roundingMode = RoundingMode.CEILING
        return df.format(value).toDouble()
    }

    fun getBody(context: Context, contact: Contact): String {
        return Translator.getString(
            context,
            Strings.common_display_text_y2y_share,
            StringUtils.getFirstname(contact.title!!),
            MyUserManager.user!!.currentCustomer.firstName,
            Constants.URL_SHARE_APP_STORE,
            Constants.URL_SHARE_PLAY_STORE
        )
    }

    fun getGeneralInvitationBody(context: Context): String {
        return Translator.getString(
            context,
            Strings.common_display_text_y2y_general_share,
            MyUserManager.user!!.currentCustomer.firstName,
            Constants.URL_SHARE_APP_STORE,
            Constants.URL_SHARE_PLAY_STORE
        )
    }

    fun getFormattedCardNumber(cardNumber: String): String {
        return if (cardNumber.length == 4)
            "XXXX XXXX XXXX $cardNumber"
        else
            "XXXX XXXX XXXX XXXX"

    }

    fun ConfirmAddBeneficiary(context: Context) {
        androidx.appcompat.app.AlertDialog.Builder(context)
            .setTitle(
                Translator.getString(
                    context,
                    R.string.screen_add_beneficiary_detail_display_text_alert_title
                )
            )
            .setMessage(
                Translator.getString(
                    context,
                    R.string.screen_add_beneficiary_detail_display_button_block_alert_description
                )
            )
            .setPositiveButton(Translator.getString(
                context,
                R.string.screen_add_beneficiary_detail_display_button_block_alert_yes
            ),
                DialogInterface.OnClickListener { dialog, which ->
                    //                    doLogout()
                })

            .setNegativeButton(
                Translator.getString(
                    context,
                    R.string.screen_add_beneficiary_detail_display_button_block_alert_no
                ),
                null
            )
            .show()

    }

}