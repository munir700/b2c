package co.yap.modules.dashboard.cards.home.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.samsung.android.sdk.samsungpay.v2.PartnerInfo;
import com.samsung.android.sdk.samsungpay.v2.SamsungPay;
import com.samsung.android.sdk.samsungpay.v2.SpaySdk;

public class PartnerInfoHolder {
    private static PartnerInfoHolder sInstance;
    private PartnerInfo mPartnerInfo;

    private PartnerInfoHolder(Context context) {
//        String serviceId = "553f9a2fbd0244e4a9c7ab";
        String serviceId = "421cb4f7453842509db829";
//        String serviceId = context.getResources().getString(R.string.gradle_product_id);

        Bundle bundle = new Bundle();
        bundle.putString(SamsungPay.PARTNER_SERVICE_TYPE, SpaySdk.ServiceType.APP2APP.toString());

        mPartnerInfo = new PartnerInfo(serviceId, bundle);
    }

    public static synchronized PartnerInfoHolder getInstance(@NonNull Context context) {
        if (sInstance == null) {
            sInstance = new PartnerInfoHolder(context);
        }
        return sInstance;
    }

    public PartnerInfo getPartnerInfo() {
        return mPartnerInfo;
    }
}
