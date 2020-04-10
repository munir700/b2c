package co.yap.sendmoney.addbeneficiary.models;

import java.lang.System;

@androidx.annotation.Keep()
@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0005\b\u0087\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005\u00a8\u0006\u0006"}, d2 = {"Lco/yap/sendmoney/addbeneficiary/models/MoneyTransferType;", "", "(Ljava/lang/String;I)V", "BANK_TRANSFER", "CASH_PICKUP", "NONE", "sendmoney_debug"})
public enum MoneyTransferType {
    /*public static final*/ BANK_TRANSFER /* = new BANK_TRANSFER() */,
    /*public static final*/ CASH_PICKUP /* = new CASH_PICKUP() */,
    /*public static final*/ NONE /* = new NONE() */;
    
    MoneyTransferType() {
    }
}