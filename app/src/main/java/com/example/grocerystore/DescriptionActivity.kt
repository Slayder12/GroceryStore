package com.example.grocerystore

import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class DescriptionActivity : AppCompatActivity() {

    private lateinit var toolbarInfo: Toolbar

    private lateinit var imageEditIV: ImageView
    private lateinit var productNameEditET: EditText
    private lateinit var priceEditET: EditText
    private lateinit var descriptionEditET: EditText
    //private lateinit var saveEditEditBTN: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_description)

        init()

        val product: Product = intent.extras?.getSerializable("product") as Product
        //val productList = intent.getSerializableExtra("productList")
        //val position = intent.extras?.getInt("position")
        //val check = intent.extras?.getBoolean("check")

        val name = product.productName
        val price = product.price
        val description = product.description
        val image: Uri? = Uri.parse(product.image)


        imageEditIV.setImageURI(image)
        productNameEditET.setText(name)
        priceEditET.setText(price)
        descriptionEditET.setText(description)


    }

    private fun init() {
        toolbarInfo = findViewById(R.id.toolbar)
        setSupportActionBar(toolbarInfo)
        title = getString(R.string.product_info_text)

        imageEditIV = findViewById(R.id.imageEditTV)
        productNameEditET = findViewById(R.id.productNameEditET)
        priceEditET = findViewById(R.id.priceEditTV)
        descriptionEditET = findViewById(R.id.descriptionEditET)
        //saveEditEditBTN = findViewById(R.id.saveEditBTN)

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