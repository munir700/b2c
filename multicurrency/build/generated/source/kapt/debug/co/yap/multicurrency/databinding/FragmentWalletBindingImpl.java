package co.yap.multicurrency.databinding;
import co.yap.multicurrency.R;
import co.yap.multicurrency.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class FragmentWalletBindingImpl extends FragmentWalletBinding implements co.yap.multicurrency.generated.callback.OnClickListener.Listener {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.clYapScreenContainer, 11);
        sViewsWithIds.put(R.id.ivFlag, 12);
        sViewsWithIds.put(R.id.tvAmount, 13);
        sViewsWithIds.put(R.id.tvCurrencyUnit, 14);
        sViewsWithIds.put(R.id.ivArrow, 15);
        sViewsWithIds.put(R.id.divider, 16);
    }
    // views
    @NonNull
    private final android.widget.ScrollView mboundView0;
    // variables
    @Nullable
    private final android.view.View.OnClickListener mCallback6;
    @Nullable
    private final android.view.View.OnClickListener mCallback8;
    @Nullable
    private final android.view.View.OnClickListener mCallback7;
    // values
    // listeners
    // Inverse Binding Event Handlers

    public FragmentWalletBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 17, sIncludes, sViewsWithIds));
    }
    private FragmentWalletBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 3
            , (androidx.constraintlayout.widget.ConstraintLayout) bindings[3]
            , (androidx.constraintlayout.widget.ConstraintLayout) bindings[11]
            , (android.view.View) bindings[16]
            , (androidx.appcompat.widget.AppCompatImageView) bindings[15]
            , (androidx.appcompat.widget.AppCompatImageView) bindings[12]
            , (androidx.recyclerview.widget.RecyclerView) bindings[10]
            , (androidx.recyclerview.widget.RecyclerView) bindings[7]
            , (android.view.View) bindings[8]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[6]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[13]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[14]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[2]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[1]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[9]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[5]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[4]
            );
        this.clPrimaryWallet.setTag(null);
        this.mboundView0 = (android.widget.ScrollView) bindings[0];
        this.mboundView0.setTag(null);
        this.rvInActiveWallets.setTag(null);
        this.rvWallets.setTag(null);
        this.secondDivider.setTag(null);
        this.tvAddNewWallet.setTag(null);
        this.tvEdit.setTag(null);
        this.tvHeading.setTag(null);
        this.tvInactiveWallet.setTag(null);
        this.tvNoWallet.setTag(null);
        this.tvWalletType.setTag(null);
        setRootTag(root);
        // listeners
        mCallback6 = new co.yap.multicurrency.generated.callback.OnClickListener(this, 1);
        mCallback8 = new co.yap.multicurrency.generated.callback.OnClickListener(this, 3);
        mCallback7 = new co.yap.multicurrency.generated.callback.OnClickListener(this, 2);
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x10L;
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
            setViewModel((co.yap.multicurrency.wallets.WalletsViewModel) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setViewModel(@Nullable co.yap.multicurrency.wallets.WalletsViewModel ViewModel) {
        this.mViewModel = ViewModel;
        synchronized(this) {
            mDirtyFlags |= 0x8L;
        }
        notifyPropertyChanged(BR.viewModel);
        super.requestRebind();
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
            case 0 :
                return onChangeViewModelActiveWallet((androidx.databinding.ObservableField<java.lang.Boolean>) object, fieldId);
            case 1 :
                return onChangeViewModelInActiveWallet((androidx.databinding.ObservableField<java.lang.Boolean>) object, fieldId);
            case 2 :
                return onChangeViewModelStateEditWalletText((androidx.databinding.ObservableField<java.lang.String>) object, fieldId);
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
    private boolean onChangeViewModelInActiveWallet(androidx.databinding.ObservableField<java.lang.Boolean> ViewModelInActiveWallet, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x2L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeViewModelStateEditWalletText(androidx.databinding.ObservableField<java.lang.String> ViewModelStateEditWalletText, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x4L;
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
        co.yap.multicurrency.wallets.IWallets.State viewModelState = null;
        boolean viewModelActiveWalletBooleanFalse = false;
        int viewModelInActiveWalletBooleanTrueViewModelActiveWalletBooleanTrueBooleanFalseViewVISIBLEViewGONE = 0;
        int viewModelInActiveWalletBooleanTrueViewVISIBLEViewGONE = 0;
        androidx.databinding.ObservableField<java.lang.Boolean> viewModelActiveWallet = null;
        int viewModelInActiveWalletBooleanFalseViewModelActiveWalletBooleanFalseBooleanFalseViewVISIBLEViewGONE = 0;
        java.lang.Boolean viewModelActiveWalletGet = null;
        int viewModelInActiveWalletsListSize = 0;
        int viewModelActiveWalletBooleanTrueViewVISIBLEViewGONE = 0;
        boolean androidxDatabindingViewDataBindingSafeUnboxViewModelInActiveWalletGet = false;
        boolean viewModelActiveWalletBooleanTrue = false;
        java.lang.Boolean viewModelInActiveWalletGet = null;
        int viewModelInActiveWalletsListSizeInt0ViewVISIBLEViewGONE = 0;
        co.yap.multicurrency.wallets.adapter.WalletsAdapter viewModelInActiveWalletsAdapter = null;
        boolean viewModelInActiveWalletBooleanTrueViewModelActiveWalletBooleanTrueBooleanFalse = false;
        boolean androidxDatabindingViewDataBindingSafeUnboxViewModelActiveWalletGet = false;
        boolean viewModelInActiveWalletBooleanTrue = false;
        boolean viewModelInActiveWalletBooleanFalse = false;
        java.lang.String viewModelStateEditWalletTextGet = null;
        java.util.List<co.yap.multicurrency.wallets.MultiCurrencyWallet> viewModelInActiveWalletsList = null;
        boolean viewModelInActiveWalletBooleanFalseViewModelActiveWalletBooleanFalseBooleanFalse = false;
        androidx.databinding.ObservableField<java.lang.Boolean> viewModelInActiveWallet = null;
        androidx.databinding.ObservableField<java.lang.String> viewModelStateEditWalletText = null;
        co.yap.multicurrency.wallets.WalletsViewModel viewModel = mViewModel;
        co.yap.multicurrency.wallets.adapter.WalletsAdapter viewModelWalletsAdapter = null;
        boolean viewModelInActiveWalletsListSizeInt0 = false;

        if ((dirtyFlags & 0x1fL) != 0) {


            if ((dirtyFlags & 0x1cL) != 0) {

                    if (viewModel != null) {
                        // read viewModel.state
                        viewModelState = viewModel.getState();
                    }


                    if (viewModelState != null) {
                        // read viewModel.state.editWalletText
                        viewModelStateEditWalletText = viewModelState.getEditWalletText();
                    }
                    updateRegistration(2, viewModelStateEditWalletText);


                    if (viewModelStateEditWalletText != null) {
                        // read viewModel.state.editWalletText.get()
                        viewModelStateEditWalletTextGet = viewModelStateEditWalletText.get();
                    }
            }
            if ((dirtyFlags & 0x19L) != 0) {

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


                    // read androidx.databinding.ViewDataBinding.safeUnbox(viewModel.activeWallet.get()) == true
                    viewModelActiveWalletBooleanTrue = (androidxDatabindingViewDataBindingSafeUnboxViewModelActiveWalletGet) == (true);
                if((dirtyFlags & 0x19L) != 0) {
                    if(viewModelActiveWalletBooleanTrue) {
                            dirtyFlags |= 0x1000L;
                    }
                    else {
                            dirtyFlags |= 0x800L;
                    }
                }


                    // read androidx.databinding.ViewDataBinding.safeUnbox(viewModel.activeWallet.get()) == true ? View.VISIBLE : View.GONE
                    viewModelActiveWalletBooleanTrueViewVISIBLEViewGONE = ((viewModelActiveWalletBooleanTrue) ? (android.view.View.VISIBLE) : (android.view.View.GONE));
            }
            if ((dirtyFlags & 0x18L) != 0) {

                    if (viewModel != null) {
                        // read viewModel.inActiveWalletsAdapter
                        viewModelInActiveWalletsAdapter = viewModel.getInActiveWalletsAdapter();
                        // read viewModel.inActiveWalletsList
                        viewModelInActiveWalletsList = viewModel.getInActiveWalletsList();
                        // read viewModel.walletsAdapter
                        viewModelWalletsAdapter = viewModel.getWalletsAdapter();
                    }


                    if (viewModelInActiveWalletsList != null) {
                        // read viewModel.inActiveWalletsList.size()
                        viewModelInActiveWalletsListSize = viewModelInActiveWalletsList.size();
                    }


                    // read viewModel.inActiveWalletsList.size() > 0
                    viewModelInActiveWalletsListSizeInt0 = (viewModelInActiveWalletsListSize) > (0);
                if((dirtyFlags & 0x18L) != 0) {
                    if(viewModelInActiveWalletsListSizeInt0) {
                            dirtyFlags |= 0x4000L;
                    }
                    else {
                            dirtyFlags |= 0x2000L;
                    }
                }


                    // read viewModel.inActiveWalletsList.size() > 0 ? View.VISIBLE : View.GONE
                    viewModelInActiveWalletsListSizeInt0ViewVISIBLEViewGONE = ((viewModelInActiveWalletsListSizeInt0) ? (android.view.View.VISIBLE) : (android.view.View.GONE));
            }
            if ((dirtyFlags & 0x1bL) != 0) {

                    if (viewModel != null) {
                        // read viewModel.inActiveWallet
                        viewModelInActiveWallet = viewModel.getInActiveWallet();
                    }
                    updateRegistration(1, viewModelInActiveWallet);


                    if (viewModelInActiveWallet != null) {
                        // read viewModel.inActiveWallet.get()
                        viewModelInActiveWalletGet = viewModelInActiveWallet.get();
                    }


                    // read androidx.databinding.ViewDataBinding.safeUnbox(viewModel.inActiveWallet.get())
                    androidxDatabindingViewDataBindingSafeUnboxViewModelInActiveWalletGet = androidx.databinding.ViewDataBinding.safeUnbox(viewModelInActiveWalletGet);


                    // read androidx.databinding.ViewDataBinding.safeUnbox(viewModel.inActiveWallet.get()) == true
                    viewModelInActiveWalletBooleanTrue = (androidxDatabindingViewDataBindingSafeUnboxViewModelInActiveWalletGet) == (true);
                    // read androidx.databinding.ViewDataBinding.safeUnbox(viewModel.inActiveWallet.get()) == false
                    viewModelInActiveWalletBooleanFalse = (androidxDatabindingViewDataBindingSafeUnboxViewModelInActiveWalletGet) == (false);
                if((dirtyFlags & 0x1aL) != 0) {
                    if(viewModelInActiveWalletBooleanTrue) {
                            dirtyFlags |= 0x100L;
                    }
                    else {
                            dirtyFlags |= 0x80L;
                    }
                }
                if((dirtyFlags & 0x1bL) != 0) {
                    if(viewModelInActiveWalletBooleanTrue) {
                            dirtyFlags |= 0x10000L;
                    }
                    else {
                            dirtyFlags |= 0x8000L;
                    }
                }
                if((dirtyFlags & 0x1bL) != 0) {
                    if(viewModelInActiveWalletBooleanFalse) {
                            dirtyFlags |= 0x40000L;
                    }
                    else {
                            dirtyFlags |= 0x20000L;
                    }
                }

                if ((dirtyFlags & 0x1aL) != 0) {

                        // read androidx.databinding.ViewDataBinding.safeUnbox(viewModel.inActiveWallet.get()) == true ? View.VISIBLE : View.GONE
                        viewModelInActiveWalletBooleanTrueViewVISIBLEViewGONE = ((viewModelInActiveWalletBooleanTrue) ? (android.view.View.VISIBLE) : (android.view.View.GONE));
                }
            }
        }
        // batch finished

        if ((dirtyFlags & 0x50000L) != 0) {

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

            if ((dirtyFlags & 0x40000L) != 0) {

                    // read androidx.databinding.ViewDataBinding.safeUnbox(viewModel.activeWallet.get()) == false
                    viewModelActiveWalletBooleanFalse = (androidxDatabindingViewDataBindingSafeUnboxViewModelActiveWalletGet) == (false);
            }
            if ((dirtyFlags & 0x10000L) != 0) {

                    // read androidx.databinding.ViewDataBinding.safeUnbox(viewModel.activeWallet.get()) == true
                    viewModelActiveWalletBooleanTrue = (androidxDatabindingViewDataBindingSafeUnboxViewModelActiveWalletGet) == (true);
                if((dirtyFlags & 0x19L) != 0) {
                    if(viewModelActiveWalletBooleanTrue) {
                            dirtyFlags |= 0x1000L;
                    }
                    else {
                            dirtyFlags |= 0x800L;
                    }
                }
            }
        }

        if ((dirtyFlags & 0x1bL) != 0) {

                // read androidx.databinding.ViewDataBinding.safeUnbox(viewModel.inActiveWallet.get()) == true ? androidx.databinding.ViewDataBinding.safeUnbox(viewModel.activeWallet.get()) == true : false
                viewModelInActiveWalletBooleanTrueViewModelActiveWalletBooleanTrueBooleanFalse = ((viewModelInActiveWalletBooleanTrue) ? (viewModelActiveWalletBooleanTrue) : (false));
                // read androidx.databinding.ViewDataBinding.safeUnbox(viewModel.inActiveWallet.get()) == false ? androidx.databinding.ViewDataBinding.safeUnbox(viewModel.activeWallet.get()) == false : false
                viewModelInActiveWalletBooleanFalseViewModelActiveWalletBooleanFalseBooleanFalse = ((viewModelInActiveWalletBooleanFalse) ? (viewModelActiveWalletBooleanFalse) : (false));
            if((dirtyFlags & 0x1bL) != 0) {
                if(viewModelInActiveWalletBooleanTrueViewModelActiveWalletBooleanTrueBooleanFalse) {
                        dirtyFlags |= 0x40L;
                }
                else {
                        dirtyFlags |= 0x20L;
                }
            }
            if((dirtyFlags & 0x1bL) != 0) {
                if(viewModelInActiveWalletBooleanFalseViewModelActiveWalletBooleanFalseBooleanFalse) {
                        dirtyFlags |= 0x400L;
                }
                else {
                        dirtyFlags |= 0x200L;
                }
            }


                // read androidx.databinding.ViewDataBinding.safeUnbox(viewModel.inActiveWallet.get()) == true ? androidx.databinding.ViewDataBinding.safeUnbox(viewModel.activeWallet.get()) == true : false ? View.VISIBLE : View.GONE
                viewModelInActiveWalletBooleanTrueViewModelActiveWalletBooleanTrueBooleanFalseViewVISIBLEViewGONE = ((viewModelInActiveWalletBooleanTrueViewModelActiveWalletBooleanTrueBooleanFalse) ? (android.view.View.VISIBLE) : (android.view.View.GONE));
                // read androidx.databinding.ViewDataBinding.safeUnbox(viewModel.inActiveWallet.get()) == false ? androidx.databinding.ViewDataBinding.safeUnbox(viewModel.activeWallet.get()) == false : false ? View.VISIBLE : View.GONE
                viewModelInActiveWalletBooleanFalseViewModelActiveWalletBooleanFalseBooleanFalseViewVISIBLEViewGONE = ((viewModelInActiveWalletBooleanFalseViewModelActiveWalletBooleanFalseBooleanFalse) ? (android.view.View.VISIBLE) : (android.view.View.GONE));
        }
        // batch finished
        if ((dirtyFlags & 0x10L) != 0) {
            // api target 1

            this.clPrimaryWallet.setOnClickListener(mCallback7);
            this.tvAddNewWallet.setOnClickListener(mCallback8);
            co.yap.yapcore.binders.UIBinder.setText(this.tvAddNewWallet, co.yap.translation.Strings.screen_multi_currency_wallet_display_text_add_new_wallet);
            this.tvEdit.setOnClickListener(mCallback6);
            co.yap.yapcore.binders.UIBinder.setText(this.tvEdit, co.yap.translation.Strings.screen_multi_currency_wallet_display_text_edit_wallet);
            co.yap.yapcore.binders.UIBinder.setText(this.tvHeading, co.yap.translation.Strings.screen_multi_currency_wallet_display_text_active_wallets);
            co.yap.yapcore.binders.UIBinder.setText(this.tvInactiveWallet, co.yap.translation.Strings.screen_multi_currency_wallet_display_text_inactive_wallets);
            co.yap.yapcore.binders.UIBinder.setText(this.tvNoWallet, co.yap.translation.Strings.screen_multi_currency_wallet_display_text_no_wallets);
            co.yap.yapcore.binders.UIBinder.setText(this.tvWalletType, co.yap.translation.Strings.screen_multi_currency_wallet_display_text_primary);
        }
        if ((dirtyFlags & 0x18L) != 0) {
            // api target 1

            this.rvInActiveWallets.setVisibility(viewModelInActiveWalletsListSizeInt0ViewVISIBLEViewGONE);
            co.yap.yapcore.binders.UIBinder.setRecycleViewAdapter(this.rvInActiveWallets, viewModelInActiveWalletsAdapter);
            co.yap.yapcore.binders.UIBinder.setRecycleViewAdapter(this.rvWallets, viewModelWalletsAdapter);
        }
        if ((dirtyFlags & 0x19L) != 0) {
            // api target 1

            this.rvWallets.setVisibility(viewModelActiveWalletBooleanTrueViewVISIBLEViewGONE);
            this.tvEdit.setVisibility(viewModelActiveWalletBooleanTrueViewVISIBLEViewGONE);
        }
        if ((dirtyFlags & 0x1bL) != 0) {
            // api target 1

            this.secondDivider.setVisibility(viewModelInActiveWalletBooleanTrueViewModelActiveWalletBooleanTrueBooleanFalseViewVISIBLEViewGONE);
            this.tvAddNewWallet.setVisibility(viewModelInActiveWalletBooleanFalseViewModelActiveWalletBooleanFalseBooleanFalseViewVISIBLEViewGONE);
            this.tvNoWallet.setVisibility(viewModelInActiveWalletBooleanFalseViewModelActiveWalletBooleanFalseBooleanFalseViewVISIBLEViewGONE);
        }
        if ((dirtyFlags & 0x1cL) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.tvEdit, viewModelStateEditWalletTextGet);
        }
        if ((dirtyFlags & 0x1aL) != 0) {
            // api target 1

            this.tvInactiveWallet.setVisibility(viewModelInActiveWalletBooleanTrueViewVISIBLEViewGONE);
        }
    }
    // Listener Stub Implementations
    // callback impls
    public final void _internalCallbackOnClick(int sourceId , android.view.View callbackArg_0) {
        switch(sourceId) {
            case 1: {
                // localize variables for thread safety
                // viewModel
                co.yap.multicurrency.wallets.WalletsViewModel viewModel = mViewModel;
                // viewModel != null
                boolean viewModelJavaLangObjectNull = false;



                viewModelJavaLangObjectNull = (viewModel) != (null);
                if (viewModelJavaLangObjectNull) {


                    viewModel.editWallet();
                }
                break;
            }
            case 3: {
                // localize variables for thread safety
                // viewModel
                co.yap.multicurrency.wallets.WalletsViewModel viewModel = mViewModel;
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
                co.yap.multicurrency.wallets.WalletsViewModel viewModel = mViewModel;
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
        flag 0 (0x1L): viewModel.activeWallet
        flag 1 (0x2L): viewModel.inActiveWallet
        flag 2 (0x3L): viewModel.state.editWalletText
        flag 3 (0x4L): viewModel
        flag 4 (0x5L): null
        flag 5 (0x6L): androidx.databinding.ViewDataBinding.safeUnbox(viewModel.inActiveWallet.get()) == true ? androidx.databinding.ViewDataBinding.safeUnbox(viewModel.activeWallet.get()) == true : false ? View.VISIBLE : View.GONE
        flag 6 (0x7L): androidx.databinding.ViewDataBinding.safeUnbox(viewModel.inActiveWallet.get()) == true ? androidx.databinding.ViewDataBinding.safeUnbox(viewModel.activeWallet.get()) == true : false ? View.VISIBLE : View.GONE
        flag 7 (0x8L): androidx.databinding.ViewDataBinding.safeUnbox(viewModel.inActiveWallet.get()) == true ? View.VISIBLE : View.GONE
        flag 8 (0x9L): androidx.databinding.ViewDataBinding.safeUnbox(viewModel.inActiveWallet.get()) == true ? View.VISIBLE : View.GONE
        flag 9 (0xaL): androidx.databinding.ViewDataBinding.safeUnbox(viewModel.inActiveWallet.get()) == false ? androidx.databinding.ViewDataBinding.safeUnbox(viewModel.activeWallet.get()) == false : false ? View.VISIBLE : View.GONE
        flag 10 (0xbL): androidx.databinding.ViewDataBinding.safeUnbox(viewModel.inActiveWallet.get()) == false ? androidx.databinding.ViewDataBinding.safeUnbox(viewModel.activeWallet.get()) == false : false ? View.VISIBLE : View.GONE
        flag 11 (0xcL): androidx.databinding.ViewDataBinding.safeUnbox(viewModel.activeWallet.get()) == true ? View.VISIBLE : View.GONE
        flag 12 (0xdL): androidx.databinding.ViewDataBinding.safeUnbox(viewModel.activeWallet.get()) == true ? View.VISIBLE : View.GONE
        flag 13 (0xeL): viewModel.inActiveWalletsList.size() > 0 ? View.VISIBLE : View.GONE
        flag 14 (0xfL): viewModel.inActiveWalletsList.size() > 0 ? View.VISIBLE : View.GONE
        flag 15 (0x10L): androidx.databinding.ViewDataBinding.safeUnbox(viewModel.inActiveWallet.get()) == true ? androidx.databinding.ViewDataBinding.safeUnbox(viewModel.activeWallet.get()) == true : false
        flag 16 (0x11L): androidx.databinding.ViewDataBinding.safeUnbox(viewModel.inActiveWallet.get()) == true ? androidx.databinding.ViewDataBinding.safeUnbox(viewModel.activeWallet.get()) == true : false
        flag 17 (0x12L): androidx.databinding.ViewDataBinding.safeUnbox(viewModel.inActiveWallet.get()) == false ? androidx.databinding.ViewDataBinding.safeUnbox(viewModel.activeWallet.get()) == false : false
        flag 18 (0x13L): androidx.databinding.ViewDataBinding.safeUnbox(viewModel.inActiveWallet.get()) == false ? androidx.databinding.ViewDataBinding.safeUnbox(viewModel.activeWallet.get()) == false : false
    flag mapping end*/
    //end
}