package ru.korolevss.l_tech.adapter

import androidx.recyclerview.widget.DiffUtil
import ru.korolevss.l_tech.model.Post

class PostDiffUtilCallback(
    private val oldList: List<Post>,
    private val newList: List<Post>
) : DiffUtil.Callback() {

    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldModel = oldList[oldItemPosition]
        val newModel = newList[newItemPosition]
        return oldModel.id == newModel.id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldModel = oldList[oldItemPosition]
        val newModel = newList[newItemPosition]
        return oldModel.image == newModel.image
                && oldModel.title == newModel.title
                && oldModel.text == newModel.text
                && oldModel.date == newModel.date
    }

}
