package com.example.databasebookusingcontentprovider

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AdapterContentProvider(val mainActivity: ProviderWorker, private val itemList: List<Item>): RecyclerView.Adapter<AdapterContentProvider.ItemsHolder>(){

    inner class ItemsHolder(itemView: View): RecyclerView.ViewHolder(itemView)
    private var onItemClickListener: ((Item) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemsHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item,
            parent,
            false)
        return  ItemsHolder(view)
    }

    override fun getItemCount(): Int = itemList.size

    override fun onBindViewHolder(holder: ItemsHolder, position: Int) {
        val currentItem = itemList[position]
        holder.itemView.apply{
            findViewById<TextView>(R.id.textView).text = currentItem.name
            findViewById<TextView>(R.id.textView2).text = currentItem.date
            findViewById<TextView>(R.id.textViewID).text = currentItem.id.toString()
            findViewById<Button>(R.id.buttonDelete).setOnClickListener {
                mainActivity.deleteItem(currentItem.id)
            }
            findViewById<Button>(R.id.buttonChange).setOnClickListener {
                mainActivity.updateItem(currentItem)
            }
        }
    }




}