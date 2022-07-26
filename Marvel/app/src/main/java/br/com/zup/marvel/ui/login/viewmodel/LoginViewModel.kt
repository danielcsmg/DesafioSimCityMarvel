package br.com.zup.marvel.ui.login.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.zup.marvel.LOGIN_MESSAGE_ERROR
import br.com.zup.marvel.LOGIN_NAME_MESSAGE_ERROR
import br.com.zup.marvel.LOGIN_PASSWORD_MESSAGE_ERROR
import br.com.zup.marvel.domain.model.User
import br.com.zup.marvel.domain.repository.AuthenticationRepository

class LoginViewModel: ViewModel() {
    private val authenticationRepository = AuthenticationRepository()

    private var _loginState = MutableLiveData<User>()
    val loginState: LiveData<User> = _loginState

    private var _errorState = MutableLiveData<String>()
    val errorState: LiveData<String> = _errorState

    fun validateUser(user: User) {
        when {
            user.mail.isEmpty() -> {
                _errorState.value = LOGIN_NAME_MESSAGE_ERROR
            }
            user.password.isEmpty() -> {
                _errorState.value = LOGIN_PASSWORD_MESSAGE_ERROR
            }
            else -> login(user)
        }
    }

    private fun login(user: User) {
        try {
            authenticationRepository.login(
                user.mail,
                user.password
            ).addOnSuccessListener {
                _loginState.value = user
            }.addOnFailureListener {
                _errorState.value = LOGIN_MESSAGE_ERROR
            }
        }catch (e: Exception){
            _errorState.value = e.message
        }
    }
}