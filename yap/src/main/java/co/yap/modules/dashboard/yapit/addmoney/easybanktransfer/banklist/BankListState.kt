package co.yap.modules.dashboard.yapit.addmoney.easybanktransfer.banklist

import co.yap.widgets.search.IYapSearchView
import co.yap.yapcore.BaseState

class BankListState : BaseState(),
    IBankList.State {
    override var yapSearchViewListener: IYapSearchView = BankListFragment()
}