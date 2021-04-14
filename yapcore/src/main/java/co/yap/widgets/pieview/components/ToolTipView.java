
package co.yap.widgets.pieview.components;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.TextView;

import co.yap.widgets.pieview.CandleEntry;
import co.yap.widgets.pieview.Entry;
import co.yap.widgets.pieview.Highlight;
import co.yap.widgets.pieview.MPPointF;
import co.yap.widgets.pieview.Utils;
import co.yap.yapcore.R;

/**
 * Custom implementation of the ToolTipView.
 *
 * @author Philipp Jahoda
 */
@SuppressLint("ViewConstructor")
public class ToolTipView extends MarkerView {

    private final TextView tvContent;

    public ToolTipView(Context context, int layoutResource) {
        super(context, layoutResource);

        tvContent = findViewById(R.id.tvContent);
    }

    // runs every time the MarkerView is redrawn, can be used to update the
    // content (user-interface)
    @Override
    public void refreshContent(Entry e, Highlight highlight) {

        if (e instanceof CandleEntry) {

            CandleEntry ce = (CandleEntry) e;

            tvContent.setText(Utils.formatNumber(ce.getHigh(), 0, true));
        } else {

            tvContent.setText(Utils.formatNumber(e.getY(), 0, true));
        }

        super.refreshContent(e, highlight);
    }

    @Override
    public MPPointF getOffset() {
        return new MPPointF(-(getWidth() / 2), -getHeight());
    }
}
