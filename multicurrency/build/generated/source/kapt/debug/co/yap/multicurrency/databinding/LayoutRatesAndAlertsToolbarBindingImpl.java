package co.yap.multicurrency.databinding;
import co.yap.multicurrency.R;
import co.yap.multicurrency.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class LayoutRatesAndAlertsToolbarBindingImpl extends LayoutRatesAndAlertsToolbarBinding implements co.yap.multicurrency.generated.callback.OnClickListener.Listener {

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
    private final android.widget.RelativeLayout mboundView0;
    // variables
    @Nullable
    private final android.view.View.OnClickListener mCallback18;
    @Nullable
    private final android.view.View.OnClickListener mCallback19;
    // values
    // listeners
    // Inverse Binding Event Handlers

    public LayoutRatesAndAlertsToolbarBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 4, sIncludes, sViewsWithIds));
    }
    private LayoutRatesAndAlertsToolbarBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 4
            , (android.widget.ImageView) bindings[1]
            , (android.widget.ImageView) bindings[3]
            , (android.widget.TextView) bindings[2]
            );
        this.mboundView0 = (android.widget.RelativeLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.tbBtnBack.setTag(null);
        this.tbBtnRefresh.setTag(null);
        this.tvTitle.setTag(null);
        setRootTag(root);
        // listeners
        mCallback18 = new co.yap.multicurrency.generated.callback.OnClickListener(this, 1);
        mCallback19 = new co.yap.multicurrency.generated.callback.OnClickListener(this, 2);
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x20L;
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
            setViewModel((co.yap.multicurrency.ratesalerts.base.RatesAndAlertsActivityVM) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setViewModel(@Nullable co.yap.multicurrency.ratesalerts.base.RatesAndAlertsActivityVM ViewModel) {
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
                return onChangeViewModelStateTbTitle((androidx.databinding.ObservableField<java.lang.String>) object, fieldId);
            case 3 :
                return onChangeViewModelStateToolVisibility((androidx.databinding.ObservableBoolean) object, fieldId);
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
    private boolean onChangeViewModelStateTbTitle(androidx.databinding.ObservableField<java.lang.String> ViewModelStateTbTitle, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x4L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeViewModelStateToolVisibility(androidx.databinding.ObservableBoolean ViewModelStateToolVisibility, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x8L;
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
        java.lang.String viewModelStateTbTitleGet = null;
        androidx.databinding.ObservableBoolean viewModelStateLeftIcon = null;
        androidx.databinding.ObservableBoolean viewModelStateRightIcon = null;
        int viewModelStateLeftIconViewVISIBLEViewGONE = 0;
        int viewModelStateRightIconViewVISIBLEViewGONE = 0;
        boolean viewModelStateRightIconGet = false;
        int viewModelStateToolVisibilityViewVISIBLEViewGONE = 0;
        androidx.databinding.ObservableField<java.lang.String> viewModelStateTbTitle = null;
        androidx.databinding.ObservableBoolean viewModelStateToolVisibility = null;
        co.yap.multicurrency.ratesalerts.base.IRatesAndAlertsBase.State viewModelState = null;
        boolean viewModelStateToolVisibilityGet = false;
        boolean viewModelStateLeftIconGet = false;
        co.yap.multicurrency.ratesalerts.base.RatesAndAlertsActivityVM viewModel = mViewModel;

        if ((dirtyFlags & 0x3fL) != 0) {



                if (viewModel != null) {
                    // read viewModel.state
                    viewModelState = viewModel.getState();
                }

            if ((dirtyFlags & 0x31L) != 0) {

                    if (viewModelState != null) {
                        // read viewModel.state.leftIcon
                        viewModelStateLeftIcon = viewModelState.getLeftIcon();
                    }
                    updateRegistration(0, viewModelStateLeftIcon);


                    if (viewModelStateLeftIcon != null) {
                        // read viewModel.state.leftIcon.get()
                        viewModelStateLeftIconGet = viewModelStateLeftIcon.get();
                    }
                if((dirtyFlags & 0x31L) != 0) {
                    if(viewModelStateLeftIconGet) {
                            dirtyFlags |= 0x80L;
                    }
                    else {
                            dirtyFlags |= 0x40L;
                    }
                }


                    // read viewModel.state.leftIcon.get() ? View.VISIBLE : View.GONE
                    viewModelStateLeftIconViewVISIBLEViewGONE = ((viewModelStateLeftIconGet) ? (android.view.View.VISIBLE) : (android.view.View.GONE));
            }
            if ((dirtyFlags & 0x32L) != 0) {

                    if (viewModelState != null) {
                        // read viewModel.state.rightIcon
                        viewModelStateRightIcon = viewModelState.getRightIcon();
                    }
                    updateRegistration(1, viewModelStateRightIcon);


                    if (viewModelStateRightIcon != null) {
                        // read viewModel.state.rightIcon.get()
                        viewModelStateRightIconGet = viewModelStateRightIcon.get();
                    }
                if((dirtyFlags & 0x32L) != 0) {
                    if(viewModelStateRightIconGet) {
                            dirtyFlags |= 0x200L;
                    }
                    else {
                            dirtyFlags |= 0x100L;
                    }
                }


                    // read viewModel.state.rightIcon.get() ? View.VISIBLE : View.GONE
                    viewModelStateRightIconViewVISIBLEViewGONE = ((viewModelStateRightIconGet) ? (android.view.View.VISIBLE) : (android.view.View.GONE));
            }
            if ((dirtyFlags & 0x34L) != 0) {

                    if (viewModelState != null) {
                        // read viewModel.state.tbTitle
                        viewModelStateTbTitle = viewModelState.getTbTitle();
                    }
                    updateRegistration(2, viewModelStateTbTitle);


                    if (viewModelStateTbTitle != null) {
                        // read viewModel.state.tbTitle.get()
                        viewModelStateTbTitleGet = viewModelStateTbTitle.get();
                    }
            }
            if ((dirtyFlags & 0x38L) != 0) {

                    if (viewModelState != null) {
                        // read viewModel.state.toolVisibility
                        viewModelStateToolVisibility = viewModelState.getToolVisibility();
                    }
                    updateRegistration(3, viewModelStateToolVisibility);


                    if (viewModelStateToolVisibility != null) {
                        // read viewModel.state.toolVisibility.get()
                        viewModelStateToolVisibilityGet = viewModelStateToolVisibility.get();
                    }
                if((dirtyFlags & 0x38L) != 0) {
                    if(viewModelStateToolVisibilityGet) {
                            dirtyFlags |= 0x800L;
                    }
                    else {
                            dirtyFlags |= 0x400L;
                    }
                }


                    // read viewModel.state.toolVisibility.get() ? View.VISIBLE : View.GONE
                    viewModelStateToolVisibilityViewVISIBLEViewGONE = ((viewModelStateToolVisibilityGet) ? (android.view.View.VISIBLE) : (android.view.View.GONE));
            }
        }
        // batch finished
        if ((dirtyFlags & 0x38L) != 0) {
            // api target 1

            this.mboundView0.setVisibility(viewModelStateToolVisibilityViewVISIBLEViewGONE);
        }
        if ((dirtyFlags & 0x20L) != 0) {
            // api target 1

            this.tbBtnBack.setOnClickListener(mCallback18);
            this.tbBtnRefresh.setOnClickListener(mCallback19);
        }
        if ((dirtyFlags & 0x31L) != 0) {
            // api target 1

            this.tbBtnBack.setVisibility(viewModelStateLeftIconViewVISIBLEViewGONE);
        }
        if ((dirtyFlags & 0x32L) != 0) {
            // api target 1

            this.tbBtnRefresh.setVisibility(viewModelStateRightIconViewVISIBLEViewGONE);
        }
        if ((dirtyFlags & 0x34L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.tvTitle, viewModelStateTbTitleGet);
        }
    }
    // Listener Stub Implementations
    // callback impls
    public final void _internalCallbackOnClick(int sourceId , android.view.View callbackArg_0) {
        switch(sourceId) {
            case 1: {
                // localize variables for thread safety
                // viewModel
                co.yap.multicurrency.ratesalerts.base.RatesAndAlertsActivityVM viewModel = mViewModel;
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
                co.yap.multicurrency.ratesalerts.base.RatesAndAlertsActivityVM viewModel = mViewModel;
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
        flag 2 (0x3L): viewModel.state.tbTitle
        flag 3 (0x4L): viewModel.state.toolVisibility
        flag 4 (0x5L): viewModel
        flag 5 (0x6L): null
        flag 6 (0x7L): viewModel.state.leftIcon.get() ? View.VISIBLE : View.GONE
        flag 7 (0x8L): viewModel.state.leftIcon.get() ? View.VISIBLE : View.GONE
        flag 8 (0x9L): viewModel.state.rightIcon.get() ? View.VISIBLE : View.GONE
        flag 9 (0xaL): viewModel.state.rightIcon.get() ? View.VISIBLE : View.GONE
        flag 10 (0xbL): viewModel.state.toolVisibility.get() ? View.VISIBLE : View.GONE
        flag 11 (0xcL): viewModel.state.toolVisibility.get() ? View.VISIBLE : View.GONE
    flag mapping end*/
    //end
}