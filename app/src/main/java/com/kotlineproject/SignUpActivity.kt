package com.kotlineproject

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import com.craftbox.hellokotlin.netUtils.MyPreferences
import kotlin.math.sign

class SignUpActivity : AppCompatActivity() {

    lateinit var username: EditText;
    lateinit var passsword: EditText;
    lateinit var submit: Button;
    lateinit var myPreferences: MyPreferences;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle("SignUp")
        myPreferences = MyPreferences(this@SignUpActivity)

        username = findViewById(R.id.user_name_txt) as EditText
        passsword = findViewById(R.id.password_txt) as EditText
        submit = findViewById(R.id.submit_txt) as Button

        if (myPreferences.getPreferences("" + myPreferences.id).equals("")) {

        } else {
            val intent = Intent(this@SignUpActivity, MainActivity::class.java)
            startActivity(intent)
        }

        submit.setOnClickListener {
            myPreferences.setPreferences(myPreferences.id, "1")
            myPreferences.setPreferences(myPreferences.name, "" + username.text)
            myPreferences.setPreferences(myPreferences.password, "" + passsword.text)
            val intent = Intent(this@SignUpActivity, LoginActivity::class.java)
            startActivity(intent)
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}
