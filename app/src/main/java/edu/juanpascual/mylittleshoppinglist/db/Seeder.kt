package edu.juanpascual.mylittleshoppinglist.db

import android.content.Context
import edu.juanpascual.mylittleshoppinglist.models.Shop

fun addExampleShops(context: Context) {
    val dbHelper = DatabaseHelper(context)

    dbHelper.insertShop(Shop("Supermercado Elche", "Calle Mayor, 123", "966 12 34 56", "www.supermercadoelche.es"))
    dbHelper.insertShop(Shop("Tienda de Ropa Moderna", "Avenida Libertad, 45", "966 98 76 54", "www.ropamoderna.com"))
    dbHelper.insertShop(Shop("La Librería", "Plaza de la Constitución, 7", "966 11 22 33", "www.lalibreria.es"))
    dbHelper.insertShop(Shop("Panadería Crujiente", "Calle del Sol, 89", "966 55 66 77", ""))
    dbHelper.insertShop(Shop("El Rincón del Artesano Local", "Travesía del Mercado, 1", "966 77 88 99", "www.artesanolocal.es"))
    dbHelper.insertShop(Shop("Electrodomésticos & Más", "C/ Alfonso XIII, nº 2", "966 44 55 66", "www.electrodomesticosymas.com"))
    dbHelper.insertShop(Shop("Restaurante Italiano 'La Pasta'", "Paseo Marítimo, s/n", "966 22 33 44", "www.lapasta.es"))
    dbHelper.insertShop(Shop("JOYERIA DIAMANTES", "CALLE ORO, 10", "966 88 99 00", "www.joyeriadiamantes.es"))
    dbHelper.insertShop(Shop("La Tienda de la Esquina", "Callejón sin Salida, 3", "966 33 44 55", ""))
    dbHelper.insertShop(Shop("Informática 24h", "Avenida de la Informática, 24", "966 66 77 88", "www.informatica24h.es"))
}