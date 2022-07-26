package br.com.zup.marvel.ui.register.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import br.com.zup.marvel.CREATE_USER_SUCCESS
import br.com.zup.marvel.R
import br.com.zup.marvel.databinding.ActivityRegisterBinding
import br.com.zup.marvel.domain.model.User
import br.com.zup.marvel.ui.home.view.HomeActivity
import br.com.zup.marvel.ui.register.viewmodel.RegisterViewModel
import com.google.android.material.snackbar.Snackbar

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private val viewModel: RegisterViewModel by lazy {
        ViewModelProvider(this)[RegisterViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
        binding.btRegister.setOnClickListener {
            val user: User = getDataUser()
            viewModel.validateDataUser(user)
        }
        initObserver()
    }

    private fun getDataUser(
        name: String = binding.etNameRegister.text.toString(),
        mail: String = binding.etEmailRegister.text.toString(),
        password: String = binding.etPasswordRegister.text.toString()
    ): User {
        return User(name, mail, password)
    }

    private fun initObserver() {
        viewModel.registerState.observe(this){
            goToHome()
            Snackbar.make(binding.root, CREATE_USER_SUCCESS, Snackbar.LENGTH_SHORT).show()
        }

        viewModel.errorState.observe(this) {
            Snackbar.make(binding.root, it, Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun goToHome() {
        startActivity(Intent(this, HomeActivity::class.java))
    }
}