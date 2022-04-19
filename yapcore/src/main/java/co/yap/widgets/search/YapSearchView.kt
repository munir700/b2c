package co.yap.widgets.search

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.EditText
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import co.yap.yapcore.R
import kotlinx.android.synthetic.main.layout_yap_searchview.view.*
import kotlinx.android.synthetic.main.layout_yap_searchview.view.tvCancel

class YapSearchView: LinearLayoutCompat {
    lateinit var searchEditText:EditText
    var yapSearchViewListener: IYapSearchView? = null
    var searchFlag=false

    var viewDataBinding: ViewDataBinding = DataBindingUtil.inflate(
    LayoutInflater.from(context),
    R.layout.layout_yap_searchview,
    this,
    true
)

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context,attrs: AttributeSet, defStyleAttr: Int) : super(
        context, attrs,
        defStyleAttr
    ) {
        init(context, attrs)
    }

    private fun init(context: Context, attrs: AttributeSet?) {
        viewDataBinding.executePendingBindings()
        prepareSearchView()
        setSearchViewFocusListener()
        setCancelClickListener()
        setTextChangeListener()
        attrs?.let {
            val typedArray =
                context.obtainStyledAttributes(it, R.styleable.YapSearchView, 0, 0)
            typedArray.recycle()
        }
    }

    private fun prepareSearchView() {
        searchEditText = viewDataBinding.root.layoutSearchView.findViewById<EditText>(androidx.appcompat.R.id.search_src_text)
        searchEditText.gravity = Gravity.CENTER
        viewDataBinding.root.layoutSearchView.isIconified = false
        viewDataBinding.root.layoutSearchView.clearFocus()
    }

    private fun prepareActiveSearchView() {
        viewDataBinding.root.tvCancel.visibility = VISIBLE
        searchEditText.gravity = Gravity.START
        searchFlag= true
    }

    private fun restSearchView() {
        searchEditText.gravity = Gravity.CENTER
        viewDataBinding.root.tvCancel.visibility = GONE
        viewDataBinding.root.layoutSearchView.clearFocus()
        viewDataBinding.root.layoutSearchView.setQuery("", false)
        searchFlag=false
    }

    fun setCancelClickListener(){
        viewDataBinding.root.tvCancel.setOnClickListener {
            yapSearchViewListener?.onSearchActive(false)
            restSearchView()
        }
    }

    fun setTextChangeListener(){
        viewDataBinding.root.layoutSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                yapSearchViewListener?.onTypingSearch(newText)
                return false
            }
        })
    }

    private fun setSearchViewFocusListener() {
        viewDataBinding.root.layoutSearchView.setOnQueryTextFocusChangeListener { _ , hasFocus ->
            if (hasFocus && !searchFlag) {
                yapSearchViewListener?.onSearchActive(true)
                prepareActiveSearchView()
            }else if(searchFlag && !hasFocus){
                showInputMethod()
            }
        }
    }

    fun showInputMethod() {
        viewDataBinding.root.layoutSearchView.requestFocus()
    }
}