package com.example.myappvolley

class productos(idprod: Int, nomProd: String, existencia: Int, precio:Float) {

    var idP: Int = 0
    var nomProd: String=""
    var existen: Int =0
    var prec: Float = 0.0f

    init{
        this.idP = idprod
        this.nomProd = nomProd
        this.existen = existencia
        this.prec = precio
    }
}