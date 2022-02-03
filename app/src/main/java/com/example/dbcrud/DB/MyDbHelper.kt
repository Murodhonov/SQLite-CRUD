package com.example.dbcrud.DB

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.ilhomjon.databasecrud.Models.Customer
import com.ilhomjon.databasecrud.Models.Employee
import com.ilhomjon.databasecrud.Models.Orders
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MyDbHelper(context: Context)
    :SQLiteOpenHelper(context, Const.DB_NAME, null,  Const.DB_VERSION), DbInterface {
    override fun onCreate(p0: SQLiteDatabase?) {
        val queryCustomer = "create table ${Const.CUSTOMER_TABLE} (${Const.CUSTOMER_ID} integer not null primary key autoincrement unique, ${Const.CUSTOMER_NAME} text not null)"
        p0?.execSQL(queryCustomer)
        val queryEmployee = "create table ${Const.EMPLOYEE_TABLE} (${Const.EMPLOYEE_ID} integer not null primary key autoincrement unique, ${Const.EMPLOYEE_NAME} text not null)"
        p0?.execSQL(queryEmployee)
        val queryOrder = "create table ${Const.ORDERS_TABLE} (${Const.ORDERS_ID} integer not null primary key autoincrement unique, ${Const.ORDER_CUSTOMER_ID} integer not null, ${Const.ORDER_EMPLOYEE_ID} integer not null, ${Const.ORDER_DATE} text not null, FOREIGN KEY (${Const.ORDER_CUSTOMER_ID}) references ${Const.CUSTOMER_TABLE} (${Const.CUSTOMER_ID}), foreign key (${Const.ORDER_EMPLOYEE_ID}) references ${Const.EMPLOYEE_TABLE} (${Const.EMPLOYEE_ID}))"
        p0?.execSQL(queryOrder)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {

    }

    override fun addCustomer(customer: Customer) {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(Const.CUSTOMER_NAME, customer.name)
        database.insert(Const.CUSTOMER_TABLE, null, contentValues)
        database.close()
    }

    override fun editCustomer(customer: Customer): Int {
        return 0
    }

    override fun deleteCustomer(customer: Customer) {

    }

    override fun getAllCustomer(): List<Customer> {
        val list = ArrayList<Customer>()
        val query = "select * from ${Const.CUSTOMER_TABLE}"
        val database = this.readableDatabase
        val cursor = database.rawQuery(query, null)

        if (cursor.moveToFirst()){
            do {
                val customer = Customer()
                customer.id = cursor.getInt(0)
                customer.name = cursor.getString(1)
                list.add(customer)
            }while (cursor.moveToNext())
        }
        return list
    }

    override fun getCustomerById(id: Int): Customer {
        val database = this.readableDatabase
        val cursor = database.query(
            Const.CUSTOMER_TABLE,
            arrayOf(
                Const.CUSTOMER_ID,
                Const.CUSTOMER_NAME
            ),
            "${Const.CUSTOMER_ID} = ?",
            arrayOf(id.toString()),
            null, null, null
        )

        cursor.moveToFirst()
        return Customer(
            cursor.getInt(0),
            cursor.getString(1)
        )
    }

    override fun addEmployee(employee: Employee) {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(Const.EMPLOYEE_NAME, employee.name)
        database.insert(Const.EMPLOYEE_TABLE, null, contentValues)
        database.close()
    }

    override fun editEmployee(employee: Employee): Int {
        return 0
    }

    override fun deleteEmployee(employee: Employee) {

    }

    override fun getAllEmployee(): List<Employee> {
        val list = ArrayList<Employee>()
        val query = "select * from ${Const.EMPLOYEE_TABLE}"
        val database = this.readableDatabase
        val cursor = database.rawQuery(query, null)

        if (cursor.moveToFirst()){
            do {
                val employee = Employee()
                employee.id = cursor.getInt(0)
                employee.name = cursor.getString(1)
                list.add(employee)
            }while (cursor.moveToNext())
        }
        return list
    }

    override fun getEmployeeById(id: Int): Employee {
        val database = this.readableDatabase
        val cursor = database.query(
            Const.EMPLOYEE_TABLE,
            arrayOf(
                Const.EMPLOYEE_ID,
                Const.EMPLOYEE_NAME
            ),
            "${Const.EMPLOYEE_ID} = ?",
            arrayOf(id.toString()),
            null, null, null
        )

        cursor.moveToFirst()
        return Employee(
            cursor.getInt(0),
            cursor.getString(1)
        )
    }

    @SuppressLint("SimpleDateFormat")
    override fun addOrder(orders: Orders) {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(Const.ORDER_CUSTOMER_ID, orders.customer?.id)
        contentValues.put(Const.ORDER_EMPLOYEE_ID, orders.employee?.id)
        val simpleDateFormat = SimpleDateFormat("dd.MM.yyyy").format(Date())
        contentValues.put(Const.ORDER_DATE, simpleDateFormat)

        database.insert(Const.ORDERS_TABLE, null, contentValues)
        database.close()
    }

    override fun editOrder(orders: Orders): Int {
        return 0
    }

    override fun deleteOrder(orders: Orders) {

    }

    override fun getAllOrder(): List<Orders> {
        val list = ArrayList<Orders>()
        val query = "select * from ${Const.ORDERS_TABLE}"
        val database = this.readableDatabase
        val cursor = database.rawQuery(query, null)

        if (cursor.moveToFirst()){
            do {
                val orders = Orders()
                orders.id = cursor.getInt(0)
                orders.customer = getCustomerById(cursor.getInt(1))
                orders.employee = getEmployeeById(cursor.getInt(2))
                orders.date = cursor.getString(3)
            }while (cursor.moveToNext())
        }
        return list
    }



}