package com.example.grocerystore

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView

class ListAdapter(context: Context, productsList: MutableList<Product>) :
    ArrayAdapter<Product>(context, R.layout.list_item ,productsList) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        val person = getItem(position)
        if (view == null) {
            view = LayoutInflater.from(context)
                .inflate(R.layout.list_item, parent, false)
        }

        val imageViewIV = view?.findViewById<ImageView>(R.id.imageTV)
        val personNameTV = view?.findViewById<TextView>(R.id.productNameTV)
        val priceTV = view?.findViewById<TextView>(R.id.priceTV)

        imageViewIV?.setImageURI(Uri.parse(person?.image))
        personNameTV?.text = person?.productName
        priceTV?.text = person?.price

        return view!!
    }
}