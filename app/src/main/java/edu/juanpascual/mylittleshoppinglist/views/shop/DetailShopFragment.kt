package edu.juanpascual.mylittleshoppinglist.views.shop

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import edu.juanpascual.mylittleshoppinglist.databinding.FragmentDetailShopBinding
import edu.juanpascual.mylittleshoppinglist.models.Shop
import edu.juanpascual.mylittleshoppinglist.utils.launchDialIntent
import edu.juanpascual.mylittleshoppinglist.utils.launchMapIntent
import edu.juanpascual.mylittleshoppinglist.utils.launchWebIntent
import edu.juanpascual.mylittleshoppinglist.utils.showDialog

class DetailShopFragment : Fragment() {

    private val args: DetailShopFragmentArgs by navArgs()

    companion object {
        fun newInstance() = DetailShopFragment()
    }

    private lateinit var shop: Shop
    private lateinit var binding: FragmentDetailShopBinding
    private val viewModel: DetailShopViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.loadShop(requireContext(), args.shopId)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailShopBinding.inflate(inflater, container, false)
        shop = viewModel.shop.value!!
        setData(shop)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setListeners()
    }

    private fun setData(shop: Shop) {
        binding.textViewName.text = shop.getName()
        binding.buttonAddress.text = shop.getAddress()
        binding.buttonPhone.text = shop.getPhone()
        binding.buttonWebsite.text = shop.getWebsite()
    }

    private fun setListeners() {
        val address = shop.getAddress()
        val phone = shop.getPhone()
        val website = shop.getWebsite()

        if (address.isNotEmpty()) {
            binding.buttonAddress.setOnClickListener {
                showDialog(requireContext(), "Address", "Open navigation app") { launchMapIntent(address) }
            }
        }

        if (phone.isNotEmpty()) {
            binding.buttonPhone.setOnClickListener {
                showDialog(requireContext(), "Phone", "Call this phone") { launchDialIntent(shop.getPhone()) }
            }
        }

        if (website.isNotEmpty()) {
            binding.buttonWebsite.setOnClickListener {
                showDialog(requireContext(), "Website", "Navigate to this website"){ launchWebIntent(shop.getWebsite()) }
            }
        }
    }
}