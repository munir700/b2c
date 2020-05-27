package co.yap.widgets.radiocus

interface Checkable {
    fun isChecked(): Boolean
    fun toggle()
    fun setChecked(checked: Boolean)
}