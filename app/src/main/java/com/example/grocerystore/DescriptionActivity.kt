package com.example.grocerystore

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar


class DescriptionActivity : AppCompatActivity() {

    private lateinit var toolbarInfo: Toolbar
    private val GALLARY_REQUEST = 1
    private lateinit var imageEditIV: ImageView
    private lateinit var productNameEditET: EditText
    private lateinit var priceEditET: EditText
    private lateinit var descriptionEditET: EditText
    private lateinit var saveEditBTN: Button

    var product: Product? = null
    var productList: MutableList<Product> = mutableListOf()
    var position: Int? = null
    var check: Boolean? = null
    var productUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_description)

        init()

        product = intent.extras?.getSerializable("product") as Product
        productList = intent.getSerializableExtra("productList") as MutableList<Product>
        position = intent.extras?.getInt("position")
        check = intent.extras?.getBoolean("check")

        val name = product?.productName
        val price = product?.price
        val description = product?.description
        val image: Uri? = Uri.parse(product?.image)

        imageEditIV.setImageURI(image)
        productNameEditET.setText(name)
        priceEditET.setText(price)
        descriptionEditET.setText(description)

        imageEditIV.setOnClickListener {
            val photoPickerIntent = Intent(Intent.ACTION_PICK)
            photoPickerIntent.type = "image/*"
            startActivityForResult(photoPickerIntent, GALLARY_REQUEST)
        }

        saveEditBTN.setOnClickListener {
            var check1 = check
            val imageEdit = productUri
            val editProduct = Product(
                productNameEditET.text.toString(),
                priceEditET.text.toString(),
                descriptionEditET.text.toString(),
                imageEdit.toString()
            )
            val list: MutableList<Product> = productList
            if (position != null) {
                swap(position!!, editProduct, productList)
            }
            productUri = null
            check1 = false
            val intent = Intent(this, StoreActivity::class.java)
            intent.putExtra("list", list as ArrayList<Product>)
            intent.putExtra("newCheck", check1)
            //Toast()
            startActivity(intent)

        }

    }

    private fun init() {
        toolbarInfo = findViewById(R.id.toolbar)
        setSupportActionBar(toolbarInfo)
        title = getString(R.string.product_info_text)

        imageEditIV = findViewById(R.id.imageEditTV)
        productNameEditET = findViewById(R.id.productNameEditET)
        priceEditET = findViewById(R.id.priceEditTV)
        descriptionEditET = findViewById(R.id.descriptionEditET)
        saveEditBTN = findViewById(R.id.saveEditBTN)
    }

    override fun onActivityResult(
        requestCode: Int, resultCode: Int, data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        imageEditIV = findViewById(R.id.imageEditTV)
        when (requestCode) {
            GALLARY_REQUEST -> if (resultCode == RESULT_OK) {
                productUri = data?.data
                imageEditIV.setImageURI(productUri)
            }
        }
    }

    private fun swap(position: Int, product: Product, productList: MutableList<Product>){
        productList.add(position + 1, product)
        productList.removeAt(position)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        val backMenu = menu?.findItem(R.id.backMenuMain)
        backMenu?.isVisible = true
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.exitMenuMain) {
            Toast.makeText(this, "Программа завершена", Toast.LENGTH_SHORT).show()
            finishAffinity()
        }
        if (item.itemId == R.id.backMenuMain) {
            backAction()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun backAction() {
        val intent = Intent(this, StoreActivity::class.java)
        intent.putExtra("product", product)
        intent.putExtra("productList", productList as ArrayList<Product>)
        intent.putExtra("position", position)
        intent.putExtra("check", check)
        finish()
    }

}