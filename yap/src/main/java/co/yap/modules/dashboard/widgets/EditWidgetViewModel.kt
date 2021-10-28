package co.yap.modules.dashboard.widgets

import android.app.Application
import co.yap.yapcore.BaseViewModel

class EditWidgetViewModel (application: Application) :
    BaseViewModel<IEditWidget.State>(application = application),
    IEditWidget.ViewModel {
    override val state: IEditWidget.State = EditWidgetState()

}