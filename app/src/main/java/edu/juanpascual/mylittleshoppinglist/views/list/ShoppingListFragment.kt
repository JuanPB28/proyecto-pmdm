package edu.juanpascual.mylittleshoppinglist.views.list

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import edu.juanpascual.mylittleshoppinglist.adapters.ProductAdapter
import edu.juanpascual.mylittleshoppinglist.databinding.FragmentShoppingListBinding
import edu.juanpascual.mylittleshoppinglist.utils.ItemClickListener
import edu.juanpascual.mylittleshoppinglist.utils.deleteDialog
import edu.juanpascual.mylittleshoppinglist.utils.showDialog
import edu.juanpascual.mylittleshoppinglist.views.forms.FormProductFragment
import edu.juanpascual.mylittleshoppinglist.views.forms.FormShoppingListFragment

class ShoppingListFragment : Fragment(), ItemClickListener<Int> {

    private val args: ShoppingListFragmentArgs by navArgs()

    companion object {
        fun newInstance() = ShoppingListFragment()
    }

    private lateinit var binding: FragmentShoppingListBinding
    private lateinit var adapter: ProductAdapter
    private val viewModel: ShoppingListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.loadProducts(requireContext(), args.listId)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentShoppingListBinding.inflate(inflater, container, false)
        adapter = ProductAdapter(viewModel.products.value ?: mutableListOf(), this)
        binding.recyclerViewLists.adapter = adapter

        // Observar los cambios en la lista
        viewModel.products.observe(viewLifecycleOwner) { products ->
            adapter.setProducts(products)
        }

        // Configurar el SwipeRefreshLayout
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.loadProducts(requireContext(), args.listId)
            binding.fabPurchase.text = "Precio: ${viewModel.getTotalPrice()}€"
            binding.swipeRefreshLayout.isRefreshing = false
        }

        // Configurar el botón flotante para añadir una nuevo producto
        binding.fabProducts.setOnClickListener {
            FormProductFragment.newInstance().show(childFragmentManager, "formProduct")
        }

        // Configurar el botón flotante para realizar la compra
        binding.fabPurchase.setOnClickListener {
            showDialog(requireContext(),"Purchase","Are you sure you want to purchase this list?"){ viewModel.deleteList(requireContext(), args.listId) }
        }

        return binding.root
    }

    override fun onItemClick(item: Int) {
        // No action needed for this example
    }

    override fun onItemLongClick(item: Int) {
        deleteDialog(requireContext()) { viewModel.deleteProduct(requireContext(), item) }
    }
}