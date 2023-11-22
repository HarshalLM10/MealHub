package com.example.mealhub

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.mealhub.databinding.ActivitySignupBinding
import com.google.firebase.auth.FirebaseAuth

class Signup : AppCompatActivity() {

    private lateinit var binding:ActivitySignupBinding
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        binding.signupButton.setOnClickListener {
            val name  = binding.signupName.text.toString()
            val email = binding.signupEmail.text.toString()
            val password = binding.signupPassword.text.toString()
            val Confirmpassword = binding.signupConfirmpass.text.toString()

            if (name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() ){
                if (password == Confirmpassword){

                    firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener {
                        if(it.isSuccessful){
                            val intent = Intent(this,Login::class.java)
                            startActivity(intent)
                        }else{
                            Toast.makeText(this,it.exception.toString(),Toast.LENGTH_SHORT).show()
                        }
                    }
                }else{
                    Toast.makeText(this,"Password Does Not Matched, Try Again",Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(this,"Fields cannot be Empty",Toast.LENGTH_SHORT).show()
            }

        }
        binding.loginRedirectText.setOnClickListener {
            val loginIntent = Intent(this,Login::class.java)
            startActivity(loginIntent)
        }


    }
}