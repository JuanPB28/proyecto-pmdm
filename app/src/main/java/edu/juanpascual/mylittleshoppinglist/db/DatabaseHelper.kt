package edu.juanpascual.mylittleshoppinglist.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import edu.juanpascual.mylittleshoppinglist.models.Product
import edu.juanpascual.mylittleshoppinglist.models.Shop
import edu.juanpascual.mylittleshoppinglist.models.ShoppingList

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "shopping_list.db"
        private const val DATABASE_VERSION = 1

        const val TABLE_SHOP = "shop"
        const val SHOP_COLUMN_ID = "_id"
        const val SHOP_COLUMN_NAME = "name"
        const val SHOP_COLUMN_ADDRESS = "address"
        const val SHOP_COLUMN_PHONE = "phone"
        const val SHOP_COLUMN_WEBSITE = "website"

        const val TABLE_LIST = "list"
        const val LIST_COLUMN_ID = "_id"
        const val LIST_COLUMN_NAME = "name"
        const val LIST_COLUMN_STATUS = "status"
        const val LIST_COLUMN_SHOP_ID = "shop_id"

        const val TABLE_PRODUCT = "product"
        const val PRODUCT_COLUMN_ID = "_id"
        const val PRODUCT_COLUMN_NAME = "name"
        const val PRODUCT_COLUMN_PRICE = "price"
        const val PRODUCT_COLUMN_QUANTITY = "quantity"
        const val PRODUCT_COLUMN_LIST_ID = "list_id"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createShopTable = ("CREATE TABLE $TABLE_SHOP ("
                + "$SHOP_COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "$SHOP_COLUMN_NAME TEXT,"
                + "$SHOP_COLUMN_ADDRESS TEXT,"
                + "$SHOP_COLUMN_PHONE TEXT,"
                + "$SHOP_COLUMN_WEBSITE TEXT"
                + ")")
        db?.execSQL(createShopTable)

        val createListTable = ("CREATE TABLE $TABLE_LIST ("
                + "$LIST_COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "$LIST_COLUMN_NAME TEXT,"
                + "$LIST_COLUMN_STATUS TEXT DEFAULT 'pending'," //pending or done
                + "$LIST_COLUMN_SHOP_ID INTEGER,"
                + "FOREIGN KEY($LIST_COLUMN_SHOP_ID) REFERENCES $TABLE_SHOP($SHOP_COLUMN_ID)"
                + ")")
        db?.execSQL(createListTable)

        val createProductTable = ("CREATE TABLE $TABLE_PRODUCT ("
                + "$PRODUCT_COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "$PRODUCT_COLUMN_NAME TEXT,"
                + "$PRODUCT_COLUMN_PRICE REAL,"
                + "$PRODUCT_COLUMN_QUANTITY INTEGER,"
                + "$PRODUCT_COLUMN_LIST_ID INTEGER,"
                + "FOREIGN KEY($PRODUCT_COLUMN_LIST_ID) REFERENCES $TABLE_LIST($LIST_COLUMN_ID)"
                + ")")
        db?.execSQL(createProductTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_SHOP")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_LIST")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_PRODUCT")
        onCreate(db)
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Helpers
    ///////////////////////////////////////////////////////////////////////////////////////////////

    private fun parseCursorToShop(cursor: Cursor): Shop {
        val id = cursor.getInt(cursor.getColumnIndexOrThrow(SHOP_COLUMN_ID))
        val name = cursor.getString(cursor.getColumnIndexOrThrow(SHOP_COLUMN_NAME))
        val address = cursor.getString(cursor.getColumnIndexOrThrow(SHOP_COLUMN_ADDRESS))
        val phone = cursor.getString(cursor.getColumnIndexOrThrow(SHOP_COLUMN_PHONE))
        val website = cursor.getString(cursor.getColumnIndexOrThrow(SHOP_COLUMN_WEBSITE))
        return Shop(id, name, address, phone, website)
    }

    private fun parseCursorToShoppingList(cursor: Cursor): ShoppingList {
        val id = cursor.getInt(cursor.getColumnIndexOrThrow(LIST_COLUMN_ID))
        val name = cursor.getString(cursor.getColumnIndexOrThrow(LIST_COLUMN_NAME))
        val status = cursor.getString(cursor.getColumnIndexOrThrow(LIST_COLUMN_STATUS))
        val shopId = cursor.getInt(cursor.getColumnIndexOrThrow(LIST_COLUMN_SHOP_ID))
        return ShoppingList(id, name, status, shopId)
    }

    private fun parseCursorToProduct(cursor: Cursor): Product {
        val id = cursor.getInt(cursor.getColumnIndexOrThrow(PRODUCT_COLUMN_ID))
        val name = cursor.getString(cursor.getColumnIndexOrThrow(PRODUCT_COLUMN_NAME))
        val price = cursor.getDouble(cursor.getColumnIndexOrThrow(PRODUCT_COLUMN_PRICE))
        val quantity = cursor.getInt(cursor.getColumnIndexOrThrow(PRODUCT_COLUMN_QUANTITY))
        val listId = cursor.getInt(cursor.getColumnIndexOrThrow(PRODUCT_COLUMN_LIST_ID))
        return Product(id, name, price, quantity, listId)
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // CRUD operations for Shop
    ///////////////////////////////////////////////////////////////////////////////////////////////

    fun insertShop(shop: Shop): Long {
        val name = shop.getName()
        val address = shop.getAddress()
        val phone = shop.getPhone()
        val website = shop.getWebsite()

        val db = this.writableDatabase
        val contentValues = ContentValues().apply {
            put(SHOP_COLUMN_NAME, name)
            put(SHOP_COLUMN_ADDRESS, address)
            put(SHOP_COLUMN_PHONE, phone)
            put(SHOP_COLUMN_WEBSITE, website)
        }
        return db.insert(TABLE_SHOP, null, contentValues)
    }

    fun getAllShops(): MutableList<Shop> {
        val shops = mutableListOf<Shop>()
        val db = this.readableDatabase

        db.rawQuery("SELECT * FROM $TABLE_SHOP", null).use { cursor ->
            while (cursor.moveToNext()) {
                shops.add(parseCursorToShop(cursor))
            }
        }
        return shops
    }

    fun getShopById(id: Int): Shop? {
        var shop: Shop? = null
        val db = this.readableDatabase

        db.rawQuery("SELECT * FROM $TABLE_SHOP WHERE $SHOP_COLUMN_ID = ?", arrayOf(id.toString())).use { cursor ->
            while (cursor.moveToNext()) {
                shop = parseCursorToShop(cursor)
            }
        }
        return shop
    }

    fun updateShop(id: Int, shop: Shop) {
        val name = shop.getName()
        val address = shop.getAddress()
        val phone = shop.getPhone()
        val website = shop.getWebsite()

        val db = this.writableDatabase
        val contentValues = ContentValues().apply {
            put(SHOP_COLUMN_NAME, name)
            put(SHOP_COLUMN_ADDRESS, address)
            put(SHOP_COLUMN_PHONE, phone)
            put(SHOP_COLUMN_WEBSITE, website)
        }
        db.update(TABLE_SHOP, contentValues, "$SHOP_COLUMN_ID = ?", arrayOf(id.toString()))
    }

    fun deleteShop(id: Int) {
        val db = this.writableDatabase
        db.delete(TABLE_SHOP, "$SHOP_COLUMN_ID = ?", arrayOf(id.toString()))
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // CRUD operations for ShoppingList
    ///////////////////////////////////////////////////////////////////////////////////////////////

    fun insertShoppingList(list: ShoppingList): Long {
        val name = list.getName()
        val shopId = list.getShopId()

        val db = this.writableDatabase
        val contentValues = ContentValues().apply {
            put(LIST_COLUMN_NAME, name)
            put(LIST_COLUMN_SHOP_ID, shopId)
        }
        return db.insert(TABLE_LIST, null, contentValues)
    }

    fun getAllShoppingLists(): MutableList<ShoppingList> {
        val lists = mutableListOf<ShoppingList>()
        val db = this.readableDatabase

        db.rawQuery("SELECT * FROM $TABLE_LIST", null).use { cursor ->
            while (cursor.moveToNext()) {
                lists.add(parseCursorToShoppingList(cursor))
            }
        }
        return lists
    }

    fun getHomeLists(): MutableList<ShoppingList> {
        val lists = mutableListOf<ShoppingList>()
        val db = this.readableDatabase

        db.rawQuery("SELECT * FROM $TABLE_LIST WHERE $LIST_COLUMN_STATUS = 'pending'", null).use { cursor ->
            while (cursor.moveToNext()) {
                lists.add(parseCursorToShoppingList(cursor))
            }
        }
        return lists
    }

    fun getHistory(): MutableList<ShoppingList> {
        val lists = mutableListOf<ShoppingList>()
        val db = this.readableDatabase

        db.rawQuery("SELECT * FROM $TABLE_LIST WHERE $LIST_COLUMN_STATUS = 'done'", null).use { cursor ->
            while (cursor.moveToNext()) {
                lists.add(parseCursorToShoppingList(cursor))
            }
        }
        return lists
    }

    fun getShoppingListById(id: Int): ShoppingList? {
        var list: ShoppingList? = null
        val db = this.readableDatabase

        db.rawQuery("SELECT * FROM $TABLE_LIST WHERE $LIST_COLUMN_ID = ?", arrayOf(id.toString())).use { cursor ->
            while (cursor.moveToNext()) {
                list = parseCursorToShoppingList(cursor)
            }
        }
        return list
    }

    fun updateShoppingList(id: Int, list: ShoppingList) {
        val name = list.getName()
        val status = list.getStatus()
        val shopId = list.getShopId()

        val db = this.writableDatabase

        val contentValues = ContentValues().apply {
            put(LIST_COLUMN_NAME, name)
            put(LIST_COLUMN_STATUS, status)
            put(LIST_COLUMN_SHOP_ID, shopId)
        }
        db.update(TABLE_LIST, contentValues, "$LIST_COLUMN_ID = ?", arrayOf(id.toString()))
    }

    fun deleteShoppingList(id: Int) {
        val db = this.writableDatabase
        db.delete(TABLE_LIST, "$LIST_COLUMN_ID = ?", arrayOf(id.toString()))
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // CRUD operations for Product
    ///////////////////////////////////////////////////////////////////////////////////////////////

    fun insertProduct(product: Product): Long {
        val name = product.getName()
        val price = product.getPrice()
        val quantity = product.getQuantity()
        val listId = product.getListId()

        val db = this.writableDatabase

        val contentValues = ContentValues().apply {
            put(PRODUCT_COLUMN_NAME, name)
            put(PRODUCT_COLUMN_PRICE, price)
            put(PRODUCT_COLUMN_QUANTITY, quantity)
            put(PRODUCT_COLUMN_LIST_ID, listId)
        }
        return db.insert(TABLE_PRODUCT, null, contentValues)
    }

    fun getAllProducts(): MutableList<Product> {
        val products = mutableListOf<Product>()
        val db = this.readableDatabase

        db.rawQuery("SELECT * FROM $TABLE_PRODUCT", null).use { cursor ->
            while (cursor.moveToNext()) {
                products.add(parseCursorToProduct(cursor))
            }
        }
        return products
    }

    fun getProductById(id: Int): Product? {
        var product: Product? = null
        val db = this.readableDatabase

        db.rawQuery("SELECT * FROM $TABLE_PRODUCT WHERE $PRODUCT_COLUMN_ID = ?", arrayOf(id.toString())).use { cursor ->
            while (cursor.moveToNext()) {
                product = parseCursorToProduct(cursor)
            }
        }
        return product
    }

    fun getProductsByListId(listId: Int): MutableList<Product> {
        val products = mutableListOf<Product>()
        val db = this.readableDatabase

        db.rawQuery("SELECT * FROM $TABLE_PRODUCT WHERE $PRODUCT_COLUMN_LIST_ID = ?", arrayOf(listId.toString())).use { cursor ->
            while (cursor.moveToNext()) {
                products.add(parseCursorToProduct(cursor))
            }
        }
        return products
    }

    fun updateProduct(id: Int, product: Product) {
        val name = product.getName()
        val price = product.getPrice()
        val quantity = product.getQuantity()
        val listId = product.getListId()

        val db = this.writableDatabase

        val contentValues = ContentValues().apply {
            put(PRODUCT_COLUMN_NAME, name)
            put(PRODUCT_COLUMN_PRICE, price)
            put(PRODUCT_COLUMN_QUANTITY, quantity)
            put(PRODUCT_COLUMN_LIST_ID, listId)
        }
        db.update(TABLE_PRODUCT, contentValues, "$PRODUCT_COLUMN_ID = ?", arrayOf(id.toString()))
    }

    fun deleteProduct(id: Int) {
        val db = this.writableDatabase
        db.delete(TABLE_PRODUCT, "$PRODUCT_COLUMN_ID = ?", arrayOf(id.toString()))
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Other methods
    ///////////////////////////////////////////////////////////////////////////////////////////////

    fun getTotalPriceByListId(listId: Int): Double {
        var totalPrice = 0.0
        val db = this.readableDatabase

        val cursor = db.rawQuery("SELECT * FROM $TABLE_PRODUCT WHERE $PRODUCT_COLUMN_LIST_ID = ?", arrayOf(listId.toString())).use { cursor ->
            while (cursor.moveToNext()) {
                totalPrice += cursor.getDouble(cursor.getColumnIndexOrThrow(PRODUCT_COLUMN_PRICE)) * cursor.getInt(cursor.getColumnIndexOrThrow(PRODUCT_COLUMN_QUANTITY))
            }
        }

        return totalPrice
    }

}