package com.beyzaterzioglu.kotlininstagram.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.beyzaterzioglu.kotlininstagram.databinding.ActivityMainBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        val view=binding.root
        setContentView(view)

        auth = Firebase.auth

        val currentUser=auth.currentUser
        if(currentUser!=null)
        { // geçerli bir kullanıcı var mı
            val intent=Intent(this, FeedActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

    fun signinClick(view:View)
    {
        val email=binding.emailText.text.toString()
        val password=binding.passwordText.text.toString()
        if(email.equals("")|| password.equals(""))
        {
            Toast.makeText(this,"Enter email and password!!",Toast.LENGTH_LONG).show()
        }
        else
        {
            auth.signInWithEmailAndPassword(email,password)
                .addOnSuccessListener {
                    val intent= Intent(this@MainActivity, FeedActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                .addOnFailureListener { Toast.makeText(this@MainActivity,it.localizedMessage,Toast.LENGTH_LONG).show() }

        }


    }
    fun signupClick(view:View)
    {
        val email=binding.emailText.text.toString()
        val password=binding.passwordText.text.toString()

        if(email.equals("")|| password.equals(""))
        {
          Toast.makeText(this,"Enter email and password!!",Toast.LENGTH_LONG).show()
        }
        else
        {
            // Bu kısımda yapılan doğrulama isteği atıldıktan sonra cevabın dönmesi daha uzun sürebilir kodlara göre.
            // Bu sebeple bu işlem arkaplanda asenkron bir şekilde yapılmalıdır.
            // Aşağıda yapılan islemler de cevabın dönmesi sonrasında yapılacak işlemlerdir.
            auth.createUserWithEmailAndPassword(email,password)
            .addOnSuccessListener {// success
            val intent= Intent(this@MainActivity, FeedActivity::class.java)
            startActivity(intent)
            finish()
        }
                .addOnFailureListener {Toast.makeText(this@MainActivity,it.localizedMessage,Toast.LENGTH_LONG).show()  }

        }


    }

}