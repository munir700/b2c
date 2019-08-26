package co.yap.modules.dashboard.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import co.yap.R
import co.yap.modules.dashboard.adapters.DashboardAdapter
import co.yap.modules.dashboard.interfaces.IDashboard
import co.yap.modules.dashboard.viewmodels.DashBoardViewModel
import co.yap.yapcore.BaseFragment
import kotlinx.android.synthetic.main.fragment_dashboard.*


class DashBoardHomeFragment : BaseFragment<IDashboard.ViewModel>(), IDashboard.View {
    var transactions: ArrayList<LinkedHashMap<String, Float>> = ArrayList()
    val hashMap: HashMap<Int, String> = HashMap<Int, String>()


    override val viewModel: IDashboard.ViewModel
        get() = ViewModelProviders.of(this).get(DashBoardViewModel::class.java)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        val hasList =
        val hashMap: HashMap<String, Float> = HashMap<String, Float>()
        hashMap.put("one", 1f)
        hashMap.put("two", 3f)
        hashMap.put("three", 6f)
        Log.i("hashMapstr", hashMap.size.toString())
        rvTransactionsBarChart.adapter = DashboardAdapter(hashMap, this!!.activity!!)
//        rvTransactionsBarChart.layoutManager =
//            LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        rvTransactionsBarChart.setLayoutManager(
            LinearLayoutManager(
                activity,
                LinearLayoutManager.HORIZONTAL,
                false
            )
        )


//

//        imageModelArrayList = eatFruits()
//        adapter = FruitAdapter(this, imageModelArrayList)
//        recyclerView.setAdapter(adapter)
//        recyclerView.setLayoutManager(
//            LinearLayoutManager(
//                getApplicationContext(),
//                LinearLayoutManager.HORIZONTAL,
//                false
//            )
//        )
//        val items:HashMap<String, Float> = hashMap
//        val item1 : LinkedHashMap<String, Float> = LinkedHashMap<String, Float>()
//        for (abc in items ) {
//            item1.put(abc.key, abc.value)
//
//        }
//        for (abc in items ) {
//
//
//            val keySet = items.keys
//            val listOfKeys = ArrayList<String>(keySet)
//            println("ArrayList Of Keys :")
//
//            for (key in listOfKeys) {
//                println(key)
//            }
//            val values = items.values
//            val listOfValues = ArrayList<Float>(values)
//            println("ArrayList Of Values :")
//            for (value in listOfValues) {
//                println(value)
//            }
//            val item1 : LinkedHashMap<String, Float> = LinkedHashMap<String, Float>()
//            item1.put(listOfKeys.get(abc),listOfValues.get(position))

//            barChart.animation.duration = 1000
//            barChart.animate(item1)

//        }

    }
}