package com.kotlineproject.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import com.kotlineproject.R

/**
 * Created by CRAFT BOX on 9/26/2016.
 */
class AddtocartDialog : DialogFragment() {

    internal var context: Context? = null
    lateinit var submit: Button
    lateinit var d: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.context = activity

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.dialog_example, container, false)
        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)
        submit = v.findViewById<View>(R.id.submit_btn) as Button

        submit.setOnClickListener {
            d.dismiss()
        }
        return v
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val inflater = activity!!.layoutInflater
        val root = inflater.inflate(R.layout.dialog_example, null)
        d = Dialog(activity!!)
        d.requestWindowFeature(Window.FEATURE_NO_TITLE)
        d.setContentView(root)
        d.window!!.setTitleColor(resources.getColor(R.color.colorPrimary))
        d.window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        return d
    }

    companion object {

        fun newInstance(): AddtocartDialog {
            val f = AddtocartDialog()
            val args = Bundle()

            f.arguments = args
            return f
        }
    }

}
