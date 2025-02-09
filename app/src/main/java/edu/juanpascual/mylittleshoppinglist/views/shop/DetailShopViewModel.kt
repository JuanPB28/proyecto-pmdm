package edu.juanpascual.mylittleshoppinglist.views.shop

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import edu.juanpascual.mylittleshoppinglist.db.DatabaseHelper
import edu.juanpascual.mylittleshoppinglist.models.Shop

class DetailShopViewModel : ViewModel() {
    private lateinit var dbHelper: DatabaseHelper

    private val _shop = MutableLiveData<Shop>()
    val shop: LiveData<Shop> get() = _shop

    fun loadShop(context: Context, id: Int) {
        dbHelper = DatabaseHelper(context)
        _shop.value = dbHelper.getShopById(id)
    }

    fun updateShop(context: Context, id: Int, shop: Shop) {
        dbHelper = DatabaseHelper(context)
        dbHelper.updateShop(id, shop)
        _shop.value = dbHelper.getShopById(id)
    }

    fun deleteShop(context: Context, id: Int) {
        dbHelper = DatabaseHelper(context)
        dbHelper.deleteShop(id)
    }
}