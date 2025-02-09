package edu.juanpascual.mylittleshoppinglist.views.shop

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import edu.juanpascual.mylittleshoppinglist.adapters.ShopAdapter
import edu.juanpascual.mylittleshoppinglist.databinding.FragmentShopBinding
import edu.juanpascual.mylittleshoppinglist.utils.ItemClickListener
import edu.juanpascual.mylittleshoppinglist.utils.deleteDialog
import edu.juanpascual.mylittleshoppinglist.views.forms.FormShopFragment

class ShopFragment : Fragment(), ItemClickListener<Int> {

    companion object {
        fun newInstance() = ShopFragment()
    }

    private lateinit var binding: FragmentShopBinding
    private lateinit var adapter: ShopAdapter
    private val viewModel: ShopViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.loadShops(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentShopBinding.inflate(inflater, container, false)
        adapter = ShopAdapter(viewModel.shops.value ?: mutableListOf(), this)
        binding.recyclerViewShops.adapter = adapter

        // Observar los cambios en la lista de tiendas
        viewModel.shops.observe(viewLifecycleOwner) { shops ->
            adapter.setShops(shops)
        }

        // Configurar el SwipeRefreshLayout
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.loadShops(requireContext())
            binding.swipeRefreshLayout.isRefreshing = false
        }


        // Configurar el botón flotante para añadir una nueva tienda
        binding.fabShops.setOnClickListener {
            FormShopFragment.newInstance().show(childFragmentManager, "formShop")
        }

        return binding.root
    }

    override fun onItemClick(item: Int) {
        findNavController().navigate(ShopFragmentDirections.actionShopFragmentToDetailShopFragment(shopId = item))
    }

    override fun onItemLongClick(item: Int) {
        deleteDialog(requireContext()) { viewModel.deleteShop(requireContext(), item) }
    }

}