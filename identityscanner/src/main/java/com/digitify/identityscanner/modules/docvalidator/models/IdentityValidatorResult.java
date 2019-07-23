package com.digitify.identityscanner.modules.docvalidator.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.digitify.identityscanner.models.Error;

import java.util.HashMap;

public class IdentityValidatorResult implements Parcelable {

    /**
     * ValidationsResult performed on video
     */
    private HashMap<String, Boolean> validations;

    /**
     * File path of the captured video
     */
    private String videoPath = "";

    /**
     * Object contains the data resulted by the comparison of face images.
     */
    private ComparisonResult comparison;

    private Error error;

    public IdentityValidatorResult() {
        validations = new HashMap<>();
    }

    public Error getError() {
        return error;
    }

    public void setError(Error error) {
        this.error = error;
    }

    public HashMap<String, Boolean> getValidations() {
        if (validations == null) validations = new HashMap<>();
        return validations;
    }

    public void setValidation(String validation, boolean value) {
        getValidations().put(validation, value);
    }

    public void removeValidation(String validation) {
        getValidations().remove(validation);
    }

    public String getVideoPath() {
        return videoPath;
    }

    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
    }

    public ComparisonResult getComparison() {
        if (comparison == null) comparison = new ComparisonResult();
        return comparison;
    }

    public void setComparison(ComparisonResult comparison) {
        this.comparison = comparison;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeSerializable(this.validations);
        dest.writeString(this.videoPath);
        dest.writeParcelable(this.comparison, flags);
        dest.writeParcelable(this.error, flags);
    }

    protected IdentityValidatorResult(Parcel in) {
        this.validations = (HashMap<String, Boolean>) in.readSerializable();
        this.videoPath = in.readString();
        this.comparison = in.readParcelable(ComparisonResult.class.getClassLoader());
        this.error = in.readParcelable(Error.class.getClassLoader());
    }

    public static final Creator<IdentityValidatorResult> CREATOR = new Creator<IdentityValidatorResult>() {
        @Override
        public IdentityValidatorResult createFromParcel(Parcel source) {
            return new IdentityValidatorResult(source);
        }

        @Override
        public IdentityValidatorResult[] newArray(int size) {
            return new IdentityValidatorResult[size];
        }
    };
}
