package com.digitify.identityscanner.modules.docscanner.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.digitify.identityscanner.core.mrz.types.Gender;
import com.digitify.identityscanner.core.mrz.types.MrzDate;
import com.digitify.identityscanner.core.mrz.types.MrzFormat;

public class Identity implements Parcelable {

    private Gender gender;
    private String givenName;
    private String sirName;
    private String nationality;
    private String issuingCountry;
    /**
     * Document number, e.g. passport number.
     */
    public String documentNumber;

    /**
     * Document number, e.g. passport number.
     */
    public String citizenNumber;
    /**
     * Date of birth.
     */
    public MrzDate dateOfBirth;
    /**
     * expiration date of passport
     */
    public MrzDate expirationDate;

    /**
     * Detected MRZ format.
     */
    public MrzFormat format;

    public Identity() {
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public String getSirName() {
        return sirName;
    }

    public void setSirName(String sirName) {
        this.sirName = sirName;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getIssuingCountry() {
        return issuingCountry;
    }

    public void setIssuingCountry(String issuingCountry) {
        this.issuingCountry = issuingCountry;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public MrzDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(MrzDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public MrzDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(MrzDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public MrzFormat getFormat() {
        return format;
    }

    public void setFormat(MrzFormat format) {
        this.format = format;
    }

    public String getCitizenNumber() {
        return citizenNumber;
    }

    public void setCitizenNumber(String citizenNumber) {
        this.citizenNumber = citizenNumber;
    }

    @Override
    public String toString() {
        return "Identity{" +
                "gender=" + gender +
                ", givenName='" + givenName + '\'' +
                ", sirName='" + sirName + '\'' +
                ", nationality='" + nationality + '\'' +
                ", issuingCountry='" + issuingCountry + '\'' +
                ", documentNumber='" + documentNumber + '\'' +
                ", citizenNumber='" + citizenNumber + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", expirationDate=" + expirationDate +
                ", format=" + format +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.gender == null ? -1 : this.gender.ordinal());
        dest.writeString(this.givenName);
        dest.writeString(this.sirName);
        dest.writeString(this.nationality);
        dest.writeString(this.issuingCountry);
        dest.writeString(this.documentNumber);
        dest.writeString(this.citizenNumber);
        dest.writeSerializable(this.dateOfBirth);
        dest.writeSerializable(this.expirationDate);
        dest.writeInt(this.format == null ? -1 : this.format.ordinal());
    }

    protected Identity(Parcel in) {
        int tmpGender = in.readInt();
        this.gender = tmpGender == -1 ? null : Gender.values()[tmpGender];
        this.givenName = in.readString();
        this.sirName = in.readString();
        this.nationality = in.readString();
        this.issuingCountry = in.readString();
        this.documentNumber = in.readString();
        this.citizenNumber = in.readString();
        this.dateOfBirth = (MrzDate) in.readSerializable();
        this.expirationDate = (MrzDate) in.readSerializable();
        int tmpFormat = in.readInt();
        this.format = tmpFormat == -1 ? null : MrzFormat.values()[tmpFormat];
    }

    public static final Creator<Identity> CREATOR = new Creator<Identity>() {
        @Override
        public Identity createFromParcel(Parcel source) {
            return new Identity(source);
        }

        @Override
        public Identity[] newArray(int size) {
            return new Identity[size];
        }
    };
}
