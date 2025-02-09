package edu.juanpascual.mylittleshoppinglist.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import edu.juanpascual.mylittleshoppinglist.databinding.RowHomeBinding
import edu.juanpascual.mylittleshoppinglist.db.DatabaseHelper
import edu.juanpascual.mylittleshoppinglist.models.ShoppingList
import edu.juanpascual.mylittleshoppinglist.utils.ItemClickListener

class HomeAdapter(
    private var lists: MutableList<ShoppingList>,
    private val itemClickListener: ItemClickListener<Int>
) : RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {

    class HomeViewHolder(val binding: RowHomeBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = RowHomeBinding.inflate(layoutInflater, parent, false)
        return HomeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val dbHelper = DatabaseHelper(holder.itemView.context)
        val idList = lists[position].getId()
        val idShop = lists[position].getShopId()


        // Establecer los valores en el ViewHolder
        holder.binding.textNameList.text = lists[position].getName()
        holder.binding.textNameShop.text = dbHelper.getShopById(idShop)?.getName()
        holder.binding.textPrice.text = dbHelper.getTotalPriceByListId(idList).toString()

        // Establecer el listeners
        holder.binding.cardViewList.setOnClickListener {
            itemClickListener.onItemClick(lists[position].getId())
        }

        holder.binding.cardViewList.setOnLongClickListener {
            itemClickListener.onItemLongClick(lists[position].getId())
            true
        }
    }

    override fun getItemCount(): Int = lists.size

    fun setLists(newLists: MutableList<ShoppingList>) {
        lists = newLists
        notifyDataSetChanged()
    }
}