package br.com.zup.marvel.ui.register.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.zup.marvel.CREATE_USER_ERROR
import br.com.zup.marvel.MAIL_REGISTER_ERROR
import br.com.zup.marvel.NAME_REGISTER_ERROR
import br.com.zup.marvel.PASSWORD_REGISTER_ERROR
import br.com.zup.marvel.domain.model.User
import br.com.zup.marvel.domain.repository.AuthenticationRepository
import java.lang.Exception

class RegisterViewModel: ViewModel() {
    private val authenticationRepository = AuthenticationRepository()

    private var _registerState = MutableLiveData<User>()
    val registerState: LiveData<User> = _registerState

    private var _errorState = MutableLiveData<String>()
    val errorState: LiveData<String> = _errorState

    fun validateDataUser(user: User){
        when {
            user.name.length < 3 -> {
                _errorState.value = NAME_REGISTER_ERROR
            }
            user.mail.isEmpty() -> {
                _errorState.value = MAIL_REGISTER_ERROR
            }
            user.password.length < 8 -> {
                _errorState.value = PASSWORD_REGISTER_ERROR
            }
            else -> {
                registerUser(user)
            }
        }
    }

    private fun registerUser(user: User) {
        try {
            authenticationRepository.registerUser(user.mail, user.password)
                .addOnSuccessListener {
                    authenticationRepository
                        .updateUserProfile(user.name)?.addOnSuccessListener {
                            _registerState.value = user
                    }
                }.addOnFailureListener {
                    _errorState.value = "$CREATE_USER_ERROR ${it.message}"
                }
        }catch (e: Exception){
            _errorState.value = CREATE_USER_ERROR
        }
    }
}