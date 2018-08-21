package com.kotlineproject.netUtils

import android.content.ContentValues
import android.content.Context
import android.content.pm.PackageInfo
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

import com.craftbox.hellokotlin.netUtils.MyPreferences

import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.text.DecimalFormat
import java.util.ArrayList


class DBHelper(private val context: Context) : SQLiteOpenHelper(context, DB_NAME, null, 1) {
    private var db: SQLiteDatabase? = null
    private val DB_PATH: String
    internal var doubleFormat: DecimalFormat
    internal var myPreferences: MyPreferences

    init {
        myPreferences = MyPreferences(context)
        DB_PATH = "/data/data/" + context.packageName + "/" + "databases/"
        doubleFormat = DecimalFormat("#")
        doubleFormat.minimumFractionDigits = 0
    }

    @Throws(IOException::class)
    fun createDataBase() {

        val dbExist = checkDataBase()
        //------------------------------------------------------------
        val pinfo: PackageInfo? = null
        if (!dbExist) {
            readableDatabase
            copyDataBase()
        } else {
            print("")
        }

    }

    private fun checkDataBase(): Boolean {
        val dbFile = File(DB_PATH + DB_NAME)
        return dbFile.exists()
    }

    private fun copyDataBase() {
        try {
            val myInput = context.assets.open(DB_NAME)
            val outFileName = DB_PATH + DB_NAME
            val myOutput = FileOutputStream(outFileName)
            val buffer = ByteArray(1024)
            while ((myInput.read(buffer)) > 0) {
                myOutput.write(buffer);
            }
            // Close the streams
            myOutput.flush()
            myOutput.close()
            myInput.close()

            /*val `is` = context.assets.open(DB_NAME)
            val outFileName = DB_PATH + DB_NAME
            val os = FileOutputStream(outFileName)
            val buffer = ByteArray(1024)
            while (`is`.read(buffer) > 0) {
                os.write(buffer)
                Log.d("#DB", "writing>>")
            }
            os.flush()
            os.close()
            `is`.close()*/
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        // TODO Auto-generated method stub
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // TODO Auto-generated method stub
    }

    fun getData(Query: String): Cursor? {
        val myPath = DB_PATH + DB_NAME
        try {
            db = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE)
            return db!!.rawQuery(Query, null)
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }

    }

    /* todo delete table*/
    fun DeleteTable(table_name: String): Boolean {
        try {
            val db = this.writableDatabase
            return if (db.delete(table_name, "1", null) > 0) {
                true
            } else {
                false
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }

    }

    /* todo delete perticuler Row */
    fun DeleteRow(table_name: String, column_name: String, id: String): Boolean {
        try {
            val db = this.writableDatabase
            return db.delete(table_name, "$column_name=$id", null) > 0
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return false
    }

    companion object {
        private val DB_NAME = "kotlin.db"
        var COUNTRY = "country"
    }
}