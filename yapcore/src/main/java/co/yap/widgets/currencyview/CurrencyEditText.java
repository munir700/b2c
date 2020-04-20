package co.yap.widgets.currencyview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import androidx.appcompat.widget.AppCompatEditText;

import java.text.DecimalFormat;
import java.util.Currency;
import java.util.Locale;

import co.yap.yapcore.R;

public class CurrencyEditText extends AppCompatEditText
{
    private Locale currencyLocale;

    private Locale defaultLocale = Locale.US;

    private boolean allowNegativeValues = false;

    private long rawValue = 0L;

    private CurrencyTextWatcher textWatcher;
    private String hintCache = null;
    public static int cursor=0;
    private int decimalDigits = 0;
    private int mValueInLowestDenom = 0;


    public CurrencyEditText(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
        processAttributes(context, attrs);
    }



    public void setAllowNegativeValues(boolean negativeValuesAllowed)
    {
        allowNegativeValues = negativeValuesAllowed;
    }


    public boolean areNegativeValuesAllowed()
    {
        return allowNegativeValues;
    }


    public long getRawValue()
    {
        return rawValue;
    }

    public void setValue(long value)
    {
        String formattedText = format(value);
        setText(formattedText);
    }


    public Locale getLocale()
    {
        return currencyLocale;
    }


    public void setLocale(Locale locale)
    {
        currencyLocale = locale;
        refreshView();
    }


    public String getHintString()
    {
        CharSequence result = super.getHint();
        if (result == null) return null;
        return super.getHint().toString();
    }


    public int getDecimalDigits()
    {
        return decimalDigits;
    }


    public void setDecimalDigits(int digits)
    {
        if (digits < 0 || digits > 27)
        {
            throw new IllegalArgumentException("Decimal Digit value must be between 0 and 27");
        }
        decimalDigits = digits;

        refreshView();
    }


    public void configureViewForLocale(Locale locale)
    {
        this.currencyLocale = locale;
        Currency currentCurrency = getCurrencyForLocale(locale);
        decimalDigits = currentCurrency.getDefaultFractionDigits();
        refreshView();
    }


    public void setDefaultLocale(Locale locale)
    {
        this.defaultLocale = locale;
    }


    public Locale getDefaultLocale()
    {
        return defaultLocale;
    }


    public String formatCurrency(String val)
    {
        return format(val);
    }


    public String formatCurrency(long rawVal)
    {
        return format(rawVal);
    }


    private void refreshView()
    {
        setText(format(getRawValue()));
        updateHint();
    }

    private String format(long val)
    {
        return formatText(String.valueOf(val), currencyLocale, defaultLocale, decimalDigits);
    }

    private String format(String val)
    {
        return formatText(val, currencyLocale, defaultLocale, decimalDigits);
    }

    private void init()
    {

        this.setOnKeyListener(new OnKeyListener()
        {
            public boolean onKey(View v, int keyCode, KeyEvent event)
            {
                if (keyCode == KeyEvent.KEYCODE_COMMA)
                {

                }
                else if (keyCode == KeyEvent.KEYCODE_NUMPAD_DOT)
                {

                }
                return false;
            }
        });


        this.setGravity(Gravity.RIGHT);
        this.setInputType(InputType.TYPE_CLASS_TEXT);
        InputFilter[] filters = new InputFilter[1];
        filters[0] = new InputFilter.LengthFilter(20);
        this.setFilters(filters);

        this.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {
                changeDecimalKeyboard();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {

            }

            @Override
            public void afterTextChanged(Editable s)
            {

            }
        });


        currencyLocale = retrieveLocale();
        Currency currentCurrency = getCurrencyForLocale(currencyLocale);
        decimalDigits = currentCurrency.getDefaultFractionDigits();
        initCurrencyTextWatcher();
    }

    protected void setValueInLowestDenom(int mValueInLowestDenom)
    {
        this.mValueInLowestDenom = mValueInLowestDenom;
    }

    private void changeDecimalKeyboard()
    {
        this.setRawInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
    }

    private void changeSignedKeyboard()
    {
        this.setRawInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_SIGNED);
    }

    private void initCurrencyTextWatcher()
    {

        if (textWatcher != null)
        {
            this.removeTextChangedListener(textWatcher);
        }
        textWatcher = new CurrencyTextWatcher(this);
        this.addTextChangedListener(textWatcher);
    }

    private void processAttributes(Context context, AttributeSet attrs)
    {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CurrencyEditText);
        this.hintCache = getHintString();
        updateHint();

        this.setAllowNegativeValues(array.getBoolean(R.styleable.CurrencyEditText_allow_negative_values, false));
        this.setDecimalDigits(array.getInteger(R.styleable.CurrencyEditText_decimal_digits, decimalDigits));

        array.recycle();
    }

    private void updateHint()
    {
        if (hintCache == null)
        {
            setHint(getDefaultHintValue());
        }
    }

    private String getDefaultHintValue()
    {
        try
        {
            return Currency.getInstance(currencyLocale).getSymbol();
        }
        catch (Exception e)
        {
            Log.w("CurrencyEditText", String.format("An error occurred while getting currency symbol for hint using locale '%s', falling back to defaultLocale", currencyLocale));
            try
            {
                return Currency.getInstance(defaultLocale).getSymbol();
            }
            catch (Exception e1)
            {
                Log.w("CurrencyEditText", String.format("An error occurred while getting currency symbol for hint using default locale '%s', falling back to USD", defaultLocale));
                return Currency.getInstance(Locale.US).getSymbol();
            }

        }
    }

    private Locale retrieveLocale()
    {
        Locale locale;
        try
        {
            locale = getResources().getConfiguration().locale;
        }
        catch (Exception e)
        {
            Log.w("CurrencyEditText", String.format("An error occurred while retrieving users device locale, using fallback locale '%s'", defaultLocale), e);
            locale = defaultLocale;
        }
        return locale;
    }

    private Currency getCurrencyForLocale(Locale locale)
    {
        Currency currency;
        try
        {
            currency = Currency.getInstance(locale);
        }
        catch (Exception e)
        {
            try
            {
                Log.w("CurrencyEditText", String.format("Error occurred while retrieving currency information for locale '%s'. Trying default locale '%s'...", currencyLocale, defaultLocale));
                currency = Currency.getInstance(defaultLocale);
            }
            catch (Exception e1)
            {
                Log.e("CurrencyEditText", "Both device and configured default locales failed to report currentCurrency data. Defaulting to USD.");
                currency = Currency.getInstance(Locale.US);
            }
        }
        return currency;
    }

    protected void setRawValue(long value)
    {
        rawValue = value;
    }

    public static String formatText(String val, Locale locale, Locale defaultLocale, Integer decimalDigits){

        if(val.equals("-")) return val;

        int currencyDecimalDigits;
        if (decimalDigits != null){
            currencyDecimalDigits = decimalDigits;
        }
        else {
            Currency currency = Currency.getInstance(locale);
            try {
                currencyDecimalDigits = currency.getDefaultFractionDigits();
            } catch (Exception e) {
                Log.e("CurrencyTextFormatter", "Illegal argument detected for currency: " + currency + ", using currency from defaultLocale: " + defaultLocale);
                currencyDecimalDigits = Currency.getInstance(defaultLocale).getDefaultFractionDigits();
            }
        }

        DecimalFormat currencyFormatter;
        try {
            currencyFormatter = (DecimalFormat) DecimalFormat.getCurrencyInstance(locale);
        } catch (Exception e) {
            try {
                Log.e("CurrencyTextFormatter", "Error detected for locale: " + locale + ", falling back to default value: " + defaultLocale);
                currencyFormatter = (DecimalFormat) DecimalFormat.getCurrencyInstance(defaultLocale);
            }
            catch(Exception e1){
                Log.e("CurrencyTextFormatter", "Error detected for defaultLocale: " + defaultLocale + ", falling back to USD.");
                currencyFormatter = (DecimalFormat) DecimalFormat.getCurrencyInstance(Locale.US);
            }
        }

        boolean isNegative = false;
        if (val.contains("-")){
            isNegative = true;
        }

        val = val.replaceAll("[^\\d]", "");

        if(!val.equals("")) {

            if (val.length() <= currencyDecimalDigits){
                String formatString = "%" + currencyDecimalDigits + "s";
                val = String.format(formatString, val).replace(' ', '0');
            }

            String preparedVal = new StringBuilder(val).insert(val.length() - currencyDecimalDigits, '.').toString();

            //Convert the string into a double, which will be passed into the currency formatter
            double newTextValue = Double.valueOf(preparedVal);

            newTextValue *= isNegative ? -1 : 1;

            currencyFormatter.setMinimumFractionDigits(currencyDecimalDigits);
            val = currencyFormatter.format(newTextValue);
        }
        else {
            throw new IllegalArgumentException("Invalid amount of digits found (either zero or too many) in argument val");
        }
        return val;
    }

class CurrencyTextWatcher implements TextWatcher
{

    private CurrencyEditText editText;
    private boolean ignoreIteration;
    private String lastGoodInput;
    int cursorPosition = 0;
    boolean okcommo = false;
    boolean clickDot = false;
    boolean isEmpty = false;
    boolean clickDelete = false;
    boolean rightPost = false;
    int currentTextsize;


    CurrencyTextWatcher(CurrencyEditText textBox)
    {
        editText = textBox;
        lastGoodInput = "";
        ignoreIteration = false;


        editText.setOnKeyListener(new View.OnKeyListener()
        {
            public boolean onKey(View v, int keyCode, KeyEvent event)
            {
                if (keyCode == KeyEvent.KEYCODE_DEL)
                {
                    clickDelete = true;
                }
                else if (keyCode == 55)
                {
                    clickDelete = false;
                    okcommo = true;
                    clickDot = true;
                    cursorPosition = editText.getText().length() - 2;
                    changeSignedKeyboard();
                }
                else if (keyCode == 56)
                {
                    clickDelete = false;
                    okcommo = true;
                    clickDot = true;
                    cursorPosition = editText.getText().length() - 2;
                    changeSignedKeyboard();
                }else{
                    clickDelete = false;
                }
                return false;
            }
        });

    }

    @Override
    public void afterTextChanged(Editable editable)
    {
        //Use the ignoreIteration flag to stop our edits to the text field from triggering an endlessly recursive call to afterTextChanged
        if (!ignoreIteration)
        {
            ignoreIteration = true;
            //Start by converting the editable to something easier to work with, then remove all non-digit characters
            String newText = editable.toString();
            String textToDisplay;
            if (newText.length() < 1)
            {
                lastGoodInput = "";
                editText.setRawValue(0);
                editText.setText("");
                return;
            }

            if(clickDelete && okcommo &&(editable.toString().length()-2)<=editText.getSelectionStart()){
                rightPost = true;
                if(editText.getSelectionStart()==editable.toString().length()-1){
                    newText = newText.substring(0,newText.length()-1) +"0"+newText.substring(newText.length()-1,newText.length());
                }else if (editText.getSelectionStart()==editable.toString().length()){
                    newText = newText+ "0";
                }
            }else{
                rightPost = false;
            }

            if(!clickDelete&&editText.getSelectionStart()-1>=0) {
                String word = newText.substring(editText.getSelectionStart()-1,editText.getSelectionStart());
                if(word.contentEquals(".") || word.contentEquals(",")){
                    okcommo = true;
                    clickDot = true;
                }else{
                    okcommo = false;
                    clickDot = false;
                }
            }else{
                okcommo = false;
                clickDot = false;
            }

            newText = (editText.areNegativeValuesAllowed()) ? newText.replaceAll("[^0-9/-]", "") : newText.replaceAll("[^0-9]", "");
            if (!newText.equals("") && !newText.equals("-"))
            {
                //Store a copy of the raw input to be retrieved later by getRawValue
                editText.setRawValue(Long.valueOf(newText));
            }

            //ondalik bolumdesin
            if(!clickDelete&&!okcommo &&(editable.toString().length()-2)<=editText.getSelectionStart()){
                newText = newText.substring(0,newText.length()-1);
                rightPost = true;
            }else{
                rightPost = false;
            }


            try
            {
                textToDisplay = CurrencyEditText.formatText(newText, editText.getLocale(), editText.getDefaultLocale(), editText.getDecimalDigits());

                textToDisplay = textToDisplay.substring(1);

            }
            catch (IllegalArgumentException exception)
            {
                textToDisplay = lastGoodInput;
            }

            editText.setText(textToDisplay);
            //Store the last known good input so if there are any issues with new input later, we can fall back gracefully.
            lastGoodInput = textToDisplay;

            if(!clickDelete && rightPost && cursorPosition<=(editable.toString().length()-2)){
                if(cursorPosition+2<=lastGoodInput.length()) {
                    editText.setSelection(cursorPosition + 1);
                }else{
                    editText.setSelection(lastGoodInput.length());
                }
                rightPost = false;
            }else if(!clickDelete &&  rightPost && cursorPosition==(editable.toString().length())){
                editText.setSelection(lastGoodInput.length());
                rightPost = false;
            }
            else {
                if (isEmpty) {
                    editText.setSelection(1);
                    cursorPosition = (1);
                    isEmpty = false;
                } else if (okcommo) {
                    if (cursorPosition != lastGoodInput.length()) {
                        if (clickDot) {
                            editText.setSelection(editText.length() - 2);
                            clickDot = false;
                        } else {
                            editText.setSelection(cursorPosition + 1);
                        }
                    } else {
                        editText.setSelection(textToDisplay.length() - 1);
                    }
                } else {
                    okcommo = false;
                    int diff = Math.abs(currentTextsize - lastGoodInput.length());
                    if (clickDelete&&!rightPost) {
                        if (diff == 2) {
                            if(cursorPosition == 0){
                                editText.setSelection(0);
                            }else {
                                editText.setSelection(cursorPosition - 1);
                            }
                        } else if (diff > 2) {
                            editText.setSelection(0);
                        } else {
                            editText.setSelection(cursorPosition);
                        }
                        clickDelete = false;
                    }else if(clickDelete&&rightPost){
                        if(cursorPosition+1<=lastGoodInput.length()) {
                            editText.setSelection(cursorPosition - 1);
                        }else{
                            editText.setSelection(lastGoodInput.length()-3);
                        }
                        clickDelete = false;
                    }
                    else {
                        if((cursorPosition + Math.abs(currentTextsize - lastGoodInput.length()))>lastGoodInput.length()){
                            editText.setSelection(editText.getSelectionStart()+1);
                        }else{

                            editText.setSelection(cursorPosition + Math.abs(currentTextsize - lastGoodInput.length()));
                        }

                    }
                }
            }

        }
        else
        {
            ignoreIteration = false;
            if(isEmpty&&editable.toString().isEmpty()){
                String tempText=null;
                try
                {
                    tempText = CurrencyEditText.formatText("000", editText.getLocale(), editText.getDefaultLocale(), editText.getDecimalDigits());
                    tempText = tempText.substring(1);

                }
                catch (IllegalArgumentException exception)
                {
                    tempText = "";
                }
                editText.setText(tempText);
                editText.setSelection(1);
                cursorPosition = (1);
                isEmpty = false;
            }
        }
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after)
    {
        if (s.length() == 0)
        {
            isEmpty = true;
        }
        if (start != 0)
        {
            currentTextsize = s.toString().length();
            cursorPosition = start;
            if(editText.getText().length()-3>=cursorPosition&&editText.getText().length()-3>0){
                okcommo=false;
            }else{
                okcommo=true;
            }
        }else if(start==0&&!ignoreIteration){
            currentTextsize = s.toString().length();
            cursorPosition = start;
            if(editText.getText().length()-3>=cursorPosition&&editText.getText().length()-3>0){
                okcommo=false;
            }else{
                okcommo=true;
            }
        }


    }

    @Override
    public void onTextChanged(final CharSequence s, int start, int before, int count)
    { }

    private void changeDecimalKeyboard()
    {
        editText.setRawInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
    }

    private void changeSignedKeyboard()
    {
        editText.setRawInputType(InputType.TYPE_CLASS_NUMBER);
    }


}
}
