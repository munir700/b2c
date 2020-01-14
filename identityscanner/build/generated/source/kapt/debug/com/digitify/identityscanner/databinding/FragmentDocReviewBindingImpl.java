package com.digitify.identityscanner.databinding;
import com.digitify.identityscanner.R;
import com.digitify.identityscanner.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class FragmentDocReviewBindingImpl extends FragmentDocReviewBinding implements com.digitify.identityscanner.generated.callback.OnClickListener.Listener {

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
    @NonNull
    private final co.yap.widgets.CoreButton mboundView4;
    @NonNull
    private final co.yap.widgets.CoreButton mboundView5;
    // variables
    @Nullable
    private final android.view.View.OnClickListener mCallback1;
    @Nullable
    private final android.view.View.OnClickListener mCallback2;
    // values
    // listeners
    // Inverse Binding Event Handlers

    public FragmentDocReviewBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 6, sIncludes, sViewsWithIds));
    }
    private FragmentDocReviewBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 1
            , (android.widget.LinearLayout) bindings[2]
            , (android.widget.ImageView) bindings[1]
            , (android.widget.TextView) bindings[3]
            );
        this.linearLayout4.setTag(null);
        this.mboundView0 = (androidx.constraintlayout.widget.ConstraintLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.mboundView4 = (co.yap.widgets.CoreButton) bindings[4];
        this.mboundView4.setTag(null);
        this.mboundView5 = (co.yap.widgets.CoreButton) bindings[5];
        this.mboundView5.setTag(null);
        this.quickDocPreview.setTag(null);
        this.reviewText.setTag(null);
        setRootTag(root);
        // listeners
        mCallback1 = new com.digitify.identityscanner.generated.callback.OnClickListener(this, 1);
        mCallback2 = new com.digitify.identityscanner.generated.callback.OnClickListener(this, 2);
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x40L;
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
        if (BR.model == variableId) {
            setModel((com.digitify.identityscanner.modules.docscanner.viewmodels.DocReviewViewModel) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setModel(@Nullable com.digitify.identityscanner.modules.docscanner.viewmodels.DocReviewViewModel Model) {
        this.mModel = Model;
        synchronized(this) {
            mDirtyFlags |= 0x2L;
        }
        notifyPropertyChanged(BR.model);
        super.requestRebind();
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
            case 0 :
                return onChangeModelState((com.digitify.identityscanner.modules.docscanner.states.DocReviewState) object, fieldId);
        }
        return false;
    }
    private boolean onChangeModelState(com.digitify.identityscanner.modules.docscanner.states.DocReviewState ModelState, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x1L;
            }
            return true;
        }
        else if (fieldId == BR.previewBitmap) {
            synchronized(this) {
                    mDirtyFlags |= 0x4L;
            }
            return true;
        }
        else if (fieldId == BR.loading) {
            synchronized(this) {
                    mDirtyFlags |= 0x8L;
            }
            return true;
        }
        else if (fieldId == BR.reviewText) {
            synchronized(this) {
                    mDirtyFlags |= 0x10L;
            }
            return true;
        }
        else if (fieldId == BR.docValid) {
            synchronized(this) {
                    mDirtyFlags |= 0x20L;
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
        com.digitify.identityscanner.modules.docscanner.viewmodels.DocReviewViewModel model = mModel;
        boolean modelStateLoading = false;
        boolean modelStateDocValid = false;
        android.graphics.Bitmap modelStatePreviewBitmap = null;
        int modelStateLoadingViewGONEViewVISIBLE = 0;
        com.digitify.identityscanner.modules.docscanner.states.DocReviewState modelState = null;
        java.lang.String modelStateReviewText = null;
        int modelStateDocValidViewVISIBLEViewGONE = 0;

        if ((dirtyFlags & 0x7fL) != 0) {



                if (model != null) {
                    // read model.state
                    modelState = model.getState();
                }
                updateRegistration(0, modelState);

            if ((dirtyFlags & 0x4bL) != 0) {

                    if (modelState != null) {
                        // read model.state.loading
                        modelStateLoading = modelState.isLoading();
                    }
                if((dirtyFlags & 0x4bL) != 0) {
                    if(modelStateLoading) {
                            dirtyFlags |= 0x100L;
                    }
                    else {
                            dirtyFlags |= 0x80L;
                    }
                }


                    // read model.state.loading ? View.GONE : View.VISIBLE
                    modelStateLoadingViewGONEViewVISIBLE = ((modelStateLoading) ? (android.view.View.GONE) : (android.view.View.VISIBLE));
            }
            if ((dirtyFlags & 0x63L) != 0) {

                    if (modelState != null) {
                        // read model.state.docValid
                        modelStateDocValid = modelState.isDocValid();
                    }
                if((dirtyFlags & 0x63L) != 0) {
                    if(modelStateDocValid) {
                            dirtyFlags |= 0x400L;
                    }
                    else {
                            dirtyFlags |= 0x200L;
                    }
                }


                    // read model.state.docValid ? View.VISIBLE : View.GONE
                    modelStateDocValidViewVISIBLEViewGONE = ((modelStateDocValid) ? (android.view.View.VISIBLE) : (android.view.View.GONE));
            }
            if ((dirtyFlags & 0x47L) != 0) {

                    if (modelState != null) {
                        // read model.state.previewBitmap
                        modelStatePreviewBitmap = modelState.getPreviewBitmap();
                    }
            }
            if ((dirtyFlags & 0x53L) != 0) {

                    if (modelState != null) {
                        // read model.state.reviewText
                        modelStateReviewText = modelState.getReviewText();
                    }
            }
        }
        // batch finished
        if ((dirtyFlags & 0x4bL) != 0) {
            // api target 1

            this.linearLayout4.setVisibility(modelStateLoadingViewGONEViewVISIBLE);
        }
        if ((dirtyFlags & 0x40L) != 0) {
            // api target 1

            this.mboundView4.setOnClickListener(mCallback1);
            this.mboundView5.setOnClickListener(mCallback2);
        }
        if ((dirtyFlags & 0x63L) != 0) {
            // api target 1

            this.mboundView5.setVisibility(modelStateDocValidViewVISIBLEViewGONE);
        }
        if ((dirtyFlags & 0x47L) != 0) {
            // api target 1

            com.digitify.identityscanner.utils.AppUIBinder.setImageBitmap(this.quickDocPreview, modelStatePreviewBitmap);
        }
        if ((dirtyFlags & 0x53L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.reviewText, modelStateReviewText);
        }
    }
    // Listener Stub Implementations
    // callback impls
    public final void _internalCallbackOnClick(int sourceId , android.view.View callbackArg_0) {
        switch(sourceId) {
            case 1: {
                // localize variables for thread safety
                // model
                com.digitify.identityscanner.modules.docscanner.viewmodels.DocReviewViewModel model = mModel;
                // model != null
                boolean modelJavaLangObjectNull = false;



                modelJavaLangObjectNull = (model) != (null);
                if (modelJavaLangObjectNull) {


                    model.handleClickOnRetake();
                }
                break;
            }
            case 2: {
                // localize variables for thread safety
                // model
                com.digitify.identityscanner.modules.docscanner.viewmodels.DocReviewViewModel model = mModel;
                // model != null
                boolean modelJavaLangObjectNull = false;



                modelJavaLangObjectNull = (model) != (null);
                if (modelJavaLangObjectNull) {


                    model.handleClickOnDone();
                }
                break;
            }
        }
    }
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): model.state
        flag 1 (0x2L): model
        flag 2 (0x3L): model.state.previewBitmap
        flag 3 (0x4L): model.state.loading
        flag 4 (0x5L): model.state.reviewText
        flag 5 (0x6L): model.state.docValid
        flag 6 (0x7L): null
        flag 7 (0x8L): model.state.loading ? View.GONE : View.VISIBLE
        flag 8 (0x9L): model.state.loading ? View.GONE : View.VISIBLE
        flag 9 (0xaL): model.state.docValid ? View.VISIBLE : View.GONE
        flag 10 (0xbL): model.state.docValid ? View.VISIBLE : View.GONE
    flag mapping end*/
    //end
}