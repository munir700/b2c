package co.yap.widgets.country_spinner;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;

import co.yap.yapcore.R;


public class CountrySpinner extends RelativeLayout {

    private RelativeLayout _spinnerContainer;
    private SpinnerDefaultSelection _spinner;
    private ArrayAdapter<String> _spinnerAdapterString;
    private ArrayAdapter<CharSequence> _spinnerAdapterCharSequence;
    private boolean _allowToSelect;
    private onSpinnerItemClickListener<String> _callback;
    private static final String TAG = CountrySpinner.class.getSimpleName();
    private boolean _isItemResourceDeclared = false;
    private int _spinnerType = 0;
    private boolean _isSelected;

    public CountrySpinner(Context context) {
        super(context);
        init(null);
    }

    public CountrySpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public CountrySpinner(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        inflate(getContext(), R.layout.spinner_view, this);
        this._spinnerContainer = findViewById(R.id.spinner_container);
        this._spinner = findViewById(R.id.countriesSpinner);
    }


    public void setAdapter(ArrayAdapter<String> adapter) {
        this._spinnerAdapterString = adapter;
        _spinner.setAdapter(_spinnerAdapterString);
        initiateSpinnerString();
    }

    public void setAdapter(ArrayAdapter<CharSequence> adapter, int idle) {
        _spinnerType = 1;
        this._spinnerAdapterCharSequence = adapter;
        _spinner.setAdapter(_spinnerAdapterCharSequence);
        initiateSpinnerCharSequence();
    }

    public boolean isSelected() {
        return _isSelected;
    }

    private void initiateSpinnerString() {

        if (!_isItemResourceDeclared) {
            _spinnerAdapterString.setDropDownViewResource(R.layout.item_place_of_birth_country);
        }

        _spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Log.d(TAG, "position selected: " + position);
                if (CountrySpinner.this._callback == null) {
                    throw new IllegalStateException("callback cannot be null");
                }
                if (_allowToSelect) {
                    _isSelected = true;
                    Object item = CountrySpinner.this._spinner.getItemAtPosition(position);
                    CountrySpinner.this._callback.onItemSelected(position, (String) item);
                }
                _allowToSelect = true;

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.d(TAG, "Nothing selected");
            }
        });


    }

    private void initiateSpinnerCharSequence() {

        if (!_isItemResourceDeclared) {
            _spinnerAdapterCharSequence.setDropDownViewResource(R.layout.item_place_of_birth_country);
        }

        _spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Log.d(TAG, "position selected: " + position);
                if (CountrySpinner.this._callback == null) {
                    throw new IllegalStateException("callback cannot be null");
                }
                if (_allowToSelect) {
                    _isSelected = true;
                    Object item = CountrySpinner.this._spinner.getItemAtPosition(position);
                    CountrySpinner.this._callback.onItemSelected(position, (String) item);
                }
                _allowToSelect = true;

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.d(TAG, "Nothing selected");
            }
        });


    }

    public void setDropDownViewResource(int resource) {

        if (_spinnerType == 1) {
            _spinnerAdapterCharSequence.setDropDownViewResource(resource);
        } else {
            _spinnerAdapterString.setDropDownViewResource(resource);
        }

        _isItemResourceDeclared = true;

    }

    public void setOnSpinnerItemClickListener(onSpinnerItemClickListener<String> callback) {

        this._callback = callback;

        _spinnerContainer.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                _spinner.performClick();
            }
        });
    }

    public void setSelection(int position) {
        _allowToSelect = true;
        _spinner.setSelection(position);
    }

    public void setSelection(String value) {
        if (value.trim().isEmpty()) {
            _allowToSelect = true;
            if (_spinnerType == 0) {
                int spinnerPosition = _spinnerAdapterString.getPosition(value);
                _spinner.setSelection(spinnerPosition);
            } else {
                int spinnerPosition = _spinnerAdapterCharSequence.getPosition(value);
                _spinner.setSelection(spinnerPosition);
            }
        }
    }

    public String getSelectedItem() {
        if (isSelected()) {
            return _spinner.getSelectedItem().toString();
        } else {
            return null;
        }
    }

    public SpinnerDefaultSelection getSpinner() {
        return _spinner;
    }

    public int getSelectedItemPosition() {
        if (isSelected()) {
            return _spinner.getSelectedItemPosition();
        } else {
            return -1;
        }
    }

    public interface onSpinnerItemClickListener<T> {
        /**
         * When a spinner item has been selected.
         *
         * @param position       Position selected
         * @param itemAtPosition Item selected
         */
        void onItemSelected(int position, T itemAtPosition);
    }
}