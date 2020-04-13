package co.yap.sendmoney.databinding;
import co.yap.sendmoney.R;
import co.yap.sendmoney.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class ItemRakBankBindingImpl extends ItemRakBankBinding implements co.yap.sendmoney.generated.callback.OnClickListener.Listener {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.content, 4);
    }
    // views
    // variables
    @Nullable
    private final android.view.View.OnClickListener mCallback32;
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ItemRakBankBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 5, sIncludes, sViewsWithIds));
    }
    private ItemRakBankBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.LinearLayout) bindings[4]
            , (co.yap.widgets.CoreCircularImageView) bindings[1]
            , (androidx.constraintlayout.widget.ConstraintLayout) bindings[0]
            , (android.widget.TextView) bindings[2]
            , (android.widget.TextView) bindings[3]
            );
        this.imgProfile.setTag(null);
        this.lyContact.setTag(null);
        this.tvName.setTag(null);
        this.tvNumber.setTag(null);
        setRootTag(root);
        // listeners
        mCallback32 = new co.yap.sendmoney.generated.callback.OnClickListener(this, 1);
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
            setViewModel((co.yap.sendmoney.addbeneficiary.adaptor.RAKBankItemViewModel) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setViewModel(@Nullable co.yap.sendmoney.addbeneficiary.adaptor.RAKBankItemViewModel ViewModel) {
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
        java.lang.String viewModelBankOtherBranchName = null;
        co.yap.networking.customers.responsedtos.sendmoney.RAKBank.Bank viewModelBank = null;
        java.lang.String viewModelBankOtherBankName = null;
        co.yap.sendmoney.addbeneficiary.adaptor.RAKBankItemViewModel viewModel = mViewModel;

        if ((dirtyFlags & 0x3L) != 0) {



                if (viewModel != null) {
                    // read viewModel.bank
                    viewModelBank = viewModel.getBank();
                }


                if (viewModelBank != null) {
                    // read viewModel.bank.other_branch_name
                    viewModelBankOtherBranchName = viewModelBank.getOther_branch_name();
                    // read viewModel.bank.other_bank_name
                    viewModelBankOtherBankName = viewModelBank.getOther_bank_name();
                }
        }
        // batch finished
        if ((dirtyFlags & 0x3L) != 0) {
            // api target 1

            co.yap.yapcore.helpers.ImageBinding.loadAvatar(this.imgProfile, " ", viewModelBankOtherBankName);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.tvName, viewModelBankOtherBankName);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.tvNumber, viewModelBankOtherBranchName);
        }
        if ((dirtyFlags & 0x2L) != 0) {
            // api target 1

            this.lyContact.setOnClickListener(mCallback32);
        }
    }
    // Listener Stub Implementations
    // callback impls
    public final void _internalCallbackOnClick(int sourceId , android.view.View callbackArg_0) {
        // localize variables for thread safety
        // viewModel
        co.yap.sendmoney.addbeneficiary.adaptor.RAKBankItemViewModel viewModel = mViewModel;
        // viewModel != null
        boolean viewModelJavaLangObjectNull = false;



        viewModelJavaLangObjectNull = (viewModel) != (null);
        if (viewModelJavaLangObjectNull) {



            viewModel.onViewClicked(callbackArg_0);
        }
    }
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): viewModel
        flag 1 (0x2L): null
    flag mapping end*/
    //end
}