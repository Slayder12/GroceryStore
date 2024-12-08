package com.example.grocerystore

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class StoreActivity : AppCompatActivity(), Removable, Updatable {

    var product: Product? = null
    private val GALLARY_REQUEST = 1
    var productUri: Uri? = null
    var listAdapter: ListAdapter? = null
    var item: Int? = 0
    var productList: MutableList<Product> = mutableListOf()
    var check = true

    private lateinit var toolbarMain: Toolbar
    private lateinit var listViewLV: ListView
    private lateinit var saveBTN: Button
    private lateinit var imageIV: ImageView
    private lateinit var productNameET: EditText
    private lateinit var priceET: EditText
    private lateinit var descriptionET: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_store)

        init()

        listAdapter = ListAdapter(this@StoreActivity, productList)
        listViewLV.adapter = listAdapter
        listAdapter?.notifyDataSetChanged()

        imageIV.setOnClickListener {
            val photoPickerIntent = Intent(Intent.ACTION_PICK)
            photoPickerIntent.type = "image/*"
            startActivityForResult(photoPickerIntent, GALLARY_REQUEST)
        }

        saveBTN.setOnClickListener {
            createProduct()
            clearEditFields()
            listAdapter?.notifyDataSetChanged()
        }

        listViewLV.setOnItemClickListener { _, _, position, _ ->
            product = listAdapter!!.getItem(position)
            item = position
            val dialog = MyAlertDialog()
            val args = Bundle()
            args.putSerializable("product", product)
            dialog.arguments = args
            dialog.show(supportFragmentManager, "custom")
        }

    }

    private fun init() {
        toolbarMain = findViewById(R.id.toolbar)
        setSupportActionBar(toolbarMain)
        title = getString(R.string.product_list_text)

        listViewLV = findViewById(R.id.listViewLV)
        saveBTN = findViewById(R.id.saveBTN)
        imageIV = findViewById(R.id.imageTV)
        productNameET = findViewById(R.id.productNameET)
        priceET = findViewById(R.id.priceET)
        descriptionET = findViewById(R.id.descriptionET)
    }

    override fun onResume() {
        super.onResume()
        check = intent.extras?.getBoolean("newCheck") ?: true
        if (!check) {
            productList = intent.getSerializableExtra("list") as MutableList<Product>
            listAdapter = ListAdapter(this, productList)
            check = true
        }
        listViewLV.adapter = listAdapter
    }

    private fun createProduct() {
        val productName = productNameET.text.toString()
        val price = priceET.text.toString()
        val description = descriptionET.text.toString()
        val image = productUri.toString()
        val product = Product(productName, price, description, image)
        productList.add(product)
        productUri = null
    }

    private fun clearEditFields() {
        productNameET.text.clear()
        priceET.text.clear()
        descriptionET.text.clear()
        imageIV.setImageResource(R.drawable.icon_image)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        imageIV = findViewById(R.id.imageTV)
        when (requestCode) {
            GALLARY_REQUEST -> if (resultCode == RESULT_OK) {
                productUri = data?.data
                imageIV.setImageURI(productUri)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        val backMenu = menu?.findItem(R.id.backMenuMain)
            backMenu?.isVisible = false
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.exitMenuMain) {
            Toast.makeText(this, "Программа завершена", Toast.LENGTH_SHORT).show()
            finishAffinity()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun remove(product: Product) {
        listAdapter?.remove(product)
    }

    override fun update(product: Product) {
        val intent = Intent(this, DescriptionActivity::class.java)
        intent.putExtra("product", product)
        intent.putExtra("productList", this.productList as ArrayList<Product>)
        intent.putExtra("position", item)
        intent.putExtra("check", check)
        startActivity(intent)
    }

}