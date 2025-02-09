package edu.juanpascual.mylittleshoppinglist.models

class Shop(
    private var name: String,
    private var address: String,
    private var phone: String,
    private var website: String
) {
    private var id: Int = 0

    constructor(id: Int, name: String, address: String, phone: String, website: String) : this(name, address, phone, website) {
        this.id = id
    }

    fun getId(): Int {
        return id
    }

    fun getName(): String {
        return name
    }

    fun getAddress(): String {
        return address
    }

    fun getPhone(): String {
        return phone
    }

    fun getWebsite(): String {
        return website
    }

    fun setName(name: String) {
        this.name = name
    }

    fun setAddress(address: String) {
        this.address = address
    }

    fun setPhone(phone: String) {
        this.phone = phone
    }

    fun setWebsite(website: String) {
        this.website = website
    }

    override fun toString(): String {
        return "${id}_${name}"
    }
}