package com.digitify.identityscanner.modules.docscanner.interfaces;

import com.digitify.identityscanner.interfaces.IBase;
import com.digitify.identityscanner.modules.docscanner.models.IdentityScannerResult;
import com.digitify.identityscanner.modules.docscanner.states.ScanResultsState;
import com.digitify.identityscanner.modules.docscanner.viewmodels.IdentityScannerViewModel;
import com.digitify.identityscanner.modules.docscanner.viewmodels.ScanResultsViewModel;

public interface IScanResults {

    interface View extends IBase.View {
        IdentityScannerResult getResults();
        ScanResultsViewModel getViewModel();
        IdentityScannerViewModel getParentViewModel();
    }

    interface ViewModel extends IBase.ViewModel {
        IScanResults.View getView();

        void init(IdentityScannerResult scanResults, IScanResults.View view);

        ScanResultsState getState();
    }
}
