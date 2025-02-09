package edu.juanpascual.mylittleshoppinglist.views.forms

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import edu.juanpascual.mylittleshoppinglist.R
import edu.juanpascual.mylittleshoppinglist.databinding.FragmentFormShoppingListBinding
import edu.juanpascual.mylittleshoppinglist.models.ShoppingList
import edu.juanpascual.mylittleshoppinglist.views.list.HomeViewModel

class FormShoppingListFragment : BottomSheetDialogFragment() {

    companion object {
        fun newInstance() = FormShoppingListFragment()
    }

    private lateinit var binding: FragmentFormShoppingListBinding
    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFormShoppingListBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]

        binding.buttonAddList.setOnClickListener {
            val name = binding.textInputNameList.text.toString()
            val shopRaw = binding.textInputShop.text

            if (name.isEmpty() || shopRaw.isEmpty())  {
                Toast.makeText(requireContext(), "Please fill all the fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val shopParts = shopRaw.toString().split("_")
            val shopId = shopParts[0].toInt()
            val newList = ShoppingList(name, shopId)
            viewModel.addList(requireContext(), newList)
            Toast.makeText(requireContext(), "Saved", Toast.LENGTH_SHORT).show()

            dismiss()
        }

        // Configuración dropdown
        val listShops = viewModel.getShops(requireContext()).map { shop -> shop.toString() }
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, listShops)
        binding.textInputShop.setAdapter(adapter)

        return binding.root
    }

}