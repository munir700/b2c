package co.yap.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.text.DecimalFormat;
import java.util.Currency;
import java.util.Locale;
import co.yap.yapcore.R;

public class CoreAmountWidget extends LinearLayout {

    private String _currencySymbol;
    private boolean _showCurrency;
    private boolean _showDecimal;
    private boolean _showCommas;
    private EditText editText;

    public CoreAmountWidget(Context context) {
        super(context);
        initView(context, null);
    }

    public CoreAmountWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        updateValue(editText.getText().toString());
    }

    private void initView(Context context, AttributeSet attrs) {
        // Setting Default Parameters
        _currencySymbol = Currency.getInstance(Locale.getDefault()).getSymbol();
        _showCurrency = true;
        _showCommas = true;
        _showDecimal = true;

        // Check for the attributes
        if (attrs != null) {
            inflate(context, R.layout.widget_core_amount, this);
            editText = findViewById(R.id.etAmount);
            // Attribute initialization
            final TypedArray attrArray = context.obtainStyledAttributes(attrs, R.styleable.EasyMoneyWidgets, 0, 0);
            try {
                String currnecy = attrArray.getString(R.styleable.EasyMoneyWidgets_currency_symbol);
                if (currnecy == null)
                    currnecy = Currency.getInstance(Locale.getDefault()).getSymbol();
                setCurrency(currnecy);

                _showCurrency = attrArray.getBoolean(R.styleable.EasyMoneyWidgets_show_currency, true);
                _showCommas = attrArray.getBoolean(R.styleable.EasyMoneyWidgets_show_commas, true);
                _showDecimal = attrArray.getBoolean(R.styleable.EasyMoneyWidgets_show_decimal, true);
            } finally {
                attrArray.recycle();
            }
        }

        // Add Text Watcher for Decimal formatting
        initTextWatchers();
    }

    private void initTextWatchers() {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                CoreAmountWidget.this.editText.removeTextChangedListener(this);
                String backupString = charSequence.toString();

                try {
                    String originalString = charSequence.toString();

                    long longval;

                    originalString = getValueString();
                    longval = (Long.parseLong(originalString));
                    String formattedString = getDecoratedStringFromNumber(longval);

                    //setting text after format to EditText
                    if (getValueInt() <= 0.0) {
                        long val = 0;
                        editText.setText("");
                        editText.setHint("0.00");
                    } else {
                        editText.setText(formattedString);
                    }
                    editText.setSelection(editText.getText().length());

                } catch (NumberFormatException nfe) {
//                    nfe.printStackTrace();
                    editText.setText(backupString);

                    String valStr = getValueString();

                    if (valStr.equals("") || getValueInt() <= 0.0) {
                        long val = 0;
                        editText.setText("");
                        editText.setHint("0.00");
                    } else {
                        // Some decimal number
                        if (valStr.contains(".")) {
                            if (valStr.indexOf(".") == valStr.length() - 1) {
                                // decimal has been currently put
                                String front = getDecoratedStringFromNumber(Long.parseLong(valStr.substring(0, valStr.length() - 1)));
                                editText.setText(front + ".");
                            } else {
                                String[] nums = getValueString().split("\\.");
                                String front = getDecoratedStringFromNumber(Long.parseLong(nums[0]));
                                editText.setText(front + "." + nums[1]);
                            }
                        }
                    }
                    editText.setSelection(editText.getText().length());
                }

                CoreAmountWidget.this.editText.addTextChangedListener(this);

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void updateValue(String text) {
        editText.setText(text);
    }

    private String getDecoratedStringFromNumber(long number) {
        String numberPattern = "#,###,###,###.00";
        String decoStr = "";

        DecimalFormat formatter = (DecimalFormat) DecimalFormat.getInstance(Locale.getDefault());
        if (_showCommas && !_showCurrency)
            formatter.applyPattern(numberPattern);
        else if (_showCommas && _showCurrency)
            formatter.applyPattern(_currencySymbol + " " + numberPattern);
        else if (!_showCommas && _showCurrency)
            formatter.applyPattern(_currencySymbol + " ");
        else if (!_showCommas && !_showCurrency) {
            decoStr = number + "";
            return decoStr;
        }

        decoStr = formatter.format(number);

        return decoStr;
    }

    private void setShowCurrency(boolean value) {
        _showCurrency = value;
        updateValue(editText.getText().toString());
    }

    /**
     * Get the value of the text without any commas and currency.
     * For example, if the edit text value is $ 1,34,000.60 then this method will return 134000.60
     *
     * @return A string of the raw value in the text field
     */
    public String getValueString() {

        String string = editText.getText().toString();

        if (string.contains(",")) {
            string = string.replace(",", "");
        }
        if (string.contains(" ")) {
            string = string.substring(string.indexOf(" ") + 1, string.length());
        }
        return string;
    }

    /**
     * Get the value of the text with formatted commas and currency.
     * For example, if the edit text value is $ 1,34,000.60 then this method will return exactly $ 1,34,000.60
     *
     * @return A string of the text value in the text field
     */
    public String getFormattedString() {
        return editText.getText().toString();
    }

    /**
     * Set the currency symbol for the edit text. (Default is US Dollar $).
     *
     * @param newSymbol the new symbol of currency in string
     */
    public void setCurrency(String newSymbol) {
        _currencySymbol = newSymbol;
        updateValue(editText.getText().toString());
    }

    /**
     * Set the currency symbol for the edit text. (Default is US Dollar $).
     *
     * @param locale the locale of new symbol. (Defaul is Locale.US)
     */
    public void setCurrency(Locale locale) {
        setCurrency(Currency.getInstance(locale).getSymbol());
    }

    /**
     * Set the currency symbol for the edit text. (Default is US Dollar $).
     *
     * @param currency the currency object of new symbol. (Defaul is Locale.US)
     */
    public void setCurrency(Currency currency) {
        setCurrency(currency.getSymbol());
    }

    /**
     * Whether currency is shown in the text or not. (Default is true)
     *
     * @return true if the currency is shown otherwise false.
     */
    public boolean isShowCurrency() {
        return _showCurrency;
    }

    /**
     * Shows the currency in the text. (Default is shown).
     */
    public void showCurrencySymbol() {
        setShowCurrency(true);
    }

    /**
     * Hides the currency in the text. (Default is shown).
     */
    public void hideCurrencySymbol() {
        setShowCurrency(false);
    }

    /**
     * Shows the commas in the text. (Default is shown).
     */
    public void showCommas() {
        _showCommas = true;
        updateValue(editText.getText().toString());
    }

    /**
     * Hides the commas in the text. (Default is shown).
     */
    public void hideCommas() {
        _showCommas = false;
        updateValue(editText.getText().toString());
    }

    public double getIntValue(String value) {
        if (!TextUtils.isEmpty(value)) {
            try {
                return Double.parseDouble(value);
            } catch (NumberFormatException e) {

            }
        }
        return 0.0;
    }

    /**
     * Get the value of the text without any commas and currency.
     * For example, if the edit text value is $ 1,34,000.60 then this method will return 134000.60
     *
     * @return A int of the raw value in the text field
     */
    public double getValueInt() {
        return getIntValue(getValueString());
    }
}
