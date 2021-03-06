package co.yap.yapcore.helpers.extentions

import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Typeface
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.ConnectivityManager
import android.net.NetworkCapabilities.NET_CAPABILITY_INTERNET
import android.os.Parcelable
import android.text.*
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.view.WindowManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.Keep
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import co.yap.yapcore.enums.YAPThemes
import co.yap.yapcore.helpers.SharedPreferenceManager
import co.yap.modules.qrcode.BarcodeEncoder
import co.yap.modules.qrcode.BarcodeFormat
import co.yap.networking.customers.responsedtos.sendmoney.Country
import co.yap.yapcore.R
import co.yap.yapcore.helpers.Utils
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.google.android.material.navigation.NavigationView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException
import java.math.RoundingMode

@Keep
enum class ExtraType {
    STRING, INT, BOOLEAN, DOUBLE, LONG, PARCEABLE, SERIALIZEABLE, BUNDLE;
}

fun Intent.getValue(key: String, type: String): Any? {
    return if (hasExtra(key)) {
        return if (ExtraType.valueOf(type).name.isNotEmpty()) {
            when (ExtraType.valueOf(type)) {
                ExtraType.BOOLEAN ->
                    getBooleanExtra(key, false)
                ExtraType.STRING ->
                    getStringExtra(key)
                ExtraType.DOUBLE ->
                    getDoubleExtra(key, 0.0)
                ExtraType.INT ->
                    getIntExtra(key, 0)
                ExtraType.LONG ->
                    getLongExtra(key, 0)
                ExtraType.PARCEABLE ->
                    getParcelableExtra<Parcelable>(key)
                ExtraType.SERIALIZEABLE ->
                    getSerializableExtra(key)
                ExtraType.BUNDLE ->
                    getBundleExtra(key)
            }
        } else null
    } else return null
}
fun ImageView.loadImage(path: String, requestOptions: RequestOptions) {
    Glide.with(this)
        .load(path)
        .apply(requestOptions)
        .into(this)
}

fun ImageView.loadImage(resourceId: Int, requestOptions: RequestOptions) {
    Glide.with(this)
        .load(resourceId)
        .apply(requestOptions)
        .into(this)
}
fun ImageView.loadImage(path: String) {
    Glide.with(this)
        .load(path).centerCrop()
        .into(this)
}

fun ImageView.loadCardImage(path: String?) {
    Glide.with(this)
        .load(path)
        .placeholder(R.drawable.card_place_holder)
        .error(R.drawable.card_spare)
        .into(this)
}

fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }

        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }
    })
}

fun AppCompatActivity.replaceFragment(tag: String?, id: Int, fragment: Fragment) {
    val fragmentTransaction = supportFragmentManager.beginTransaction()
    fragmentTransaction.replace(id, fragment, tag)
    fragmentTransaction.commit()
}

fun RecyclerView.fixSwipeToRefresh(refreshLayout: SwipeRefreshLayout): RecyclerViewSwipeToRefresh {
    return RecyclerViewSwipeToRefresh(refreshLayout).also {
        this.addOnScrollListener(it)
    }
}

class RecyclerViewSwipeToRefresh(private val refreshLayout: SwipeRefreshLayout) :
    RecyclerView.OnScrollListener() {

    private val directionUp = -1

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        refreshLayout.isEnabled = !(recyclerView?.canScrollVertically(directionUp) ?: return)
    }

}


fun NavigationView?.navViewWidth(percent: Int) {
    this?.let {
        val params = it.layoutParams
        params.width = Utils.getDimensionInPercent(it.context, true, percent)
    }
}

fun Context?.isNetworkAvailable(): Boolean {
    val cm = this?.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
    val capabilities = cm.getNetworkCapabilities(cm.activeNetwork)
    return capabilities?.hasCapability(NET_CAPABILITY_INTERNET) == true
}

fun TextView.makeLinks(
    vararg links: Pair<String, View.OnClickListener>,
    @ColorInt color: Int = 0,
    underline: Boolean = false,
    isBold: Boolean = false
) {
    val spannableString = SpannableString(this.text)
    for (link in links) {
        val clickableSpan = object : ClickableSpan() {
            override fun onClick(view: View) {
                Selection.setSelection((view as TextView).text as Spannable, 0)
                view.invalidate()
                link.second.onClick(view)
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.isUnderlineText = underline
                if (isBold) ds.typeface = Typeface.DEFAULT_BOLD
            }
        }

        val startIndexOfLink = this.text.toString().indexOf(link.first)
        spannableString.setSpan(
            clickableSpan, startIndexOfLink, startIndexOfLink + link.first.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        if (color != 0) {
            spannableString.setSpan(
                ForegroundColorSpan(color),
                startIndexOfLink,
                startIndexOfLink + link.first.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
    }
    this.movementMethod =
        LinkMovementMethod.getInstance() // without LinkMovementMethod, link can not click
    this.setText(spannableString, TextView.BufferType.SPANNABLE)
}

fun Context?.switchTheme(theme: YAPThemes) {
    this?.let {
        SharedPreferenceManager.getInstance(it).setThemeValue(theme::class.simpleName.toString())
    }
}

/**
 * Extension method to get the TAG name for all object
 */
fun <T : Any> T.TAG() = this::class.simpleName
fun String.getCountryTwoDigitCodeFromThreeDigitCode(): String {
    if (this.isEmpty()) {
        return this
    }

    return this.substring(0, 2);
}

fun Double?.roundVal(): Double {
    val floatingMultiplier = (this ?: 0.0) * 100
    val rounded =
        floatingMultiplier.toBigDecimal().setScale(2, RoundingMode.HALF_UP)?.toDouble()
    val floatingDivisor = (rounded ?: 0.0).div(100)
    return floatingDivisor.toBigDecimal().setScale(2, RoundingMode.HALF_UP)?.toDouble() ?: 0.0
}

fun Double?.roundValHalfEven(): Double {
    val floatingMultiplier = (this ?: 0.0) * 100
    val rounded =
        floatingMultiplier.toBigDecimal().setScale(2, RoundingMode.HALF_EVEN)?.toDouble()
    val floatingDivisor = (rounded ?: 0.0).div(100)
    return floatingDivisor.toBigDecimal().setScale(2, RoundingMode.HALF_EVEN)?.toDouble() ?: 0.0
}

fun Context?.startSmsConsent() {
    this?.let {
        SmsRetriever.getClient(it).startSmsUserConsent(null)
            .addOnSuccessListener {

            }.addOnFailureListener {

            }
    }
}

fun Context.generateQrCode(resourceKey: String): Drawable? {
    var drawable: Drawable? = null
    try {
        val barcodeEncoder = BarcodeEncoder()
        val bitmap: Bitmap =
            barcodeEncoder.encodeBitmap(resourceKey, BarcodeFormat.QR_CODE, 400, 400)
        drawable = BitmapDrawable(resources, bitmap)
        return drawable
    } catch (e: Exception) {
    }
    return drawable
}

fun <T> isEqual(first: List<T>, second: List<T>): Boolean {
    if (first.size != second.size) {
        return false
    }

    return first.zip(second).all { (x, y) -> x == y }
}

fun Context?.readManifestPlaceholders(metaDataName: String?): String =
    this?.let {
        val ai: ApplicationInfo = it.applicationContext.packageManager.getApplicationInfo(
            it.applicationContext.packageName,
            PackageManager.GET_META_DATA
        )
        if (metaDataName.isNullOrBlank().not())
            ai.metaData[metaDataName].toString()
        else ""
    } ?: ""

fun String?.jsonToList(): ArrayList<Country> {
    return if (this.isNullOrBlank().not()) {
        try {
            val gson = Gson()
            val type = object : TypeToken<java.util.ArrayList<Country?>?>() {}.type
            gson.fromJson<ArrayList<Country>>(this, type)
        } catch (e: Exception) {
            arrayListOf<Country>()
        }
    } else arrayListOf()
}

fun <T> ArrayList<Country>.listToJson(): String? {
    val gson = Gson()
    return gson.toJson(this)
}
