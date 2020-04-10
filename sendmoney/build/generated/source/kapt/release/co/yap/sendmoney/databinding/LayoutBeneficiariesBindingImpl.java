package co.yap.sendmoney.databinding;
import co.yap.sendmoney.R;
import co.yap.sendmoney.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class LayoutBeneficiariesBindingImpl extends LayoutBeneficiariesBinding implements co.yap.sendmoney.generated.callback.OnClickListener.Listener {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = new androidx.databinding.ViewDataBinding.IncludedLayouts(8);
        sIncludes.setIncludes(0, 
            new String[] {"layout_recent_beneficiaries", "layout_beneficiairy_search"},
            new int[] {5, 6},
            new int[] {co.yap.sendmoney.R.layout.layout_recent_beneficiaries,
                co.yap.sendmoney.R.layout.layout_beneficiairy_search});
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.rvAllBeneficiaries, 7);
    }
    // views
    // variables
    @Nullable
    private final android.view.View.OnClickListener mCallback35;
    // values
    // listeners
    // Inverse Binding Event Handlers

    public LayoutBeneficiariesBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 8, sIncludes, sViewsWithIds));
    }
    private LayoutBeneficiariesBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 7
            , (androidx.constraintlayout.widget.ConstraintLayout) bindings[0]
            , (co.yap.sendmoney.databinding.LayoutRecentBeneficiariesBinding) bindings[5]
            , (co.yap.sendmoney.databinding.LayoutBeneficiairySearchBinding) bindings[6]
            , (androidx.recyclerview.widget.RecyclerView) bindings[7]
            , (android.widget.TextView) bindings[4]
            , (android.widget.TextView) bindings[2]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[1]
            , (android.widget.TextView) bindings[3]
            );
        this.clYapScreenContainer.setTag(null);
        this.tvAllBeneficiaries.setTag(null);
        this.tvCancel.setTag(null);
        this.tvSendMoneyTo.setTag(null);
        this.txtError.setTag(null);
        setRootTag(root);
        // listeners
        mCallback35 = new co.yap.sendmoney.generated.callback.OnClickListener(this, 1);
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x100L;
        }
        layoutRecent.invalidateAll();
        layoutSearchView.invalidateAll();
        requestRebind();
    }

    @Override
    public boolean hasPendingBindings() {
        synchronized(this) {
            if (mDirtyFlags != 0) {
                return true;
            }
        }
        if (layoutRecent.hasPendingBindings()) {
            return true;
        }
        if (layoutSearchView.hasPendingBindings()) {
            return true;
        }
        return false;
    }

    @Override
    public boolean setVariable(int variableId, @Nullable Object variable)  {
        boolean variableSet = true;
        if (BR.viewModel == variableId) {
            setViewModel((co.yap.sendMoney.home.viewmodels.SendMoneyHomeScreenViewModel) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setViewModel(@Nullable co.yap.sendMoney.home.viewmodels.SendMoneyHomeScreenViewModel ViewModel) {
        this.mViewModel = ViewModel;
        synchronized(this) {
            mDirtyFlags |= 0x80L;
        }
        notifyPropertyChanged(BR.viewModel);
        super.requestRebind();
    }

    @Override
    public void setLifecycleOwner(@Nullable androidx.lifecycle.LifecycleOwner lifecycleOwner) {
        super.setLifecycleOwner(lifecycleOwner);
        layoutRecent.setLifecycleOwner(lifecycleOwner);
        layoutSearchView.setLifecycleOwner(lifecycleOwner);
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
            case 0 :
                return onChangeViewModelState((co.yap.sendMoney.home.states.SendMoneyHomeState) object, fieldId);
            case 1 :
                return onChangeViewModelStateIsSearching((androidx.databinding.ObservableField<java.lang.Boolean>) object, fieldId);
            case 2 :
                return onChangeViewModelStateIsSearching1((androidx.databinding.ObservableField<java.lang.Boolean>) object, fieldId);
            case 3 :
                return onChangeLayoutRecent((co.yap.sendmoney.databinding.LayoutRecentBeneficiariesBinding) object, fieldId);
            case 4 :
                return onChangeViewModelAdapter((androidx.databinding.ObservableField<co.yap.sendMoney.home.adapters.RecentTransferAdaptor>) object, fieldId);
            case 5 :
                return onChangeLayoutSearchView((co.yap.sendmoney.databinding.LayoutBeneficiairySearchBinding) object, fieldId);
            case 6 :
                return onChangeViewModelStateIsNoRecentBeneficiary((androidx.databinding.ObservableField<java.lang.Boolean>) object, fieldId);
        }
        return false;
    }
    private boolean onChangeViewModelState(co.yap.sendMoney.home.states.SendMoneyHomeState ViewModelState, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x1L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeViewModelStateIsSearching(androidx.databinding.ObservableField<java.lang.Boolean> ViewModelStateIsSearching, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x2L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeViewModelStateIsSearching1(androidx.databinding.ObservableField<java.lang.Boolean> ViewModelStateIsSearching, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x4L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeLayoutRecent(co.yap.sendmoney.databinding.LayoutRecentBeneficiariesBinding LayoutRecent, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x8L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeViewModelAdapter(androidx.databinding.ObservableField<co.yap.sendMoney.home.adapters.RecentTransferAdaptor> ViewModelAdapter, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x10L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeLayoutSearchView(co.yap.sendmoney.databinding.LayoutBeneficiairySearchBinding LayoutSearchView, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x20L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeViewModelStateIsNoRecentBeneficiary(androidx.databinding.ObservableField<java.lang.Boolean> ViewModelStateIsNoRecentBeneficiary, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x40L;
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
        boolean androidxDatabindingViewDataBindingSafeUnboxViewModelStateIsNoRecentBeneficiaryGet = false;
        co.yap.sendMoney.home.states.SendMoneyHomeState viewModelState = null;
        androidx.databinding.ObservableField<java.lang.Boolean> viewModelStateIsSearching = null;
        java.lang.Boolean viewModelStateIsNoRecentBeneficiaryGet = null;
        java.lang.Boolean viewModelStateIsSearchingGet = null;
        androidx.databinding.ObservableField<java.lang.Boolean> ViewModelStateIsSearching1 = null;
        androidx.databinding.ObservableField<co.yap.sendMoney.home.adapters.RecentTransferAdaptor> viewModelAdapter = null;
        java.lang.Boolean ViewModelStateIsSearchingGet1 = null;
        int viewModelStateIsSearchingViewGONEViewVISIBLE = 0;
        androidx.databinding.ObservableField<java.lang.Boolean> viewModelStateIsNoRecentBeneficiary = null;
        boolean viewModelStateIsSearchingBooleanTrueViewModelStateIsNoRecentBeneficiary = false;
        int viewModelStateIsSearchingViewVISIBLEViewGONE = 0;
        boolean androidxDatabindingViewDataBindingSafeUnboxViewModelStateIsSearchingGet = false;
        boolean AndroidxDatabindingViewDataBindingSafeUnboxViewModelStateIsSearchingGet1 = false;
        int ViewModelStateIsSearchingViewGONEViewVISIBLE1 = 0;
        int viewModelStateIsSearchingBooleanTrueViewModelStateIsNoRecentBeneficiaryViewGONEViewVISIBLE = 0;
        co.yap.sendMoney.home.adapters.RecentTransferAdaptor viewModelAdapterGet = null;
        co.yap.sendMoney.home.viewmodels.SendMoneyHomeScreenViewModel viewModel = mViewModel;

        if ((dirtyFlags & 0x1d7L) != 0) {


            if ((dirtyFlags & 0x1c7L) != 0) {

                    if (viewModel != null) {
                        // read viewModel.state
                        viewModelState = viewModel.getState();
                    }
                    updateRegistration(0, viewModelState);

                if ((dirtyFlags & 0x1c3L) != 0) {

                        if (viewModelState != null) {
                            // read viewModel.state.isSearching
                            viewModelStateIsSearching = viewModelState.isSearching();
                        }
                        updateRegistration(1, viewModelStateIsSearching);


                        if (viewModelStateIsSearching != null) {
                            // read viewModel.state.isSearching.get()
                            viewModelStateIsSearchingGet = viewModelStateIsSearching.get();
                        }


                        // read androidx.databinding.ViewDataBinding.safeUnbox(viewModel.state.isSearching.get())
                        androidxDatabindingViewDataBindingSafeUnboxViewModelStateIsSearchingGet = androidx.databinding.ViewDataBinding.safeUnbox(viewModelStateIsSearchingGet);
                    if((dirtyFlags & 0x1c3L) != 0) {
                        if(androidxDatabindingViewDataBindingSafeUnboxViewModelStateIsSearchingGet) {
                                dirtyFlags |= 0x1000L;
                        }
                        else {
                                dirtyFlags |= 0x800L;
                        }
                    }
                    if((dirtyFlags & 0x183L) != 0) {
                        if(androidxDatabindingViewDataBindingSafeUnboxViewModelStateIsSearchingGet) {
                                dirtyFlags |= 0x10000L;
                        }
                        else {
                                dirtyFlags |= 0x8000L;
                        }
                    }

                    if ((dirtyFlags & 0x183L) != 0) {

                            // read androidx.databinding.ViewDataBinding.safeUnbox(viewModel.state.isSearching.get()) ? View.GONE : View.VISIBLE
                            ViewModelStateIsSearchingViewGONEViewVISIBLE1 = ((androidxDatabindingViewDataBindingSafeUnboxViewModelStateIsSearchingGet) ? (android.view.View.GONE) : (android.view.View.VISIBLE));
                    }
                }
                if ((dirtyFlags & 0x185L) != 0) {

                        if (viewModelState != null) {
                            // read viewModel.state.isSearching()
                            ViewModelStateIsSearching1 = viewModelState.isSearching();
                        }
                        updateRegistration(2, ViewModelStateIsSearching1);


                        if (ViewModelStateIsSearching1 != null) {
                            // read viewModel.state.isSearching().get()
                            ViewModelStateIsSearchingGet1 = ViewModelStateIsSearching1.get();
                        }


                        // read androidx.databinding.ViewDataBinding.safeUnbox(viewModel.state.isSearching().get())
                        AndroidxDatabindingViewDataBindingSafeUnboxViewModelStateIsSearchingGet1 = androidx.databinding.ViewDataBinding.safeUnbox(ViewModelStateIsSearchingGet1);
                    if((dirtyFlags & 0x185L) != 0) {
                        if(AndroidxDatabindingViewDataBindingSafeUnboxViewModelStateIsSearchingGet1) {
                                dirtyFlags |= 0x400L;
                                dirtyFlags |= 0x4000L;
                        }
                        else {
                                dirtyFlags |= 0x200L;
                                dirtyFlags |= 0x2000L;
                        }
                    }


                        // read androidx.databinding.ViewDataBinding.safeUnbox(viewModel.state.isSearching().get()) ? View.GONE : View.VISIBLE
                        viewModelStateIsSearchingViewGONEViewVISIBLE = ((AndroidxDatabindingViewDataBindingSafeUnboxViewModelStateIsSearchingGet1) ? (android.view.View.GONE) : (android.view.View.VISIBLE));
                        // read androidx.databinding.ViewDataBinding.safeUnbox(viewModel.state.isSearching().get()) ? View.VISIBLE : View.GONE
                        viewModelStateIsSearchingViewVISIBLEViewGONE = ((AndroidxDatabindingViewDataBindingSafeUnboxViewModelStateIsSearchingGet1) ? (android.view.View.VISIBLE) : (android.view.View.GONE));
                }
            }
            if ((dirtyFlags & 0x190L) != 0) {

                    if (viewModel != null) {
                        // read viewModel.adapter
                        viewModelAdapter = viewModel.getAdapter();
                    }
                    updateRegistration(4, viewModelAdapter);


                    if (viewModelAdapter != null) {
                        // read viewModel.adapter.get()
                        viewModelAdapterGet = viewModelAdapter.get();
                    }
            }
        }
        // batch finished

        if ((dirtyFlags & 0x800L) != 0) {

                if (viewModelState != null) {
                    // read viewModel.state.isNoRecentBeneficiary
                    viewModelStateIsNoRecentBeneficiary = viewModelState.isNoRecentBeneficiary();
                }
                updateRegistration(6, viewModelStateIsNoRecentBeneficiary);


                if (viewModelStateIsNoRecentBeneficiary != null) {
                    // read viewModel.state.isNoRecentBeneficiary.get()
                    viewModelStateIsNoRecentBeneficiaryGet = viewModelStateIsNoRecentBeneficiary.get();
                }


                // read androidx.databinding.ViewDataBinding.safeUnbox(viewModel.state.isNoRecentBeneficiary.get())
                androidxDatabindingViewDataBindingSafeUnboxViewModelStateIsNoRecentBeneficiaryGet = androidx.databinding.ViewDataBinding.safeUnbox(viewModelStateIsNoRecentBeneficiaryGet);
        }

        if ((dirtyFlags & 0x1c3L) != 0) {

                // read androidx.databinding.ViewDataBinding.safeUnbox(viewModel.state.isSearching.get()) ? true : androidx.databinding.ViewDataBinding.safeUnbox(viewModel.state.isNoRecentBeneficiary.get())
                viewModelStateIsSearchingBooleanTrueViewModelStateIsNoRecentBeneficiary = ((androidxDatabindingViewDataBindingSafeUnboxViewModelStateIsSearchingGet) ? (true) : (androidxDatabindingViewDataBindingSafeUnboxViewModelStateIsNoRecentBeneficiaryGet));
            if((dirtyFlags & 0x1c3L) != 0) {
                if(viewModelStateIsSearchingBooleanTrueViewModelStateIsNoRecentBeneficiary) {
                        dirtyFlags |= 0x40000L;
                }
                else {
                        dirtyFlags |= 0x20000L;
                }
            }


                // read androidx.databinding.ViewDataBinding.safeUnbox(viewModel.state.isSearching.get()) ? true : androidx.databinding.ViewDataBinding.safeUnbox(viewModel.state.isNoRecentBeneficiary.get()) ? View.GONE : View.VISIBLE
                viewModelStateIsSearchingBooleanTrueViewModelStateIsNoRecentBeneficiaryViewGONEViewVISIBLE = ((viewModelStateIsSearchingBooleanTrueViewModelStateIsNoRecentBeneficiary) ? (android.view.View.GONE) : (android.view.View.VISIBLE));
        }
        // batch finished
        if ((dirtyFlags & 0x1c3L) != 0) {
            // api target 1

            this.layoutRecent.getRoot().setVisibility(viewModelStateIsSearchingBooleanTrueViewModelStateIsNoRecentBeneficiaryViewGONEViewVISIBLE);
        }
        if ((dirtyFlags & 0x190L) != 0) {
            // api target 1

            this.layoutRecent.setAdapter(viewModelAdapterGet);
        }
        if ((dirtyFlags & 0x180L) != 0) {
            // api target 1

            this.layoutSearchView.setViewModel(viewModel);
        }
        if ((dirtyFlags & 0x183L) != 0) {
            // api target 1

            this.tvAllBeneficiaries.setVisibility(ViewModelStateIsSearchingViewGONEViewVISIBLE1);
        }
        if ((dirtyFlags & 0x100L) != 0) {
            // api target 1

            co.yap.yapcore.binders.UIBinder.setText(this.tvAllBeneficiaries, co.yap.translation.Strings.screen_send_money_display_text_all_beneficiaries);
            this.tvCancel.setOnClickListener(mCallback35);
            co.yap.yapcore.binders.UIBinder.setText(this.tvSendMoneyTo, co.yap.translation.Strings.screen_send_money_no_contacts_display_text_sub_heading);
            co.yap.yapcore.binders.UIBinder.setText(this.txtError, co.yap.translation.Strings.screen_y2y_display_text_no_result);
        }
        if ((dirtyFlags & 0x185L) != 0) {
            // api target 1

            this.tvCancel.setVisibility(viewModelStateIsSearchingViewVISIBLEViewGONE);
            this.tvSendMoneyTo.setVisibility(viewModelStateIsSearchingViewGONEViewVISIBLE);
        }
        executeBindingsOn(layoutRecent);
        executeBindingsOn(layoutSearchView);
    }
    // Listener Stub Implementations
    // callback impls
    public final void _internalCallbackOnClick(int sourceId , android.view.View callbackArg_0) {
        // localize variables for thread safety
        // viewModel
        co.yap.sendMoney.home.viewmodels.SendMoneyHomeScreenViewModel viewModel = mViewModel;
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
        flag 0 (0x1L): viewModel.state
        flag 1 (0x2L): viewModel.state.isSearching
        flag 2 (0x3L): viewModel.state.isSearching()
        flag 3 (0x4L): layoutRecent
        flag 4 (0x5L): viewModel.adapter
        flag 5 (0x6L): layoutSearchView
        flag 6 (0x7L): viewModel.state.isNoRecentBeneficiary
        flag 7 (0x8L): viewModel
        flag 8 (0x9L): null
        flag 9 (0xaL): androidx.databinding.ViewDataBinding.safeUnbox(viewModel.state.isSearching().get()) ? View.GONE : View.VISIBLE
        flag 10 (0xbL): androidx.databinding.ViewDataBinding.safeUnbox(viewModel.state.isSearching().get()) ? View.GONE : View.VISIBLE
        flag 11 (0xcL): androidx.databinding.ViewDataBinding.safeUnbox(viewModel.state.isSearching.get()) ? true : androidx.databinding.ViewDataBinding.safeUnbox(viewModel.state.isNoRecentBeneficiary.get())
        flag 12 (0xdL): androidx.databinding.ViewDataBinding.safeUnbox(viewModel.state.isSearching.get()) ? true : androidx.databinding.ViewDataBinding.safeUnbox(viewModel.state.isNoRecentBeneficiary.get())
        flag 13 (0xeL): androidx.databinding.ViewDataBinding.safeUnbox(viewModel.state.isSearching().get()) ? View.VISIBLE : View.GONE
        flag 14 (0xfL): androidx.databinding.ViewDataBinding.safeUnbox(viewModel.state.isSearching().get()) ? View.VISIBLE : View.GONE
        flag 15 (0x10L): androidx.databinding.ViewDataBinding.safeUnbox(viewModel.state.isSearching.get()) ? View.GONE : View.VISIBLE
        flag 16 (0x11L): androidx.databinding.ViewDataBinding.safeUnbox(viewModel.state.isSearching.get()) ? View.GONE : View.VISIBLE
        flag 17 (0x12L): androidx.databinding.ViewDataBinding.safeUnbox(viewModel.state.isSearching.get()) ? true : androidx.databinding.ViewDataBinding.safeUnbox(viewModel.state.isNoRecentBeneficiary.get()) ? View.GONE : View.VISIBLE
        flag 18 (0x13L): androidx.databinding.ViewDataBinding.safeUnbox(viewModel.state.isSearching.get()) ? true : androidx.databinding.ViewDataBinding.safeUnbox(viewModel.state.isNoRecentBeneficiary.get()) ? View.GONE : View.VISIBLE
    flag mapping end*/
    //end
}