package co.yap.widgets.country_spinner;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.widget.AdapterView;
import android.widget.Spinner;

/**
 * Created by Faheem
 */

public class SpinnerDefaultSelection extends Spinner {

    AdapterView.OnItemSelectedListener listener;
    int prevPos = 0;

    public SpinnerDefaultSelection(Context context) {
        super(context);
        init();
    }

    public SpinnerDefaultSelection(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SpinnerDefaultSelection(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        this.getBackground().setColorFilter(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
    }

    @Override
    public void setSelection(int position) {
        super.setSelection(position);
        if (position == getSelectedItemPosition() && prevPos == position) {
            getOnItemSelectedListener().onItemSelected(null, null, position, 0);
        }
        prevPos = position;
    }
}