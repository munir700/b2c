package co.yap.multicurrency.databinding;
import co.yap.multicurrency.R;
import co.yap.multicurrency.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class LayoutWalletsToolbarBindingImpl extends LayoutWalletsToolbarBinding implements co.yap.multicurrency.generated.callback.OnClickListener.Listener {

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
    private final android.view.View.OnClickListener mCallback4;
    @Nullable
    private final android.view.View.OnClickListener mCallback5;
    @Nullable
    private final android.view.View.OnClickListener mCallback3;
    // values
    // listeners
    // Inverse Binding Event Handlers

    public LayoutWalletsToolbarBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 5, sIncludes, sViewsWithIds));
    }
    private LayoutWalletsToolbarBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 4
            , (androidx.appcompat.widget.AppCompatImageView) bindings[4]
            , (androidx.appcompat.widget.AppCompatImageView) bindings[3]
            , (androidx.appcompat.widget.AppCompatImageView) bindings[1]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[2]
            );
        this.ivAddWallet.setTag(null);
        this.ivRate.setTag(null);
        this.mboundView0 = (androidx.constraintlayout.widget.ConstraintLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.tbBtnBack.setTag(null);
        this.tvTitle.setTag(null);
        setRootTag(root);
        // listeners
        mCallback4 = new co.yap.multicurrency.generated.callback.OnClickListener(this, 2);
        mCallback5 = new co.yap.multicurrency.generated.callback.OnClickListener(this, 3);
        mCallback3 = new co.yap.multicurrency.generated.callback.OnClickListener(this, 1);
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
            setViewModel((co.yap.multicurrency.base.MCLandingViewModel) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setViewModel(@Nullable co.yap.multicurrency.base.MCLandingViewModel ViewModel) {
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
                return onChangeViewModelStateToolbarBackIcon((androidx.databinding.ObservableBoolean) object, fieldId);
            case 1 :
                return onChangeViewModelStateToolbarAddWalletIcon((androidx.databinding.ObservableBoolean) object, fieldId);
            case 2 :
                return onChangeViewModelStateToolVisibility((androidx.databinding.ObservableBoolean) object, fieldId);
            case 3 :
                return onChangeViewModelStateToolbarRateIcon((androidx.databinding.ObservableBoolean) object, fieldId);
        }
        return false;
    }
    private boolean onChangeViewModelStateToolbarBackIcon(androidx.databinding.ObservableBoolean ViewModelStateToolbarBackIcon, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x1L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeViewModelStateToolbarAddWalletIcon(androidx.databinding.ObservableBoolean ViewModelStateToolbarAddWalletIcon, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x2L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeViewModelStateToolVisibility(androidx.databinding.ObservableBoolean ViewModelStateToolVisibility, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x4L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeViewModelStateToolbarRateIcon(androidx.databinding.ObservableBoolean ViewModelStateToolbarRateIcon, int fieldId) {
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
        androidx.databinding.ObservableBoolean viewModelStateToolbarBackIcon = null;
        boolean viewModelStateToolbarAddWalletIconGet = false;
        int viewModelStateToolVisibilityViewVISIBLEViewGONE = 0;
        co.yap.multicurrency.base.IMCLanding.State viewModelState = null;
        boolean viewModelStateToolbarRateIconGet = false;
        int viewModelStateToolbarAddWalletIconViewVISIBLEViewGONE = 0;
        int viewModelStateToolbarRateIconViewVISIBLEViewGONE = 0;
        int viewModelStateToolbarBackIconViewVISIBLEViewGONE = 0;
        androidx.databinding.ObservableBoolean viewModelStateToolbarAddWalletIcon = null;
        androidx.databinding.ObservableBoolean viewModelStateToolVisibility = null;
        boolean viewModelStateToolVisibilityGet = false;
        boolean viewModelStateToolbarBackIconGet = false;
        androidx.databinding.ObservableBoolean viewModelStateToolbarRateIcon = null;
        co.yap.multicurrency.base.MCLandingViewModel viewModel = mViewModel;

        if ((dirtyFlags & 0x3fL) != 0) {



                if (viewModel != null) {
                    // read viewModel.state
                    viewModelState = viewModel.getState();
                }

            if ((dirtyFlags & 0x31L) != 0) {

                    if (viewModelState != null) {
                        // read viewModel.state.toolbarBackIcon
                        viewModelStateToolbarBackIcon = viewModelState.getToolbarBackIcon();
                    }
                    updateRegistration(0, viewModelStateToolbarBackIcon);


                    if (viewModelStateToolbarBackIcon != null) {
                        // read viewModel.state.toolbarBackIcon.get()
                        viewModelStateToolbarBackIconGet = viewModelStateToolbarBackIcon.get();
                    }
                if((dirtyFlags & 0x31L) != 0) {
                    if(viewModelStateToolbarBackIconGet) {
                            dirtyFlags |= 0x2000L;
                    }
                    else {
                            dirtyFlags |= 0x1000L;
                    }
                }


                    // read viewModel.state.toolbarBackIcon.get() ? View.VISIBLE : View.GONE
                    viewModelStateToolbarBackIconViewVISIBLEViewGONE = ((viewModelStateToolbarBackIconGet) ? (android.view.View.VISIBLE) : (android.view.View.GONE));
            }
            if ((dirtyFlags & 0x32L) != 0) {

                    if (viewModelState != null) {
                        // read viewModel.state.toolbarAddWalletIcon
                        viewModelStateToolbarAddWalletIcon = viewModelState.getToolbarAddWalletIcon();
                    }
                    updateRegistration(1, viewModelStateToolbarAddWalletIcon);


                    if (viewModelStateToolbarAddWalletIcon != null) {
                        // read viewModel.state.toolbarAddWalletIcon.get()
                        viewModelStateToolbarAddWalletIconGet = viewModelStateToolbarAddWalletIcon.get();
                    }
                if((dirtyFlags & 0x32L) != 0) {
                    if(viewModelStateToolbarAddWalletIconGet) {
                            dirtyFlags |= 0x200L;
                    }
                    else {
                            dirtyFlags |= 0x100L;
                    }
                }


                    // read viewModel.state.toolbarAddWalletIcon.get() ? View.VISIBLE : View.GONE
                    viewModelStateToolbarAddWalletIconViewVISIBLEViewGONE = ((viewModelStateToolbarAddWalletIconGet) ? (android.view.View.VISIBLE) : (android.view.View.GONE));
            }
            if ((dirtyFlags & 0x34L) != 0) {

                    if (viewModelState != null) {
                        // read viewModel.state.toolVisibility
                        viewModelStateToolVisibility = viewModelState.getToolVisibility();
                    }
                    updateRegistration(2, viewModelStateToolVisibility);


                    if (viewModelStateToolVisibility != null) {
                        // read viewModel.state.toolVisibility.get()
                        viewModelStateToolVisibilityGet = viewModelStateToolVisibility.get();
                    }
                if((dirtyFlags & 0x34L) != 0) {
                    if(viewModelStateToolVisibilityGet) {
                            dirtyFlags |= 0x80L;
                    }
                    else {
                            dirtyFlags |= 0x40L;
                    }
                }


                    // read viewModel.state.toolVisibility.get() ? View.VISIBLE : View.GONE
                    viewModelStateToolVisibilityViewVISIBLEViewGONE = ((viewModelStateToolVisibilityGet) ? (android.view.View.VISIBLE) : (android.view.View.GONE));
            }
            if ((dirtyFlags & 0x38L) != 0) {

                    if (viewModelState != null) {
                        // read viewModel.state.toolbarRateIcon
                        viewModelStateToolbarRateIcon = viewModelState.getToolbarRateIcon();
                    }
                    updateRegistration(3, viewModelStateToolbarRateIcon);


                    if (viewModelStateToolbarRateIcon != null) {
                        // read viewModel.state.toolbarRateIcon.get()
                        viewModelStateToolbarRateIconGet = viewModelStateToolbarRateIcon.get();
                    }
                if((dirtyFlags & 0x38L) != 0) {
                    if(viewModelStateToolbarRateIconGet) {
                            dirtyFlags |= 0x800L;
                    }
                    else {
                            dirtyFlags |= 0x400L;
                    }
                }


                    // read viewModel.state.toolbarRateIcon.get() ? View.VISIBLE : View.GONE
                    viewModelStateToolbarRateIconViewVISIBLEViewGONE = ((viewModelStateToolbarRateIconGet) ? (android.view.View.VISIBLE) : (android.view.View.GONE));
            }
        }
        // batch finished
        if ((dirtyFlags & 0x20L) != 0) {
            // api target 1

            this.ivAddWallet.setOnClickListener(mCallback5);
            this.ivRate.setOnClickListener(mCallback4);
            this.tbBtnBack.setOnClickListener(mCallback3);
            co.yap.yapcore.binders.UIBinder.setText(this.tvTitle, co.yap.translation.Strings.screen_multi_currency_wallet_display_text_title);
        }
        if ((dirtyFlags & 0x32L) != 0) {
            // api target 1

            this.ivAddWallet.setVisibility(viewModelStateToolbarAddWalletIconViewVISIBLEViewGONE);
        }
        if ((dirtyFlags & 0x38L) != 0) {
            // api target 1

            this.ivRate.setVisibility(viewModelStateToolbarRateIconViewVISIBLEViewGONE);
        }
        if ((dirtyFlags & 0x34L) != 0) {
            // api target 1

            this.mboundView0.setVisibility(viewModelStateToolVisibilityViewVISIBLEViewGONE);
        }
        if ((dirtyFlags & 0x31L) != 0) {
            // api target 1

            this.tbBtnBack.setVisibility(viewModelStateToolbarBackIconViewVISIBLEViewGONE);
        }
    }
    // Listener Stub Implementations
    // callback impls
    public final void _internalCallbackOnClick(int sourceId , android.view.View callbackArg_0) {
        switch(sourceId) {
            case 2: {
                // localize variables for thread safety
                // viewModel
                co.yap.multicurrency.base.MCLandingViewModel viewModel = mViewModel;
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
            case 3: {
                // localize variables for thread safety
                // viewModel
                co.yap.multicurrency.base.MCLandingViewModel viewModel = mViewModel;
                // viewModel != null
                boolean viewModelJavaLangObjectNull = false;
                // v.id
                int callbackArg0Id = 0;



                viewModelJavaLangObjectNull = (viewModel) != (null);
                if (viewModelJavaLangObjectNull) {


                    if ((callbackArg_0) != (null)) {


                        callbackArg0Id = callbackArg_0.getId();

                        viewModel.handlePressOnAddWalletView(callbackArg0Id);
                    }
                }
                break;
            }
            case 1: {
                // localize variables for thread safety
                // viewModel
                co.yap.multicurrency.base.MCLandingViewModel viewModel = mViewModel;
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
        flag 0 (0x1L): viewModel.state.toolbarBackIcon
        flag 1 (0x2L): viewModel.state.toolbarAddWalletIcon
        flag 2 (0x3L): viewModel.state.toolVisibility
        flag 3 (0x4L): viewModel.state.toolbarRateIcon
        flag 4 (0x5L): viewModel
        flag 5 (0x6L): null
        flag 6 (0x7L): viewModel.state.toolVisibility.get() ? View.VISIBLE : View.GONE
        flag 7 (0x8L): viewModel.state.toolVisibility.get() ? View.VISIBLE : View.GONE
        flag 8 (0x9L): viewModel.state.toolbarAddWalletIcon.get() ? View.VISIBLE : View.GONE
        flag 9 (0xaL): viewModel.state.toolbarAddWalletIcon.get() ? View.VISIBLE : View.GONE
        flag 10 (0xbL): viewModel.state.toolbarRateIcon.get() ? View.VISIBLE : View.GONE
        flag 11 (0xcL): viewModel.state.toolbarRateIcon.get() ? View.VISIBLE : View.GONE
        flag 12 (0xdL): viewModel.state.toolbarBackIcon.get() ? View.VISIBLE : View.GONE
        flag 13 (0xeL): viewModel.state.toolbarBackIcon.get() ? View.VISIBLE : View.GONE
    flag mapping end*/
    //end
}