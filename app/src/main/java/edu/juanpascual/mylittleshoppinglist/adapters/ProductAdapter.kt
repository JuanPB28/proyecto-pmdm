package edu.juanpascual.mylittleshoppinglist.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import edu.juanpascual.mylittleshoppinglist.databinding.RowProductBinding
import edu.juanpascual.mylittleshoppinglist.models.Product
import edu.juanpascual.mylittleshoppinglist.utils.ItemClickListener

class ProductAdapter(
    private var products: MutableList<Product>,
    private val itemClickListener: ItemClickListener<Int>
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    class ProductViewHolder(val binding: RowProductBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = RowProductBinding.inflate(layoutInflater, parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {

        // Establecer los valores en el ViewHolder
        holder.binding.textNameProduct.text = products[position].getName()
        holder.binding.textQuantity.text = products[position].getQuantity().toString()
        holder.binding.textPrice.text = products[position].getPrice().toString()

        // Establecer el listeners
        holder.binding.cardViewProduct.setOnClickListener {
            itemClickListener.onItemClick(products[position].getId())
        }

        holder.binding.cardViewProduct.setOnLongClickListener {
            itemClickListener.onItemLongClick(products[position].getId())
            true
        }
    }

    override fun getItemCount(): Int = products.size

    fun setProducts(newproducts: MutableList<Product>) {
        products = newproducts
        notifyDataSetChanged()
    }
}