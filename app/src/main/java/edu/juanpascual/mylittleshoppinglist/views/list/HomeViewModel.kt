package edu.juanpascual.mylittleshoppinglist.views.list

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import edu.juanpascual.mylittleshoppinglist.db.DatabaseHelper
import edu.juanpascual.mylittleshoppinglist.models.Shop
import edu.juanpascual.mylittleshoppinglist.models.ShoppingList

class HomeViewModel : ViewModel() {
    private lateinit var dbHelper: DatabaseHelper

    private val _lists = MutableLiveData<MutableList<ShoppingList>>()
    val lists: LiveData<MutableList<ShoppingList>> get() = _lists

    fun loadLists(context: Context) {
        dbHelper = DatabaseHelper(context)
        _lists.value = dbHelper.getHomeLists()
    }

    fun addList(context: Context, list: ShoppingList) {
        dbHelper = DatabaseHelper(context)
        dbHelper.insertShoppingList(list)
        _lists.value = dbHelper.getHomeLists()
    }

    fun updateList(context: Context, id: Int, list: ShoppingList) {
        dbHelper = DatabaseHelper(context)
        dbHelper.updateShoppingList(id, list)
        _lists.value = dbHelper.getHomeLists()
    }

    fun deleteList(context: Context, id: Int) {
        dbHelper = DatabaseHelper(context)

        val list = dbHelper.getShoppingListById(id)
        if (list != null) {
            list.setStatus("done")
            dbHelper.updateShoppingList(id, list)
        }
        _lists.value = dbHelper.getHomeLists()
    }

    fun getShops(context: Context): MutableList<Shop> {
        dbHelper = DatabaseHelper(context)
        return dbHelper.getAllShops()
    }
}