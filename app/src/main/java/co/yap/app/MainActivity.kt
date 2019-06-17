package co.yap.app

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import co.yap.app.adapters.TestAdapter
import co.yap.app.di.CoreActivity
import co.yap.yapcore.BaseBindingAdapter
import co.yap.yapcore.IBase

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : CoreActivity() {
    override fun <T : IBase.ViewModel> getViewModel(): T {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        val adapter: TestAdapter = TestAdapter(this)
        adapter.onItemClickListener = object: BaseBindingAdapter.OnItemClickListener {
            override fun onItemClick(view: View, pos: Int) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        }
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
