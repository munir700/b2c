package co.yap.sendmoney.databinding;
import co.yap.sendmoney.R;
import co.yap.sendmoney.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class LayoutItemBeneficiaryBindingImpl extends LayoutItemBeneficiaryBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.swipe, 8);
        sViewsWithIds.put(R.id.btnEdit, 9);
        sViewsWithIds.put(R.id.btnDelete, 10);
        sViewsWithIds.put(R.id.foregroundContainer, 11);
        sViewsWithIds.put(R.id.lyUserImage, 12);
        sViewsWithIds.put(R.id.content, 13);
        sViewsWithIds.put(R.id.section, 14);
    }
    // views
    @NonNull
    private final android.widget.RelativeLayout mboundView0;
    @NonNull
    private final android.widget.TextView mboundView1;
    @NonNull
    private final android.widget.TextView mboundView2;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public LayoutItemBeneficiaryBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 15, sIncludes, sViewsWithIds));
    }
    private LayoutItemBeneficiaryBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.RelativeLayout) bindings[10]
            , (android.widget.RelativeLayout) bindings[9]
            , (android.widget.LinearLayout) bindings[13]
            , (androidx.appcompat.widget.AppCompatImageView) bindings[7]
            , (androidx.constraintlayout.widget.ConstraintLayout) bindings[11]
            , (co.yap.widgets.CoreCircularImageView) bindings[3]
            , (android.widget.ImageView) bindings[6]
            , (androidx.constraintlayout.widget.ConstraintLayout) bindings[12]
            , (android.widget.LinearLayout) bindings[14]
            , (android.widget.LinearLayout) bindings[8]
            , (android.widget.TextView) bindings[5]
            , (android.widget.TextView) bindings[4]
            );
        this.flagImg.setTag(null);
        this.imgProfile.setTag(null);
        this.ivTransferType.setTag(null);
        this.mboundView0 = (android.widget.RelativeLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.mboundView1 = (android.widget.TextView) bindings[1];
        this.mboundView1.setTag(null);
        this.mboundView2 = (android.widget.TextView) bindings[2];
        this.mboundView2.setTag(null);
        this.tvFullName.setTag(null);
        this.tvName.setTag(null);
        setRootTag(root);
        // listeners
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x4L;
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
        if (BR.dataCountry == variableId) {
            setDataCountry((co.yap.countryutils.country.Country) variable);
        }
        else if (BR.viewModel == variableId) {
            setViewModel((co.yap.sendmoney.home.adapters.BeneficiaryItemViewModel) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setDataCountry(@Nullable co.yap.countryutils.country.Country DataCountry) {
        this.mDataCountry = DataCountry;
    }
    public void setViewModel(@Nullable co.yap.sendmoney.home.adapters.BeneficiaryItemViewModel ViewModel) {
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
        java.lang.String viewModelBeneficiaryFullName = null;
        java.lang.String viewModelBeneficiaryBeneficiaryPictureUrl = null;
        java.lang.String viewModelBeneficiaryCountry = null;
        int viewModelPosition = 0;
        java.lang.String viewModelBeneficiaryTitle = null;
        co.yap.networking.customers.responsedtos.sendmoney.Beneficiary viewModelBeneficiary = null;
        boolean viewModelBeneficiaryBeneficiaryTypeEqualsJavaLangStringCASHPAYOUT = false;
        int viewModelBeneficiaryBeneficiaryTypeEqualsJavaLangStringCASHPAYOUTIvTransferTypeAndroidColorGreyDarkIvTransferTypeAndroidColorGreyDark = 0;
        co.yap.sendmoney.home.adapters.BeneficiaryItemViewModel viewModel = mViewModel;
        java.lang.String viewModelBeneficiaryBeneficiaryType = null;

        if ((dirtyFlags & 0x6L) != 0) {



                if (viewModel != null) {
                    // read viewModel.position
                    viewModelPosition = viewModel.getPosition();
                    // read viewModel.beneficiary
                    viewModelBeneficiary = viewModel.getBeneficiary();
                }


                if (viewModelBeneficiary != null) {
                    // read viewModel.beneficiary.fullName()
                    viewModelBeneficiaryFullName = viewModelBeneficiary.fullName();
                    // read viewModel.beneficiary.beneficiaryPictureUrl
                    viewModelBeneficiaryBeneficiaryPictureUrl = viewModelBeneficiary.getBeneficiaryPictureUrl();
                    // read viewModel.beneficiary.country
                    viewModelBeneficiaryCountry = viewModelBeneficiary.getCountry();
                    // read viewModel.beneficiary.title
                    viewModelBeneficiaryTitle = viewModelBeneficiary.getTitle();
                    // read viewModel.beneficiary.beneficiaryType
                    viewModelBeneficiaryBeneficiaryType = viewModelBeneficiary.getBeneficiaryType();
                }


                if (viewModelBeneficiaryBeneficiaryType != null) {
                    // read viewModel.beneficiary.beneficiaryType.equals("CASHPAYOUT")
                    viewModelBeneficiaryBeneficiaryTypeEqualsJavaLangStringCASHPAYOUT = viewModelBeneficiaryBeneficiaryType.equals("CASHPAYOUT");
                }
            if((dirtyFlags & 0x6L) != 0) {
                if(viewModelBeneficiaryBeneficiaryTypeEqualsJavaLangStringCASHPAYOUT) {
                        dirtyFlags |= 0x10L;
                }
                else {
                        dirtyFlags |= 0x8L;
                }
            }


                // read viewModel.beneficiary.beneficiaryType.equals("CASHPAYOUT") ? @android:color/greyDark : @android:color/greyDark
                viewModelBeneficiaryBeneficiaryTypeEqualsJavaLangStringCASHPAYOUTIvTransferTypeAndroidColorGreyDarkIvTransferTypeAndroidColorGreyDark = ((viewModelBeneficiaryBeneficiaryTypeEqualsJavaLangStringCASHPAYOUT) ? (getColorFromResource(ivTransferType, R.color.greyDark)) : (getColorFromResource(ivTransferType, R.color.greyDark)));
        }
        // batch finished
        if ((dirtyFlags & 0x6L) != 0) {
            // api target 1

            co.yap.yapcore.helpers.ImageBinding.setIsoCountryDrawable(this.flagImg, viewModelBeneficiaryCountry);
            co.yap.yapcore.helpers.ImageBinding.loadAvatar(this.imgProfile, viewModelBeneficiaryBeneficiaryPictureUrl, viewModelBeneficiaryFullName, viewModelPosition, "Beneficiary");
            co.yap.yapcore.binders.UIBinder.setImageSrc(this.ivTransferType, viewModelBeneficiaryBeneficiaryType);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.tvFullName, viewModelBeneficiaryFullName);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.tvName, viewModelBeneficiaryTitle);
            // api target 21
            if(getBuildSdkInt() >= 21) {

                this.ivTransferType.setImageTintList(androidx.databinding.adapters.Converters.convertColorToColorStateList(viewModelBeneficiaryBeneficiaryTypeEqualsJavaLangStringCASHPAYOUTIvTransferTypeAndroidColorGreyDarkIvTransferTypeAndroidColorGreyDark));
            }
        }
        if ((dirtyFlags & 0x4L) != 0) {
            // api target 1

            co.yap.yapcore.binders.UIBinder.setText(this.mboundView1, co.yap.translation.Strings.screen_send_money_display_text_edit);
            co.yap.yapcore.binders.UIBinder.setText(this.mboundView2, co.yap.translation.Strings.screen_send_money_display_text_delete);
        }
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): dataCountry
        flag 1 (0x2L): viewModel
        flag 2 (0x3L): null
        flag 3 (0x4L): viewModel.beneficiary.beneficiaryType.equals("CASHPAYOUT") ? @android:color/greyDark : @android:color/greyDark
        flag 4 (0x5L): viewModel.beneficiary.beneficiaryType.equals("CASHPAYOUT") ? @android:color/greyDark : @android:color/greyDark
    flag mapping end*/
    //end
}