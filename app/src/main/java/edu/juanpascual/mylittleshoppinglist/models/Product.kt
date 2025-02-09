package edu.juanpascual.mylittleshoppinglist.models

class Product(
    private var name: String,
    private var price: Double,
    private var quantity: Int,
    private var listId: Int
) {
    private var id: Int = 0

    constructor(id: Int, name: String, price: Double, quantity: Int, listId: Int) : this(name, price, quantity, listId) {
        this.id = id
    }

    fun getId(): Int {
        return id
    }

    fun getName(): String {
        return name
    }

    fun getPrice(): Double {
        return price
    }

    fun getQuantity(): Int {
        return quantity
    }

    fun getListId(): Int {
        return listId
    }

    fun setName(name: String) {
        this.name = name
    }

    fun setPrice(price: Double) {
        this.price = price
    }

    fun setQuantity(quantity: Int) {
        this.quantity = quantity
    }

    fun setListId(listId: Int) {
        this.listId = listId
    }

    override fun toString(): String {
        return "${id}_${name}"
    }
}