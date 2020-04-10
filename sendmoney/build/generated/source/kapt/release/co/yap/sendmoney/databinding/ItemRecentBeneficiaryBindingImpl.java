package co.yap.sendmoney.databinding;
import co.yap.sendmoney.R;
import co.yap.sendmoney.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class ItemRecentBeneficiaryBindingImpl extends ItemRecentBeneficiaryBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.rlPicture, 3);
        sViewsWithIds.put(R.id.ivProfilePic, 4);
        sViewsWithIds.put(R.id.rlAddNewProfilePic, 5);
        sViewsWithIds.put(R.id.ivAddProfilePic, 6);
    }
    // views
    @NonNull
    private final android.widget.LinearLayout mboundView0;
    @NonNull
    private final androidx.appcompat.widget.AppCompatTextView mboundView2;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ItemRecentBeneficiaryBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 7, sIncludes, sViewsWithIds));
    }
    private ItemRecentBeneficiaryBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 1
            , (android.widget.ImageView) bindings[6]
            , (co.yap.widgets.CoreCircularImageView) bindings[4]
            , (android.widget.RelativeLayout) bindings[5]
            , (android.widget.RelativeLayout) bindings[3]
            , (android.widget.TextView) bindings[1]
            );
        this.mboundView0 = (android.widget.LinearLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.mboundView2 = (androidx.appcompat.widget.AppCompatTextView) bindings[2];
        this.mboundView2.setTag(null);
        this.tvAccountHolderNameInitials.setTag(null);
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
        if (BR.recentTransferItemVM == variableId) {
            setRecentTransferItemVM((co.yap.sendMoney.home.adapters.RecentTransferItemVM) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setRecentTransferItemVM(@Nullable co.yap.sendMoney.home.adapters.RecentTransferItemVM RecentTransferItemVM) {
        updateRegistration(0, RecentTransferItemVM);
        this.mRecentTransferItemVM = RecentTransferItemVM;
        synchronized(this) {
            mDirtyFlags |= 0x1L;
        }
        notifyPropertyChanged(BR.recentTransferItemVM);
        super.requestRebind();
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
            case 0 :
                return onChangeRecentTransferItemVM((co.yap.sendMoney.home.adapters.RecentTransferItemVM) object, fieldId);
        }
        return false;
    }
    private boolean onChangeRecentTransferItemVM(co.yap.sendMoney.home.adapters.RecentTransferItemVM RecentTransferItemVM, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x1L;
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
        java.lang.String recentTransferItemVMItemTitle = null;
        co.yap.networking.customers.responsedtos.sendmoney.Beneficiary recentTransferItemVMItem = null;
        co.yap.sendMoney.home.adapters.RecentTransferItemVM recentTransferItemVM = mRecentTransferItemVM;
        java.lang.String stringUtilsINSTANCEGetInitialsRecentTransferItemVMItemTitle = null;

        if ((dirtyFlags & 0x3L) != 0) {



                if (recentTransferItemVM != null) {
                    // read recentTransferItemVM.item
                    recentTransferItemVMItem = recentTransferItemVM.getItem();
                }


                if (recentTransferItemVMItem != null) {
                    // read recentTransferItemVM.item.title
                    recentTransferItemVMItemTitle = recentTransferItemVMItem.getTitle();
                }


                // read StringUtils.INSTANCE.getInitials(recentTransferItemVM.item.title)
                stringUtilsINSTANCEGetInitialsRecentTransferItemVMItemTitle = co.yap.yapcore.helpers.StringUtils.INSTANCE.getInitials(recentTransferItemVMItemTitle);
        }
        // batch finished
        if ((dirtyFlags & 0x3L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.mboundView2, recentTransferItemVMItemTitle);
            co.yap.yapcore.binders.UIBinder.setText(this.tvAccountHolderNameInitials, stringUtilsINSTANCEGetInitialsRecentTransferItemVMItemTitle);
        }
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): recentTransferItemVM
        flag 1 (0x2L): null
    flag mapping end*/
    //end
}