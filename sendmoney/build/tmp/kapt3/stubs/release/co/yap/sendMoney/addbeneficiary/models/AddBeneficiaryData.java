package co.yap.sendmoney.addbeneficiary.models;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u001e\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B?\u0012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\t\u0012\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u000b\u00a2\u0006\u0002\u0010\fJ\u000b\u0010!\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\t\u0010\"\u001a\u00020\u0005H\u00c6\u0003J\u000b\u0010#\u001a\u0004\u0018\u00010\u0007H\u00c6\u0003J\u000b\u0010$\u001a\u0004\u0018\u00010\tH\u00c6\u0003J\u000b\u0010%\u001a\u0004\u0018\u00010\u000bH\u00c6\u0003JC\u0010&\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00072\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\t2\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u000bH\u00c6\u0001J\u0013\u0010\'\u001a\u00020\u00052\b\u0010(\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010)\u001a\u00020*H\u00d6\u0001J\t\u0010+\u001a\u00020,H\u00d6\u0001R\u001c\u0010\b\u001a\u0004\u0018\u00010\tX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R\u001c\u0010\n\u001a\u0004\u0018\u00010\u000bX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014R\u001c\u0010\u0002\u001a\u0004\u0018\u00010\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\u0016\"\u0004\b\u0017\u0010\u0018R\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\u001a\"\u0004\b\u001b\u0010\u001cR\u001c\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001d\u0010\u001e\"\u0004\b\u001f\u0010 \u00a8\u0006-"}, d2 = {"Lco/yap/sendmoney/addbeneficiary/models/AddBeneficiaryData;", "", "country", "Lco/yap/countryutils/country/Country;", "localBeneficiary", "", "transferType", "Lco/yap/sendmoney/addbeneficiary/models/MoneyTransferType;", "beneficiaryAccount", "Lco/yap/sendmoney/addbeneficiary/models/BeneficiaryAccount;", "beneficiaryBank", "Lco/yap/sendmoney/addbeneficiary/models/BeneficiaryBank;", "(Lco/yap/countryutils/country/Country;ZLco/yap/sendmoney/addbeneficiary/models/MoneyTransferType;Lco/yap/sendmoney/addbeneficiary/models/BeneficiaryAccount;Lco/yap/sendmoney/addbeneficiary/models/BeneficiaryBank;)V", "getBeneficiaryAccount", "()Lco/yap/sendmoney/addbeneficiary/models/BeneficiaryAccount;", "setBeneficiaryAccount", "(Lco/yap/sendmoney/addbeneficiary/models/BeneficiaryAccount;)V", "getBeneficiaryBank", "()Lco/yap/sendmoney/addbeneficiary/models/BeneficiaryBank;", "setBeneficiaryBank", "(Lco/yap/sendmoney/addbeneficiary/models/BeneficiaryBank;)V", "getCountry", "()Lco/yap/countryutils/country/Country;", "setCountry", "(Lco/yap/countryutils/country/Country;)V", "getLocalBeneficiary", "()Z", "setLocalBeneficiary", "(Z)V", "getTransferType", "()Lco/yap/sendmoney/addbeneficiary/models/MoneyTransferType;", "setTransferType", "(Lco/yap/sendmoney/addbeneficiary/models/MoneyTransferType;)V", "component1", "component2", "component3", "component4", "component5", "copy", "equals", "other", "hashCode", "", "toString", "", "sendmoney_release"})
public final class AddBeneficiaryData {
    @org.jetbrains.annotations.Nullable()
    private co.yap.countryutils.country.Country country;
    private boolean localBeneficiary;
    @org.jetbrains.annotations.Nullable()
    private co.yap.sendmoney.addbeneficiary.models.MoneyTransferType transferType;
    @org.jetbrains.annotations.Nullable()
    private co.yap.sendmoney.addbeneficiary.models.BeneficiaryAccount beneficiaryAccount;
    @org.jetbrains.annotations.Nullable()
    private co.yap.sendmoney.addbeneficiary.models.BeneficiaryBank beneficiaryBank;
    
    @org.jetbrains.annotations.Nullable()
    public final co.yap.countryutils.country.Country getCountry() {
        return null;
    }
    
    public final void setCountry(@org.jetbrains.annotations.Nullable()
    co.yap.countryutils.country.Country p0) {
    }
    
    public final boolean getLocalBeneficiary() {
        return false;
    }
    
    public final void setLocalBeneficiary(boolean p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final co.yap.sendmoney.addbeneficiary.models.MoneyTransferType getTransferType() {
        return null;
    }
    
    public final void setTransferType(@org.jetbrains.annotations.Nullable()
    co.yap.sendmoney.addbeneficiary.models.MoneyTransferType p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final co.yap.sendmoney.addbeneficiary.models.BeneficiaryAccount getBeneficiaryAccount() {
        return null;
    }
    
    public final void setBeneficiaryAccount(@org.jetbrains.annotations.Nullable()
    co.yap.sendmoney.addbeneficiary.models.BeneficiaryAccount p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final co.yap.sendmoney.addbeneficiary.models.BeneficiaryBank getBeneficiaryBank() {
        return null;
    }
    
    public final void setBeneficiaryBank(@org.jetbrains.annotations.Nullable()
    co.yap.sendmoney.addbeneficiary.models.BeneficiaryBank p0) {
    }
    
    public AddBeneficiaryData(@org.jetbrains.annotations.Nullable()
    co.yap.countryutils.country.Country country, boolean localBeneficiary, @org.jetbrains.annotations.Nullable()
    co.yap.sendmoney.addbeneficiary.models.MoneyTransferType transferType, @org.jetbrains.annotations.Nullable()
    co.yap.sendmoney.addbeneficiary.models.BeneficiaryAccount beneficiaryAccount, @org.jetbrains.annotations.Nullable()
    co.yap.sendmoney.addbeneficiary.models.BeneficiaryBank beneficiaryBank) {
        super();
    }
    
    public AddBeneficiaryData() {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final co.yap.countryutils.country.Country component1() {
        return null;
    }
    
    public final boolean component2() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final co.yap.sendmoney.addbeneficiary.models.MoneyTransferType component3() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final co.yap.sendmoney.addbeneficiary.models.BeneficiaryAccount component4() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final co.yap.sendmoney.addbeneficiary.models.BeneficiaryBank component5() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final co.yap.sendmoney.addbeneficiary.models.AddBeneficiaryData copy(@org.jetbrains.annotations.Nullable()
    co.yap.countryutils.country.Country country, boolean localBeneficiary, @org.jetbrains.annotations.Nullable()
    co.yap.sendmoney.addbeneficiary.models.MoneyTransferType transferType, @org.jetbrains.annotations.Nullable()
    co.yap.sendmoney.addbeneficiary.models.BeneficiaryAccount beneficiaryAccount, @org.jetbrains.annotations.Nullable()
    co.yap.sendmoney.addbeneficiary.models.BeneficiaryBank beneficiaryBank) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public java.lang.String toString() {
        return null;
    }
    
    @java.lang.Override()
    public int hashCode() {
        return 0;
    }
    
    @java.lang.Override()
    public boolean equals(@org.jetbrains.annotations.Nullable()
    java.lang.Object p0) {
        return false;
    }
}