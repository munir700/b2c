package co.yap.widgets.expandablelist

data class Parent(val name: String) : BaseExpandableRecyclerViewAdapter.ExpandableGroup<Child>() {

    override fun getExpandingItems(): List<Child> {
        val list = ArrayList<Child>(10)
        for (i in 0..10)
            list.add(Child("Child $i"))
        return list
    }
}