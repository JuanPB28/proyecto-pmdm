package edu.juanpascual.mylittleshoppinglist.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import edu.juanpascual.mylittleshoppinglist.databinding.RowShopBinding
import edu.juanpascual.mylittleshoppinglist.models.Shop
import edu.juanpascual.mylittleshoppinglist.utils.ItemClickListener

class ShopAdapter(
    private var shops: MutableList<Shop>,
    private val itemClickListener: ItemClickListener<Int>
) : RecyclerView.Adapter<ShopAdapter.ShopViewHolder>() {

    class ShopViewHolder(val binding: RowShopBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = RowShopBinding.inflate(layoutInflater, parent, false)
        return ShopViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ShopViewHolder, position: Int) {
        // Establecer los valores en el ViewHolder
        holder.binding.textNameShop.text = shops[position].getName()

        // Establecer el listener de clics en el elemento
        holder.binding.cardViewShop.setOnClickListener {
            itemClickListener.onItemClick(shops[position].getId())
        }

        // Establecer el listener de clics largos en el elemento
        holder.binding.cardViewShop.setOnLongClickListener {
            itemClickListener.onItemLongClick(shops[position].getId())
            true
        }
    }

    override fun getItemCount(): Int = shops.size

    fun setShops(newShops: MutableList<Shop>) {
        shops = newShops
        notifyDataSetChanged()
    }

}