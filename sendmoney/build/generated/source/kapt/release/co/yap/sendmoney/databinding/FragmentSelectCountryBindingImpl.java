package co.yap.sendmoney.databinding;
import co.yap.sendmoney.R;
import co.yap.sendmoney.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class FragmentSelectCountryBindingImpl extends FragmentSelectCountryBinding implements co.yap.sendmoney.generated.callback.OnClickListener.Listener {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.spinner_container, 4);
        sViewsWithIds.put(R.id.countriesSpinner, 5);
    }
    // views
    @NonNull
    private final androidx.constraintlayout.widget.ConstraintLayout mboundView0;
    // variables
    @Nullable
    private final android.view.View.OnClickListener mCallback33;
    // values
    // listeners
    // Inverse Binding Event Handlers

    public FragmentSelectCountryBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 6, sIncludes, sViewsWithIds));
    }
    private FragmentSelectCountryBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 1
            , (android.widget.Spinner) bindings[5]
            , (co.yap.widgets.CoreButton) bindings[3]
            , (android.widget.RelativeLayout) bindings[4]
            , (android.widget.TextView) bindings[2]
            , (android.widget.TextView) bindings[1]
            );
        this.mboundView0 = (androidx.constraintlayout.widget.ConstraintLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.nextButton.setTag(null);
        this.tvHeadingDetail.setTag(null);
        this.tvSelectCountryHeading.setTag(null);
        setRootTag(root);
        // listeners
        mCallback33 = new co.yap.sendmoney.generated.callback.OnClickListener(this, 1);
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
            setViewModel((co.yap.sendMoney.addbeneficiary.viewmodels.SelectCountryViewModel) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setViewModel(@Nullable co.yap.sendMoney.addbeneficiary.viewmodels.SelectCountryViewModel ViewModel) {
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
                return onChangeViewModelState((co.yap.sendMoney.addbeneficiary.states.SelectCountryState) object, fieldId);
        }
        return false;
    }
    private boolean onChangeViewModelState(co.yap.sendMoney.addbeneficiary.states.SelectCountryState ViewModelState, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x1L;
            }
            return true;
        }
        else if (fieldId == BR.valid) {
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
        boolean viewModelStateValid = false;
        co.yap.sendMoney.addbeneficiary.states.SelectCountryState viewModelState = null;
        co.yap.sendMoney.addbeneficiary.viewmodels.SelectCountryViewModel viewModel = mViewModel;

        if ((dirtyFlags & 0xfL) != 0) {



                if (viewModel != null) {
                    // read viewModel.state
                    viewModelState = viewModel.getState();
                }
                updateRegistration(0, viewModelState);


                if (viewModelState != null) {
                    // read viewModel.state.valid
                    viewModelStateValid = viewModelState.getValid();
                }
        }
        // batch finished
        if ((dirtyFlags & 0x8L) != 0) {
            // api target 1

            this.nextButton.setOnClickListener(mCallback33);
            co.yap.yapcore.binders.UIBinder.setText(this.nextButton, co.yap.translation.Strings.screen_add_beneficiary_button_next);
            co.yap.yapcore.binders.UIBinder.setText(this.tvHeadingDetail, co.yap.translation.Strings.screen_add_beneficiary_display_text_select_country);
            co.yap.yapcore.binders.UIBinder.setText(this.tvSelectCountryHeading, co.yap.translation.Strings.screen_add_beneficiary_display_text_country_title);
        }
        if ((dirtyFlags & 0xfL) != 0) {
            // api target 1

            co.yap.yapcore.binders.UIBinder.setEnable(this.nextButton, viewModelStateValid);
        }
    }
    // Listener Stub Implementations
    // callback impls
    public final void _internalCallbackOnClick(int sourceId , android.view.View callbackArg_0) {
        // localize variables for thread safety
        // viewModel
        co.yap.sendMoney.addbeneficiary.viewmodels.SelectCountryViewModel viewModel = mViewModel;
        // viewModel != null
        boolean viewModelJavaLangObjectNull = false;
        // v.id
        int callbackArg0Id = 0;



        viewModelJavaLangObjectNull = (viewModel) != (null);
        if (viewModelJavaLangObjectNull) {


            if ((callbackArg_0) != (null)) {


                callbackArg0Id = callbackArg_0.getId();

                viewModel.handlePressOnSeclectCountry(callbackArg0Id);
            }
        }
    }
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): viewModel.state
        flag 1 (0x2L): viewModel
        flag 2 (0x3L): viewModel.state.valid
        flag 3 (0x4L): null
    flag mapping end*/
    //end
}