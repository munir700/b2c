package co.yap.widgets.expandableview;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;

/**
 * Created by Anton Knyazev on 21.08.18.
 */
final class SectionContentFrameLayout extends FrameLayout {

    public SectionContentFrameLayout(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (getChildCount() == 0) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        } else {
            View child = getChildAt(0);
            child.measure(widthMeasureSpec, heightMeasureSpec);

            MarginLayoutParams layoutParams = (MarginLayoutParams) child.getLayoutParams();
            setMeasuredDimension(child.getMeasuredWidth(),
                    child.getMeasuredHeight() + layoutParams.bottomMargin + layoutParams.topMargin);
        }

    }
}
