package co.yap.sendmoney.databinding;
import co.yap.sendmoney.R;
import co.yap.sendmoney.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class ItemBankParamsBindingImpl extends ItemBankParamsBinding  {

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
    // values
    // listeners
    // Inverse Binding Event Handlers
    private androidx.databinding.InverseBindingListener etBankNameandroidTextAttrChanged = new androidx.databinding.InverseBindingListener() {
        @Override
        public void onChange() {
            // Inverse of viewModel.bankParams.data
            //         is viewModel.bankParams.setData((java.lang.String) callbackArg_0)
            java.lang.String callbackArg_0 = androidx.databinding.adapters.TextViewBindingAdapter.getTextString(etBankName);
            // localize variables for thread safety
            // viewModel.bankParams != null
            boolean viewModelBankParamsJavaLangObjectNull = false;
            // viewModel
            co.yap.sendMoney.addbeneficiary.viewmodels.BankParamsItemViewModel viewModel = mViewModel;
            // viewModel.bankParams.data
            java.lang.String viewModelBankParamsData = null;
            // viewModel != null
            boolean viewModelJavaLangObjectNull = false;
            // viewModel.bankParams
            co.yap.networking.customers.responsedtos.beneficiary.BankParams viewModelBankParams = null;



            viewModelJavaLangObjectNull = (viewModel) != (null);
            if (viewModelJavaLangObjectNull) {


                viewModelBankParams = viewModel.getBankParams();

                viewModelBankParamsJavaLangObjectNull = (viewModelBankParams) != (null);
                if (viewModelBankParamsJavaLangObjectNull) {




                    viewModelBankParams.setData(((java.lang.String) (callbackArg_0)));
                }
            }
        }
    };

    public ItemBankParamsBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 3, sIncludes, sViewsWithIds));
    }
    private ItemBankParamsBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.EditText) bindings[2]
            , (androidx.constraintlayout.widget.ConstraintLayout) bindings[0]
            , (android.widget.TextView) bindings[1]
            );
        this.etBankName.setTag(null);
        this.swipeContainer.setTag(null);
        this.txtTitle.setTag(null);
        setRootTag(root);
        // listeners
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x2L;
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
            setViewModel((co.yap.sendMoney.addbeneficiary.viewmodels.BankParamsItemViewModel) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setViewModel(@Nullable co.yap.sendMoney.addbeneficiary.viewmodels.BankParamsItemViewModel ViewModel) {
        this.mViewModel = ViewModel;
        synchronized(this) {
            mDirtyFlags |= 0x1L;
        }
        notifyPropertyChanged(BR.viewModel);
        super.requestRebind();
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
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
        java.lang.String viewModelBankParamsIsMandatory = null;
        boolean viewModelBankParamsIsMandatoryEqualsJavaLangStringY = false;
        java.lang.String viewModelBankParamsIsMandatoryEqualsJavaLangStringYViewModelBankParamsNameJavaLangStringViewModelBankParamsName = null;
        java.lang.Integer integerValueOfViewModelBankParamsMaxCharacters = null;
        java.lang.String viewModelBankParamsMaxCharacters = null;
        java.lang.String viewModelBankParamsData = null;
        co.yap.networking.customers.responsedtos.beneficiary.BankParams viewModelBankParams = null;
        int androidxDatabindingViewDataBindingSafeUnboxIntegerValueOfViewModelBankParamsMaxCharacters = 0;
        java.lang.String viewModelBankParamsName = null;
        java.lang.String viewModelBankParamsNameJavaLangString = null;
        co.yap.sendMoney.addbeneficiary.viewmodels.BankParamsItemViewModel viewModel = mViewModel;

        if ((dirtyFlags & 0x3L) != 0) {



                if (viewModel != null) {
                    // read viewModel.bankParams
                    viewModelBankParams = viewModel.getBankParams();
                }


                if (viewModelBankParams != null) {
                    // read viewModel.bankParams.isMandatory()
                    viewModelBankParamsIsMandatory = viewModelBankParams.isMandatory();
                    // read viewModel.bankParams.maxCharacters
                    viewModelBankParamsMaxCharacters = viewModelBankParams.getMaxCharacters();
                    // read viewModel.bankParams.data
                    viewModelBankParamsData = viewModelBankParams.getData();
                    // read viewModel.bankParams.name
                    viewModelBankParamsName = viewModelBankParams.getName();
                }


                if (viewModelBankParamsIsMandatory != null) {
                    // read viewModel.bankParams.isMandatory().equals("Y")
                    viewModelBankParamsIsMandatoryEqualsJavaLangStringY = viewModelBankParamsIsMandatory.equals("Y");
                }
            if((dirtyFlags & 0x3L) != 0) {
                if(viewModelBankParamsIsMandatoryEqualsJavaLangStringY) {
                        dirtyFlags |= 0x8L;
                }
                else {
                        dirtyFlags |= 0x4L;
                }
            }
                // read Integer.valueOf(viewModel.bankParams.maxCharacters)
                integerValueOfViewModelBankParamsMaxCharacters = java.lang.Integer.valueOf(viewModelBankParamsMaxCharacters);


                // read androidx.databinding.ViewDataBinding.safeUnbox(Integer.valueOf(viewModel.bankParams.maxCharacters))
                androidxDatabindingViewDataBindingSafeUnboxIntegerValueOfViewModelBankParamsMaxCharacters = androidx.databinding.ViewDataBinding.safeUnbox(integerValueOfViewModelBankParamsMaxCharacters);
        }
        // batch finished

        if ((dirtyFlags & 0x8L) != 0) {

                // read (viewModel.bankParams.name) + (" *")
                viewModelBankParamsNameJavaLangString = (viewModelBankParamsName) + (" *");
        }

        if ((dirtyFlags & 0x3L) != 0) {

                // read viewModel.bankParams.isMandatory().equals("Y") ? (viewModel.bankParams.name) + (" *") : viewModel.bankParams.name
                viewModelBankParamsIsMandatoryEqualsJavaLangStringYViewModelBankParamsNameJavaLangStringViewModelBankParamsName = ((viewModelBankParamsIsMandatoryEqualsJavaLangStringY) ? (viewModelBankParamsNameJavaLangString) : (viewModelBankParamsName));
        }
        // batch finished
        if ((dirtyFlags & 0x3L) != 0) {
            // api target 1

            this.etBankName.setHint(viewModelBankParamsName);
            androidx.databinding.adapters.TextViewBindingAdapter.setMaxLength(this.etBankName, androidxDatabindingViewDataBindingSafeUnboxIntegerValueOfViewModelBankParamsMaxCharacters);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.etBankName, viewModelBankParamsData);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.txtTitle, viewModelBankParamsIsMandatoryEqualsJavaLangStringYViewModelBankParamsNameJavaLangStringViewModelBankParamsName);
        }
        if ((dirtyFlags & 0x2L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setTextWatcher(this.etBankName, (androidx.databinding.adapters.TextViewBindingAdapter.BeforeTextChanged)null, (androidx.databinding.adapters.TextViewBindingAdapter.OnTextChanged)null, (androidx.databinding.adapters.TextViewBindingAdapter.AfterTextChanged)null, etBankNameandroidTextAttrChanged);
        }
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): viewModel
        flag 1 (0x2L): null
        flag 2 (0x3L): viewModel.bankParams.isMandatory().equals("Y") ? (viewModel.bankParams.name) + (" *") : viewModel.bankParams.name
        flag 3 (0x4L): viewModel.bankParams.isMandatory().equals("Y") ? (viewModel.bankParams.name) + (" *") : viewModel.bankParams.name
    flag mapping end*/
    //end
}