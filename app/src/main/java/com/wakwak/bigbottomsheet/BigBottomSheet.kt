package com.wakwak.bigbottomsheet

import android.os.Bundle
import android.support.design.widget.BottomSheetDialogFragment
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.layout_big_bottom_sheet.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch

class BigBottomSheet : BottomSheetDialogFragment() {

    companion object {
        fun newInstance(): BigBottomSheet = BigBottomSheet()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.layout_big_bottom_sheet, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        launch(UI) {
            progress.visibility = View.VISIBLE
            val items = addItem().await()
            recyclerView.adapter = Adapter(items)
            progress.visibility = View.GONE
        }
    }

    private fun addItem() = async {
        val items = mutableListOf<String>()
        for (i in 0..10000) {
            items.add("Item $i")
            Log.d("wakwak3125", "Item $i")
        }
        return@async items
    }

    class Adapter(private val items: List<String>) : RecyclerView.Adapter<Adapter.ViewHolder>() {
        class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
            val textView by lazy { itemView?.findViewById<TextView>(R.id.text) }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(
                    LayoutInflater.from(parent.context).inflate(R.layout.item_row, parent, false)
            )
        }

        override fun getItemCount(): Int = items.count()

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.textView?.text = items[position]
        }
    }
}
