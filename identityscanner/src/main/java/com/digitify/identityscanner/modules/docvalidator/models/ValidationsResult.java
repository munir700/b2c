package com.digitify.identityscanner.modules.docvalidator.models;

import android.os.Parcel;
import android.os.Parcelable;

public class ValidationsResult implements Parcelable {

    boolean smileValidated = false;
    boolean eyesValidated = false;
    boolean faceValidated = false;


    public boolean isSmileValidated() {
        return smileValidated;
    }

    public void setSmileValidated(boolean smileValidated) {
        this.smileValidated = smileValidated;
    }

    public boolean isEyesValidated() {
        return eyesValidated;
    }

    public void setEyesValidated(boolean eyesValidated) {
        this.eyesValidated = eyesValidated;
    }

    public boolean isFaceValidated() {
        return faceValidated;
    }

    public void setFaceValidated(boolean faceValidated) {
        this.faceValidated = faceValidated;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(this.smileValidated ? (byte) 1 : (byte) 0);
        dest.writeByte(this.eyesValidated ? (byte) 1 : (byte) 0);
        dest.writeByte(this.faceValidated ? (byte) 1 : (byte) 0);
    }

    public ValidationsResult() {
    }

    protected ValidationsResult(Parcel in) {
        this.smileValidated = in.readByte() != 0;
        this.eyesValidated = in.readByte() != 0;
        this.faceValidated = in.readByte() != 0;
    }

    public static final Parcelable.Creator<ValidationsResult> CREATOR = new Parcelable.Creator<ValidationsResult>() {
        @Override
        public ValidationsResult createFromParcel(Parcel source) {
            return new ValidationsResult(source);
        }

        @Override
        public ValidationsResult[] newArray(int size) {
            return new ValidationsResult[size];
        }
    };
}
