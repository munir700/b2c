package co.yap.widgets.search

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.EditText
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import co.yap.yapcore.R
import co.yap.yapcore.databinding.LayoutYapSearchviewBinding

class YapSearchView(context: Context,attrs: AttributeSet): LinearLayoutCompat(context,attrs) {
    lateinit var searchEditText:EditText
    var yapSearchViewListener: IYapSearchView? = null
    var searchFlag=false

    var viewDataBinding: LayoutYapSearchviewBinding = DataBindingUtil.inflate(
        LayoutInflater.from(context),
        R.layout.layout_yap_searchview,
        this,
        true
    )

    init {
        viewDataBinding.executePendingBindings()
        prepareSearchView()
        setSearchViewFocusListener()
        setCancelClickListener()
        setTextChangeListener()
    }

    private fun prepareSearchView() {
        searchEditText = viewDataBinding.layoutSearchView.findViewById(androidx.appcompat.R.id.search_src_text)
        searchEditText.gravity = Gravity.CENTER
        viewDataBinding.layoutSearchView.isIconified = false
        viewDataBinding.layoutSearchView.clearFocus()
    }

    private fun prepareActiveSearchView() {
        viewDataBinding.tvCancel.visibility = VISIBLE
        searchEditText.gravity = Gravity.START
        searchFlag= true
    }

    private fun resetSearchView() {
        searchEditText.gravity = Gravity.CENTER
        viewDataBinding.tvCancel.visibility = GONE
        viewDataBinding.layoutSearchView.clearFocus()
        viewDataBinding.layoutSearchView.setQuery("", false)
        searchFlag=false
    }

    fun setCancelClickListener(){
        viewDataBinding.tvCancel.setOnClickListener {
            yapSearchViewListener?.onSearchActive(false)
            resetSearchView()
        }
    }

    fun setTextChangeListener(){
        viewDataBinding.layoutSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                yapSearchViewListener?.onTypingSearch(newText)
                return true
            }
        })
    }

    private fun setSearchViewFocusListener() {
        viewDataBinding.layoutSearchView.setOnQueryTextFocusChangeListener { _ , hasFocus ->
            if (hasFocus && !searchFlag) {
                yapSearchViewListener?.onSearchActive(true)
                prepareActiveSearchView()
            }else if(searchFlag && !hasFocus){
                showInputMethod()
            }
        }
    }

    fun showInputMethod() {
        viewDataBinding.layoutSearchView.requestFocus()
    }
}