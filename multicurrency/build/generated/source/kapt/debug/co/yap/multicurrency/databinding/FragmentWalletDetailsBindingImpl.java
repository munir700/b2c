package co.yap.multicurrency.databinding;
import co.yap.multicurrency.R;
import co.yap.multicurrency.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class FragmentWalletDetailsBindingImpl extends FragmentWalletDetailsBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = new androidx.databinding.ViewDataBinding.IncludedLayouts(9);
        sIncludes.setIncludes(1, 
            new String[] {"layout_wallet_actions"},
            new int[] {4},
            new int[] {co.yap.multicurrency.R.layout.layout_wallet_actions});
        sIncludes.setIncludes(2, 
            new String[] {"layout_other_currency_wallet_activation", "layout_default_wallet_bank_details"},
            new int[] {5, 6},
            new int[] {co.yap.multicurrency.R.layout.layout_other_currency_wallet_activation,
                co.yap.multicurrency.R.layout.layout_default_wallet_bank_details});
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.ivCountryFlag, 7);
        sViewsWithIds.put(R.id.tvAmount, 8);
    }
    // views
    @NonNull
    private final androidx.constraintlayout.widget.ConstraintLayout mboundView0;
    @NonNull
    private final androidx.constraintlayout.widget.ConstraintLayout mboundView1;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public FragmentWalletDetailsBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 9, sIncludes, sViewsWithIds));
    }
    private FragmentWalletDetailsBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 5
            , (co.yap.widgets.CoreCircularImageView) bindings[7]
            , (co.yap.multicurrency.databinding.LayoutDefaultWalletBankDetailsBinding) bindings[6]
            , (co.yap.multicurrency.databinding.LayoutOtherCurrencyWalletActivationBinding) bindings[5]
            , (co.yap.multicurrency.databinding.LayoutWalletActionsBinding) bindings[4]
            , (androidx.constraintlayout.widget.ConstraintLayout) bindings[2]
            , (androidx.recyclerview.widget.RecyclerView) bindings[3]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[8]
            );
        this.lyWalletTypeDetails.setTag(null);
        this.mboundView0 = (androidx.constraintlayout.widget.ConstraintLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.mboundView1 = (androidx.constraintlayout.widget.ConstraintLayout) bindings[1];
        this.mboundView1.setTag(null);
        this.rvWalletDescription.setTag(null);
        setRootTag(root);
        // listeners
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x40L;
        }
        layoutWalletActions.invalidateAll();
        layoutOtherCurrencyWallet.invalidateAll();
        layoutDefaultCurrencyWallet.invalidateAll();
        requestRebind();
    }

    @Override
    public boolean hasPendingBindings() {
        synchronized(this) {
            if (mDirtyFlags != 0) {
                return true;
            }
        }
        if (layoutWalletActions.hasPendingBindings()) {
            return true;
        }
        if (layoutOtherCurrencyWallet.hasPendingBindings()) {
            return true;
        }
        if (layoutDefaultCurrencyWallet.hasPendingBindings()) {
            return true;
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
            mDirtyFlags |= 0x20L;
        }
        notifyPropertyChanged(BR.viewModel);
        super.requestRebind();
    }

    @Override
    public void setLifecycleOwner(@Nullable androidx.lifecycle.LifecycleOwner lifecycleOwner) {
        super.setLifecycleOwner(lifecycleOwner);
        layoutWalletActions.setLifecycleOwner(lifecycleOwner);
        layoutOtherCurrencyWallet.setLifecycleOwner(lifecycleOwner);
        layoutDefaultCurrencyWallet.setLifecycleOwner(lifecycleOwner);
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
            case 0 :
                return onChangeLayoutDefaultCurrencyWallet((co.yap.multicurrency.databinding.LayoutDefaultWalletBankDetailsBinding) object, fieldId);
            case 1 :
                return onChangeViewModelState((co.yap.multicurrency.walletcountry.WalletDetailsState) object, fieldId);
            case 2 :
                return onChangeViewModelStateIsPrimaryWallet((androidx.lifecycle.MutableLiveData<java.lang.Boolean>) object, fieldId);
            case 3 :
                return onChangeLayoutOtherCurrencyWallet((co.yap.multicurrency.databinding.LayoutOtherCurrencyWalletActivationBinding) object, fieldId);
            case 4 :
                return onChangeLayoutWalletActions((co.yap.multicurrency.databinding.LayoutWalletActionsBinding) object, fieldId);
        }
        return false;
    }
    private boolean onChangeLayoutDefaultCurrencyWallet(co.yap.multicurrency.databinding.LayoutDefaultWalletBankDetailsBinding LayoutDefaultCurrencyWallet, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x1L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeViewModelState(co.yap.multicurrency.walletcountry.WalletDetailsState ViewModelState, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x2L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeViewModelStateIsPrimaryWallet(androidx.lifecycle.MutableLiveData<java.lang.Boolean> ViewModelStateIsPrimaryWallet, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x4L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeLayoutOtherCurrencyWallet(co.yap.multicurrency.databinding.LayoutOtherCurrencyWalletActivationBinding LayoutOtherCurrencyWallet, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x8L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeLayoutWalletActions(co.yap.multicurrency.databinding.LayoutWalletActionsBinding LayoutWalletActions, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x10L;
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
        co.yap.multicurrency.walletcountry.adapter.WalletDetailsListAdapter viewModelAdapter = null;
        java.lang.Boolean viewModelStateIsPrimaryWalletGetValue = null;
        boolean androidxDatabindingViewDataBindingSafeUnboxViewModelStateIsPrimaryWalletGetValue = false;
        int viewModelStateIsPrimaryWalletViewGONEViewVISIBLE = 0;
        co.yap.multicurrency.walletcountry.WalletDetailsState viewModelState = null;
        androidx.lifecycle.MutableLiveData<java.lang.Boolean> viewModelStateIsPrimaryWallet = null;
        int viewModelStateIsPrimaryWalletViewVISIBLEViewGONE = 0;
        co.yap.multicurrency.walletcountry.WalletDetailsViewModel viewModel = mViewModel;

        if ((dirtyFlags & 0x66L) != 0) {


            if ((dirtyFlags & 0x60L) != 0) {

                    if (viewModel != null) {
                        // read viewModel.adapter
                        viewModelAdapter = viewModel.getAdapter();
                    }
            }

                if (viewModel != null) {
                    // read viewModel.state
                    viewModelState = viewModel.getState();
                }
                updateRegistration(1, viewModelState);


                if (viewModelState != null) {
                    // read viewModel.state.isPrimaryWallet
                    viewModelStateIsPrimaryWallet = viewModelState.isPrimaryWallet();
                }
                updateLiveDataRegistration(2, viewModelStateIsPrimaryWallet);


                if (viewModelStateIsPrimaryWallet != null) {
                    // read viewModel.state.isPrimaryWallet.getValue()
                    viewModelStateIsPrimaryWalletGetValue = viewModelStateIsPrimaryWallet.getValue();
                }


                // read androidx.databinding.ViewDataBinding.safeUnbox(viewModel.state.isPrimaryWallet.getValue())
                androidxDatabindingViewDataBindingSafeUnboxViewModelStateIsPrimaryWalletGetValue = androidx.databinding.ViewDataBinding.safeUnbox(viewModelStateIsPrimaryWalletGetValue);
            if((dirtyFlags & 0x66L) != 0) {
                if(androidxDatabindingViewDataBindingSafeUnboxViewModelStateIsPrimaryWalletGetValue) {
                        dirtyFlags |= 0x100L;
                        dirtyFlags |= 0x400L;
                }
                else {
                        dirtyFlags |= 0x80L;
                        dirtyFlags |= 0x200L;
                }
            }


                // read androidx.databinding.ViewDataBinding.safeUnbox(viewModel.state.isPrimaryWallet.getValue()) ? View.GONE : View.VISIBLE
                viewModelStateIsPrimaryWalletViewGONEViewVISIBLE = ((androidxDatabindingViewDataBindingSafeUnboxViewModelStateIsPrimaryWalletGetValue) ? (android.view.View.GONE) : (android.view.View.VISIBLE));
                // read androidx.databinding.ViewDataBinding.safeUnbox(viewModel.state.isPrimaryWallet.getValue()) ? View.VISIBLE : View.GONE
                viewModelStateIsPrimaryWalletViewVISIBLEViewGONE = ((androidxDatabindingViewDataBindingSafeUnboxViewModelStateIsPrimaryWalletGetValue) ? (android.view.View.VISIBLE) : (android.view.View.GONE));
        }
        // batch finished
        if ((dirtyFlags & 0x66L) != 0) {
            // api target 1

            this.layoutDefaultCurrencyWallet.getRoot().setVisibility(viewModelStateIsPrimaryWalletViewGONEViewVISIBLE);
            this.layoutOtherCurrencyWallet.getRoot().setVisibility(viewModelStateIsPrimaryWalletViewVISIBLEViewGONE);
        }
        if ((dirtyFlags & 0x60L) != 0) {
            // api target 1

            this.layoutDefaultCurrencyWallet.setViewModel(viewModel);
            this.layoutOtherCurrencyWallet.setViewModel(viewModel);
            this.layoutWalletActions.setViewModel(viewModel);
            co.yap.yapcore.binders.UIBinder.setRecycleViewAdapter(this.rvWalletDescription, viewModelAdapter);
        }
        executeBindingsOn(layoutWalletActions);
        executeBindingsOn(layoutOtherCurrencyWallet);
        executeBindingsOn(layoutDefaultCurrencyWallet);
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): layoutDefaultCurrencyWallet
        flag 1 (0x2L): viewModel.state
        flag 2 (0x3L): viewModel.state.isPrimaryWallet
        flag 3 (0x4L): layoutOtherCurrencyWallet
        flag 4 (0x5L): layoutWalletActions
        flag 5 (0x6L): viewModel
        flag 6 (0x7L): null
        flag 7 (0x8L): androidx.databinding.ViewDataBinding.safeUnbox(viewModel.state.isPrimaryWallet.getValue()) ? View.GONE : View.VISIBLE
        flag 8 (0x9L): androidx.databinding.ViewDataBinding.safeUnbox(viewModel.state.isPrimaryWallet.getValue()) ? View.GONE : View.VISIBLE
        flag 9 (0xaL): androidx.databinding.ViewDataBinding.safeUnbox(viewModel.state.isPrimaryWallet.getValue()) ? View.VISIBLE : View.GONE
        flag 10 (0xbL): androidx.databinding.ViewDataBinding.safeUnbox(viewModel.state.isPrimaryWallet.getValue()) ? View.VISIBLE : View.GONE
    flag mapping end*/
    //end
}