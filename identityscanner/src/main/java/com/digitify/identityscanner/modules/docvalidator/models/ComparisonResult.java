package com.digitify.identityscanner.modules.docvalidator.models;

import android.os.Parcel;
import android.os.Parcelable;

public class ComparisonResult implements Parcelable {
    String sourceImage = "", targetImage = "";
    double confidence = 0;
    boolean matched = false;

    public String getSourceImage() {
        return sourceImage;
    }

    public void setSourceImage(String sourceImage) {
        this.sourceImage = sourceImage;
    }

    public String getTargetImage() {
        return targetImage;
    }

    public void setTargetImage(String targetImage) {
        this.targetImage = targetImage;
    }

    public double getConfidence() {
        return confidence;
    }

    public void setConfidence(double confidence) {
        this.confidence = confidence;
    }

    public boolean isMatched() {
        return matched;
    }

    public void setMatched(boolean matched) {
        this.matched = matched;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.sourceImage);
        dest.writeString(this.targetImage);
        dest.writeDouble(this.confidence);
        dest.writeByte(this.matched ? (byte) 1 : (byte) 0);
    }

    public ComparisonResult() {
    }

    protected ComparisonResult(Parcel in) {
        this.sourceImage = in.readString();
        this.targetImage = in.readString();
        this.confidence = in.readDouble();
        this.matched = in.readByte() != 0;
    }

    public static final Creator<ComparisonResult> CREATOR = new Creator<ComparisonResult>() {
        @Override
        public ComparisonResult createFromParcel(Parcel source) {
            return new ComparisonResult(source);
        }

        @Override
        public ComparisonResult[] newArray(int size) {
            return new ComparisonResult[size];
        }
    };
}
