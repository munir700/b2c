package co.yap.multicurrency.databinding;
import co.yap.multicurrency.R;
import co.yap.multicurrency.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class ItemWalletBindingImpl extends ItemWalletBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.swipe, 6);
        sViewsWithIds.put(R.id.btnDelete, 7);
        sViewsWithIds.put(R.id.foregroundContainer, 8);
    }
    // views
    @NonNull
    private final android.widget.RelativeLayout mboundView0;
    @NonNull
    private final android.widget.TextView mboundView1;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ItemWalletBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 9, sIncludes, sViewsWithIds));
    }
    private ItemWalletBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 1
            , (android.widget.RelativeLayout) bindings[7]
            , (androidx.constraintlayout.widget.ConstraintLayout) bindings[8]
            , (androidx.appcompat.widget.AppCompatImageView) bindings[5]
            , (androidx.appcompat.widget.AppCompatImageView) bindings[2]
            , (android.widget.LinearLayout) bindings[6]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[3]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[4]
            );
        this.ivArrow.setTag(null);
        this.ivFlag.setTag(null);
        this.mboundView0 = (android.widget.RelativeLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.mboundView1 = (android.widget.TextView) bindings[1];
        this.mboundView1.setTag(null);
        this.tvAmount.setTag(null);
        this.tvCurrencyUnit.setTag(null);
        setRootTag(root);
        // listeners
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x4L;
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
            setViewModel((co.yap.multicurrency.wallets.adapter.WalletItemViewModel) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setViewModel(@Nullable co.yap.multicurrency.wallets.adapter.WalletItemViewModel ViewModel) {
        this.mViewModel = ViewModel;
        synchronized(this) {
            mDirtyFlags |= 0x2L;
        }
        notifyPropertyChanged(BR.viewModel);
        super.requestRebind();
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
            case 0 :
                return onChangeViewModelActiveWallet((androidx.databinding.ObservableField<java.lang.Boolean>) object, fieldId);
        }
        return false;
    }
    private boolean onChangeViewModelActiveWallet(androidx.databinding.ObservableField<java.lang.Boolean> ViewModelActiveWallet, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x1L;
            }
            return true;
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
        int coYapMulticurrencyRDrawableIcBackArrowLeftGery = 0;
        java.lang.String viewModelMultiCurrencyWalletCurrencyUnit = null;
        int coYapMulticurrencyRDrawableIcHamburger = 0;
        boolean androidxDatabindingViewDataBindingSafeUnboxViewModelActiveWalletGet = false;
        java.lang.String viewModelMultiCurrencyWalletCountry2DigitCode = null;
        java.lang.String viewModelMultiCurrencyWalletAmount = null;
        androidx.databinding.ObservableField<java.lang.Boolean> viewModelActiveWallet = null;
        java.lang.Boolean viewModelActiveWalletGet = null;
        co.yap.multicurrency.wallets.MultiCurrencyWallet viewModelMultiCurrencyWallet = null;
        boolean ViewModelActiveWallet1 = false;
        co.yap.multicurrency.wallets.adapter.WalletItemViewModel viewModel = mViewModel;
        int viewModelActiveWalletCoYapMulticurrencyRDrawableIcBackArrowLeftGeryCoYapMulticurrencyRDrawableIcHamburger = 0;

        if ((dirtyFlags & 0x7L) != 0) {



                if (viewModel != null) {
                    // read viewModel.activeWallet
                    viewModelActiveWallet = viewModel.getActiveWallet();
                }
                updateRegistration(0, viewModelActiveWallet);


                if (viewModelActiveWallet != null) {
                    // read viewModel.activeWallet.get()
                    viewModelActiveWalletGet = viewModelActiveWallet.get();
                }


                // read androidx.databinding.ViewDataBinding.safeUnbox(viewModel.activeWallet.get())
                androidxDatabindingViewDataBindingSafeUnboxViewModelActiveWalletGet = androidx.databinding.ViewDataBinding.safeUnbox(viewModelActiveWalletGet);


                // read !androidx.databinding.ViewDataBinding.safeUnbox(viewModel.activeWallet.get())
                ViewModelActiveWallet1 = !androidxDatabindingViewDataBindingSafeUnboxViewModelActiveWalletGet;
            if((dirtyFlags & 0x7L) != 0) {
                if(ViewModelActiveWallet1) {
                        dirtyFlags |= 0x10L;
                }
                else {
                        dirtyFlags |= 0x8L;
                }
            }
            if ((dirtyFlags & 0x6L) != 0) {

                    if (viewModel != null) {
                        // read viewModel.multiCurrencyWallet
                        viewModelMultiCurrencyWallet = viewModel.getMultiCurrencyWallet();
                    }


                    if (viewModelMultiCurrencyWallet != null) {
                        // read viewModel.multiCurrencyWallet.currencyUnit
                        viewModelMultiCurrencyWalletCurrencyUnit = viewModelMultiCurrencyWallet.getCurrencyUnit();
                        // read viewModel.multiCurrencyWallet.country2DigitCode
                        viewModelMultiCurrencyWalletCountry2DigitCode = viewModelMultiCurrencyWallet.getCountry2DigitCode();
                        // read viewModel.multiCurrencyWallet.amount
                        viewModelMultiCurrencyWalletAmount = viewModelMultiCurrencyWallet.getAmount();
                    }
            }
        }
        // batch finished

        if ((dirtyFlags & 0x10L) != 0) {

                // read co.yap.multicurrency.R.drawable.ic_back_arrow_left_gery
                coYapMulticurrencyRDrawableIcBackArrowLeftGery = co.yap.multicurrency.R.drawable.ic_back_arrow_left_gery;
        }
        if ((dirtyFlags & 0x8L) != 0) {

                // read co.yap.multicurrency.R.drawable.ic_hamburger
                coYapMulticurrencyRDrawableIcHamburger = co.yap.multicurrency.R.drawable.ic_hamburger;
        }

        if ((dirtyFlags & 0x7L) != 0) {

                // read !androidx.databinding.ViewDataBinding.safeUnbox(viewModel.activeWallet.get()) ? co.yap.multicurrency.R.drawable.ic_back_arrow_left_gery : co.yap.multicurrency.R.drawable.ic_hamburger
                viewModelActiveWalletCoYapMulticurrencyRDrawableIcBackArrowLeftGeryCoYapMulticurrencyRDrawableIcHamburger = ((ViewModelActiveWallet1) ? (coYapMulticurrencyRDrawableIcBackArrowLeftGery) : (coYapMulticurrencyRDrawableIcHamburger));
        }
        // batch finished
        if ((dirtyFlags & 0x7L) != 0) {
            // api target 1

            co.yap.multicurrency.helper.UIBindingAdapters.updateIconTint(this.ivArrow, viewModelActiveWalletGet);
            co.yap.yapcore.binders.UIBinder.setImageResId(this.ivArrow, viewModelActiveWalletCoYapMulticurrencyRDrawableIcBackArrowLeftGeryCoYapMulticurrencyRDrawableIcHamburger);
        }
        if ((dirtyFlags & 0x6L) != 0) {
            // api target 1

            co.yap.yapcore.helpers.ImageBinding.setIsoCountryDrawable(this.ivFlag, viewModelMultiCurrencyWalletCountry2DigitCode);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.tvAmount, viewModelMultiCurrencyWalletAmount);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.tvCurrencyUnit, viewModelMultiCurrencyWalletCurrencyUnit);
        }
        if ((dirtyFlags & 0x4L) != 0) {
            // api target 1

            co.yap.yapcore.binders.UIBinder.setText(this.mboundView1, co.yap.translation.Strings.common_button_delete);
        }
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): viewModel.activeWallet
        flag 1 (0x2L): viewModel
        flag 2 (0x3L): null
        flag 3 (0x4L): !androidx.databinding.ViewDataBinding.safeUnbox(viewModel.activeWallet.get()) ? co.yap.multicurrency.R.drawable.ic_back_arrow_left_gery : co.yap.multicurrency.R.drawable.ic_hamburger
        flag 4 (0x5L): !androidx.databinding.ViewDataBinding.safeUnbox(viewModel.activeWallet.get()) ? co.yap.multicurrency.R.drawable.ic_back_arrow_left_gery : co.yap.multicurrency.R.drawable.ic_hamburger
    flag mapping end*/
    //end
}