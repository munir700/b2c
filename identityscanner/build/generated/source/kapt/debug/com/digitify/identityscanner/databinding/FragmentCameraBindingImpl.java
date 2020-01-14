package com.digitify.identityscanner.databinding;
import com.digitify.identityscanner.R;
import com.digitify.identityscanner.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class FragmentCameraBindingImpl extends FragmentCameraBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.opencvCam, 5);
        sViewsWithIds.put(R.id.drawView, 6);
        sViewsWithIds.put(R.id.cardOverlay, 7);
    }
    // views
    @NonNull
    private final androidx.constraintlayout.widget.ConstraintLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public FragmentCameraBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 8, sIncludes, sViewsWithIds));
    }
    private FragmentCameraBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 1
            , (co.yap.widgets.CoreButton) bindings[4]
            , (android.widget.TextView) bindings[1]
            , (android.widget.TextView) bindings[2]
            , (android.widget.TextView) bindings[3]
            , (com.digitify.identityscanner.components.TransparentCardView) bindings[7]
            , (com.digitify.identityscanner.modules.docscanner.components.Overlay) bindings[6]
            , (com.digitify.identityscanner.components.OpenCVCameraView) bindings[5]
            );
        this.camFab.setTag(null);
        this.camInst.setTag(null);
        this.camSteps.setTag(null);
        this.camTitle.setTag(null);
        this.mboundView0 = (androidx.constraintlayout.widget.ConstraintLayout) bindings[0];
        this.mboundView0.setTag(null);
        setRootTag(root);
        // listeners
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x80L;
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
            setModel((com.digitify.identityscanner.modules.docscanner.viewmodels.CameraViewModel) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setModel(@Nullable com.digitify.identityscanner.modules.docscanner.viewmodels.CameraViewModel Model) {
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
                return onChangeModelState((com.digitify.identityscanner.modules.docscanner.states.CameraState) object, fieldId);
        }
        return false;
    }
    private boolean onChangeModelState(com.digitify.identityscanner.modules.docscanner.states.CameraState ModelState, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x1L;
            }
            return true;
        }
        else if (fieldId == BR.instructions) {
            synchronized(this) {
                    mDirtyFlags |= 0x4L;
            }
            return true;
        }
        else if (fieldId == BR.stepInstructions) {
            synchronized(this) {
                    mDirtyFlags |= 0x8L;
            }
            return true;
        }
        else if (fieldId == BR.title) {
            synchronized(this) {
                    mDirtyFlags |= 0x10L;
            }
            return true;
        }
        else if (fieldId == BR.submitButtonTitle) {
            synchronized(this) {
                    mDirtyFlags |= 0x20L;
            }
            return true;
        }
        else if (fieldId == BR.capturing) {
            synchronized(this) {
                    mDirtyFlags |= 0x40L;
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
        com.digitify.identityscanner.modules.docscanner.viewmodels.CameraViewModel model = mModel;
        java.lang.String modelStateTitle = null;
        java.lang.String modelStateSubmitButtonTitle = null;
        boolean textUtilsIsEmptyModelStateInstructions = false;
        boolean modelStateCapturing = false;
        boolean ModelStateCapturing1 = false;
        java.lang.String modelStateInstructions = null;
        int textUtilsIsEmptyModelStateInstructionsViewGONEViewVISIBLE = 0;
        java.lang.String modelStateStepInstructions = null;
        com.digitify.identityscanner.modules.docscanner.states.CameraState modelState = null;

        if ((dirtyFlags & 0xffL) != 0) {



                if (model != null) {
                    // read model.state
                    modelState = model.getState();
                }
                updateRegistration(0, modelState);

            if ((dirtyFlags & 0x93L) != 0) {

                    if (modelState != null) {
                        // read model.state.title
                        modelStateTitle = modelState.getTitle();
                    }
            }
            if ((dirtyFlags & 0xa3L) != 0) {

                    if (modelState != null) {
                        // read model.state.submitButtonTitle
                        modelStateSubmitButtonTitle = modelState.getSubmitButtonTitle();
                    }
            }
            if ((dirtyFlags & 0xc3L) != 0) {

                    if (modelState != null) {
                        // read model.state.capturing
                        modelStateCapturing = modelState.isCapturing();
                    }


                    // read !model.state.capturing
                    ModelStateCapturing1 = !modelStateCapturing;
            }
            if ((dirtyFlags & 0x87L) != 0) {

                    if (modelState != null) {
                        // read model.state.instructions
                        modelStateInstructions = modelState.getInstructions();
                    }


                    // read TextUtils.isEmpty(model.state.instructions)
                    textUtilsIsEmptyModelStateInstructions = android.text.TextUtils.isEmpty(modelStateInstructions);
                if((dirtyFlags & 0x87L) != 0) {
                    if(textUtilsIsEmptyModelStateInstructions) {
                            dirtyFlags |= 0x200L;
                    }
                    else {
                            dirtyFlags |= 0x100L;
                    }
                }


                    // read TextUtils.isEmpty(model.state.instructions) ? View.GONE : View.VISIBLE
                    textUtilsIsEmptyModelStateInstructionsViewGONEViewVISIBLE = ((textUtilsIsEmptyModelStateInstructions) ? (android.view.View.GONE) : (android.view.View.VISIBLE));
            }
            if ((dirtyFlags & 0x8bL) != 0) {

                    if (modelState != null) {
                        // read model.state.stepInstructions
                        modelStateStepInstructions = modelState.getStepInstructions();
                    }
            }
        }
        // batch finished
        if ((dirtyFlags & 0xa3L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.camFab, modelStateSubmitButtonTitle);
        }
        if ((dirtyFlags & 0xc3L) != 0) {
            // api target 1

            this.camFab.setClickable(ModelStateCapturing1);
        }
        if ((dirtyFlags & 0x87L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.camInst, modelStateInstructions);
            this.camInst.setVisibility(textUtilsIsEmptyModelStateInstructionsViewGONEViewVISIBLE);
        }
        if ((dirtyFlags & 0x8bL) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.camSteps, modelStateStepInstructions);
        }
        if ((dirtyFlags & 0x93L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.camTitle, modelStateTitle);
        }
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): model.state
        flag 1 (0x2L): model
        flag 2 (0x3L): model.state.instructions
        flag 3 (0x4L): model.state.stepInstructions
        flag 4 (0x5L): model.state.title
        flag 5 (0x6L): model.state.submitButtonTitle
        flag 6 (0x7L): model.state.capturing
        flag 7 (0x8L): null
        flag 8 (0x9L): TextUtils.isEmpty(model.state.instructions) ? View.GONE : View.VISIBLE
        flag 9 (0xaL): TextUtils.isEmpty(model.state.instructions) ? View.GONE : View.VISIBLE
    flag mapping end*/
    //end
}