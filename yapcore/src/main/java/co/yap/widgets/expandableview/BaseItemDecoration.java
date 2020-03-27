package co.yap.widgets.expandableview;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public abstract class BaseItemDecoration extends OmegaRecyclerView.ItemDecoration {


    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        if (!(parent.getLayoutManager() instanceof LinearLayoutManager)) return;

        RecyclerView.Adapter adapter = parent.getAdapter();
        if (adapter == null) return;

        int itemCount = adapter.getItemCount();
        if (itemCount == 0) return;

        int position = getAdapterPosition(parent, view);
        if (position == RecyclerView.NO_POSITION) return;


        getItemOffset(outRect, parent, position, itemCount);
    }

    abstract void getItemOffset(@NonNull Rect outRect, @NonNull RecyclerView parent, int position, int itemCount);


}
