package com.kotlineproject.model

import java.io.Serializable

class GeneralModel : Serializable {

    constructor()

    var id: String = ""
    var name: String = ""

    // getter
        get() = field

    //setter
        set(value) {
            field = value
        }

}
