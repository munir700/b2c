package co.yap.widgets.expandableview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ExpandedRecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

import co.yap.yapcore.R;

public class OmegaRecyclerView extends ExpandedRecyclerView {

    private static final int[] DEFAULT_DIVIDER_ATTRS = new int[]{android.R.attr.listDivider};

    private View mEmptyView;
    private int mEmptyViewId;
    private boolean mFinishedInflate = false;
    private List<View> mHeadersList = new ArrayList<>();
    private List<View> mFooterList = new ArrayList<>();
    private WeakHashMap<ViewGroup.LayoutParams, SectionState> mLayoutParamCache = new WeakHashMap<>();
    @Nullable
    private int mItemSpace;
    private boolean mIsAdapterConnected;

    public OmegaRecyclerView(Context context) {
        super(context);
        mFinishedInflate = true;
        init(context, null, 0);
    }

    public OmegaRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public OmegaRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs, defStyle);
    }

    private void init(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        initDefaultLayoutManager(attrs, defStyleAttr);
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.OmegaRecyclerView, defStyleAttr, 0);
            initItemSpace(a);
            initEmptyView(a);
            a.recycle();
        }
    }

    protected void initDefaultLayoutManager(@Nullable AttributeSet attrs, int defStyleAttr) {
        if (getLayoutManager() == null) {
            setLayoutManager(new LinearLayoutManager(getContext(), attrs, defStyleAttr, 0));
        }
    }

    private void initEmptyView(TypedArray a) {
        if (a.hasValue(R.styleable.OmegaRecyclerView_emptyView)) {
            mEmptyViewId = a.getResourceId(R.styleable.OmegaRecyclerView_emptyView, 0);
        }
    }

    public void initItemSpace(TypedArray a) {
        if (a.hasValue(R.styleable.OmegaRecyclerView_itemSpace)) {
            mItemSpace = (int) a.getDimension(R.styleable.OmegaRecyclerView_itemSpace, 0);
            addItemDecoration(new SpaceItemDecoration(0, mItemSpace));
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public void setAdapter(RecyclerView.Adapter adapter) {
        RecyclerView.Adapter shellAdapter = adapter;
        mIsAdapterConnected = true;
        unregisterObservers();
        if (!(adapter instanceof HeaderFooterWrapperAdapter)) {
            if (!mHeadersList.isEmpty() || !mFooterList.isEmpty()) {
                shellAdapter = new HeaderFooterWrapperAdapter(shellAdapter);
                ((HeaderFooterWrapperAdapter) shellAdapter).setHeaders(mHeadersList);
                ((HeaderFooterWrapperAdapter) shellAdapter).setFooters(mFooterList);
            }
        }

        super.setAdapter(shellAdapter);
        registerObservers(shellAdapter);
    }

    private void unregisterObservers() {
        RecyclerView.Adapter currentAdapter = super.getAdapter();
        if (currentAdapter != null) {
            currentAdapter.unregisterAdapterDataObserver(mEmptyObserver);
        }
        mEmptyObserver.onChanged();
        mHeaderObserver.onChanged();
    }

    private void registerObservers(RecyclerView.Adapter adapter) {
        adapter.registerAdapterDataObserver(mEmptyObserver);
    }

    @Override
    public void addView(View view, int index, ViewGroup.LayoutParams params) {
        if (mFinishedInflate) {
            super.addView(view, index, params);
        } else {
            view.setLayoutParams(params);
            SectionState sectionState = mLayoutParamCache.get(params);
            if (sectionState == null) return;

            if (sectionState.position == 0) {
                mHeadersList.add(view);
            } else {
                mFooterList.add(view);
            }
        }
    }

    @Override
    public int getChildCount() {
        if (!mIsAdapterConnected && areSectionsInitialized()) {
            return super.getChildCount() + mHeadersList.size() + mFooterList.size();
        }

        return super.getChildCount();
    }

    @Override
    public View getChildAt(int index) {
        if (!mIsAdapterConnected && areSectionsInitialized()) {
            int realChildrenCount = super.getChildCount();
            int headersCount = mHeadersList.size();
            int footersCount = mFooterList.size();

            if (index < realChildrenCount) {
                return super.getChildAt(index);
            } else if (index < realChildrenCount + headersCount) {
                return mHeadersList.get(index - realChildrenCount);
            } else if (index < realChildrenCount + headersCount + footersCount) {
                return mFooterList.get(index - realChildrenCount - headersCount);
            }

        }
        return super.getChildAt(index);
    }

    private boolean areSectionsInitialized() {
        return mHeadersList != null && mFooterList != null;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mFinishedInflate = true;
        mLayoutParamCache.clear();
        if (getAdapter() != null) {
            setAdapter(getAdapter());
        }
    }

    @SuppressWarnings({"unchecked", "unused"})
    protected <T extends View> T findViewTraversal(@IdRes int id) {
        if (id == getId()) {
            return (T) this;
        }
        for (View view : mHeadersList) {
            View viewById = view.findViewById(id);
            if (viewById != null) return (T) viewById;
        }
        for (View view : mFooterList) {
            View viewById = view.findViewById(id);
            if (viewById != null) return (T) viewById;
        }

        final int len = getChildCount();

        for (int i = 0; i < len; i++) {
            View v = getChildAt(i);

            v = v.findViewById(id);

            if (v != null) {
                return (T) v;
            }
        }

        return null;
    }

    @SuppressLint("CustomViewStyleable")
    @Override
    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attrs) {
        ViewGroup.LayoutParams layoutParams = super.generateLayoutParams(attrs);
        if (!mFinishedInflate) {
            final TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.OmegaRecyclerView_Layout);
            int section = typedArray.getInt(R.styleable.OmegaRecyclerView_Layout_layout_section, 0);
            typedArray.recycle();
            mLayoutParamCache.put(layoutParams, new SectionState(section, false));
        }
        return layoutParams;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        findEmptyView();
    }

    private void findEmptyView() {
        if (mEmptyViewId == 0 || isInEditMode()) {
            return;
        }
        if (getParent() instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) getParent();
            mEmptyView = viewGroup.findViewById(mEmptyViewId);
            mEmptyObserver.onChanged();
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean isIntercepted = super.onInterceptTouchEvent(ev);
        if (ev.getActionIndex() != 0) return true;
        int action = ev.getAction();

        return isIntercepted;
    }


    @Override
    protected int getAdapterPositionFor(RecyclerView.ViewHolder viewHolder) {
        RecyclerView.Adapter adapter = getAdapter();
        int realPosition = super.getAdapterPositionFor(viewHolder);

        if (adapter == null) return realPosition;
        return realPosition;
    }


    public void setHeadersVisibility(boolean visible) {
        RecyclerView.Adapter adapter = getAdapter();
        if (adapter instanceof HeaderFooterWrapperAdapter) {
            ((HeaderFooterWrapperAdapter) adapter).setHeadersVisible(visible);
        }
    }

    public void setFootersVisibility(boolean visible) {
        RecyclerView.Adapter adapter = getAdapter();
        if (adapter instanceof HeaderFooterWrapperAdapter) {
            ((HeaderFooterWrapperAdapter) adapter).setFootersVisible(visible);
        }
    }

    private final RecyclerView.AdapterDataObserver mEmptyObserver = new RecyclerView.AdapterDataObserver() {
        @Override
        public void onChanged() {
            RecyclerView.Adapter adapter = getAdapter();
            if (adapter != null && mEmptyView != null) {
                mEmptyView.setVisibility(adapter.getItemCount() == 0 ? VISIBLE : GONE);
            }
        }
    };

    private class SectionState {

        int position;
        boolean showDivider;

        public SectionState(int position, boolean showDivider) {
            this.position = position;
            this.showDivider = showDivider;
        }
    }

    private final RecyclerView.AdapterDataObserver mHeaderObserver = new RecyclerView.AdapterDataObserver() {
        @Override
        public void onChanged() {
            if (getAdapter() instanceof HeaderFooterWrapperAdapter) {
                ((Adapter) getAdapter()).tryNotifyDataSetChanged();
            }
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            if (getAdapter() instanceof HeaderFooterWrapperAdapter) {
                ((Adapter) getAdapter()).tryNotifyItemRangeInserted(positionStart, itemCount);
            }
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
            if (getAdapter() instanceof HeaderFooterWrapperAdapter) {
                ((Adapter) getAdapter()).tryNotifyItemRangeChanged(positionStart, itemCount, payload);
            }
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            if (getAdapter() instanceof HeaderFooterWrapperAdapter) {
                ((Adapter) getAdapter()).tryNotifyItemRangeInserted(positionStart, itemCount);
            }
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            if (getAdapter() instanceof HeaderFooterWrapperAdapter) {
                ((Adapter) getAdapter()).tryNotifyItemRangeRemoved(positionStart, itemCount);
            }
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            if (getAdapter() instanceof HeaderFooterWrapperAdapter) {
                ((Adapter) getAdapter()).tryNotifyItemMoved(fromPosition, toPosition);
            }
        }
    };

    public abstract static class Adapter<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

        @Nullable
        protected RecyclerView recyclerView;

        @Override
        public void onAttachedToRecyclerView(RecyclerView recyclerView) {
            super.onAttachedToRecyclerView(recyclerView);
            this.recyclerView = recyclerView;
        }

        protected void tryNotifyDataSetChanged() {
            if (recyclerView == null) return;

            if (!recyclerView.isComputingLayout()) {
                notifyDataSetChanged();
            } else {
                recyclerView.post(new Runnable() {
                    @Override
                    public void run() {
                        tryNotifyDataSetChanged();
                    }
                });
            }
        }

        protected void tryNotifyItemChanged(final int position) {
            if (recyclerView == null) return;

            if (!recyclerView.isComputingLayout()) {
                notifyItemChanged(position);
            } else {
                recyclerView.post(new Runnable() {
                    @Override
                    public void run() {
                        tryNotifyItemChanged(position);
                    }
                });
            }
        }

        protected void tryNotifyItemRangeInserted(final int positionStart, final int itemCount) {
            if (recyclerView == null) return;

            if (!recyclerView.isComputingLayout()) {
                notifyItemRangeInserted(positionStart, itemCount);
            } else {
                recyclerView.post(new Runnable() {
                    @Override
                    public void run() {
                        tryNotifyItemRangeInserted(positionStart, itemCount);
                    }
                });
            }
        }

        protected void tryNotifyItemRemoved(final int position) {
            if (recyclerView == null) return;

            if (!recyclerView.isComputingLayout()) {
                notifyItemRemoved(position);
            } else {
                recyclerView.post(new Runnable() {
                    @Override
                    public void run() {
                        tryNotifyItemRemoved(position);
                    }
                });
            }
        }

        protected void tryNotifyItemRangeChanged(final int positionStart, final int itemCount, final Object payload) {
            if (recyclerView == null) return;

            if (!recyclerView.isComputingLayout()) {
                notifyItemRangeChanged(positionStart, itemCount, payload);
            } else {
                recyclerView.post(new Runnable() {
                    @Override
                    public void run() {
                        tryNotifyItemRangeChanged(positionStart, itemCount, payload);
                    }
                });
            }
        }

        protected void tryNotifyItemRangeRemoved(final int positionStart, final int itemCount) {
            if (recyclerView == null) return;

            if (!recyclerView.isComputingLayout()) {
                notifyItemRangeRemoved(positionStart, itemCount);
            } else {
                recyclerView.post(new Runnable() {
                    @Override
                    public void run() {
                        tryNotifyItemRangeRemoved(positionStart, itemCount);
                    }
                });
            }
        }

        protected void tryNotifyItemMoved(final int fromPosition, final int toPosition) {
            if (recyclerView == null) return;

            if (!recyclerView.isComputingLayout()) {
                notifyItemMoved(fromPosition, toPosition);
            } else {
                recyclerView.post(new Runnable() {
                    @Override
                    public void run() {
                        tryNotifyItemRangeRemoved(fromPosition, toPosition);
                    }
                });
            }
        }
    }

    public static class ViewHolder extends ExpandedRecyclerView.ViewHolder {

        public ViewHolder(ViewGroup parent, @LayoutRes int res) {
            this(parent, LayoutInflater.from(parent.getContext()), res);
        }

        public ViewHolder(ViewGroup parent, LayoutInflater layoutInflater, @LayoutRes int res) {
            this(layoutInflater.inflate(res, parent, false));
        }

        public ViewHolder(View itemView) {
            super(itemView);
        }

        public final <T extends View> T findViewById(int id) {
            //noinspection unchecked
            return (T) itemView.findViewById(id);
        }

        public final Context getContext() {
            return itemView.getContext();
        }

        protected final Resources getResources() {
            return getContext().getResources();
        }

        protected final String getString(@StringRes int res) {
            return getResources().getString(res);
        }

        protected final String getString(@StringRes int res, Object... formatArgs) {
            return getResources().getString(res, formatArgs);
        }

        @ColorInt
        protected final int getColor(@ColorRes int id) {
            return ContextCompat.getColor(getContext(), id);
        }
    }

    public static class ItemDecoration extends RecyclerView.ItemDecoration {

        protected int getAdapterPosition(RecyclerView parent, View view) {
            int childPosition = parent.getChildAdapterPosition(view);

            if (parent instanceof OmegaRecyclerView) {
                RecyclerView.Adapter adapter = parent.getAdapter();
                if (adapter instanceof HeaderFooterWrapperAdapter) {
                    return ((HeaderFooterWrapperAdapter) adapter).applyChildPositionToRealPosition(childPosition);
                }
            }

            return childPosition;
        }
    }

}
