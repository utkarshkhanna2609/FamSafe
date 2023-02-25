package com.example.famsafe

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class LoginActivity : AppCompatActivity() {
    private val RC_SIGN_IN = 89
    private lateinit var googleSignInClient: GoogleSignInClient
    public lateinit var name:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("664754189657-3k17i9pbhu3gu9d4dejfmfmq8s537khf.apps.googleusercontent.com")
            .requestEmail()
            .build()

        googleSignInClient=GoogleSignIn.getClient(this,gso)
    }

    fun signIn(view: View) {
        val signInIntent=googleSignInClient.signInIntent
        startActivityForResult(signInIntent,RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI accordingly
                Log.w(TAG, "Google sign in failed", e)
                // ...
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val firebaseAuth=FirebaseAuth.getInstance()
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")
                    val user = firebaseAuth.currentUser
                    startActivity(Intent(this,MainActivity::class.java))
                    Log.d("Uttu","Firebase Auth with google:${user?.displayName}")
                    name=user?.displayName.toString()
                } else {
                    // Sign in failed, display a message to the user.
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    // ...
                }
            }

    }

}