package co.yap.yapcore

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.fragment.app.Fragment
import co.yap.translation.Translator


abstract class BaseFragment<V: IBase.ViewModel<*>> : BaseNavFragment(), IBase.View<V> {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is IFragmentHolder) {
            context.onFragmentAttached()
        } else {
            throw IllegalStateException("Could not find reference to IFragmentHolder. Make sure parent activity implements IFragmentHolder interface")
        }

        if (context !is IBase.View<*>) {
            throw IllegalStateException("Could not find reference to IBase.View. Make sure parent activity implements IBase.View interface")
        }
    }

    override fun onDetach() {
        super.onDetach()
        if (getFragmentHolder() != null) {
            getFragmentHolder()?.onFragmentDetached("")
        }
    }

    private val progressDialogueFragment: ProgressDialogueFragment =
        ProgressDialogueFragment()


    override fun showLoader(isVisible: Boolean) {
        if (isVisible) {
            fragmentManager?.let { progressDialogueFragment.show(it, "loading") }

        } else {
            progressDialogueFragment.dismiss()

        }
    }

    override fun showToast(msg: String) {
        Toast.makeText(activity, msg, Toast.LENGTH_LONG).show()
    }

    override fun onBackPressed() {
        getBaseView()?.onBackPressed()
    }

    private fun getFragmentHolder(): IFragmentHolder? {
        if (context is IFragmentHolder) {
            return context as IFragmentHolder
        }

        return null
    }

    private fun getBaseView(): IBase.View<*>? {
        if (context is IBase.View<*>) {
            return context as IBase.View<*>
        }

        return null
    }

    override fun showInternetSnack(isVisible: Boolean) {
        getBaseView()?.showInternetSnack(isVisible)
    }

    override fun onNetworkStateChanged(isConnected: Boolean) {
        getBaseView()?.onNetworkStateChanged(isConnected)
    }

    override fun isPermissionGranted(permission: String): Boolean {
        return getBaseView()?.isPermissionGranted(permission)!!
    }

    override fun requestPermissions() {
        getBaseView()?.requestPermissions()
    }

    override fun getString(resourceKey: String): String = Translator.getString(context!!, resourceKey)
}
