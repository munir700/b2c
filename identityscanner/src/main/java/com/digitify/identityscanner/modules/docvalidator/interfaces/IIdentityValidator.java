package com.digitify.identityscanner.modules.docvalidator.interfaces;

import android.content.Context;

import com.digitify.identityscanner.modules.docvalidator.models.IdentityValidatorResult;

public interface IIdentityValidator {
    interface View {
        void init();

        void finishWithResult(IdentityValidatorResult result);

        void finishWithInvalidDocumentResult();

        void finishWithoutResult();

        Context getContext();

        void setObservers();

        void validateDocument(String documentPath);
    }

    interface ViewModel {

    }
}
