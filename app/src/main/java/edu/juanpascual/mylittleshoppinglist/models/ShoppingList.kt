package edu.juanpascual.mylittleshoppinglist.models

class ShoppingList(
    private var name: String,
    private var shopId: Int
) {
    private var id: Int = 0
    private var status: String = "pending"

    constructor(id: Int, name: String, status: String, shopId: Int) : this(name, shopId) {
        this.id = id
    }

    fun getId(): Int {
        return id
    }

    fun getName(): String {
        return name
    }

    fun getStatus(): String {
        return status
    }

    fun getShopId(): Int {
        return shopId
    }

    fun setName(name: String) {
        this.name = name
    }

    fun setStatus(status: String) {
        this.status = status
    }

    fun setShopId(shopId: Int) {
        this.shopId = shopId
    }

    override fun toString(): String {
        return "${id}_${name}"
    }
}