package co.yap.widgets.expandableview;

import android.graphics.Rect;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class SpaceItemDecoration extends BaseItemDecoration {

    private final int space;

    public SpaceItemDecoration(int showDivider, int space) {
        this.space = space;
    }

    @Override
    void getItemOffset(@NonNull Rect outRect, @NonNull RecyclerView parent, int position, int itemCount) {
        int countBeginEndPositions = getCountBeginEndPositions(parent);
    }

    private int getCountBeginEndPositions(RecyclerView recyclerView) {
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            return ((GridLayoutManager) layoutManager).getSpanCount();
        } else return 1;
    }

}

