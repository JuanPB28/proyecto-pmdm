package edu.juanpascual.mylittleshoppinglist.utils

import android.content.Context
import android.widget.Toast
import com.google.android.material.dialog.MaterialAlertDialogBuilder

fun showDialog(
    context: Context,
    title: String,
    message: String,
    onPositiveClick: () -> Unit) {

    MaterialAlertDialogBuilder(context)
        .setTitle(title)
        .setMessage(message)
        .setNegativeButton("Cancel") { _, _ ->
            Toast.makeText(context, "Canceled", Toast.LENGTH_SHORT).show()
        }
        .setPositiveButton("Ok") { _, _ ->
            onPositiveClick()
        }
        .show()
}

fun deleteDialog(context: Context, onDelete: () -> Unit) {
    MaterialAlertDialogBuilder(context)
        .setTitle("Delete")
        .setMessage("Remove item")
        .setNegativeButton("Cancel") { _, _ ->
            Toast.makeText(context, "Canceled", Toast.LENGTH_SHORT).show()
        }
        .setPositiveButton("Delete") { _, _ ->
            onDelete()
            Toast.makeText(context, "Removed", Toast.LENGTH_SHORT).show()
        }
        .show()
}
