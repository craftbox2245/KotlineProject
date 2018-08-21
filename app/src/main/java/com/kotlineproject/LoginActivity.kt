package com.kotlineproject

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.craftbox.hellokotlin.netUtils.MyPreferences

class LoginActivity : AppCompatActivity() {

    lateinit var username: EditText
    lateinit var password: EditText
    lateinit var submit: Button
    lateinit var signup_btn: Button
    lateinit var myPreferences: MyPreferences;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle("Login")
        myPreferences = MyPreferences(this@LoginActivity)

        username = findViewById(R.id.user_name_txt) as EditText
        password = findViewById(R.id.password_txt) as EditText
        submit = findViewById(R.id.submit_txt) as Button
        signup_btn = findViewById(R.id.signup_btn) as Button

        submit.setOnClickListener {
            if (myPreferences.getPreferences("" + myPreferences.name).equals("" + username.text) && myPreferences.getPreferences("" + myPreferences.password).equals("" + password.text)) {
                Toast.makeText(this@LoginActivity, "Login Successsfully", Toast.LENGTH_LONG).show()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            } else {
                System.out.print("")
                Toast.makeText(this@LoginActivity, "User name and Password invalid ", Toast.LENGTH_LONG).show()
            }
        }

        signup_btn.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}
