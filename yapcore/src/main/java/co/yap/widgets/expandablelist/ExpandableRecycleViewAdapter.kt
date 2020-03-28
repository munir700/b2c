package co.yap.widgets.expandablelist

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import co.yap.yapcore.R
import co.yap.yapcore.databinding.ChildRowBinding
import co.yap.yapcore.databinding.ParentRowBinding

class ExpandableRecycleViewAdapter(parents: ArrayList<Parent>) :
    BaseExpandableRecyclerViewAdapter<Child, Parent, ExpandableRecycleViewAdapter.PViewHolder, ExpandableRecycleViewAdapter.CViewHolder>(
        parents, ExpandingDirection.VERTICAL
    ) {

    override fun onCreateParentViewHolder(parent: ViewGroup, viewType: Int): PViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding =
            DataBindingUtil.inflate<ViewDataBinding>(
                layoutInflater,
                R.layout.parent_row,
                parent,
                false
            )
        return PViewHolder(binding as ParentRowBinding)
    }

    override fun onCreateChildViewHolder(child: ViewGroup, viewType: Int): CViewHolder {
        val layoutInflater = LayoutInflater.from(child.context)
        val binding =
            DataBindingUtil.inflate<ViewDataBinding>(
                layoutInflater,
                R.layout.child_row,
                child,
                false
            )
        return CViewHolder(binding as ChildRowBinding)
    }

    override fun onBindParentViewHolder(
        parentViewHolder: PViewHolder,
        expandableType: Parent,
        position: Int
    ) {
        parentViewHolder.v.tvP.text = expandableType.name

    }

    override fun onBindChildViewHolder(
        childViewHolder: CViewHolder,
        expandedType: Child,
        expandableType: Parent,
        position: Int
    ) {
        childViewHolder.v.tvC.text = expandedType.name
    }

    override fun onExpandedClick(
        expandableViewHolder: PViewHolder,
        expandedViewHolder: CViewHolder,
        expandedType: Child,
        expandableType: Parent
    ) {
        Toast.makeText(
            expandableViewHolder.containerView.context,
            expandableType.name + " " + expandedType.name + " Position: " + expandedViewHolder.adapterPosition,
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun onExpandableClick(
        expandableViewHolder: PViewHolder,
        expandableType: Parent
    ) {
        Toast.makeText(
            expandableViewHolder.containerView.context,
            expandableType.name + " Position: " + expandableViewHolder.adapterPosition,
            Toast.LENGTH_SHORT
        ).show()
    }

    class PViewHolder(val v: ParentRowBinding) :
        BaseExpandableRecyclerViewAdapter.ExpandableViewHolder(v.root)

    class CViewHolder(val v: ChildRowBinding) :
        BaseExpandableRecyclerViewAdapter.ExpandedViewHolder(v.root)
}