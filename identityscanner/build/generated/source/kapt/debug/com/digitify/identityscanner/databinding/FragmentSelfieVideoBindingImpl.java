package com.digitify.identityscanner.databinding;
import com.digitify.identityscanner.R;
import com.digitify.identityscanner.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class FragmentSelfieVideoBindingImpl extends FragmentSelfieVideoBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.opencvCam, 7);
        sViewsWithIds.put(R.id.linearLayout, 8);
        sViewsWithIds.put(R.id.record, 9);
    }
    // views
    @NonNull
    private final androidx.constraintlayout.widget.ConstraintLayout mboundView0;
    @NonNull
    private final android.widget.LinearLayout mboundView6;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public FragmentSelfieVideoBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 10, sIncludes, sViewsWithIds));
    }
    private FragmentSelfieVideoBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 1
            , (android.widget.TextView) bindings[3]
            , (android.widget.TextView) bindings[2]
            , (android.widget.ImageView) bindings[1]
            , (android.widget.LinearLayout) bindings[8]
            , (com.digitify.identityscanner.components.OpenCVCameraView) bindings[7]
            , (com.digitify.identityscanner.components.RecordButton) bindings[9]
            , (android.widget.TextView) bindings[4]
            , (android.widget.TextView) bindings[5]
            );
        this.blinkEyes.setTag(null);
        this.clearFace.setTag(null);
        this.faceView.setTag(null);
        this.mboundView0 = (androidx.constraintlayout.widget.ConstraintLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.mboundView6 = (android.widget.LinearLayout) bindings[6];
        this.mboundView6.setTag(null);
        this.smile.setTag(null);
        this.textView.setTag(null);
        setRootTag(root);
        // listeners
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x100L;
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
            setModel((com.digitify.identityscanner.modules.docvalidator.viewmodels.SelfieVideoViewModel) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setModel(@Nullable com.digitify.identityscanner.modules.docvalidator.viewmodels.SelfieVideoViewModel Model) {
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
                return onChangeModelState((com.digitify.identityscanner.modules.docvalidator.states.SelfieVideoState) object, fieldId);
        }
        return false;
    }
    private boolean onChangeModelState(com.digitify.identityscanner.modules.docvalidator.states.SelfieVideoState ModelState, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x1L;
            }
            return true;
        }
        else if (fieldId == BR.faceBitmap) {
            synchronized(this) {
                    mDirtyFlags |= 0x4L;
            }
            return true;
        }
        else if (fieldId == BR.faceValidationText) {
            synchronized(this) {
                    mDirtyFlags |= 0x8L;
            }
            return true;
        }
        else if (fieldId == BR.blinkValidationText) {
            synchronized(this) {
                    mDirtyFlags |= 0x10L;
            }
            return true;
        }
        else if (fieldId == BR.voiceValidationText) {
            synchronized(this) {
                    mDirtyFlags |= 0x20L;
            }
            return true;
        }
        else if (fieldId == BR.flashMessage) {
            synchronized(this) {
                    mDirtyFlags |= 0x40L;
            }
            return true;
        }
        else if (fieldId == BR.loading) {
            synchronized(this) {
                    mDirtyFlags |= 0x80L;
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
        com.digitify.identityscanner.modules.docvalidator.viewmodels.SelfieVideoViewModel model = mModel;
        java.lang.String modelStateFaceValidationText = null;
        com.digitify.identityscanner.modules.docvalidator.states.SelfieVideoState modelState = null;
        java.lang.String modelStateFlashMessage = null;
        android.graphics.Bitmap modelStateFaceBitmap = null;
        int modelStateLoadingViewVISIBLEViewGONE = 0;
        java.lang.String modelStateVoiceValidationText = null;
        boolean modelStateLoading = false;
        java.lang.String modelStateBlinkValidationText = null;

        if ((dirtyFlags & 0x1ffL) != 0) {



                if (model != null) {
                    // read model.state
                    modelState = model.getState();
                }
                updateRegistration(0, modelState);

            if ((dirtyFlags & 0x10bL) != 0) {

                    if (modelState != null) {
                        // read model.state.faceValidationText
                        modelStateFaceValidationText = modelState.getFaceValidationText();
                    }
            }
            if ((dirtyFlags & 0x143L) != 0) {

                    if (modelState != null) {
                        // read model.state.flashMessage
                        modelStateFlashMessage = modelState.getFlashMessage();
                    }
            }
            if ((dirtyFlags & 0x107L) != 0) {

                    if (modelState != null) {
                        // read model.state.faceBitmap
                        modelStateFaceBitmap = modelState.getFaceBitmap();
                    }
            }
            if ((dirtyFlags & 0x123L) != 0) {

                    if (modelState != null) {
                        // read model.state.voiceValidationText
                        modelStateVoiceValidationText = modelState.getVoiceValidationText();
                    }
            }
            if ((dirtyFlags & 0x183L) != 0) {

                    if (modelState != null) {
                        // read model.state.loading
                        modelStateLoading = modelState.isLoading();
                    }
                if((dirtyFlags & 0x183L) != 0) {
                    if(modelStateLoading) {
                            dirtyFlags |= 0x400L;
                    }
                    else {
                            dirtyFlags |= 0x200L;
                    }
                }


                    // read model.state.loading ? View.VISIBLE : View.GONE
                    modelStateLoadingViewVISIBLEViewGONE = ((modelStateLoading) ? (android.view.View.VISIBLE) : (android.view.View.GONE));
            }
            if ((dirtyFlags & 0x113L) != 0) {

                    if (modelState != null) {
                        // read model.state.blinkValidationText
                        modelStateBlinkValidationText = modelState.getBlinkValidationText();
                    }
            }
        }
        // batch finished
        if ((dirtyFlags & 0x113L) != 0) {
            // api target 1

            com.digitify.identityscanner.utils.AppUIBinder.setPrePostText(this.blinkEyes, modelStateBlinkValidationText, blinkEyes.getResources().getString(R.string.validation_blink_eyes));
        }
        if ((dirtyFlags & 0x10bL) != 0) {
            // api target 1

            com.digitify.identityscanner.utils.AppUIBinder.setPrePostText(this.clearFace, modelStateFaceValidationText, clearFace.getResources().getString(R.string.validation_clear_face));
        }
        if ((dirtyFlags & 0x107L) != 0) {
            // api target 1

            com.digitify.identityscanner.utils.AppUIBinder.setImageBitmap(this.faceView, modelStateFaceBitmap);
        }
        if ((dirtyFlags & 0x183L) != 0) {
            // api target 1

            this.mboundView6.setVisibility(modelStateLoadingViewVISIBLEViewGONE);
        }
        if ((dirtyFlags & 0x123L) != 0) {
            // api target 1

            com.digitify.identityscanner.utils.AppUIBinder.setPrePostText(this.smile, modelStateVoiceValidationText, smile.getResources().getString(R.string.validation_voice));
        }
        if ((dirtyFlags & 0x143L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.textView, modelStateFlashMessage);
        }
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): model.state
        flag 1 (0x2L): model
        flag 2 (0x3L): model.state.faceBitmap
        flag 3 (0x4L): model.state.faceValidationText
        flag 4 (0x5L): model.state.blinkValidationText
        flag 5 (0x6L): model.state.voiceValidationText
        flag 6 (0x7L): model.state.flashMessage
        flag 7 (0x8L): model.state.loading
        flag 8 (0x9L): null
        flag 9 (0xaL): model.state.loading ? View.VISIBLE : View.GONE
        flag 10 (0xbL): model.state.loading ? View.VISIBLE : View.GONE
    flag mapping end*/
    //end
}