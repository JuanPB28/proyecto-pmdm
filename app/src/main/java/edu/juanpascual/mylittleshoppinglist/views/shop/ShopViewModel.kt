package edu.juanpascual.mylittleshoppinglist.views.shop

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import edu.juanpascual.mylittleshoppinglist.db.DatabaseHelper
import edu.juanpascual.mylittleshoppinglist.models.Shop

class ShopViewModel : ViewModel() {
    private lateinit var dbHelper: DatabaseHelper

    private val _shops = MutableLiveData<MutableList<Shop>>()
    val shops: LiveData<MutableList<Shop>> get() = _shops

    fun loadShops(context: Context) {
        dbHelper = DatabaseHelper(context)
        _shops.value = dbHelper.getAllShops()
    }

    fun addShop(context: Context, shop: Shop) {
        dbHelper = DatabaseHelper(context)
        dbHelper.insertShop(shop)
        _shops.value = dbHelper.getAllShops()
    }

    fun updateShop(context: Context, id: Int, shop: Shop) {
        dbHelper = DatabaseHelper(context)
        dbHelper.updateShop(id, shop)
        _shops.value = dbHelper.getAllShops()
    }

    fun deleteShop(context: Context, id: Int) {
        dbHelper = DatabaseHelper(context)
        dbHelper.deleteShop(id)
        _shops.value = dbHelper.getAllShops()
    }

}