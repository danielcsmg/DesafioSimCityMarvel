package br.com.zup.marvel.ui.login.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import br.com.zup.marvel.databinding.ActivityLoginBinding
import br.com.zup.marvel.domain.model.User
import br.com.zup.marvel.ui.home.view.HomeActivity
import br.com.zup.marvel.ui.login.viewmodel.LoginViewModel
import br.com.zup.marvel.ui.register.view.RegisterActivity

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by lazy {
        ViewModelProvider(this)[LoginViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityLoginBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()

        binding.tvRegisterNow.setOnClickListener {
            goToRegister()
        }

        binding.btLogin.setOnClickListener {
            val user = getDataUser()
            viewModel.validateUser(user)
        }

        initObserver()
    }

    private fun initObserver() {
        viewModel.loginState.observe(this) {
            goToHome()
        }

        viewModel.errorState.observe(this){
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
    }

    private fun goToHome() {
        startActivity(Intent(this, HomeActivity::class.java))
    }

    private fun getDataUser(): User {
        return User(
            mail = binding.etNameLogin.text.toString(),
            password = binding.etPasswordLogin.text.toString()
        )
    }

    private fun goToRegister() {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }
}