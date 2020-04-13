package co.yap.sendmoney.databinding;
import co.yap.sendmoney.R;
import co.yap.sendmoney.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class ActivitySendMoneyLandingBindingImpl extends ActivitySendMoneyLandingBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = new androidx.databinding.ViewDataBinding.IncludedLayouts(4);
        sIncludes.setIncludes(0, 
            new String[] {"layout_send_beneficiaries_toolbar", "layout_no_contacts", "layout_beneficiaries"},
            new int[] {1, 2, 3},
            new int[] {co.yap.sendmoney.R.layout.layout_send_beneficiaries_toolbar,
                co.yap.sendmoney.R.layout.layout_no_contacts,
                co.yap.sendmoney.R.layout.layout_beneficiaries});
        sViewsWithIds = null;
    }
    // views
    @NonNull
    private final android.widget.LinearLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ActivitySendMoneyLandingBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 4, sIncludes, sViewsWithIds));
    }
    private ActivitySendMoneyLandingBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 6
            , (co.yap.sendmoney.databinding.LayoutBeneficiariesBinding) bindings[3]
            , (co.yap.sendmoney.databinding.LayoutNoContactsBinding) bindings[2]
            , (co.yap.sendmoney.databinding.LayoutSendBeneficiariesToolbarBinding) bindings[1]
            );
        this.mboundView0 = (android.widget.LinearLayout) bindings[0];
        this.mboundView0.setTag(null);
        setRootTag(root);
        // listeners
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x80L;
        }
        toolbar.invalidateAll();
        layoutNoContacts.invalidateAll();
        layoutBeneficiaries.invalidateAll();
        requestRebind();
    }

    @Override
    public boolean hasPendingBindings() {
        synchronized(this) {
            if (mDirtyFlags != 0) {
                return true;
            }
        }
        if (toolbar.hasPendingBindings()) {
            return true;
        }
        if (layoutNoContacts.hasPendingBindings()) {
            return true;
        }
        if (layoutBeneficiaries.hasPendingBindings()) {
            return true;
        }
        return false;
    }

    @Override
    public boolean setVariable(int variableId, @Nullable Object variable)  {
        boolean variableSet = true;
        if (BR.viewModel == variableId) {
            setViewModel((co.yap.sendmoney.home.viewmodels.SendMoneyHomeScreenViewModel) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setViewModel(@Nullable co.yap.sendmoney.home.viewmodels.SendMoneyHomeScreenViewModel ViewModel) {
        this.mViewModel = ViewModel;
        synchronized(this) {
            mDirtyFlags |= 0x40L;
        }
        notifyPropertyChanged(BR.viewModel);
        super.requestRebind();
    }

    @Override
    public void setLifecycleOwner(@Nullable androidx.lifecycle.LifecycleOwner lifecycleOwner) {
        super.setLifecycleOwner(lifecycleOwner);
        toolbar.setLifecycleOwner(lifecycleOwner);
        layoutNoContacts.setLifecycleOwner(lifecycleOwner);
        layoutBeneficiaries.setLifecycleOwner(lifecycleOwner);
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
            case 0 :
                return onChangeLayoutBeneficiaries((co.yap.sendmoney.databinding.LayoutBeneficiariesBinding) object, fieldId);
            case 1 :
                return onChangeToolbar((co.yap.sendmoney.databinding.LayoutSendBeneficiariesToolbarBinding) object, fieldId);
            case 2 :
                return onChangeViewModelState((co.yap.sendmoney.home.states.SendMoneyHomeState) object, fieldId);
            case 3 :
                return onChangeViewModelStateIsNoBeneficiary((androidx.databinding.ObservableField<java.lang.Boolean>) object, fieldId);
            case 4 :
                return onChangeLayoutNoContacts((co.yap.sendmoney.databinding.LayoutNoContactsBinding) object, fieldId);
            case 5 :
                return onChangeViewModelStateIsSearching((androidx.databinding.ObservableField<java.lang.Boolean>) object, fieldId);
        }
        return false;
    }
    private boolean onChangeLayoutBeneficiaries(co.yap.sendmoney.databinding.LayoutBeneficiariesBinding LayoutBeneficiaries, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x1L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeToolbar(co.yap.sendmoney.databinding.LayoutSendBeneficiariesToolbarBinding Toolbar, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x2L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeViewModelState(co.yap.sendmoney.home.states.SendMoneyHomeState ViewModelState, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x4L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeViewModelStateIsNoBeneficiary(androidx.databinding.ObservableField<java.lang.Boolean> ViewModelStateIsNoBeneficiary, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x8L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeLayoutNoContacts(co.yap.sendmoney.databinding.LayoutNoContactsBinding LayoutNoContacts, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x10L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeViewModelStateIsSearching(androidx.databinding.ObservableField<java.lang.Boolean> ViewModelStateIsSearching, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x20L;
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
        java.lang.Boolean viewModelStateIsSearchingGet = null;
        java.lang.Boolean viewModelStateIsNoBeneficiaryGet = null;
        int viewModelStateIsNoBeneficiaryViewVISIBLEViewGONE = 0;
        int viewModelStateIsSearchingViewGONEViewVISIBLE = 0;
        boolean androidxDatabindingViewDataBindingSafeUnboxViewModelStateIsSearchingGet = false;
        co.yap.sendmoney.home.states.SendMoneyHomeState viewModelState = null;
        androidx.databinding.ObservableField<java.lang.Boolean> viewModelStateIsNoBeneficiary = null;
        co.yap.sendmoney.home.viewmodels.SendMoneyHomeScreenViewModel viewModel = mViewModel;
        androidx.databinding.ObservableField<java.lang.Boolean> viewModelStateIsSearching = null;
        boolean androidxDatabindingViewDataBindingSafeUnboxViewModelStateIsNoBeneficiaryGet = false;

        if ((dirtyFlags & 0xecL) != 0) {



                if (viewModel != null) {
                    // read viewModel.state
                    viewModelState = viewModel.getState();
                }
                updateRegistration(2, viewModelState);

            if ((dirtyFlags & 0xccL) != 0) {

                    if (viewModelState != null) {
                        // read viewModel.state.isNoBeneficiary()
                        viewModelStateIsNoBeneficiary = viewModelState.isNoBeneficiary();
                    }
                    updateRegistration(3, viewModelStateIsNoBeneficiary);


                    if (viewModelStateIsNoBeneficiary != null) {
                        // read viewModel.state.isNoBeneficiary().get()
                        viewModelStateIsNoBeneficiaryGet = viewModelStateIsNoBeneficiary.get();
                    }


                    // read androidx.databinding.ViewDataBinding.safeUnbox(viewModel.state.isNoBeneficiary().get())
                    androidxDatabindingViewDataBindingSafeUnboxViewModelStateIsNoBeneficiaryGet = androidx.databinding.ViewDataBinding.safeUnbox(viewModelStateIsNoBeneficiaryGet);
                if((dirtyFlags & 0xccL) != 0) {
                    if(androidxDatabindingViewDataBindingSafeUnboxViewModelStateIsNoBeneficiaryGet) {
                            dirtyFlags |= 0x200L;
                    }
                    else {
                            dirtyFlags |= 0x100L;
                    }
                }


                    // read androidx.databinding.ViewDataBinding.safeUnbox(viewModel.state.isNoBeneficiary().get()) ? View.VISIBLE : View.GONE
                    viewModelStateIsNoBeneficiaryViewVISIBLEViewGONE = ((androidxDatabindingViewDataBindingSafeUnboxViewModelStateIsNoBeneficiaryGet) ? (android.view.View.VISIBLE) : (android.view.View.GONE));
            }
            if ((dirtyFlags & 0xe4L) != 0) {

                    if (viewModelState != null) {
                        // read viewModel.state.isSearching()
                        viewModelStateIsSearching = viewModelState.isSearching();
                    }
                    updateRegistration(5, viewModelStateIsSearching);


                    if (viewModelStateIsSearching != null) {
                        // read viewModel.state.isSearching().get()
                        viewModelStateIsSearchingGet = viewModelStateIsSearching.get();
                    }


                    // read androidx.databinding.ViewDataBinding.safeUnbox(viewModel.state.isSearching().get())
                    androidxDatabindingViewDataBindingSafeUnboxViewModelStateIsSearchingGet = androidx.databinding.ViewDataBinding.safeUnbox(viewModelStateIsSearchingGet);
                if((dirtyFlags & 0xe4L) != 0) {
                    if(androidxDatabindingViewDataBindingSafeUnboxViewModelStateIsSearchingGet) {
                            dirtyFlags |= 0x800L;
                    }
                    else {
                            dirtyFlags |= 0x400L;
                    }
                }


                    // read androidx.databinding.ViewDataBinding.safeUnbox(viewModel.state.isSearching().get()) ? View.GONE : View.VISIBLE
                    viewModelStateIsSearchingViewGONEViewVISIBLE = ((androidxDatabindingViewDataBindingSafeUnboxViewModelStateIsSearchingGet) ? (android.view.View.GONE) : (android.view.View.VISIBLE));
            }
        }
        // batch finished
        if ((dirtyFlags & 0xc0L) != 0) {
            // api target 1

            this.layoutBeneficiaries.setViewModel(viewModel);
            this.layoutNoContacts.setViewModel(viewModel);
            this.toolbar.setViewModel(viewModel);
        }
        if ((dirtyFlags & 0xccL) != 0) {
            // api target 1

            this.layoutNoContacts.getRoot().setVisibility(viewModelStateIsNoBeneficiaryViewVISIBLEViewGONE);
        }
        if ((dirtyFlags & 0xe4L) != 0) {
            // api target 1

            this.toolbar.getRoot().setVisibility(viewModelStateIsSearchingViewGONEViewVISIBLE);
        }
        executeBindingsOn(toolbar);
        executeBindingsOn(layoutNoContacts);
        executeBindingsOn(layoutBeneficiaries);
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): layoutBeneficiaries
        flag 1 (0x2L): toolbar
        flag 2 (0x3L): viewModel.state
        flag 3 (0x4L): viewModel.state.isNoBeneficiary()
        flag 4 (0x5L): layoutNoContacts
        flag 5 (0x6L): viewModel.state.isSearching()
        flag 6 (0x7L): viewModel
        flag 7 (0x8L): null
        flag 8 (0x9L): androidx.databinding.ViewDataBinding.safeUnbox(viewModel.state.isNoBeneficiary().get()) ? View.VISIBLE : View.GONE
        flag 9 (0xaL): androidx.databinding.ViewDataBinding.safeUnbox(viewModel.state.isNoBeneficiary().get()) ? View.VISIBLE : View.GONE
        flag 10 (0xbL): androidx.databinding.ViewDataBinding.safeUnbox(viewModel.state.isSearching().get()) ? View.GONE : View.VISIBLE
        flag 11 (0xcL): androidx.databinding.ViewDataBinding.safeUnbox(viewModel.state.isSearching().get()) ? View.GONE : View.VISIBLE
    flag mapping end*/
    //end
}