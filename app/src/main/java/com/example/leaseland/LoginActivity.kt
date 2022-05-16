package com.example.leaseland

import androidx.appcompat.app.AppCompatActivity
import android.widget.EditText
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import android.os.Bundle
import com.example.leaseland.R
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.AuthResult
import android.widget.Toast
import android.content.Intent
import android.util.Patterns
import android.view.View
import android.widget.Button
import com.example.leaseland.HomeActivity
import com.google.android.gms.tasks.OnFailureListener
import com.example.leaseland.SignupActivity
import com.example.leaseland.ForgetPasswordActivity

class LoginActivity : AppCompatActivity(), View.OnClickListener {
    private var mEmail: EditText? = null
    private var mPass: EditText? = null
    lateinit var mTextView: TextView
    lateinit var forgetPasswordTextView: TextView
    lateinit var signInButton: Button
    private var mAuth: FirebaseAuth? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        mEmail = findViewById(R.id.login_email_text)
        mPass = findViewById(R.id.login_password_text)
        signInButton = findViewById(R.id.login_button)
        mTextView = findViewById(R.id.register_link)
        forgetPasswordTextView = findViewById(R.id.forget_password)
        mAuth = FirebaseAuth.getInstance()

        forgetPasswordTextView.setOnClickListener(this)
        mTextView.setOnClickListener(this)
        signInButton.setOnClickListener(this)
    }

    private fun loginUser() {
        val email = mEmail!!.text.toString()
        val password = mPass!!.text.toString()
        if (!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            if (!password.isEmpty()) {
                mAuth!!.signInWithEmailAndPassword(email, password)
                        .addOnSuccessListener {
                            Toast.makeText(this@LoginActivity, "Login Successfully !!", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
                            finish()
                        }.addOnFailureListener { Toast.makeText(this@LoginActivity, "Login Failed !!", Toast.LENGTH_SHORT).show() }
            } else {
                mPass!!.error = "Empty Fields are not Allowed"
            }
        } else if (email.isEmpty()) {
            mEmail!!.error = "Empty fields are not allowed"
        } else {
            mEmail!!.error = "Please enter a valid email address"
        }
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.forget_password -> forgetPassword()
            R.id.register_link -> startActivity(Intent(this@LoginActivity, SignupActivity::class.java))
            R.id.login_button -> loginUser()
        }
    }

    private fun forgetPassword() {
        startActivity(Intent(this, ForgetPasswordActivity::class.java))
        finish()
    }
}