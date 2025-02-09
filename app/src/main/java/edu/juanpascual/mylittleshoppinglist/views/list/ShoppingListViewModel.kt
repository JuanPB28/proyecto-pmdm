package edu.juanpascual.mylittleshoppinglist.views.list

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import edu.juanpascual.mylittleshoppinglist.db.DatabaseHelper
import edu.juanpascual.mylittleshoppinglist.models.Product

class ShoppingListViewModel : ViewModel() {
    private lateinit var dbHelper: DatabaseHelper

    private val _products = MutableLiveData<MutableList<Product>>()
    val products: LiveData<MutableList<Product>> get() = _products

    fun loadProducts(context: Context, shoppingListId: Int) {
        dbHelper = DatabaseHelper(context)
        _products.value = dbHelper.getProductsByListId(shoppingListId)
    }

    fun deleteProduct(context: Context, productId: Int) {
        dbHelper = DatabaseHelper(context)
        dbHelper.deleteProduct(productId)
        if (_products.value != null) {
            _products.value = dbHelper.getProductsByListId(_products.value!![0].getListId())
        }
    }

    fun deleteList(context: Context, listId: Int) {
        dbHelper = DatabaseHelper(context)
        val list = dbHelper.getShoppingListById(listId)
        if (list != null) {
            list.setStatus("done")
            dbHelper.updateShoppingList(listId, list)
        }
    }

    fun updateProduct(context: Context, product: Product) {
        dbHelper = DatabaseHelper(context)
        dbHelper.updateProduct(product.getId(), product)
        _products.value = dbHelper.getProductsByListId(product.getListId())
    }

    fun addProduct(context: Context, product: Product) {
        dbHelper = DatabaseHelper(context)
        dbHelper.insertProduct(product)
        _products.value = dbHelper.getProductsByListId(product.getListId())
    }

    fun getTotalPrice(): Double {
        var totalPrice = 0.0
        _products.value?.forEach { product ->
            totalPrice += product.getPrice() * product.getQuantity()
        }
        return totalPrice
    }

    fun getListId(): Int {
        return _products.value!![0].getListId()
    }
}