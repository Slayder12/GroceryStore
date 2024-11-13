package com.example.grocerystore

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class StoreActivity : AppCompatActivity() {

    private val GALLARY_REQUEST = 1
    var bitmap: Bitmap? = null
    var productList: MutableList<Product> = mutableListOf()

    private lateinit var toolbarMain: Toolbar
    private lateinit var listViewLV: ListView
    private lateinit var saveBTN: Button
    private lateinit var imageIV: ImageView
    private lateinit var productNameET: EditText
    private lateinit var priceET: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_store)

        init()

        imageIV.setOnClickListener {
            val photoPickerIntent = Intent(Intent.ACTION_PICK)
            photoPickerIntent.type = "image/*"
            startActivityForResult(photoPickerIntent, GALLARY_REQUEST)
        }

        saveBTN.setOnClickListener {

            createProduct()

            val listAdapter = ListAdapter(this@StoreActivity, productList)
            listViewLV.adapter = listAdapter
            listAdapter.notifyDataSetChanged()

            clearEditFields()

        }

    }

    private fun createProduct() {
        val productName = productNameET.text.toString()
        val price = priceET.text.toString()
        val image = bitmap
        val product = Product(productName, price, image)
        productList.add(product)
    }

    private fun clearEditFields() {
        productNameET.text.clear()
        priceET.text.clear()
        imageIV.setImageResource(R.drawable.icon_image)
    }

    private fun init() {
        toolbarMain = findViewById(R.id.toolbarMain)
        setSupportActionBar(toolbarMain)
        title = getString(R.string.product_list_text)

        listViewLV = findViewById(R.id.listViewLV)
        saveBTN = findViewById(R.id.saveBTN)
        imageIV = findViewById(R.id.imageIV)
        productNameET = findViewById(R.id.productNameET)
        priceET = findViewById(R.id.priceET)
    }

    override fun onActivityResult(requestCode: Int,resultCode: Int,data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        imageIV = findViewById(R.id.imageIV)
        when (requestCode) {
            GALLARY_REQUEST -> if (resultCode == RESULT_OK) {
                val selectedImage: Uri? = data?.data
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(contentResolver, selectedImage)
                    imageIV.setImageBitmap(bitmap)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                imageIV.setImageBitmap(bitmap)
               
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.exitMenuMain) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

}