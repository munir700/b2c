package co.yap.sendmoney.databinding;
import co.yap.sendmoney.R;
import co.yap.sendmoney.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class ItemRecentBeneficiariesTransferBindingImpl extends ItemRecentBeneficiariesTransferBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.rlPicture, 4);
    }
    // views
    @NonNull
    private final android.widget.LinearLayout mboundView0;
    @NonNull
    private final androidx.appcompat.widget.AppCompatTextView mboundView3;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ItemRecentBeneficiariesTransferBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 5, sIncludes, sViewsWithIds));
    }
    private ItemRecentBeneficiariesTransferBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 1
            , (co.yap.widgets.CoreCircularImageView) bindings[1]
            , (android.widget.ImageView) bindings[2]
            , (android.widget.RelativeLayout) bindings[4]
            );
        this.imgProfile.setTag(null);
        this.ivCountryFlag.setTag(null);
        this.mboundView0 = (android.widget.LinearLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.mboundView3 = (androidx.appcompat.widget.AppCompatTextView) bindings[3];
        this.mboundView3.setTag(null);
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
            setRecentTransferItemVM((co.yap.sendmoney.home.adapters.RecentTransferItemVM) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setRecentTransferItemVM(@Nullable co.yap.sendmoney.home.adapters.RecentTransferItemVM RecentTransferItemVM) {
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
                return onChangeRecentTransferItemVM((co.yap.sendmoney.home.adapters.RecentTransferItemVM) object, fieldId);
        }
        return false;
    }
    private boolean onChangeRecentTransferItemVM(co.yap.sendmoney.home.adapters.RecentTransferItemVM RecentTransferItemVM, int fieldId) {
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
        java.lang.String recentTransferItemVMItemCountry = null;
        int recentTransferItemVMPosition = 0;
        co.yap.networking.customers.responsedtos.sendmoney.Beneficiary recentTransferItemVMItem = null;
        co.yap.sendmoney.home.adapters.RecentTransferItemVM recentTransferItemVM = mRecentTransferItemVM;
        java.lang.String recentTransferItemVMItemFullName = null;
        java.lang.String recentTransferItemVMItemBeneficiaryPictureUrl = null;

        if ((dirtyFlags & 0x3L) != 0) {



                if (recentTransferItemVM != null) {
                    // read recentTransferItemVM.position
                    recentTransferItemVMPosition = recentTransferItemVM.getPosition();
                    // read recentTransferItemVM.item
                    recentTransferItemVMItem = recentTransferItemVM.getItem();
                }


                if (recentTransferItemVMItem != null) {
                    // read recentTransferItemVM.item.country
                    recentTransferItemVMItemCountry = recentTransferItemVMItem.getCountry();
                    // read recentTransferItemVM.item.fullName()
                    recentTransferItemVMItemFullName = recentTransferItemVMItem.fullName();
                    // read recentTransferItemVM.item.beneficiaryPictureUrl
                    recentTransferItemVMItemBeneficiaryPictureUrl = recentTransferItemVMItem.getBeneficiaryPictureUrl();
                }
        }
        // batch finished
        if ((dirtyFlags & 0x3L) != 0) {
            // api target 1

            co.yap.yapcore.helpers.ImageBinding.loadAvatar(this.imgProfile, recentTransferItemVMItemBeneficiaryPictureUrl, recentTransferItemVMItemFullName, recentTransferItemVMPosition, "Beneficiary");
            co.yap.yapcore.helpers.ImageBinding.setIsoCountryDrawable(this.ivCountryFlag, recentTransferItemVMItemCountry);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.mboundView3, recentTransferItemVMItemFullName);
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