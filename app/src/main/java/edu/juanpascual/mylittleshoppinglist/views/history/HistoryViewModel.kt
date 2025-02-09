package edu.juanpascual.mylittleshoppinglist.views.history

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData
import edu.juanpascual.mylittleshoppinglist.db.DatabaseHelper
import edu.juanpascual.mylittleshoppinglist.models.ShoppingList

class HistoryViewModel : ViewModel() {
    private lateinit var dbHelper: DatabaseHelper

    private val _historyList = MutableLiveData<MutableList<ShoppingList>>()
    val historyList: LiveData<MutableList<ShoppingList>> get() = _historyList

    fun loadHistory(context: Context) {
        dbHelper = DatabaseHelper(context)
        _historyList.value = dbHelper.getHistory()
    }

    fun deleteList(context: Context , id: Int) {
        dbHelper = DatabaseHelper(context)
        dbHelper.deleteShoppingList(id)
        _historyList.value = dbHelper.getHistory()
    }

}