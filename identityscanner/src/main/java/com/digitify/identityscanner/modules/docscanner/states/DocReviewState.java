package com.digitify.identityscanner.modules.docscanner.states;

import android.graphics.Bitmap;

import com.digitify.identityscanner.BR;
import com.digitify.identityscanner.states.State;

import androidx.databinding.Bindable;

public class DocReviewState extends State {

    private String reviewText;
    private boolean docValid, loading;
    private Bitmap previewBitmap;

    @Override
    public void reset() {
        reviewText = "";
        docValid = false;
        previewBitmap = null;
    }

    @Bindable
    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
        notifyPropertyChanged(BR.reviewText);
    }

    @Bindable
    public boolean isDocValid() {
        return docValid;
    }

    public void setDocValid(boolean docValid) {
        this.docValid = docValid;
        notifyPropertyChanged(BR.docValid);
    }

    @Bindable
    public Bitmap getPreviewBitmap() {
        return previewBitmap;
    }

    public void setPreviewBitmap(Bitmap previewBitmap) {
        this.previewBitmap = previewBitmap;
        notifyPropertyChanged(BR.previewBitmap);
    }

    @Bindable
    public boolean isLoading() {
        return loading;
    }

    public void setLoading(boolean loading) {
        this.loading = loading;
        notifyPropertyChanged(BR.loading);
    }
}
