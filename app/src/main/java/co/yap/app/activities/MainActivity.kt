package co.yap.app.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import co.yap.app.R
import co.yap.app.di.BaseActivity
import co.yap.translation.Translator
import co.yap.translation.TraslatorKeys
import co.yap.yapcore.IBase

class MainActivity : BaseActivity<IBase.ViewModel<IBase.State>>() {

    override val viewModel: IBase.ViewModel<IBase.State>
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var str: String = Translator.getString(application, TraslatorKeys.txt_input_hint_search_currency)
        showToast(str)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
