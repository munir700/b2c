package com.digitify.identityscanner.docscanner.interfaces;

import com.digitify.identityscanner.interfaces.IBase;
import com.digitify.identityscanner.docscanner.enums.DocumentPageType;
import com.digitify.identityscanner.docscanner.enums.DocumentType;
import com.digitify.identityscanner.docscanner.states.DocReviewState;

public interface IDocReview {

    interface View extends IBase.View {
        void requestCancel();

        void requestDone();

        void requestRetake();
    }

    interface ViewModel extends IBase.ViewModel {
        IDocReview.View getView();

        void init(String filepath, DocumentType docType, DocumentPageType type, IDocReview.View view);

        void handleClickOnCancel();

        void handleClickOnDone();

        void handleClickOnRetake();

        DocReviewState getState();
    }
}
