package com.kotlineproject.model

import java.io.Serializable
import java.util.ArrayList

/**
 * Created by CRAFT BOX on 11/23/2016.
 */

class SubCategoryModel : Serializable {

    var id: Int = 0
    var cid: Int = 0
    var display_order: Int = 0
    var name: String =""
    var slug: String =""
    var image_path: String =""
    var banner_image_path: String =""
    var attr: String =""
    var descr: String =""

    lateinit var subsubcategory: ArrayList<SubSubCategoryModel>
}
