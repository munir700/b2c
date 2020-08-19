package co.yap.multicurrency.databinding;
import co.yap.multicurrency.R;
import co.yap.multicurrency.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class LayoutWalletActionsBindingImpl extends LayoutWalletActionsBinding implements co.yap.multicurrency.generated.callback.OnClickListener.Listener {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.btnExchange, 7);
        sViewsWithIds.put(R.id.btnRates, 8);
        sViewsWithIds.put(R.id.btnStatement, 9);
    }
    // views
    // variables
    @Nullable
    private final android.view.View.OnClickListener mCallback14;
    @Nullable
    private final android.view.View.OnClickListener mCallback12;
    @Nullable
    private final android.view.View.OnClickListener mCallback13;
    // values
    // listeners
    // Inverse Binding Event Handlers

    public LayoutWalletActionsBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 10, sIncludes, sViewsWithIds));
    }
    private LayoutWalletActionsBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (com.google.android.material.floatingactionbutton.FloatingActionButton) bindings[7]
            , (com.google.android.material.floatingactionbutton.FloatingActionButton) bindings[8]
            , (com.google.android.material.floatingactionbutton.FloatingActionButton) bindings[9]
            , (android.widget.LinearLayout) bindings[1]
            , (android.widget.LinearLayout) bindings[3]
            , (android.widget.LinearLayout) bindings[5]
            , (androidx.appcompat.widget.LinearLayoutCompat) bindings[0]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[2]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[4]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[6]
            );
        this.lyExchange.setTag(null);
        this.lyRates.setTag(null);
        this.lyStatement.setTag(null);
        this.mainActions.setTag(null);
        this.tvExchange.setTag(null);
        this.tvRates.setTag(null);
        this.tvStatement.setTag(null);
        setRootTag(root);
        // listeners
        mCallback14 = new co.yap.multicurrency.generated.callback.OnClickListener(this, 3);
        mCallback12 = new co.yap.multicurrency.generated.callback.OnClickListener(this, 1);
        mCallback13 = new co.yap.multicurrency.generated.callback.OnClickListener(this, 2);
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
            setViewModel((co.yap.multicurrency.walletcountry.WalletDetailsViewModel) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setViewModel(@Nullable co.yap.multicurrency.walletcountry.WalletDetailsViewModel ViewModel) {
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
        co.yap.multicurrency.walletcountry.WalletDetailsViewModel viewModel = mViewModel;
        // batch finished
        if ((dirtyFlags & 0x2L) != 0) {
            // api target 1

            this.lyExchange.setOnClickListener(mCallback12);
            this.lyRates.setOnClickListener(mCallback13);
            this.lyStatement.setOnClickListener(mCallback14);
            co.yap.yapcore.binders.UIBinder.setText(this.tvExchange, co.yap.translation.Strings.screen_multi_currency_wallet_details_actions_exchange);
            co.yap.yapcore.binders.UIBinder.setText(this.tvRates, co.yap.translation.Strings.screen_multi_currency_wallet_details_actions_rates);
            co.yap.yapcore.binders.UIBinder.setText(this.tvStatement, co.yap.translation.Strings.screen_multi_currency_wallet_details_actions_statement);
        }
    }
    // Listener Stub Implementations
    // callback impls
    public final void _internalCallbackOnClick(int sourceId , android.view.View callbackArg_0) {
        switch(sourceId) {
            case 3: {
                // localize variables for thread safety
                // viewModel
                co.yap.multicurrency.walletcountry.WalletDetailsViewModel viewModel = mViewModel;
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
                break;
            }
            case 1: {
                // localize variables for thread safety
                // viewModel
                co.yap.multicurrency.walletcountry.WalletDetailsViewModel viewModel = mViewModel;
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
                break;
            }
            case 2: {
                // localize variables for thread safety
                // viewModel
                co.yap.multicurrency.walletcountry.WalletDetailsViewModel viewModel = mViewModel;
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
                break;
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