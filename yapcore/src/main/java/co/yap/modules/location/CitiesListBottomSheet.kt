package co.yap.modules.location

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.yap.yapcore.R
import co.yap.yapcore.interfaces.OnItemClickListener
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class CitiesListBottomSheet(
    private val mListener: OnItemClickListener,
    private val citiesList: List<String>
) : BottomSheetDialogFragment() {

    override fun getTheme(): Int = R.style.AppBottomSheetDialogTheme
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val adapter = CitiesAdapter(citiesList as MutableList<String>)
        val view = inflater.inflate(R.layout.bottom_sheet_cities, container, false)
        val recyclerView: RecyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter.allowFullItemClickListener = true
        adapter.onItemClickListener = mListener
        recyclerView.adapter = adapter
        return view
    }

}