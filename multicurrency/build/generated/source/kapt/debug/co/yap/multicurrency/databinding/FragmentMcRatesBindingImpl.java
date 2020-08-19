package co.yap.multicurrency.databinding;
import co.yap.multicurrency.R;
import co.yap.multicurrency.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class FragmentMcRatesBindingImpl extends FragmentMcRatesBinding implements co.yap.multicurrency.generated.callback.OnClickListener.Listener {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.btnAdd, 4);
    }
    // views
    @NonNull
    private final android.widget.TextView mboundView2;
    // variables
    @Nullable
    private final android.view.View.OnClickListener mCallback2;
    // values
    // listeners
    // Inverse Binding Event Handlers

    public FragmentMcRatesBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 5, sIncludes, sViewsWithIds));
    }
    private FragmentMcRatesBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (com.google.android.material.floatingactionbutton.FloatingActionButton) bindings[4]
            , (androidx.constraintlayout.widget.ConstraintLayout) bindings[0]
            , (android.widget.LinearLayout) bindings[1]
            , (androidx.recyclerview.widget.RecyclerView) bindings[3]
            );
        this.clRatesAndAlertsScreenContainer.setTag(null);
        this.lyAddCurrency.setTag(null);
        this.mboundView2 = (android.widget.TextView) bindings[2];
        this.mboundView2.setTag(null);
        this.rvMcRates.setTag(null);
        setRootTag(root);
        // listeners
        mCallback2 = new co.yap.multicurrency.generated.callback.OnClickListener(this, 1);
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x2L;
        }
        requestRebind();
    }

    @Override
    public boolean hasPendingBindings() {
        synchronized(this) {
            if (mDirtyFlags != 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean setVariable(int variableId, @Nullable Object variable)  {
        boolean variableSet = true;
        if (BR.viewModel == variableId) {
            setViewModel((co.yap.multicurrency.ratesalerts.rates.MCRatesVM) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setViewModel(@Nullable co.yap.multicurrency.ratesalerts.rates.MCRatesVM ViewModel) {
        this.mViewModel = ViewModel;
        synchronized(this) {
            mDirtyFlags |= 0x1L;
        }
        notifyPropertyChanged(BR.viewModel);
        super.requestRebind();
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
        }
        return false;
    }

    @Override
    protected void executeBindings() {
        long dirtyFlags = 0;
        synchronized(this) {
            dirtyFlags = mDirtyFlags;
            mDirtyFlags = 0;
        }
        co.yap.multicurrency.ratesalerts.rates.MCRatesVM viewModel = mViewModel;
        co.yap.multicurrency.ratesalerts.rates.adaptor.MCRatesAdapter viewModelRatesAdaptor = null;

        if ((dirtyFlags & 0x3L) != 0) {



                if (viewModel != null) {
                    // read viewModel.ratesAdaptor
                    viewModelRatesAdaptor = viewModel.getRatesAdaptor();
                }
        }
        // batch finished
        if ((dirtyFlags & 0x2L) != 0) {
            // api target 1

            this.lyAddCurrency.setOnClickListener(mCallback2);
            co.yap.yapcore.binders.UIBinder.setText(this.mboundView2, co.yap.translation.Strings.screen_rates_and_alerts_display_text_add_currency);
        }
        if ((dirtyFlags & 0x3L) != 0) {
            // api target 1

            co.yap.yapcore.binders.UIBinder.setRecycleViewAdapter(this.rvMcRates, viewModelRatesAdaptor);
        }
    }
    // Listener Stub Implementations
    // callback impls
    public final void _internalCallbackOnClick(int sourceId , android.view.View callbackArg_0) {
        // localize variables for thread safety
        // viewModel
        co.yap.multicurrency.ratesalerts.rates.MCRatesVM viewModel = mViewModel;
        // viewModel != null
        boolean viewModelJavaLangObjectNull = false;
        // v.id
        int callbackArg0Id = 0;



        viewModelJavaLangObjectNull = (viewModel) != (null);
        if (viewModelJavaLangObjectNull) {


            if ((callbackArg_0) != (null)) {


                callbackArg0Id = callbackArg_0.getId();

                viewModel.handlePressOnView(callbackArg0Id);
            }
        }
    }
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): viewModel
        flag 1 (0x2L): null
    flag mapping end*/
    //end
}