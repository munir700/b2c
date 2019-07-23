package com.digitify.identityscanner.helpers;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.Log;

import com.digitify.identityscanner.core.detection.detectors.MrzDetector;
import com.digitify.identityscanner.core.detection.models.Mrz;
import com.digitify.identityscanner.core.detection.models.Passport;
import com.digitify.identityscanner.core.detection.utils.OpenCVUtils;
import com.digitify.identityscanner.core.mrz.records.FrenchIdCard;
import com.digitify.identityscanner.core.mrz.records.MRP;
import com.digitify.identityscanner.core.mrz.records.MrtdTd1;
import com.digitify.identityscanner.core.mrz.records.MrtdTd2;
import com.digitify.identityscanner.core.mrz.records.MrvA;
import com.digitify.identityscanner.core.mrz.records.MrvB;
import com.digitify.identityscanner.core.mrz.records.SlovakId2_34;
import com.digitify.identityscanner.core.mrz.types.MrzFormat;
import com.digitify.identityscanner.core.ocr.Tesseract;
import com.digitify.identityscanner.modules.docscanner.enums.DocumentType;
import com.digitify.identityscanner.modules.docscanner.models.Identity;
import com.digitify.identityscanner.core.mrz.MrzParseException;
import com.digitify.identityscanner.core.mrz.MrzParser;
import com.digitify.identityscanner.core.mrz.MrzRecord;
import com.digitify.identityscanner.utils.ImageUtils;
import com.digitify.identityscanner.modules.docscanner.utils.OrcUtils;

import org.opencv.core.Mat;

import java.util.Arrays;

public class IdentityBuilder {
    public interface OnIdentityFetchingCompleteListener {
        void onIdentityFetchingComplete(Identity identity, String mrz);

        void onIdentityFetchingFailed(String error);

        OnIdentityFetchingCompleteListener DEFAULT = new OnIdentityFetchingCompleteListener() {
            @Override
            public void onIdentityFetchingComplete(Identity identity, String mrz) {

            }

            @Override
            public void onIdentityFetchingFailed(String error) {

            }
        };

    }

    private Bitmap bitmap;
    private String mrz;
    private OnIdentityFetchingCompleteListener listener = OnIdentityFetchingCompleteListener.DEFAULT;
    private DocumentType documentType;
    private Context context;

    public IdentityBuilder(Context context) {
        this.context = context;
        mrz = "";
    }

    public IdentityBuilder from(Bitmap bitmap, DocumentType documentType) {
        this.bitmap = bitmap;
        this.documentType = documentType;
        return this;
    }

    public void build() {
        try {
            MrzDetector detector = new MrzDetector();
            Mrz mrz = detector.detect(bitmap);
            if (detector.validate(mrz)) {
                performOCR(mrz.getBitmap());
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        listener.onIdentityFetchingFailed("Error detecting MRZ. Please scan the document again.");
    }

    public IdentityBuilder listen(OnIdentityFetchingCompleteListener listener) {
        this.listener = listener;
        if (listener == null) this.listener = OnIdentityFetchingCompleteListener.DEFAULT;
        return this;
    }

    private void performOCR(final Bitmap mrzBitmap) {
        // Bitmap enhanced = scaleDownForOCR(mrzBitmap);
        Bitmap enhanced = getDocumentType() == DocumentType.PASSPORT ? mrzBitmap : OpenCVUtils.binaryText(mrzBitmap);

        OrcUtils.runTextRecognition(context, enhanced, new OrcUtils.OnOCRCompleteListener() {
            @Override
            public void onTextRecognised(String text) {
                mrz = OrcUtils.formatForMrz(text);
                try {
                    MrzRecord record = MrzParser.parse(mrz);
                    listener.onIdentityFetchingComplete(parse(record), getMrz());
                } catch (RuntimeException ex) {
                    ex.printStackTrace();
                    listener.onIdentityFetchingFailed("Could not extract data from card. Please scan the document again.");
                }
            }

            @Override
            public void onTextRecognitionFailed(String error) {
                listener.onIdentityFetchingFailed("Could not extract data from card. Please scan the document again.");
            }
        });
    }

    private Bitmap scaleDownForOCR(Bitmap original) {
        int targetWidth = 500;
        int maxHeight = 300;
        if (original.getWidth() > targetWidth) {
            // Determine how much to scale down the image
            float scaleFactor =
                    Math.max(
                            (float) original.getWidth() / (float) targetWidth,
                            (float) original.getHeight() / (float) maxHeight);
            Bitmap scaled = ImageUtils.downScaleBitmap(original, scaleFactor);
            return scaled;
        }
        return original;
    }

    public String getMrz() {
        return mrz;
    }

    public DocumentType getDocumentType() {
        return documentType;
    }

    private Identity parse(MrzRecord record) {
        Identity id = new Identity();
        id.setDateOfBirth(record.dateOfBirth);
        id.setDocumentNumber(record.documentNumber);
        id.setExpirationDate(record.expirationDate);
        id.setFormat(record.format);
        id.setGender(record.sex);
        id.setIssuingCountry(record.issuingCountry);
        id.setNationality(record.nationality);

        // find the optional citizen number
        switch (record.format) {
            case PASSPORT:
                id.setCitizenNumber(((MRP) record).personalNumber);
                break;
            case MRTD_TD1:
                id.setCitizenNumber(((MrtdTd1) record).optional);
                break;
            case MRTD_TD2:
                id.setCitizenNumber(((MrtdTd2) record).optional);
                break;
            case FRENCH_ID:
                id.setCitizenNumber(((FrenchIdCard) record).optional);
                break;
            case MRV_VISA_A:
                id.setCitizenNumber(((MrvA) record).optional);
                break;
            case MRV_VISA_B:
                id.setCitizenNumber(((MrvB) record).optional);
                break;
            case SLOVAK_ID_234:
                id.setCitizenNumber(((SlovakId2_34) record).optional);
                break;
        }

        // If only 1 name is detected then split it into 2
        String givenName = record.givenNames;
        String sirName = record.surname;
        if (!TextUtils.isEmpty(givenName) && !TextUtils.isEmpty(sirName)) {
            id.setGivenName(givenName);
            id.setSirName(sirName);
        } else {
            // check which name is available
            String name = "";
            if (!TextUtils.isEmpty(givenName)) {
                name = givenName;
            } else if (!TextUtils.isEmpty(sirName)) {
                name = sirName;
            }

            // split the name into first and last name
            if (!TextUtils.isEmpty(name)) {

                String[] splittedName = name.split(" ");
                int size = splittedName.length;
                int half = (size % 2 == 0) ? (size / 2) : (size + 1) / 2;
                String fname = join(splittedName, 0, half);
                String lname = join(splittedName, half, size);

                id.setGivenName(fname);
                id.setSirName(lname);
            }
        }

        return id;
    }

    private String join(String[] texts, int start, int end) {
        if (texts.length >= end) {
            String[] range = Arrays.copyOfRange(texts, start, end);
            return TextUtils.join(" ", range);
        }

        return TextUtils.join(" ", texts);
    }


}
