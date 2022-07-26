package br.com.zup.marvel.ui.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.zup.marvel.data.model.Marvel
import br.com.zup.marvel.data.repository.MarvelRepository
import br.com.zup.marvel.domain.model.User
import br.com.zup.marvel.domain.repository.AuthenticationRepository

class HomeViewModel: ViewModel() {
    private val marvelRepository = MarvelRepository()
    private val authenticationRepository = AuthenticationRepository()

    private var _marvelListState = MutableLiveData<List<Marvel>>()
    val marvelListState: LiveData<List<Marvel>> = _marvelListState

    fun getListMarvel() {
       val listMarvel =  marvelRepository.getMarvelList()
        _marvelListState.value = listMarvel
    }

    fun logout() {
        authenticationRepository.logout()
    }

    fun getCurrentUser(
        name: String = authenticationRepository.getUsername(),
        mail: String = authenticationRepository.getUserMail()
    ): User {
       return User(
           name = name,
           mail = mail
       )
    }
}