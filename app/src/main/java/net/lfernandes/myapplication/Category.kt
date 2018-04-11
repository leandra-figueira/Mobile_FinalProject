package net.lfernandes.myapplication

/**
 * Created by Leandra Fernandes on 2018-03-26.
 */
class Category{
    companion object Factory {
        fun create(): Category = Category()
    }
    var id: String? = null
    var name: String? = null
}


