package co.yap.sendmoney.databinding;
import co.yap.sendmoney.R;
import co.yap.sendmoney.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class LayoutBeneficiaryTransferToolBarBindingImpl extends LayoutBeneficiaryTransferToolBarBinding implements co.yap.sendmoney.generated.callback.OnClickListener.Listener {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = null;
    }
    // views
    @NonNull
    private final androidx.constraintlayout.widget.ConstraintLayout mboundView0;
    // variables
    @Nullable
    private final android.view.View.OnClickListener mCallback16;
    @Nullable
    private final android.view.View.OnClickListener mCallback17;
    // values
    // listeners
    // Inverse Binding Event Handlers

    public LayoutBeneficiaryTransferToolBarBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 4, sIncludes, sViewsWithIds));
    }
    private LayoutBeneficiaryTransferToolBarBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 4
            , (android.widget.ImageView) bindings[1]
            , (android.widget.TextView) bindings[3]
            , (android.widget.TextView) bindings[2]
            );
        this.mboundView0 = (androidx.constraintlayout.widget.ConstraintLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.tbIvClose.setTag(null);
        this.tvRightToolbar.setTag(null);
        this.tvTitle.setTag(null);
        setRootTag(root);
        // listeners
        mCallback16 = new co.yap.sendmoney.generated.callback.OnClickListener(this, 1);
        mCallback17 = new co.yap.sendmoney.generated.callback.OnClickListener(this, 2);
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x80L;
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
            setViewModel((co.yap.sendMoney.fundtransfer.viewmodels.BeneficiaryFundTransferViewModel) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setViewModel(@Nullable co.yap.sendMoney.fundtransfer.viewmodels.BeneficiaryFundTransferViewModel ViewModel) {
        this.mViewModel = ViewModel;
        synchronized(this) {
            mDirtyFlags |= 0x10L;
        }
        notifyPropertyChanged(BR.viewModel);
        super.requestRebind();
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
            case 0 :
                return onChangeViewModelStateLeftIcon((androidx.databinding.ObservableBoolean) object, fieldId);
            case 1 :
                return onChangeViewModelStateRightIcon((androidx.databinding.ObservableBoolean) object, fieldId);
            case 2 :
                return onChangeViewModelStateToolbarVisibility((androidx.databinding.ObservableBoolean) object, fieldId);
            case 3 :
                return onChangeViewModelState((co.yap.sendMoney.fundtransfer.states.BeneficiaryFundTransferState) object, fieldId);
        }
        return false;
    }
    private boolean onChangeViewModelStateLeftIcon(androidx.databinding.ObservableBoolean ViewModelStateLeftIcon, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x1L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeViewModelStateRightIcon(androidx.databinding.ObservableBoolean ViewModelStateRightIcon, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x2L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeViewModelStateToolbarVisibility(androidx.databinding.ObservableBoolean ViewModelStateToolbarVisibility, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x4L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeViewModelState(co.yap.sendMoney.fundtransfer.states.BeneficiaryFundTransferState ViewModelState, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x8L;
            }
            return true;
        }
        else if (fieldId == BR.toolBarTitle) {
            synchronized(this) {
                    mDirtyFlags |= 0x20L;
            }
            return true;
        }
        else if (fieldId == BR.rightButtonText) {
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
        androidx.databinding.ObservableBoolean viewModelStateLeftIcon = null;
        androidx.databinding.ObservableBoolean viewModelStateRightIcon = null;
        java.lang.String viewModelStateToolBarTitle = null;
        java.lang.String viewModelStateRightButtonText = null;
        androidx.databinding.ObservableBoolean viewModelStateToolbarVisibility = null;
        int viewModelStateLeftIconViewVISIBLEViewINVISIBLE = 0;
        boolean viewModelStateRightIconGet = false;
        co.yap.sendMoney.fundtransfer.states.BeneficiaryFundTransferState viewModelState = null;
        boolean viewModelStateLeftIconGet = false;
        co.yap.sendMoney.fundtransfer.viewmodels.BeneficiaryFundTransferViewModel viewModel = mViewModel;
        boolean viewModelStateToolbarVisibilityGet = false;
        int viewModelStateToolbarVisibilityViewVISIBLEViewGONE = 0;
        int viewModelStateRightIconViewVISIBLEViewINVISIBLE = 0;

        if ((dirtyFlags & 0xffL) != 0) {



                if (viewModel != null) {
                    // read viewModel.state
                    viewModelState = viewModel.getState();
                }
                updateRegistration(3, viewModelState);

            if ((dirtyFlags & 0x99L) != 0) {

                    if (viewModelState != null) {
                        // read viewModel.state.leftIcon
                        viewModelStateLeftIcon = viewModelState.getLeftIcon();
                    }
                    updateRegistration(0, viewModelStateLeftIcon);


                    if (viewModelStateLeftIcon != null) {
                        // read viewModel.state.leftIcon.get()
                        viewModelStateLeftIconGet = viewModelStateLeftIcon.get();
                    }
                if((dirtyFlags & 0x99L) != 0) {
                    if(viewModelStateLeftIconGet) {
                            dirtyFlags |= 0x200L;
                    }
                    else {
                            dirtyFlags |= 0x100L;
                    }
                }


                    // read viewModel.state.leftIcon.get() ? View.VISIBLE : View.INVISIBLE
                    viewModelStateLeftIconViewVISIBLEViewINVISIBLE = ((viewModelStateLeftIconGet) ? (android.view.View.VISIBLE) : (android.view.View.INVISIBLE));
            }
            if ((dirtyFlags & 0x9aL) != 0) {

                    if (viewModelState != null) {
                        // read viewModel.state.rightIcon
                        viewModelStateRightIcon = viewModelState.getRightIcon();
                    }
                    updateRegistration(1, viewModelStateRightIcon);


                    if (viewModelStateRightIcon != null) {
                        // read viewModel.state.rightIcon.get()
                        viewModelStateRightIconGet = viewModelStateRightIcon.get();
                    }
                if((dirtyFlags & 0x9aL) != 0) {
                    if(viewModelStateRightIconGet) {
                            dirtyFlags |= 0x2000L;
                    }
                    else {
                            dirtyFlags |= 0x1000L;
                    }
                }


                    // read viewModel.state.rightIcon.get() ? View.VISIBLE : View.INVISIBLE
                    viewModelStateRightIconViewVISIBLEViewINVISIBLE = ((viewModelStateRightIconGet) ? (android.view.View.VISIBLE) : (android.view.View.INVISIBLE));
            }
            if ((dirtyFlags & 0xb8L) != 0) {

                    if (viewModelState != null) {
                        // read viewModel.state.toolBarTitle
                        viewModelStateToolBarTitle = viewModelState.getToolBarTitle();
                    }
            }
            if ((dirtyFlags & 0xd8L) != 0) {

                    if (viewModelState != null) {
                        // read viewModel.state.rightButtonText
                        viewModelStateRightButtonText = viewModelState.getRightButtonText();
                    }
            }
            if ((dirtyFlags & 0x9cL) != 0) {

                    if (viewModelState != null) {
                        // read viewModel.state.toolbarVisibility
                        viewModelStateToolbarVisibility = viewModelState.getToolbarVisibility();
                    }
                    updateRegistration(2, viewModelStateToolbarVisibility);


                    if (viewModelStateToolbarVisibility != null) {
                        // read viewModel.state.toolbarVisibility.get()
                        viewModelStateToolbarVisibilityGet = viewModelStateToolbarVisibility.get();
                    }
                if((dirtyFlags & 0x9cL) != 0) {
                    if(viewModelStateToolbarVisibilityGet) {
                            dirtyFlags |= 0x800L;
                    }
                    else {
                            dirtyFlags |= 0x400L;
                    }
                }


                    // read viewModel.state.toolbarVisibility.get() ? View.VISIBLE : View.GONE
                    viewModelStateToolbarVisibilityViewVISIBLEViewGONE = ((viewModelStateToolbarVisibilityGet) ? (android.view.View.VISIBLE) : (android.view.View.GONE));
            }
        }
        // batch finished
        if ((dirtyFlags & 0x9cL) != 0) {
            // api target 1

            this.mboundView0.setVisibility(viewModelStateToolbarVisibilityViewVISIBLEViewGONE);
        }
        if ((dirtyFlags & 0x80L) != 0) {
            // api target 1

            this.tbIvClose.setOnClickListener(mCallback16);
            this.tvRightToolbar.setOnClickListener(mCallback17);
        }
        if ((dirtyFlags & 0x99L) != 0) {
            // api target 1

            this.tbIvClose.setVisibility(viewModelStateLeftIconViewVISIBLEViewINVISIBLE);
        }
        if ((dirtyFlags & 0xd8L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.tvRightToolbar, viewModelStateRightButtonText);
        }
        if ((dirtyFlags & 0x9aL) != 0) {
            // api target 1

            this.tvRightToolbar.setVisibility(viewModelStateRightIconViewVISIBLEViewINVISIBLE);
        }
        if ((dirtyFlags & 0xb8L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.tvTitle, viewModelStateToolBarTitle);
        }
    }
    // Listener Stub Implementations
    // callback impls
    public final void _internalCallbackOnClick(int sourceId , android.view.View callbackArg_0) {
        switch(sourceId) {
            case 1: {
                // localize variables for thread safety
                // viewModel
                co.yap.sendMoney.fundtransfer.viewmodels.BeneficiaryFundTransferViewModel viewModel = mViewModel;
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
                co.yap.sendMoney.fundtransfer.viewmodels.BeneficiaryFundTransferViewModel viewModel = mViewModel;
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
        flag 0 (0x1L): viewModel.state.leftIcon
        flag 1 (0x2L): viewModel.state.rightIcon
        flag 2 (0x3L): viewModel.state.toolbarVisibility
        flag 3 (0x4L): viewModel.state
        flag 4 (0x5L): viewModel
        flag 5 (0x6L): viewModel.state.toolBarTitle
        flag 6 (0x7L): viewModel.state.rightButtonText
        flag 7 (0x8L): null
        flag 8 (0x9L): viewModel.state.leftIcon.get() ? View.VISIBLE : View.INVISIBLE
        flag 9 (0xaL): viewModel.state.leftIcon.get() ? View.VISIBLE : View.INVISIBLE
        flag 10 (0xbL): viewModel.state.toolbarVisibility.get() ? View.VISIBLE : View.GONE
        flag 11 (0xcL): viewModel.state.toolbarVisibility.get() ? View.VISIBLE : View.GONE
        flag 12 (0xdL): viewModel.state.rightIcon.get() ? View.VISIBLE : View.INVISIBLE
        flag 13 (0xeL): viewModel.state.rightIcon.get() ? View.VISIBLE : View.INVISIBLE
    flag mapping end*/
    //end
}