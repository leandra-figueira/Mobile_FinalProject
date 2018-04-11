package net.lfernandes.myapplication

import android.media.MediaRouter

/**
 * Created by Leandra Fernandes on 2018-03-17.
 */
class Note {
    companion object Factory {
        fun create(): Note = Note()
    }
    var id: String? = null
    var title: String? = null
    var category: String? = null
    var location: String? = null
    var description: String? = null
}