package co.yap.sendmoney.databinding;
import co.yap.sendmoney.R;
import co.yap.sendmoney.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class ItemBeneficiariesBindingImpl extends ItemBeneficiariesBinding implements co.yap.sendmoney.generated.callback.OnClickListener.Listener {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.backgroundContainer, 8);
        sViewsWithIds.put(R.id.btnEdit, 9);
        sViewsWithIds.put(R.id.btnDelete, 10);
        sViewsWithIds.put(R.id.foregroundContainer, 11);
        sViewsWithIds.put(R.id.lyUserImage, 12);
        sViewsWithIds.put(R.id.content, 13);
        sViewsWithIds.put(R.id.section, 14);
        sViewsWithIds.put(R.id.flag_img, 15);
    }
    // views
    @NonNull
    private final android.widget.TextView mboundView1;
    @NonNull
    private final android.widget.TextView mboundView2;
    // variables
    @Nullable
    private final android.view.View.OnClickListener mCallback2;
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ItemBeneficiariesBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 16, sIncludes, sViewsWithIds));
    }
    private ItemBeneficiariesBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.LinearLayout) bindings[8]
            , (android.widget.FrameLayout) bindings[10]
            , (android.widget.FrameLayout) bindings[9]
            , (android.widget.LinearLayout) bindings[13]
            , (co.yap.widgets.CoreCircularImageView) bindings[15]
            , (androidx.constraintlayout.widget.ConstraintLayout) bindings[11]
            , (co.yap.widgets.CoreCircularImageView) bindings[3]
            , (android.widget.ImageView) bindings[7]
            , (android.widget.LinearLayout) bindings[4]
            , (androidx.constraintlayout.widget.ConstraintLayout) bindings[12]
            , (android.widget.LinearLayout) bindings[14]
            , (co.yap.widgets.swipe_lib.SwipeLayout) bindings[0]
            , (android.widget.TextView) bindings[6]
            , (android.widget.TextView) bindings[5]
            );
        this.imgProfile.setTag(null);
        this.ivTransferType.setTag(null);
        this.lyNameInitials.setTag(null);
        this.mboundView1 = (android.widget.TextView) bindings[1];
        this.mboundView1.setTag(null);
        this.mboundView2 = (android.widget.TextView) bindings[2];
        this.mboundView2.setTag(null);
        this.swipeContainer.setTag(null);
        this.tvName.setTag(null);
        this.tvNameInitials.setTag(null);
        setRootTag(root);
        // listeners
        mCallback2 = new co.yap.sendmoney.generated.callback.OnClickListener(this, 1);
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
            setViewModel((co.yap.sendMoney.home.adapters.BeneficiaryItemViewModel) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setViewModel(@Nullable co.yap.sendMoney.home.adapters.BeneficiaryItemViewModel ViewModel) {
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
        java.lang.String viewModelBeneficiaryFirstNameCharViewModelBeneficiaryLastName = null;
        boolean viewModelBeneficiaryBeneficiaryPictureUrlEqualsJavaLangString = false;
        java.lang.String viewModelBeneficiaryBeneficiaryPictureUrl = null;
        java.lang.String viewModelBeneficiaryLastName = null;
        java.lang.String viewModelBeneficiaryTitle = null;
        java.lang.String viewModelBeneficiaryFirstNameChar = null;
        int viewModelBeneficiaryBeneficiaryPictureUrlEqualsJavaLangStringViewVISIBLEViewGONE = 0;
        co.yap.networking.customers.responsedtos.sendmoney.Beneficiary viewModelBeneficiary = null;
        java.lang.String viewModelBeneficiaryFirstName = null;
        java.lang.String utilsINSTANCEShortNameViewModelBeneficiaryFirstNameCharViewModelBeneficiaryLastName = null;
        co.yap.sendMoney.home.adapters.BeneficiaryItemViewModel viewModel = mViewModel;
        java.lang.String viewModelBeneficiaryBeneficiaryType = null;

        if ((dirtyFlags & 0x3L) != 0) {



                if (viewModel != null) {
                    // read viewModel.beneficiary
                    viewModelBeneficiary = viewModel.getBeneficiary();
                }


                if (viewModelBeneficiary != null) {
                    // read viewModel.beneficiary.beneficiaryPictureUrl
                    viewModelBeneficiaryBeneficiaryPictureUrl = viewModelBeneficiary.getBeneficiaryPictureUrl();
                    // read viewModel.beneficiary.lastName
                    viewModelBeneficiaryLastName = viewModelBeneficiary.getLastName();
                    // read viewModel.beneficiary.title
                    viewModelBeneficiaryTitle = viewModelBeneficiary.getTitle();
                    // read viewModel.beneficiary.firstName
                    viewModelBeneficiaryFirstName = viewModelBeneficiary.getFirstName();
                    // read viewModel.beneficiary.beneficiaryType
                    viewModelBeneficiaryBeneficiaryType = viewModelBeneficiary.getBeneficiaryType();
                }


                if (viewModelBeneficiaryBeneficiaryPictureUrl != null) {
                    // read viewModel.beneficiary.beneficiaryPictureUrl.equals("")
                    viewModelBeneficiaryBeneficiaryPictureUrlEqualsJavaLangString = viewModelBeneficiaryBeneficiaryPictureUrl.equals("");
                }
            if((dirtyFlags & 0x3L) != 0) {
                if(viewModelBeneficiaryBeneficiaryPictureUrlEqualsJavaLangString) {
                        dirtyFlags |= 0x8L;
                }
                else {
                        dirtyFlags |= 0x4L;
                }
            }
                // read (viewModel.beneficiary.firstName) + (' ')
                viewModelBeneficiaryFirstNameChar = (viewModelBeneficiaryFirstName) + (' ');


                // read viewModel.beneficiary.beneficiaryPictureUrl.equals("") ? View.VISIBLE : View.GONE
                viewModelBeneficiaryBeneficiaryPictureUrlEqualsJavaLangStringViewVISIBLEViewGONE = ((viewModelBeneficiaryBeneficiaryPictureUrlEqualsJavaLangString) ? (android.view.View.VISIBLE) : (android.view.View.GONE));
                // read ((viewModel.beneficiary.firstName) + (' ')) + (viewModel.beneficiary.lastName)
                viewModelBeneficiaryFirstNameCharViewModelBeneficiaryLastName = (viewModelBeneficiaryFirstNameChar) + (viewModelBeneficiaryLastName);


                // read Utils.INSTANCE.shortName(((viewModel.beneficiary.firstName) + (' ')) + (viewModel.beneficiary.lastName))
                utilsINSTANCEShortNameViewModelBeneficiaryFirstNameCharViewModelBeneficiaryLastName = co.yap.yapcore.helpers.Utils.INSTANCE.shortName(viewModelBeneficiaryFirstNameCharViewModelBeneficiaryLastName);
        }
        // batch finished
        if ((dirtyFlags & 0x3L) != 0) {
            // api target 1

            co.yap.yapcore.binders.UIBinder.getPhoto(this.imgProfile, viewModelBeneficiaryBeneficiaryPictureUrl);
            co.yap.yapcore.binders.UIBinder.setImageSrc(this.ivTransferType, viewModelBeneficiaryBeneficiaryType);
            this.lyNameInitials.setVisibility(viewModelBeneficiaryBeneficiaryPictureUrlEqualsJavaLangStringViewVISIBLEViewGONE);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.tvName, viewModelBeneficiaryTitle);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.tvNameInitials, utilsINSTANCEShortNameViewModelBeneficiaryFirstNameCharViewModelBeneficiaryLastName);
        }
        if ((dirtyFlags & 0x2L) != 0) {
            // api target 1

            co.yap.yapcore.binders.UIBinder.setText(this.mboundView1, co.yap.translation.Strings.screen_send_money_display_text_edit);
            co.yap.yapcore.binders.UIBinder.setText(this.mboundView2, co.yap.translation.Strings.screen_send_money_display_text_delete);
            this.swipeContainer.setOnClickListener(mCallback2);
        }
    }
    // Listener Stub Implementations
    // callback impls
    public final void _internalCallbackOnClick(int sourceId , android.view.View callbackArg_0) {
        // localize variables for thread safety
        // viewModel
        co.yap.sendMoney.home.adapters.BeneficiaryItemViewModel viewModel = mViewModel;
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
        flag 2 (0x3L): viewModel.beneficiary.beneficiaryPictureUrl.equals("") ? View.VISIBLE : View.GONE
        flag 3 (0x4L): viewModel.beneficiary.beneficiaryPictureUrl.equals("") ? View.VISIBLE : View.GONE
    flag mapping end*/
    //end
}