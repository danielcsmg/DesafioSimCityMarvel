package br.com.zup.marvel.domain.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class AuthenticationRepository {
    private val auth: FirebaseAuth = Firebase.auth

    fun registerUser(mail: String, password: String): Task<AuthResult> {
        return auth.createUserWithEmailAndPassword(mail, password)
    }

    fun updateUserProfile(name: String): Task<Void>? {
        val profile = UserProfileChangeRequest.Builder().setDisplayName(name).build()
        return auth.currentUser?.updateProfile(profile)
    }

    fun login(mail: String, password: String): Task<AuthResult> {
        return auth.signInWithEmailAndPassword(mail, password)
    }

    fun logout() {
        auth.signOut()
    }

    fun getUserMail(): String = auth.currentUser?.email.toString()

    fun getUsername(): String = auth.currentUser?.displayName.toString()
}