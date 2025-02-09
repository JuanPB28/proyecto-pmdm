package edu.juanpascual.mylittleshoppinglist.views.list

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import edu.juanpascual.mylittleshoppinglist.adapters.HomeAdapter
import edu.juanpascual.mylittleshoppinglist.databinding.FragmentHomeBinding
import edu.juanpascual.mylittleshoppinglist.utils.ItemClickListener
import edu.juanpascual.mylittleshoppinglist.utils.deleteDialog
import edu.juanpascual.mylittleshoppinglist.views.forms.FormShoppingListFragment

class HomeFragment : Fragment(), ItemClickListener<Int> {

    companion object {
        fun newInstance() = HomeFragment()
    }

    private lateinit var binding: FragmentHomeBinding
    private lateinit var adapter: HomeAdapter
    private val viewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.loadLists(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        adapter = HomeAdapter(viewModel.lists.value ?: mutableListOf(), this)
        binding.recyclerViewLists.adapter = adapter

        // Observar los cambios en la lista
        viewModel.lists.observe(viewLifecycleOwner) { lists ->
            adapter.setLists(lists)
        }

        // Configurar el SwipeRefreshLayout
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.loadLists(requireContext())
            binding.swipeRefreshLayout.isRefreshing = false
        }

        // Configurar el botón flotante para añadir una nueva lista
        binding.fabShoppingLists.setOnClickListener {
            FormShoppingListFragment.newInstance().show(childFragmentManager, "formList")
        }

        return binding.root
    }

    override fun onItemClick(item: Int) {
        findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToShoppingListFragment(item))
    }

    override fun onItemLongClick(item: Int) {
        deleteDialog(requireContext()) { viewModel.deleteList(requireContext(), item) }
    }


}