package edu.juanpascual.mylittleshoppinglist.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import edu.juanpascual.mylittleshoppinglist.databinding.RowHistoryBinding
import edu.juanpascual.mylittleshoppinglist.db.DatabaseHelper
import edu.juanpascual.mylittleshoppinglist.models.ShoppingList
import edu.juanpascual.mylittleshoppinglist.utils.ItemClickListener

class HistoryAdapter(
    private var historyList: MutableList<ShoppingList>,
    private val itemClickListener: ItemClickListener<Int>
) : RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

    class HistoryViewHolder(val binding: RowHistoryBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = RowHistoryBinding.inflate(layoutInflater, parent, false)
        return HistoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val dbHelper = DatabaseHelper(holder.itemView.context)
        val id = historyList[position].getId()

        // Establecer los valores en el ViewHolder
        holder.binding.textNameList.text = historyList[position].getName()
        holder.binding.textPrice.text = dbHelper.getTotalPriceByListId(id).toString()

        // Establecer el listener
        holder.binding.cardViewList.setOnLongClickListener {
            itemClickListener.onItemClick(historyList[position].getId())
            true
        }
    }

    override fun getItemCount(): Int = historyList.size

    fun setHistoryList(newHistoryList: MutableList<ShoppingList>) {
        historyList = newHistoryList
        notifyDataSetChanged()
    }
}