package co.yap.yapcore.dagger.base.interfaces

import androidx.fragment.app.Fragment
import co.yap.yapcore.R

interface ManageToolBarListener {
    /**
     * set toolbar title for [Fragment]!
     *  Gets called whenever [Fragment] implement [ManageToolBarListener] interface
     */
    var toolBarTitle: String?

    /**
     * set toolbar Visibility for [Fragment]
     * Gets called whenever [Fragment] implement [ManageToolBarListener] interface
     * Show [androidx.appcompat.widget.Toolbar] in Activity if its return true
     * if [androidx.appcompat.widget.Toolbar] in Activity then [Fragment] need to manage [androidx.appcompat.widget.Toolbar] in layout file
     */
    var toolBarVisibility: Boolean?
    var displayHomeAsUpEnabled: Boolean?
    var homeAsUpIndicator: Int?

//    fun setSupportActionBar(displayHomeAsUpEnabled: Boolean, homeAsUpIndicator: Int)
//    fun getToolBarTitle(): String?
}