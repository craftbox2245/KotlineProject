package com.kotlineproject.dialog

import android.app.ProgressDialog
import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import android.support.design.widget.BottomSheetDialogFragment
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView


import com.kotlineproject.GlobalElements
import com.kotlineproject.R

import org.json.JSONObject

/**
 * Created by mayowa.adegeye on 28/06/2016.
 */
class CustomProductdetailBottomSheetDialog : BottomSheetDialogFragment() {

    var uid: String? = null
    var _pincode: String? = null
    lateinit var check_msg: TextView
    internal var type: String? = null //todo type = 1=productdetail , 2 = cartactivity

    interface InterfaceCommunicator {
        fun sendRequestCode(code: Int, pincode: String)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val b = arguments
        uid = b!!.getString("uid")
        type = b.getString("type")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.layout_custom_product_detail_bottom_sheet, container, false)
        val pin = v.findViewById<View>(R.id.custom_bottom_sheet_p_d_pin) as EditText
        val check = v.findViewById<View>(R.id.custom_bottom_sheet_p_d_check) as Button
        check_msg = v.findViewById<View>(R.id.custom_bottom_sheet_p_d_check_msg) as TextView
        check.setOnClickListener {
            if (pin.text.toString().length < 6) {
                pin.error = "Please enter pin code"
            } else {
                val imm = activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(pin.windowToken, 0)
                dismiss()
            }
        }
        return v
    }

    companion object {

        fun getInstance(uid: String, type: String): CustomProductdetailBottomSheetDialog {
            val f = CustomProductdetailBottomSheetDialog()
            val b = Bundle()
            b.putString("uid", uid)
            b.putString("type", type)
            f.arguments = b
            return f
        }
    }

    /*class All extends AsyncTask<String, Void, JSONObject> {
        ProgressDialog pd;
        String temp = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(getContext());
            pd.setTitle("Please Wait");
            pd.setMessage("Loading");
            pd.setCancelable(false);
            pd.show();
        }

        @Override
        protected JSONObject doInBackground(String... args) {

            temp = args[0];
            if (temp.equals("pincode")) {
                UserFunction uf = new UserFunction();
                JSONObject json = uf.CheckDeliveryPincode(_pincode);
                return json;
            } else if (temp.equals("update_pincode")) {
                UserFunction uf = new UserFunction();
                JSONObject json = uf.Updatedeliverypincode(_pincode, uid);
                return json;
            }
            return null;
        }

        @Override
        protected void onPostExecute(JSONObject json) {
            super.onPostExecute(json);
            try {
                pd.dismiss();
                if (temp.equals("pincode")) {
                    check_msg.setVisibility(View.VISIBLE);
                    if (json.getInt("ack") == 1) {
                        CustomProductdetailBottomSheetDialog.InterfaceCommunicator i = (CustomProductdetailBottomSheetDialog.InterfaceCommunicator) getActivity();
                        i.sendRequestCode(1, _pincode);
                        check_msg.setText(Html.fromHtml("<font color='#20d2bb'>" + json.getString("ack_msg") + "</font>"));
                    } else {
                        check_msg.setText(Html.fromHtml("<font color='#fd5459'>" + json.getString("ack_msg") + "</font>"));
                    }
                } else if (temp.equals("update_pincode")) {
                    check_msg.setVisibility(View.VISIBLE);
                    if (json.getInt("ack") == 1) {
                        CustomProductdetailBottomSheetDialog.InterfaceCommunicator i = (CustomProductdetailBottomSheetDialog.InterfaceCommunicator) getActivity();
                        i.sendRequestCode(1, _pincode);
                        check_msg.setText(Html.fromHtml("<font color='#20d2bb'>" + json.getString("ack_msg") + "</font>"));
                    } else {
                        check_msg.setText(Html.fromHtml("<font color='#fd5459'>" + json.getString("ack_msg") + "</font>"));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }*/
}
