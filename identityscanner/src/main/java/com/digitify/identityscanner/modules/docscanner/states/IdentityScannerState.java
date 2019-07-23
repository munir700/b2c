package com.digitify.identityscanner.modules.docscanner.states;

import com.digitify.identityscanner.BR;
import com.digitify.identityscanner.modules.docscanner.enums.DocumentPageType;
import com.digitify.identityscanner.states.State;

import androidx.databinding.Bindable;

public class IdentityScannerState extends State {
    private boolean isLoading;
    private boolean isProcessing;
    private DocumentPageType scanMode;

    public IdentityScannerState() {
        this.isLoading = false;
        this.isProcessing = false;
        setScanMode(DocumentPageType.FRONT);
    }

    @Bindable
    public boolean isLoading() {
        return isLoading;
    }

    public void setLoading(boolean loading) {
        isLoading = loading;
        notifyPropertyChanged(BR.loading);
    }

    @Bindable
    public boolean isProcessing() {
        return isProcessing;
    }

    public void setProcessing(boolean processing) {
        isProcessing = processing;
        notifyPropertyChanged(BR.processing);
    }

    @Bindable
    public DocumentPageType getScanMode() {
        return scanMode;
    }

    public void setScanMode(DocumentPageType scanMode) {
        this.scanMode = scanMode;
        notifyPropertyChanged(BR.scanMode);
    }

    public void reset() {
        this.isLoading = false;
        this.isProcessing = false;
        setScanMode(DocumentPageType.FRONT);
    }
}
