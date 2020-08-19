package co.yap.multicurrency.databinding;
import co.yap.multicurrency.R;
import co.yap.multicurrency.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class ItemCurrencyCountryBindingImpl extends ItemCurrencyCountryBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = null;
    }
    // views
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ItemCurrencyCountryBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 3, sIncludes, sViewsWithIds));
    }
    private ItemCurrencyCountryBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (androidx.constraintlayout.widget.ConstraintLayout) bindings[0]
            , (androidx.appcompat.widget.AppCompatImageView) bindings[1]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[2]
            );
        this.foregroundContainer.setTag(null);
        this.ivFlag.setTag(null);
        this.tvCurrencyName.setTag(null);
        setRootTag(root);
        // listeners
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
            setViewModel((co.yap.multicurrency.currency.adapters.CurrencyItemViewModel) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setViewModel(@Nullable co.yap.multicurrency.currency.adapters.CurrencyItemViewModel ViewModel) {
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
        java.lang.String viewModelMultiCurrencyWalletGetCurrencySymbolWithName = null;
        java.lang.String viewModelMultiCurrencyWalletCountry2DigitCode = null;
        co.yap.multicurrency.wallets.MultiCurrencyWallet viewModelMultiCurrencyWallet = null;
        co.yap.multicurrency.currency.adapters.CurrencyItemViewModel viewModel = mViewModel;

        if ((dirtyFlags & 0x3L) != 0) {



                if (viewModel != null) {
                    // read viewModel.multiCurrencyWallet
                    viewModelMultiCurrencyWallet = viewModel.getMultiCurrencyWallet();
                }


                if (viewModelMultiCurrencyWallet != null) {
                    // read viewModel.multiCurrencyWallet.getCurrencySymbolWithName()
                    viewModelMultiCurrencyWalletGetCurrencySymbolWithName = viewModelMultiCurrencyWallet.getCurrencySymbolWithName();
                    // read viewModel.multiCurrencyWallet.country2DigitCode
                    viewModelMultiCurrencyWalletCountry2DigitCode = viewModelMultiCurrencyWallet.getCountry2DigitCode();
                }
        }
        // batch finished
        if ((dirtyFlags & 0x3L) != 0) {
            // api target 1

            co.yap.yapcore.helpers.ImageBinding.setIsoCountryDrawable(this.ivFlag, viewModelMultiCurrencyWalletCountry2DigitCode);
            co.yap.multicurrency.helper.UIBindingAdapters.spanColor(this.tvCurrencyName, viewModelMultiCurrencyWalletGetCurrencySymbolWithName);
        }
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): viewModel
        flag 1 (0x2L): null
    flag mapping end*/
    //end
}