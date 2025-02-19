package edu.juanpascual.mylittleshoppinglist.views.forms

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import edu.juanpascual.mylittleshoppinglist.databinding.FragmentFormProductBinding
import edu.juanpascual.mylittleshoppinglist.models.Product
import edu.juanpascual.mylittleshoppinglist.views.list.ShoppingListViewModel

class FormProductFragment : BottomSheetDialogFragment() {

    companion object {
        var listId: Int = 0

        fun newInstance() = FormProductFragment()
    }

    private lateinit var binding: FragmentFormProductBinding
    private lateinit var viewModel: ShoppingListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFormProductBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[ShoppingListViewModel::class.java]

        binding.buttonAddProduct.setOnClickListener {
            val name = binding.textInputName.text.toString()
            val priceTxt = binding.textInputPrice.text.toString()
            val amountTxt = binding.textInputAmount.text.toString()
            val listId = listId

            var price = 0.0
            var amount = 0

            if (priceTxt.isNotEmpty()) {
                price = priceTxt.toDouble()
            }

            if (amountTxt.isNotEmpty()) {
                amount = amountTxt.toInt()
            }

            if (name.isEmpty()) {
                Toast.makeText(requireContext(), "Please fill at least the name field", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val product = Product(name, price, amount, listId)
            viewModel.addProduct(requireContext(), product)
            Toast.makeText(requireContext(), "Product added successfully", Toast.LENGTH_SHORT).show()
            dismiss()
        }

        return binding.root
    }
}