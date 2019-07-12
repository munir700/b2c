package co.yap.widgets.mobile

import android.app.Dialog
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import co.yap.yapcore.R
import com.futuremind.recyclerviewfastscroll.SectionTitleProvider
import java.util.ArrayList


internal class CountryCodeAdapter(
    var context: Context,
    countries: List<CCPCountry>,
    var codePicker: CountryCodePicker,
    var rlQueryHolder: RelativeLayout,
    var editText_search: EditText?,
    var textView_noResult: TextView,
    var dialog: Dialog,
    var imgClearQuery: ImageView
) :
    RecyclerView.Adapter<CountryCodeAdapter.CountryCodeViewHolder>(), SectionTitleProvider {
    var filteredCountries: List<CCPCountry>? = null
    var masterCountries: List<CCPCountry>? = null
    var inflater: LayoutInflater
    var preferredCountriesCount = 0

    init {
        this.masterCountries = countries
        this.inflater = LayoutInflater.from(context)
        this.filteredCountries = getFilteredCountries("")
        setSearchBar()
    }

    private fun setSearchBar() {
        if (codePicker.isSearchAllowed) {
            imgClearQuery.visibility = View.GONE
            setTextWatcher()
            setQueryClearListener()
        } else {
            rlQueryHolder.visibility = View.GONE
        }
    }

    private fun setQueryClearListener() {
        imgClearQuery.setOnClickListener { editText_search!!.setText("") }
    }

    /**
     * add textChangeListener, to apply new query each time editText get text changed.
     */
    private fun setTextWatcher() {
        if (this.editText_search != null) {
            this.editText_search!!.addTextChangedListener(object : TextWatcher {

                override fun afterTextChanged(s: Editable) {}

                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                    applyQuery(s.toString())
                    if (s.toString().trim { it <= ' ' } == "") {
                        imgClearQuery.visibility = View.GONE
                    } else {
                        imgClearQuery.visibility = View.VISIBLE
                    }
                }
            })

            this.editText_search!!.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, event ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    val `in` = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    `in`.hideSoftInputFromWindow(editText_search!!.windowToken, 0)
                    return@OnEditorActionListener true
                }

                false
            })
        }
    }

    /**
     * Filter country list for given keyWord / query.
     * Lists all countries that contains @param query in country's name, name code or phone code.
     *
     * @param query : text to match against country name, name code or phone code
     */
    private fun applyQuery(query: String) {
        var query = query


        textView_noResult.visibility = View.GONE
        query = query.toLowerCase()

        //if query started from "+" ignore it
        if (query.length > 0 && query[0] == '+') {
            query = query.substring(1)
        }

        filteredCountries = getFilteredCountries(query)

        if (filteredCountries!!.size == 0) {
            textView_noResult.visibility = View.VISIBLE
        }
        notifyDataSetChanged()
    }

    private fun getFilteredCountries(query: String): List<CCPCountry> {
        val tempCCPCountryList = ArrayList<CCPCountry>()
        preferredCountriesCount = 0
        if (codePicker.preferredCountries != null && codePicker.preferredCountries!!.size > 0) {
            for (CCPCountry in codePicker.preferredCountries!!) {
                if (CCPCountry.isEligibleForQuery(query)) {
                    tempCCPCountryList.add(CCPCountry)
                    preferredCountriesCount++
                }
            }

            if (tempCCPCountryList.size > 0) { //means at least one preferred country is added.
                val divider: CCPCountry? = null
                tempCCPCountryList.add(divider!!)
                preferredCountriesCount++
            }
        }

        for (CCPCountry in masterCountries!!) {
            if (CCPCountry.isEligibleForQuery(query)) {
                tempCCPCountryList.add(CCPCountry)
            }
        }
        return tempCCPCountryList
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): CountryCodeViewHolder {
        val rootView = inflater.inflate(R.layout.layout_recycler_country_tile, viewGroup, false)
        return CountryCodeViewHolder(rootView)
    }

    override fun onBindViewHolder(countryCodeViewHolder: CountryCodeViewHolder, i: Int) {
        countryCodeViewHolder.setCountry(filteredCountries!![i])
        if (filteredCountries!!.size > i && filteredCountries!![i] != null) {
            countryCodeViewHolder.mainView.setOnClickListener { view ->
                if (filteredCountries != null && filteredCountries!!.size > i) {
                    codePicker.onUserTappedCountry(filteredCountries!![i])
                }
                if (view != null && filteredCountries != null && filteredCountries!!.size > i && filteredCountries!![i] != null) {
                    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(view.windowToken, 0)
                    dialog.dismiss()
                }
            }
        } else {
            countryCodeViewHolder.mainView.setOnClickListener(null)
        }

    }

    override fun getItemCount(): Int {
        return filteredCountries!!.size
    }

    override fun getSectionTitle(position: Int): String {
        val ccpCountry = filteredCountries!![position]
        return if (preferredCountriesCount > position) {
            "★"
        } else ccpCountry?.name?.substring(0, 1) ?: "☺" //this should never be the case
    }

    internal inner class CountryCodeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var mainView: RelativeLayout
        var textView_name: TextView
        var textView_code: TextView
        var imageViewFlag: ImageView
        var linearFlagHolder: LinearLayout
        var divider: View

        init {
            mainView = itemView as RelativeLayout
            textView_name = mainView.findViewById<View>(R.id.textView_countryName) as TextView
            textView_code = mainView.findViewById<View>(R.id.textView_code) as TextView
            imageViewFlag = mainView.findViewById<View>(R.id.image_flag) as ImageView

            linearFlagHolder = mainView.findViewById<View>(R.id.linear_flag_holder) as LinearLayout
            divider = mainView.findViewById(R.id.preferenceDivider)

            if (codePicker.dialogTextColor !== 0) {
                textView_name.setTextColor(codePicker.dialogTextColor)
                textView_code.setTextColor(codePicker.dialogTextColor)
                divider.setBackgroundColor(codePicker.dialogTextColor)
            }

            try {
                if (codePicker.getDialogTypeFace() != null) {
                    if (codePicker.dialogTypeFaceStyle !== CountryCodePicker.DEFAULT_UNSET) {
                        textView_code.setTypeface(codePicker.getDialogTypeFace(), codePicker.dialogTypeFaceStyle)
                        textView_name.setTypeface(codePicker.getDialogTypeFace(), codePicker.dialogTypeFaceStyle)
                    } else {
                        textView_code.typeface = codePicker.getDialogTypeFace()
                        textView_name.typeface = codePicker.getDialogTypeFace()
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }

        fun setCountry(ccpCountry: CCPCountry?) {
            if (ccpCountry != null) {
                divider.visibility = View.GONE
                textView_name.visibility = View.VISIBLE
                textView_code.visibility = View.VISIBLE
                if (codePicker.isCcpDialogShowPhoneCode) {
                    textView_code.visibility = View.VISIBLE
                } else {
                    textView_code.visibility = View.GONE
                }

                var countryName = ""

                if (codePicker.getCcpDialogShowFlag() && codePicker.ccpUseEmoji) {
                    //extra space is just for alignment purpose
                    countryName += CCPCountry.getFlagEmoji(ccpCountry) + "   "
                }

                countryName += ccpCountry.getName()

                if (codePicker.getCcpDialogShowNameCode()) {
                    countryName += " (" + ccpCountry.getNameCode().toUpperCase() + ")"
                }

                textView_name.text = countryName
                textView_code.text = "+" + ccpCountry.getPhoneCode()

                if (!codePicker.getCcpDialogShowFlag() || codePicker.ccpUseEmoji) {
                    linearFlagHolder.visibility = View.GONE
                } else {
                    linearFlagHolder.visibility = View.VISIBLE
                    imageViewFlag.setImageResource(ccpCountry.getFlagID())

                    //                    Picasso.get()
                    //                            .load(ccpCountry.getFlagID())
                    //                            .transform(new )
                    //                            .into(imageViewFlag);
                }
            } else {
                divider.visibility = View.VISIBLE
                textView_name.visibility = View.GONE
                textView_code.visibility = View.GONE
                linearFlagHolder.visibility = View.GONE
            }
        }
    }
}
