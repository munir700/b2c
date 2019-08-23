package co.yap.modules.dashboard.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.yap.R
import kotlinx.android.synthetic.main.item_bar_chart.view.*

class DashboardAdapter(val listItems: HashMap<String, Float>, val context: Context) :
    RecyclerView.Adapter<DashboardAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.item_bar_chart,
                parent,
                false
            )
        )
    }
//    fun createSetValz(position:Int){
//        //Creating an ArrayList of values
//
////        val listOfValues = ArrayList<String, Float>()
//        val barHeight: HashMap<String, Float> = listItems.get(position)
//
//        val values = barHeight.values
//
////        var listOfValues  = ArrayList<>(values)
//
//        println("ArrayList Of Values :")
//
//        for (value in listOfValues) {
//            println(value)
//        }
//
//        println("--------------------------")
//
//        //Getting the Set of entries
//
//        val entrySet = values.entrySet()
//
//        //Creating an ArrayList Of Entry objects
//
//        val listOfEntry = ArrayList<Entry<String, String>>(entrySet)
//
//        println("ArrayList of Key-Values :")
//
//        for (entry in listOfEntry) {
//            println(entry.key + " : " + entry.value)
//        }
//    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        holder?.barChartView = listItems.get(position)
        //now set up that bar here
        val items:HashMap<String, Float> = listItems
//        val barHeight: HashMap<String, Float> = listItems.get(position)


        //
        //Getting Set of keys

//        val keySet = barHeight.keys
//
//
//        //Creating an ArrayList of keys
//
//        val listOfKeys = ArrayList<String>(keySet)
//
//        println("ArrayList Of Keys :")
//
//        for (key in listOfKeys) {
//            println(key)
//        }
//
//        println("--------------------------")
//
//        //Getting Collection of values
//
//        val values = barHeight.values
//
//        //Creating an ArrayList of values
//
//        val listOfValues = ArrayList<Float>(values)
//
//        println("ArrayList Of Values :")
//
//        for (value in listOfValues) {
//            println(value)
//        }
//
//        println("--------------------------")
//        var abcdef: Set<Entry<String, Float>>? = null
////        entrySet
////        val listOfEntry = ArrayList<String>(keySet)
////        val listOfEntry : Set<Entry<String, Float>> = ArrayList<abcdef>
//
//
//        val entrySet = barHeight.entries
//
//        //Creating an ArrayList Of Entry objects
//        var mapentries: Map.Entry<String, Float> ?= null
//        mapentries=  Map.Entry<String, Float>
//
//
//        val listOfEntry: ArrayList<Entry<String, Float>> = ArrayList<Entry<String, Float>>()
//        listOfEntry.add(entrySet)
//
////        var listOfEntry : ArrayList<String, Float> = ArrayList<Entry<String, Float>()
////        var listOfEntry : ArrayList<Entry<String, Float>> = ArrayList<Entry<String, Float>>()
//
//        for (i in listOfValues.indices)
//            listOfEntry.add(keySet, listOfValues.get(i))
//
//        println("ArrayList of Key-Values :")
//
//        for (entry in listOfEntry) {
//            println(entry.key + " : " + entry.value)
//        }
//
//
//        ///
//
////        val listOfKeys = ArrayList<String>(keySet)
//        Log.i("formaters", listOfKeys.get(position) + listOfKeys.get(1) + listOfKeys.get(2))
////        Log.i("formates", barHeight.get(1))
//
//


        //Getting Set of keys

        val keySet = items.keys


        //Creating an ArrayList of keys

        val listOfKeys = ArrayList<String>(keySet)

        println("ArrayList Of Keys :")

        for (key in listOfKeys) {
            println(key)
        }



        val values = items.values


        val listOfValues = ArrayList<Float>(values)

        println("ArrayList Of Values :")

        for (value in listOfValues) {
            println(value)
        }

////      val barHeight:HashMap<String, Float> = listItems.get(position)
        val item1 : LinkedHashMap<String, Float> = LinkedHashMap<String, Float>()

        item1.put(listOfKeys.get(position),listOfValues.get(position))
        holder?.barChartView.animation.duration = 1000
        holder?.barChartView.animate(item1)
    }

    // Gets the number of animals in the list
    override fun getItemCount(): Int {
        return listItems.size
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        // Holds the TextView that will add each animal to
        var barChartView = view.transactionBar
    }
}