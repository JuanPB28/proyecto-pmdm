package edu.juanpascual.mylittleshoppinglist.utils

interface ItemClickListener<T> {
    fun onItemClick(item: T)
    fun onItemLongClick(item: T)
}