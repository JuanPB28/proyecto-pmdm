package edu.juanpascual.mylittleshoppinglist.views.forms

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import edu.juanpascual.mylittleshoppinglist.databinding.FragmentFormShopBinding
import edu.juanpascual.mylittleshoppinglist.models.Shop
import edu.juanpascual.mylittleshoppinglist.views.shop.ShopViewModel

class FormShopFragment : BottomSheetDialogFragment() {

    companion object {
        fun newInstance() = FormShopFragment()
    }

    private lateinit var binding: FragmentFormShopBinding
    private lateinit var viewModel: ShopViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFormShopBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[ShopViewModel::class.java]

        binding.buttonAddShop.setOnClickListener {
            val name = binding.textInputNameShop.text.toString()
            val address = binding.textInputAddressShop.text.toString()
            val phone = binding.textInputPhoneShop.text.toString()
            val website = binding.textInputWebsiteShop.text.toString()

            if (name.isEmpty()) {
                Toast.makeText(requireContext(), "Please fill at least the name field", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val shop = Shop(name, address, phone, website)
            viewModel.addShop(requireContext(), shop)
            Toast.makeText(requireContext(), "Saved", Toast.LENGTH_SHORT).show()

            dismiss()
        }

        return binding.root
    }
}