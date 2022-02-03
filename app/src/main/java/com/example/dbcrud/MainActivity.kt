package com.example.dbcrud

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.dbcrud.DB.MyDbHelper
import com.ilhomjon.databasecrud.Models.Customer
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn1.setOnClickListener {
            val name = name_customer.text.toString()
            val db = MyDbHelper(this)
            db.addCustomer(Customer(name))
            Toast.makeText(this, "saved", Toast.LENGTH_SHORT).show()
            onStart()
        }

    }

    override fun onStart() {
        super.onStart()
        val db = MyDbHelper(this)
        val list = db.getAllCustomer()
        rv1.adapter = AdapterRv(list)

    }
}