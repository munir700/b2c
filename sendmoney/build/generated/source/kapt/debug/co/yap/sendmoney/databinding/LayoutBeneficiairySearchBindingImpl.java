package co.yap.sendmoney.databinding;
import co.yap.sendmoney.R;
import co.yap.sendmoney.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class LayoutBeneficiairySearchBindingImpl extends LayoutBeneficiairySearchBinding implements co.yap.sendmoney.generated.callback.OnClickListener.Listener {

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
    @Nullable
    private final android.view.View.OnClickListener mCallback1;
    // values
    // listeners
    // Inverse Binding Event Handlers

    public LayoutBeneficiairySearchBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 4, sIncludes, sViewsWithIds));
    }
    private LayoutBeneficiairySearchBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 2
            , (androidx.constraintlayout.widget.ConstraintLayout) bindings[0]
            , (android.widget.ImageView) bindings[2]
            , (androidx.appcompat.widget.SearchView) bindings[1]
            , (android.widget.TextView) bindings[3]
            );
        this.clSearchBeneficiary.setTag(null);
        this.ivSearch.setTag(null);
        this.svBeneficiary.setTag(null);
        this.tvSearch.setTag(null);
        setRootTag(root);
        // listeners
        mCallback1 = new co.yap.sendmoney.generated.callback.OnClickListener(this, 1);
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x8L;
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
            mDirtyFlags |= 0x4L;
        }
        notifyPropertyChanged(BR.viewModel);
        super.requestRebind();
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
            case 0 :
                return onChangeViewModelState((co.yap.sendmoney.home.states.SendMoneyHomeState) object, fieldId);
            case 1 :
                return onChangeViewModelStateIsSearching((androidx.databinding.ObservableField<java.lang.Boolean>) object, fieldId);
        }
        return false;
    }
    private boolean onChangeViewModelState(co.yap.sendmoney.home.states.SendMoneyHomeState ViewModelState, int fieldId) {
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

    @Override
    protected void executeBindings() {
        long dirtyFlags = 0;
        synchronized(this) {
            dirtyFlags = mDirtyFlags;
            mDirtyFlags = 0;
        }
        java.lang.Boolean viewModelStateIsSearchingGet = null;
        int viewModelStateIsSearchingViewGONEViewVISIBLE = 0;
        int viewModelStateIsSearchingViewVISIBLEViewGONE = 0;
        boolean androidxDatabindingViewDataBindingSafeUnboxViewModelStateIsSearchingGet = false;
        co.yap.sendmoney.home.states.SendMoneyHomeState viewModelState = null;
        co.yap.sendmoney.home.viewmodels.SendMoneyHomeScreenViewModel viewModel = mViewModel;
        androidx.databinding.ObservableField<java.lang.Boolean> viewModelStateIsSearching = null;

        if ((dirtyFlags & 0xfL) != 0) {



                if (viewModel != null) {
                    // read viewModel.state
                    viewModelState = viewModel.getState();
                }
                updateRegistration(0, viewModelState);


                if (viewModelState != null) {
                    // read viewModel.state.isSearching()
                    viewModelStateIsSearching = viewModelState.isSearching();
                }
                updateRegistration(1, viewModelStateIsSearching);


                if (viewModelStateIsSearching != null) {
                    // read viewModel.state.isSearching().get()
                    viewModelStateIsSearchingGet = viewModelStateIsSearching.get();
                }


                // read androidx.databinding.ViewDataBinding.safeUnbox(viewModel.state.isSearching().get())
                androidxDatabindingViewDataBindingSafeUnboxViewModelStateIsSearchingGet = androidx.databinding.ViewDataBinding.safeUnbox(viewModelStateIsSearchingGet);
            if((dirtyFlags & 0xfL) != 0) {
                if(androidxDatabindingViewDataBindingSafeUnboxViewModelStateIsSearchingGet) {
                        dirtyFlags |= 0x20L;
                        dirtyFlags |= 0x80L;
                }
                else {
                        dirtyFlags |= 0x10L;
                        dirtyFlags |= 0x40L;
                }
            }


                // read androidx.databinding.ViewDataBinding.safeUnbox(viewModel.state.isSearching().get()) ? View.GONE : View.VISIBLE
                viewModelStateIsSearchingViewGONEViewVISIBLE = ((androidxDatabindingViewDataBindingSafeUnboxViewModelStateIsSearchingGet) ? (android.view.View.GONE) : (android.view.View.VISIBLE));
                // read androidx.databinding.ViewDataBinding.safeUnbox(viewModel.state.isSearching().get()) ? View.VISIBLE : View.GONE
                viewModelStateIsSearchingViewVISIBLEViewGONE = ((androidxDatabindingViewDataBindingSafeUnboxViewModelStateIsSearchingGet) ? (android.view.View.VISIBLE) : (android.view.View.GONE));
        }
        // batch finished
        if ((dirtyFlags & 0x8L) != 0) {
            // api target 1

            this.clSearchBeneficiary.setOnClickListener(mCallback1);
        }
        if ((dirtyFlags & 0xfL) != 0) {
            // api target 1

            this.ivSearch.setVisibility(viewModelStateIsSearchingViewGONEViewVISIBLE);
            this.svBeneficiary.setVisibility(viewModelStateIsSearchingViewVISIBLEViewGONE);
            this.tvSearch.setVisibility(viewModelStateIsSearchingViewGONEViewVISIBLE);
        }
    }
    // Listener Stub Implementations
    // callback impls
    public final void _internalCallbackOnClick(int sourceId , android.view.View callbackArg_0) {
        // localize variables for thread safety
        // viewModel
        co.yap.sendmoney.home.viewmodels.SendMoneyHomeScreenViewModel viewModel = mViewModel;
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
        flag 1 (0x2L): viewModel.state.isSearching()
        flag 2 (0x3L): viewModel
        flag 3 (0x4L): null
        flag 4 (0x5L): androidx.databinding.ViewDataBinding.safeUnbox(viewModel.state.isSearching().get()) ? View.GONE : View.VISIBLE
        flag 5 (0x6L): androidx.databinding.ViewDataBinding.safeUnbox(viewModel.state.isSearching().get()) ? View.GONE : View.VISIBLE
        flag 6 (0x7L): androidx.databinding.ViewDataBinding.safeUnbox(viewModel.state.isSearching().get()) ? View.VISIBLE : View.GONE
        flag 7 (0x8L): androidx.databinding.ViewDataBinding.safeUnbox(viewModel.state.isSearching().get()) ? View.VISIBLE : View.GONE
    flag mapping end*/
    //end
}