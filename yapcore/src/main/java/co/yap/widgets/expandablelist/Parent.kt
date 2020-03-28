package co.yap.widgets.expandablelist

import co.yap.networking.transactions.responsedtos.purposepayment.PurposeOfPayment

data class Parent(val name: String, val childList: List<PurposeOfPayment>) :
    BaseExpandableRecyclerViewAdapter.ExpandableGroup<Child>() {

    override fun getExpandingItems(): List<Child> {
        val list = ArrayList<Child>(childList.size)
        for (item in childList)
            list.add(Child(item.purposeDescription.toString()))
        return list
    }
}