package co.yap.widgets.mobile

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.os.Build
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.yap.yapcore.R
import java.lang.reflect.Field


internal object CountryCodeDialog {
    private val sEditorField: Field?
    private val sCursorDrawableField: Field?
    private val sCursorDrawableResourceField: Field?
    var dialog: Dialog? = null
    var context: Context? = null

    init {
        var editorField: Field? = null
        var cursorDrawableField: Field? = null
        var cursorDrawableResourceField: Field? = null
        var exceptionThrown = false
        try {
            cursorDrawableResourceField = TextView::class.java.getDeclaredField("mCursorDrawableRes")
            cursorDrawableResourceField!!.isAccessible = true
            val drawableFieldClass: Class<*>
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                drawableFieldClass = TextView::class.java
            } else {
                editorField = TextView::class.java.getDeclaredField("mEditor")
                editorField!!.isAccessible = true
                drawableFieldClass = editorField.type
            }
            cursorDrawableField = drawableFieldClass.getDeclaredField("mCursorDrawable")
            cursorDrawableField!!.isAccessible = true
        } catch (e: Exception) {
            exceptionThrown = true
        }

        if (exceptionThrown) {
            sEditorField = null
            sCursorDrawableField = null
            sCursorDrawableResourceField = null
        } else {
            sEditorField = editorField
            sCursorDrawableField = cursorDrawableField
            sCursorDrawableResourceField = cursorDrawableResourceField
        }
    }

    @JvmOverloads
    fun openCountryCodeDialog(codePicker: CountryCodePicker, countryNameCode: String? = null) {
        context = codePicker.context
        dialog = Dialog(context!!)
        codePicker.refreshCustomMasterList()
        codePicker.refreshPreferredCountries()
        val masterCountries = CCPCountry.getCustomMasterCountryList(context, codePicker)
        dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog!!.window!!.setContentView(R.layout.layout_picker_dialog)

        //keyboard
        if (codePicker.isSearchAllowed() && codePicker.isDialogKeyboardAutoPopup()) {
            dialog!!.window!!.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)
        } else {
            dialog!!.window!!.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
        }


        //dialog views
        val recyclerView_countryDialog = dialog!!.findViewById<View>(R.id.recycler_countryDialog) as RecyclerView
        val textViewTitle = dialog!!.findViewById<View>(R.id.textView_title) as TextView
        val rlQueryHolder = dialog!!.findViewById<View>(R.id.rl_query_holder) as RelativeLayout
        val imgClearQuery = dialog!!.findViewById<View>(R.id.img_clear_query) as ImageView
        val editText_search = dialog!!.findViewById<View>(R.id.editText_search) as EditText
        val textView_noResult = dialog!!.findViewById<View>(R.id.textView_noresult) as TextView
        val rlHolder = dialog!!.findViewById<View>(R.id.rl_holder) as RelativeLayout
        val imgDismiss = dialog!!.findViewById<View>(R.id.img_dismiss) as ImageView

        // type faces
        //set type faces
        try {
            if (codePicker.getDialogTypeFace() != null) {
                if (codePicker.getDialogTypeFaceStyle() !== CountryCodePicker.DEFAULT_UNSET) {
                    textView_noResult.setTypeface(codePicker.getDialogTypeFace(), codePicker.getDialogTypeFaceStyle())
                    editText_search.setTypeface(codePicker.getDialogTypeFace(), codePicker.getDialogTypeFaceStyle())
                    textViewTitle.setTypeface(codePicker.getDialogTypeFace(), codePicker.getDialogTypeFaceStyle())
                } else {
                    textView_noResult.typeface = codePicker.getDialogTypeFace()
                    editText_search.typeface = codePicker.getDialogTypeFace()
                    textViewTitle.typeface = codePicker.getDialogTypeFace()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        //dialog background color
        if (codePicker.getDialogBackgroundColor() !== 0) {
            rlHolder.setBackgroundColor(codePicker.getDialogBackgroundColor())
        }

        //close button visibility
        if (codePicker.isShowCloseIcon()) {
            imgDismiss.visibility = View.VISIBLE
            imgDismiss.setOnClickListener { dialog!!.dismiss() }
        } else {
            imgDismiss.visibility = View.GONE
        }

        //title
        if (!codePicker.getCcpDialogShowTitle()) {
            textViewTitle.visibility = View.GONE
        }

        //clear button color and title color
        if (codePicker.getDialogTextColor() !== 0) {
            val textColor = codePicker.getDialogTextColor()
            imgClearQuery.setColorFilter(textColor)
            imgDismiss.setColorFilter(textColor)
            textViewTitle.setTextColor(textColor)
            textView_noResult.setTextColor(textColor)
            editText_search.setTextColor(textColor)
            editText_search.setHintTextColor(
                Color.argb(
                    100,
                    Color.red(textColor),
                    Color.green(textColor),
                    Color.blue(textColor)
                )
            )
        }


        //editText tint
        if (codePicker.getDialogSearchEditTextTintColor() !== 0) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                editText_search.backgroundTintList =
                    ColorStateList.valueOf(codePicker.getDialogSearchEditTextTintColor())
                setCursorColor(editText_search, codePicker.getDialogSearchEditTextTintColor())
            }
        }


        //add messages to views
        textViewTitle.setText(codePicker.getDialogTitle())
        editText_search.setHint(codePicker.getSearchHintText())
        textView_noResult.setText(codePicker.getNoResultACK())

        //this will make dialog compact
        if (!codePicker.isSearchAllowed()) {
            val params = recyclerView_countryDialog.layoutParams as RelativeLayout.LayoutParams
            params.height = RecyclerView.LayoutParams.WRAP_CONTENT
            recyclerView_countryDialog.layoutParams = params
        }

        val cca = CountryCodeAdapter(
            context,
            masterCountries,
            codePicker,
            rlQueryHolder,
            editText_search,
            textView_noResult,
            dialog,
            imgClearQuery
        )
        recyclerView_countryDialog.layoutManager = LinearLayoutManager(context)
        recyclerView_countryDialog.adapter = cca

        //fast scroller
        val fastScroller = dialog!!.findViewById<View>(R.id.fastscroll) as FastScroller
        fastScroller.setRecyclerView(recyclerView_countryDialog)
        if (codePicker.isShowFastScroller()) {
            if (codePicker.getFastScrollerBubbleColor() !== 0) {
                fastScroller.setBubbleColor(codePicker.getFastScrollerBubbleColor())
            }

            if (codePicker.getFastScrollerHandleColor() !== 0) {
                fastScroller.setHandleColor(codePicker.getFastScrollerHandleColor())
            }

            if (codePicker.getFastScrollerBubbleTextAppearance() !== 0) {
                try {
                    fastScroller.setBubbleTextAppearance(codePicker.getFastScrollerBubbleTextAppearance())
                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }

        } else {
            fastScroller.setVisibility(View.GONE)
        }

        dialog!!.setOnDismissListener { dialogInterface ->
            hideKeyboard(context)
            if (codePicker.getDialogEventsListener() != null) {
                codePicker.getDialogEventsListener().onCcpDialogDismiss(dialogInterface)
            }
        }

        dialog!!.setOnCancelListener { dialogInterface ->
            hideKeyboard(context)
            if (codePicker.getDialogEventsListener() != null) {
                codePicker.getDialogEventsListener().onCcpDialogCancel(dialogInterface)
            }
        }

        //auto scroll to mentioned countryNameCode
        if (countryNameCode != null) {
            var isPreferredCountry = false
            if (codePicker.preferredCountries != null) {
                for (preferredCountry in codePicker.preferredCountries) {
                    if (preferredCountry.nameCode.equalsIgnoreCase(countryNameCode)) {
                        isPreferredCountry = true
                        break
                    }
                }
            }

            //if selection is from preferred countries then it should show all (or maximum) preferred countries.
            // don't scroll if it was one of those preferred countries
            if (!isPreferredCountry) {
                var preferredCountriesOffset = 0
                if (codePicker.preferredCountries != null && codePicker.preferredCountries.size() > 0) {
                    preferredCountriesOffset = codePicker.preferredCountries.size() + 1 //+1 is for divider
                }
                for (i in masterCountries.indices) {
                    if (masterCountries.get(i).nameCode.equalsIgnoreCase(countryNameCode)) {
                        recyclerView_countryDialog.scrollToPosition(i + preferredCountriesOffset)
                        break
                    }
                }
            }
        }

        dialog!!.show()
        if (codePicker.getDialogEventsListener() != null) {
            codePicker.getDialogEventsListener().onCcpDialogOpen(dialog)
        }
    }

    private fun hideKeyboard(context: Context?) {
        if (context is Activity) {
            val activity = context as Activity?
            val imm = activity!!.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            //Find the currently focused view, so we can grab the correct window token from it.
            var view = activity.currentFocus
            //If no view currently has focus, create a new one, just so we can grab a window token from it
            if (view == null) {
                view = View(activity)
            }
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    fun setCursorColor(editText: EditText, color: Int) {
        if (sCursorDrawableField == null) {
            return
        }
        try {
            val drawable = getDrawable(
                editText.context,
                sCursorDrawableResourceField!!.getInt(editText)
            )
            drawable!!.setColorFilter(color, PorterDuff.Mode.SRC_IN)
            sCursorDrawableField.set(
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN)
                    editText
                else
                    sEditorField!!.get(editText), arrayOf(drawable, drawable)
            )
        } catch (ignored: Exception) {
        }

    }

    fun clear() {
        if (dialog != null) {
            dialog!!.dismiss()
        }
        dialog = null
        context = null
    }

    private fun getDrawable(context: Context, id: Int): Drawable? {
        return if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            context.resources.getDrawable(id)
        } else {
            context.getDrawable(id)
        }
    }
}

