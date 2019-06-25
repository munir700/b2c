package co.yap.app

import android.os.Bundle
import co.yap.app.di.BaseActivity
import co.yap.translation.Translator
import co.yap.translation.TraslatorKeys
import co.yap.yapcore.IBase
import kotlinx.android.synthetic.main.activity_account_selection.*


 class AccountSelectionActivity : BaseActivity<IBase.ViewModel<IBase.State>>() {
     override val viewModel: IBase.ViewModel<IBase.State>
         get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.


     var btnTxt: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_selection)

        var str: String = Translator.getString(application, TraslatorKeys.txt_input_hint_search_currency)

        btnBusiness.setOnClickListener {


        }
    }
}
