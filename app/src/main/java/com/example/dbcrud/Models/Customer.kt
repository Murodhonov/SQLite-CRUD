package com.ilhomjon.databasecrud.Models

class Customer {
    var id:Int? = null
    var name:String? = null

    constructor(id: Int?, name: String?) {
        this.id = id
        this.name = name
    }

    constructor(name: String?) {
        this.name = name
    }

    constructor()

    override fun toString(): String {
        return "Customer(id=$id, name=$name)"
    }
}