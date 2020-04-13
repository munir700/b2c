package co.yap.sendmoney.databinding;
import co.yap.sendmoney.R;
import co.yap.sendmoney.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class FragmentAddBeneficiaryDomesticTransferBindingImpl extends FragmentAddBeneficiaryDomesticTransferBinding implements co.yap.sendmoney.generated.callback.OnClickListener.Listener {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.lyContainer, 16);
    }
    // views
    @NonNull
    private final android.widget.ScrollView mboundView0;
    @NonNull
    private final android.widget.TextView mboundView1;
    @NonNull
    private final android.widget.TextView mboundView11;
    @NonNull
    private final android.widget.TextView mboundView13;
    @NonNull
    private final android.widget.TextView mboundView2;
    @NonNull
    private final android.widget.TextView mboundView4;
    @NonNull
    private final android.widget.TextView mboundView5;
    @NonNull
    private final android.widget.TextView mboundView7;
    @NonNull
    private final android.widget.TextView mboundView9;
    // variables
    @Nullable
    private final android.view.View.OnClickListener mCallback25;
    // values
    // listeners
    // Inverse Binding Event Handlers
    private androidx.databinding.InverseBindingListener etConfirmIbanandroidTextAttrChanged = new androidx.databinding.InverseBindingListener() {
        @Override
        public void onChange() {
            // Inverse of viewModel.state.confirmIban
            //         is viewModel.state.setConfirmIban((java.lang.String) callbackArg_0)
            java.lang.String callbackArg_0 = androidx.databinding.adapters.TextViewBindingAdapter.getTextString(etConfirmIban);
            // localize variables for thread safety
            // viewModel.state.confirmIban
            java.lang.String viewModelStateConfirmIban = null;
            // viewModel.state
            co.yap.sendmoney.addbeneficiary.states.AddBeneficiaryStates viewModelState = null;
            // viewModel
            co.yap.sendmoney.addbeneficiary.viewmodels.AddBeneficiaryViewModel viewModel = mViewModel;
            // viewModel != null
            boolean viewModelJavaLangObjectNull = false;
            // viewModel.state != null
            boolean viewModelStateJavaLangObjectNull = false;



            viewModelJavaLangObjectNull = (viewModel) != (null);
            if (viewModelJavaLangObjectNull) {


                viewModelState = viewModel.getState();

                viewModelStateJavaLangObjectNull = (viewModelState) != (null);
                if (viewModelStateJavaLangObjectNull) {




                    viewModelState.setConfirmIban(((java.lang.String) (callbackArg_0)));
                }
            }
        }
    };
    private androidx.databinding.InverseBindingListener etFirstNameandroidTextAttrChanged = new androidx.databinding.InverseBindingListener() {
        @Override
        public void onChange() {
            // Inverse of viewModel.state.firstName
            //         is viewModel.state.setFirstName((java.lang.String) callbackArg_0)
            java.lang.String callbackArg_0 = androidx.databinding.adapters.TextViewBindingAdapter.getTextString(etFirstName);
            // localize variables for thread safety
            // viewModel.state
            co.yap.sendmoney.addbeneficiary.states.AddBeneficiaryStates viewModelState = null;
            // viewModel.state.firstName
            java.lang.String viewModelStateFirstName = null;
            // viewModel
            co.yap.sendmoney.addbeneficiary.viewmodels.AddBeneficiaryViewModel viewModel = mViewModel;
            // viewModel != null
            boolean viewModelJavaLangObjectNull = false;
            // viewModel.state != null
            boolean viewModelStateJavaLangObjectNull = false;



            viewModelJavaLangObjectNull = (viewModel) != (null);
            if (viewModelJavaLangObjectNull) {


                viewModelState = viewModel.getState();

                viewModelStateJavaLangObjectNull = (viewModelState) != (null);
                if (viewModelStateJavaLangObjectNull) {




                    viewModelState.setFirstName(((java.lang.String) (callbackArg_0)));
                }
            }
        }
    };
    private androidx.databinding.InverseBindingListener etIbanandroidTextAttrChanged = new androidx.databinding.InverseBindingListener() {
        @Override
        public void onChange() {
            // Inverse of viewModel.state.iban
            //         is viewModel.state.setIban((java.lang.String) callbackArg_0)
            java.lang.String callbackArg_0 = androidx.databinding.adapters.TextViewBindingAdapter.getTextString(etIban);
            // localize variables for thread safety
            // viewModel.state
            co.yap.sendmoney.addbeneficiary.states.AddBeneficiaryStates viewModelState = null;
            // viewModel
            co.yap.sendmoney.addbeneficiary.viewmodels.AddBeneficiaryViewModel viewModel = mViewModel;
            // viewModel != null
            boolean viewModelJavaLangObjectNull = false;
            // viewModel.state.iban
            java.lang.String viewModelStateIban = null;
            // viewModel.state != null
            boolean viewModelStateJavaLangObjectNull = false;



            viewModelJavaLangObjectNull = (viewModel) != (null);
            if (viewModelJavaLangObjectNull) {


                viewModelState = viewModel.getState();

                viewModelStateJavaLangObjectNull = (viewModelState) != (null);
                if (viewModelStateJavaLangObjectNull) {




                    viewModelState.setIban(((java.lang.String) (callbackArg_0)));
                }
            }
        }
    };
    private androidx.databinding.InverseBindingListener etLastNameandroidTextAttrChanged = new androidx.databinding.InverseBindingListener() {
        @Override
        public void onChange() {
            // Inverse of viewModel.state.lastName
            //         is viewModel.state.setLastName((java.lang.String) callbackArg_0)
            java.lang.String callbackArg_0 = androidx.databinding.adapters.TextViewBindingAdapter.getTextString(etLastName);
            // localize variables for thread safety
            // viewModel.state
            co.yap.sendmoney.addbeneficiary.states.AddBeneficiaryStates viewModelState = null;
            // viewModel
            co.yap.sendmoney.addbeneficiary.viewmodels.AddBeneficiaryViewModel viewModel = mViewModel;
            // viewModel != null
            boolean viewModelJavaLangObjectNull = false;
            // viewModel.state.lastName
            java.lang.String viewModelStateLastName = null;
            // viewModel.state != null
            boolean viewModelStateJavaLangObjectNull = false;



            viewModelJavaLangObjectNull = (viewModel) != (null);
            if (viewModelJavaLangObjectNull) {


                viewModelState = viewModel.getState();

                viewModelStateJavaLangObjectNull = (viewModelState) != (null);
                if (viewModelStateJavaLangObjectNull) {




                    viewModelState.setLastName(((java.lang.String) (callbackArg_0)));
                }
            }
        }
    };
    private androidx.databinding.InverseBindingListener etnickNameandroidTextAttrChanged = new androidx.databinding.InverseBindingListener() {
        @Override
        public void onChange() {
            // Inverse of viewModel.state.nickName
            //         is viewModel.state.setNickName((java.lang.String) callbackArg_0)
            java.lang.String callbackArg_0 = androidx.databinding.adapters.TextViewBindingAdapter.getTextString(etnickName);
            // localize variables for thread safety
            // viewModel.state
            co.yap.sendmoney.addbeneficiary.states.AddBeneficiaryStates viewModelState = null;
            // viewModel.state.nickName
            java.lang.String viewModelStateNickName = null;
            // viewModel
            co.yap.sendmoney.addbeneficiary.viewmodels.AddBeneficiaryViewModel viewModel = mViewModel;
            // viewModel != null
            boolean viewModelJavaLangObjectNull = false;
            // viewModel.state != null
            boolean viewModelStateJavaLangObjectNull = false;



            viewModelJavaLangObjectNull = (viewModel) != (null);
            if (viewModelJavaLangObjectNull) {


                viewModelState = viewModel.getState();

                viewModelStateJavaLangObjectNull = (viewModelState) != (null);
                if (viewModelStateJavaLangObjectNull) {




                    viewModelState.setNickName(((java.lang.String) (callbackArg_0)));
                }
            }
        }
    };

    public FragmentAddBeneficiaryDomesticTransferBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 17, sIncludes, sViewsWithIds));
    }
    private FragmentAddBeneficiaryDomesticTransferBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 1
            , (co.yap.widgets.CoreButton) bindings[15]
            , (androidx.appcompat.widget.AppCompatEditText) bindings[14]
            , (androidx.appcompat.widget.AppCompatEditText) bindings[8]
            , (co.yap.widgets.DrawableClickEditText) bindings[12]
            , (androidx.appcompat.widget.AppCompatEditText) bindings[10]
            , (androidx.appcompat.widget.AppCompatEditText) bindings[6]
            , (co.yap.widgets.CoreCircularImageView) bindings[3]
            , (android.widget.LinearLayout) bindings[16]
            );
        this.confirmButton.setTag(null);
        this.etConfirmIban.setTag(null);
        this.etFirstName.setTag(null);
        this.etIban.setTag(null);
        this.etLastName.setTag(null);
        this.etnickName.setTag(null);
        this.flagImg.setTag(null);
        this.mboundView0 = (android.widget.ScrollView) bindings[0];
        this.mboundView0.setTag(null);
        this.mboundView1 = (android.widget.TextView) bindings[1];
        this.mboundView1.setTag(null);
        this.mboundView11 = (android.widget.TextView) bindings[11];
        this.mboundView11.setTag(null);
        this.mboundView13 = (android.widget.TextView) bindings[13];
        this.mboundView13.setTag(null);
        this.mboundView2 = (android.widget.TextView) bindings[2];
        this.mboundView2.setTag(null);
        this.mboundView4 = (android.widget.TextView) bindings[4];
        this.mboundView4.setTag(null);
        this.mboundView5 = (android.widget.TextView) bindings[5];
        this.mboundView5.setTag(null);
        this.mboundView7 = (android.widget.TextView) bindings[7];
        this.mboundView7.setTag(null);
        this.mboundView9 = (android.widget.TextView) bindings[9];
        this.mboundView9.setTag(null);
        setRootTag(root);
        // listeners
        mCallback25 = new co.yap.sendmoney.generated.callback.OnClickListener(this, 1);
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x400L;
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
            setViewModel((co.yap.sendmoney.addbeneficiary.viewmodels.AddBeneficiaryViewModel) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setViewModel(@Nullable co.yap.sendmoney.addbeneficiary.viewmodels.AddBeneficiaryViewModel ViewModel) {
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
                return onChangeViewModelState((co.yap.sendmoney.addbeneficiary.states.AddBeneficiaryStates) object, fieldId);
        }
        return false;
    }
    private boolean onChangeViewModelState(co.yap.sendmoney.addbeneficiary.states.AddBeneficiaryStates ViewModelState, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x1L;
            }
            return true;
        }
        else if (fieldId == BR.flagDrawableResId) {
            synchronized(this) {
                    mDirtyFlags |= 0x4L;
            }
            return true;
        }
        else if (fieldId == BR.country) {
            synchronized(this) {
                    mDirtyFlags |= 0x8L;
            }
            return true;
        }
        else if (fieldId == BR.nickName) {
            synchronized(this) {
                    mDirtyFlags |= 0x10L;
            }
            return true;
        }
        else if (fieldId == BR.firstName) {
            synchronized(this) {
                    mDirtyFlags |= 0x20L;
            }
            return true;
        }
        else if (fieldId == BR.lastName) {
            synchronized(this) {
                    mDirtyFlags |= 0x40L;
            }
            return true;
        }
        else if (fieldId == BR.iban) {
            synchronized(this) {
                    mDirtyFlags |= 0x80L;
            }
            return true;
        }
        else if (fieldId == BR.confirmIban) {
            synchronized(this) {
                    mDirtyFlags |= 0x100L;
            }
            return true;
        }
        else if (fieldId == BR.valid) {
            synchronized(this) {
                    mDirtyFlags |= 0x200L;
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
        boolean viewModelStateLastNameEmpty = false;
        java.lang.String viewModelStateIban = null;
        boolean viewModelStateValid = false;
        co.yap.sendmoney.addbeneficiary.states.AddBeneficiaryStates viewModelState = null;
        boolean viewModelStateFirstNameEmpty = false;
        boolean viewModelStateFirstNameEmptyBooleanTrueBooleanFalse = false;
        java.lang.String viewModelStateNickName = null;
        boolean viewModelStateNickNameEmpty = false;
        java.lang.String viewModelStateConfirmIban = null;
        boolean viewModelStateLastNameEmptyBooleanTrueBooleanFalse = false;
        java.lang.String viewModelStateFirstName = null;
        java.lang.String viewModelStateLastName = null;
        java.lang.String viewModelStateCountry = null;
        int viewModelStateFlagDrawableResId = 0;
        boolean viewModelStateNickNameEmptyBooleanTrueBooleanFalse = false;
        co.yap.sendmoney.addbeneficiary.viewmodels.AddBeneficiaryViewModel viewModel = mViewModel;

        if ((dirtyFlags & 0x7ffL) != 0) {



                if (viewModel != null) {
                    // read viewModel.state
                    viewModelState = viewModel.getState();
                }
                updateRegistration(0, viewModelState);

            if ((dirtyFlags & 0x483L) != 0) {

                    if (viewModelState != null) {
                        // read viewModel.state.iban
                        viewModelStateIban = viewModelState.getIban();
                    }
            }
            if ((dirtyFlags & 0x603L) != 0) {

                    if (viewModelState != null) {
                        // read viewModel.state.valid
                        viewModelStateValid = viewModelState.getValid();
                    }
            }
            if ((dirtyFlags & 0x413L) != 0) {

                    if (viewModelState != null) {
                        // read viewModel.state.nickName
                        viewModelStateNickName = viewModelState.getNickName();
                    }


                    if (viewModelStateNickName != null) {
                        // read viewModel.state.nickName.empty
                        viewModelStateNickNameEmpty = viewModelStateNickName.isEmpty();
                    }
                if((dirtyFlags & 0x413L) != 0) {
                    if(viewModelStateNickNameEmpty) {
                            dirtyFlags |= 0x10000L;
                    }
                    else {
                            dirtyFlags |= 0x8000L;
                    }
                }


                    // read viewModel.state.nickName.empty ? true : false
                    viewModelStateNickNameEmptyBooleanTrueBooleanFalse = ((viewModelStateNickNameEmpty) ? (true) : (false));
            }
            if ((dirtyFlags & 0x503L) != 0) {

                    if (viewModelState != null) {
                        // read viewModel.state.confirmIban
                        viewModelStateConfirmIban = viewModelState.getConfirmIban();
                    }
            }
            if ((dirtyFlags & 0x423L) != 0) {

                    if (viewModelState != null) {
                        // read viewModel.state.firstName
                        viewModelStateFirstName = viewModelState.getFirstName();
                    }


                    if (viewModelStateFirstName != null) {
                        // read viewModel.state.firstName.empty
                        viewModelStateFirstNameEmpty = viewModelStateFirstName.isEmpty();
                    }
                if((dirtyFlags & 0x423L) != 0) {
                    if(viewModelStateFirstNameEmpty) {
                            dirtyFlags |= 0x1000L;
                    }
                    else {
                            dirtyFlags |= 0x800L;
                    }
                }


                    // read viewModel.state.firstName.empty ? true : false
                    viewModelStateFirstNameEmptyBooleanTrueBooleanFalse = ((viewModelStateFirstNameEmpty) ? (true) : (false));
            }
            if ((dirtyFlags & 0x443L) != 0) {

                    if (viewModelState != null) {
                        // read viewModel.state.lastName
                        viewModelStateLastName = viewModelState.getLastName();
                    }


                    if (viewModelStateLastName != null) {
                        // read viewModel.state.lastName.empty
                        viewModelStateLastNameEmpty = viewModelStateLastName.isEmpty();
                    }
                if((dirtyFlags & 0x443L) != 0) {
                    if(viewModelStateLastNameEmpty) {
                            dirtyFlags |= 0x4000L;
                    }
                    else {
                            dirtyFlags |= 0x2000L;
                    }
                }


                    // read viewModel.state.lastName.empty ? true : false
                    viewModelStateLastNameEmptyBooleanTrueBooleanFalse = ((viewModelStateLastNameEmpty) ? (true) : (false));
            }
            if ((dirtyFlags & 0x40bL) != 0) {

                    if (viewModelState != null) {
                        // read viewModel.state.country
                        viewModelStateCountry = viewModelState.getCountry();
                    }
            }
            if ((dirtyFlags & 0x407L) != 0) {

                    if (viewModelState != null) {
                        // read viewModel.state.flagDrawableResId
                        viewModelStateFlagDrawableResId = viewModelState.getFlagDrawableResId();
                    }
            }
        }
        // batch finished
        if ((dirtyFlags & 0x400L) != 0) {
            // api target 1

            this.confirmButton.setOnClickListener(mCallback25);
            co.yap.yapcore.binders.UIBinder.setText(this.confirmButton, co.yap.translation.Strings.screen_add_beneficiary_button_next);
            co.yap.yapcore.binders.UIBinder.maskIbanNo(this.etConfirmIban, "#### #### #### #### #### #### ####");
            androidx.databinding.adapters.TextViewBindingAdapter.setTextWatcher(this.etConfirmIban, (androidx.databinding.adapters.TextViewBindingAdapter.BeforeTextChanged)null, (androidx.databinding.adapters.TextViewBindingAdapter.OnTextChanged)null, (androidx.databinding.adapters.TextViewBindingAdapter.AfterTextChanged)null, etConfirmIbanandroidTextAttrChanged);
            co.yap.yapcore.binders.UIBinder.setHint(this.etFirstName, co.yap.translation.Strings.screen_add_beneficiary_detail_input_text_first_name_hint);
            androidx.databinding.adapters.TextViewBindingAdapter.setTextWatcher(this.etFirstName, (androidx.databinding.adapters.TextViewBindingAdapter.BeforeTextChanged)null, (androidx.databinding.adapters.TextViewBindingAdapter.OnTextChanged)null, (androidx.databinding.adapters.TextViewBindingAdapter.AfterTextChanged)null, etFirstNameandroidTextAttrChanged);
            co.yap.yapcore.binders.UIBinder.maskIbanNo(this.etIban, "#### #### #### #### #### #### ####");
            androidx.databinding.adapters.TextViewBindingAdapter.setTextWatcher(this.etIban, (androidx.databinding.adapters.TextViewBindingAdapter.BeforeTextChanged)null, (androidx.databinding.adapters.TextViewBindingAdapter.OnTextChanged)null, (androidx.databinding.adapters.TextViewBindingAdapter.AfterTextChanged)null, etIbanandroidTextAttrChanged);
            co.yap.yapcore.binders.UIBinder.setHint(this.etLastName, co.yap.translation.Strings.screen_add_beneficiary_detail_input_text_last_name_hint);
            androidx.databinding.adapters.TextViewBindingAdapter.setTextWatcher(this.etLastName, (androidx.databinding.adapters.TextViewBindingAdapter.BeforeTextChanged)null, (androidx.databinding.adapters.TextViewBindingAdapter.OnTextChanged)null, (androidx.databinding.adapters.TextViewBindingAdapter.AfterTextChanged)null, etLastNameandroidTextAttrChanged);
            co.yap.yapcore.binders.UIBinder.setHint(this.etnickName, co.yap.translation.Strings.screen_add_beneficiary_detail_input_text_nick_name_hint);
            androidx.databinding.adapters.TextViewBindingAdapter.setTextWatcher(this.etnickName, (androidx.databinding.adapters.TextViewBindingAdapter.BeforeTextChanged)null, (androidx.databinding.adapters.TextViewBindingAdapter.OnTextChanged)null, (androidx.databinding.adapters.TextViewBindingAdapter.AfterTextChanged)null, etnickNameandroidTextAttrChanged);
            co.yap.yapcore.binders.UIBinder.setText(this.mboundView1, co.yap.translation.Strings.screen_add_beneficiary_detail_display_text_title);
            co.yap.yapcore.binders.UIBinder.setText(this.mboundView11, co.yap.translation.Strings.screen_beneficiary_account_details_display_text_iban);
            co.yap.yapcore.binders.UIBinder.setText(this.mboundView13, co.yap.translation.Strings.screen_sdd_beneficiary_display_confirm_iban);
            co.yap.yapcore.binders.UIBinder.setText(this.mboundView2, co.yap.translation.Strings.screen_add_beneficiary_detail_display_text_country);
            co.yap.yapcore.binders.UIBinder.setText(this.mboundView5, co.yap.translation.Strings.screen_add_beneficiary_detail_display_text_transfer_nick_name);
            co.yap.yapcore.binders.UIBinder.setText(this.mboundView7, co.yap.translation.Strings.screen_add_beneficiary_detail_display_text_transfer_first_name);
            co.yap.yapcore.binders.UIBinder.setText(this.mboundView9, co.yap.translation.Strings.screen_add_beneficiary_detail_display_text_transfer_last_name);
        }
        if ((dirtyFlags & 0x603L) != 0) {
            // api target 1

            co.yap.yapcore.binders.UIBinder.setEnable(this.confirmButton, viewModelStateValid);
        }
        if ((dirtyFlags & 0x503L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.etConfirmIban, viewModelStateConfirmIban);
        }
        if ((dirtyFlags & 0x423L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.etFirstName, viewModelStateFirstName);
            co.yap.yapcore.binders.UIBinder.setSelectedTextColor(this.mboundView7, viewModelStateFirstNameEmptyBooleanTrueBooleanFalse);
        }
        if ((dirtyFlags & 0x483L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.etIban, viewModelStateIban);
        }
        if ((dirtyFlags & 0x443L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.etLastName, viewModelStateLastName);
            co.yap.yapcore.binders.UIBinder.setSelectedTextColor(this.mboundView11, viewModelStateLastNameEmptyBooleanTrueBooleanFalse);
            co.yap.yapcore.binders.UIBinder.setSelectedTextColor(this.mboundView13, viewModelStateLastNameEmptyBooleanTrueBooleanFalse);
            co.yap.yapcore.binders.UIBinder.setSelectedTextColor(this.mboundView9, viewModelStateLastNameEmptyBooleanTrueBooleanFalse);
        }
        if ((dirtyFlags & 0x413L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.etnickName, viewModelStateNickName);
            co.yap.yapcore.binders.UIBinder.setSelectedTextColor(this.mboundView5, viewModelStateNickNameEmptyBooleanTrueBooleanFalse);
        }
        if ((dirtyFlags & 0x407L) != 0) {
            // api target 1

            co.yap.yapcore.binders.UIBinder.setImageResId(this.flagImg, viewModelStateFlagDrawableResId);
        }
        if ((dirtyFlags & 0x40bL) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.mboundView4, viewModelStateCountry);
        }
    }
    // Listener Stub Implementations
    // callback impls
    public final void _internalCallbackOnClick(int sourceId , android.view.View callbackArg_0) {
        // localize variables for thread safety
        // viewModel
        co.yap.sendmoney.addbeneficiary.viewmodels.AddBeneficiaryViewModel viewModel = mViewModel;
        // viewModel != null
        boolean viewModelJavaLangObjectNull = false;
        // v.id
        int callbackArg0Id = 0;



        viewModelJavaLangObjectNull = (viewModel) != (null);
        if (viewModelJavaLangObjectNull) {


            if ((callbackArg_0) != (null)) {


                callbackArg0Id = callbackArg_0.getId();

                viewModel.handlePressOnAddNow(callbackArg0Id);
            }
        }
    }
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): viewModel.state
        flag 1 (0x2L): viewModel
        flag 2 (0x3L): viewModel.state.flagDrawableResId
        flag 3 (0x4L): viewModel.state.country
        flag 4 (0x5L): viewModel.state.nickName
        flag 5 (0x6L): viewModel.state.firstName
        flag 6 (0x7L): viewModel.state.lastName
        flag 7 (0x8L): viewModel.state.iban
        flag 8 (0x9L): viewModel.state.confirmIban
        flag 9 (0xaL): viewModel.state.valid
        flag 10 (0xbL): null
        flag 11 (0xcL): viewModel.state.firstName.empty ? true : false
        flag 12 (0xdL): viewModel.state.firstName.empty ? true : false
        flag 13 (0xeL): viewModel.state.lastName.empty ? true : false
        flag 14 (0xfL): viewModel.state.lastName.empty ? true : false
        flag 15 (0x10L): viewModel.state.nickName.empty ? true : false
        flag 16 (0x11L): viewModel.state.nickName.empty ? true : false
    flag mapping end*/
    //end
}