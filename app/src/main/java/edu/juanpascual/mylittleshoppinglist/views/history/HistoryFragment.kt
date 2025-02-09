package edu.juanpascual.mylittleshoppinglist.views.history

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import edu.juanpascual.mylittleshoppinglist.adapters.HistoryAdapter
import edu.juanpascual.mylittleshoppinglist.databinding.FragmentHistoryBinding
import edu.juanpascual.mylittleshoppinglist.utils.ItemClickListener
import edu.juanpascual.mylittleshoppinglist.utils.deleteDialog

class HistoryFragment : Fragment(), ItemClickListener<Int> {

    companion object {
        fun newInstance() = HistoryFragment()
    }

    private lateinit var binding: FragmentHistoryBinding
    private lateinit var adapter: HistoryAdapter
    private val viewModel: HistoryViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.loadHistory(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHistoryBinding.inflate(inflater, container, false)
        adapter = HistoryAdapter(viewModel.historyList.value ?: mutableListOf(), this)
        binding.recyclerViewHistory.adapter = adapter

        // Observar los cambios en la lista
        viewModel.historyList.observe(viewLifecycleOwner) { historyList ->
            adapter.setHistoryList(historyList)
        }

        // Configurar el SwipeRefreshLayout
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.loadHistory(requireContext())
            binding.swipeRefreshLayout.isRefreshing = false
        }

        return binding.root
    }

    override fun onItemLongClick(item: Int) {
        deleteDialog(requireContext()) { viewModel.deleteList(requireContext(), item) }
    }

    override fun onItemClick(item: Int) {
        // No es necesario implementar en HistoryFragment
    }
}