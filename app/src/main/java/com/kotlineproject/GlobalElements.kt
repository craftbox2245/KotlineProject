package com.kotlineproject

import android.app.AlertDialog
import android.app.Application
import android.content.Context
import android.content.DialogInterface
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Build
import android.support.multidex.MultiDex
import android.text.Html
import android.text.Spanned

import java.text.DateFormat
import java.text.DecimalFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import com.google.android.gms.internal.cm



/**
 * Created by CRAFT BOX on 10/7/2016.
 */
class GlobalElements : Application() {
    companion object {

        var message_pack_name = "INFOSM"
        var directory = "/neon/"
        var database_directory = "/neon/database/"
        var addExpanseStatus = 0
        var cart_item = 0
        var LowerLimit = 20
        var file: String? = null
        var version_update = ""
        var fileprovider_path = "com.neon.fileprovider"

        var UpdateItem = false

        fun isConnectingToInternet(context: Context): Boolean {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork = cm.activeNetworkInfo
            if (activeNetwork != null && activeNetwork.isConnected) {
                val networkType = activeNetwork.type
                return networkType == ConnectivityManager.TYPE_WIFI || networkType == ConnectivityManager.TYPE_MOBILE
            } else {
                return false
            }
        }

        fun showDialog(context: Context) {
            try {
                val alertDialog = AlertDialog.Builder(context).create()
                // Set Dialog Title
                alertDialog.setTitle("Internet Connection")
                // Set Dialog Message
                alertDialog.setMessage("Please check your internet connection ..")
                // Set OK Button
                alertDialog.setButton("OK") { dialog, which -> }
                // Show Alert Message
                alertDialog.show()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        fun compareDate(fromDate: String, formatOfDate: String): Boolean {
            var success = false
            try {
                if (fromDate == "") {
                    return false
                } else {
                    val sdf = SimpleDateFormat("dd-MM-yyyy")
                    //Date date1 = sdf.parse("31-03-2016");
                    val date1 = sdf.parse("" + fromDate)
                    val date2 = sdf.parse("" + sdf.format(Date()))
                    if (date1 == date2) {
                        success = true
                    } else {
                        success = false
                    }
                }

            } catch (ex: ParseException) {
                ex.printStackTrace()
                return success
            }

            return success
        }

        fun FirstRemoveZero(text: String): String {
            var text = text
            text = text.replaceFirst("^0-*".toRegex(), "")
            return text
        }

        fun getDate(date: Date, format: String): String {
            val dateFormat = SimpleDateFormat(format)
            // Messages.debugOnLog("getDateToString","-->"+dateFormat.format(date));
            return dateFormat.format(date)
        }

        fun getDateFrom_DD_MM_YYYY(get_date: String): String {

            var outputFormat: DateFormat? = null
            var date: Date? = null
            try {
                val dateFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.US)
                outputFormat = SimpleDateFormat("dd-MM-yyyy")
                date = dateFormatter.parse("" + get_date)
            } catch (e: Exception) {
                e.printStackTrace()
                return ""
            }

            return outputFormat.format(date)
        }

        fun getDateFrom_YYYY_MM_DD(get_date: String): String {

            var outputFormat: DateFormat? = null
            var date: Date? = null
            try {
                val dateFormatter = SimpleDateFormat("dd-MM-yyyy", Locale.US)
                outputFormat = SimpleDateFormat("yyyy-MM-dd")
                date = dateFormatter.parse("" + get_date)
            } catch (e: Exception) {
                e.printStackTrace()
                return ""
            }

            return outputFormat.format(date)
        }

        fun beforeDate(fromDate: String, ToDate: String, formatOfDate: String): Boolean {
            val success = false
            val sdf = SimpleDateFormat(formatOfDate)
            try {
                return if (sdf.parse(fromDate).before(sdf.parse(ToDate)) || sdf.parse(fromDate).compareTo(sdf.parse(ToDate)) == 0) {
                    true
                } else {
                    false
                }
            } catch (e: ParseException) {
                e.printStackTrace()
            }

            return success
        }

        var c = Calendar.getInstance()
        var cu_date_time = SimpleDateFormat("dd-MM-yyyy hh;mm;ss a")
        var datetime = cu_date_time.format(c.time)

        var cu_date = SimpleDateFormat("dd-MM-yyyy")
        var current_date = cu_date.format(c.time)

        var cu_date_yyyy = SimpleDateFormat("yyyy-MM-dd")
        var current_date_yyyy = cu_date_yyyy.format(c.time)

        var cu_date_yyyy_time = SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
        var current_date_yyyy_time = cu_date_yyyy_time.format(c.time)


        fun monthOfdate(month: Int): String? {
            var first_last_date: String? = null
            try {
                val sdf = SimpleDateFormat("dd-MM-yyyy")
                val cal = Calendar.getInstance()
                cal.set(Calendar.MONTH, month - 1)
                cal.set(Calendar.DATE, 1)
                val firstDateOfPreviousMonth = cal.time
                cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DATE)) // changed calendar to cal
                val lastDateOfPreviousMonth = cal.time

                first_last_date = "" + sdf.format(firstDateOfPreviousMonth) + "," + sdf.format(lastDateOfPreviousMonth)
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return first_last_date
        }

        fun getNumber(text: String): String {
            try {
                return text.replace("[^0-9]".toRegex(), "")
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return ""
        }

        fun getRemoveLastComma(text: String): String {
            var refine_txt: String? = null

            try {
                if (text == "") {
                    return ""
                } else {
                    refine_txt = text.substring(0, text.lastIndexOf(","))
                    return refine_txt
                }


            } catch (e: Exception) {
                e.printStackTrace()
                return ""
            }

        }

        fun fromHtml(source: String): Spanned {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Html.fromHtml(source, Html.FROM_HTML_MODE_LEGACY)
            } else {
                Html.fromHtml(source)
            }
        }

        fun versionCheck(): Boolean {
            return Build.VERSION.SDK_INT >= Build.VERSION_CODES.N
        }

        fun Double.format(fracDigits: Int): String {
            val df = DecimalFormat()
            df.setMaximumFractionDigits(fracDigits)
            return df.format(this)
        }

        fun DecimalFormat(value: String): String {
            var value = value
            val doubleFormat = java.text.DecimalFormat("#.##")
            doubleFormat.setMinimumFractionDigits(2)
            value = doubleFormat.format(java.lang.Double.parseDouble(value))
            return value
        }

        fun DecimalFormat(value: String, precision: Int): String {
            var value = value
            val doubleFormat = java.text.DecimalFormat("#.##")
            doubleFormat.setMinimumFractionDigits(precision)
            value = doubleFormat.format(java.lang.Double.parseDouble(value))
            return value
        }
    }
}
