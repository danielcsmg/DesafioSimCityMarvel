package br.com.zup.marvel.ui.home.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.zup.marvel.*
import br.com.zup.marvel.databinding.ActivityHomeBinding
import br.com.zup.marvel.ui.detalhe.DetalheActivity
import br.com.zup.marvel.data.model.Marvel
import br.com.zup.marvel.domain.model.User
import br.com.zup.marvel.ui.home.viewmodel.HomeViewModel
import br.com.zup.marvel.ui.login.view.LoginActivity

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding

    private val adapter: MarvelAdapter by lazy {
        MarvelAdapter(arrayListOf(), this::goToDetail)
    }

    private val viewModel: HomeViewModel by lazy {
        ViewModelProvider(this)[HomeViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.getListMarvel()
        setUpRecyclerView()
        showDataUser()
        initObserver()
    }

    private fun setUpRecyclerView() {
        binding.rvHerois.adapter = adapter
        binding.rvHerois.layoutManager = LinearLayoutManager(this)
    }

    private fun initObserver(){
        viewModel.marvelListState.observe(this){
            adapter.updateMarvelList(it.toMutableList())
        }
    }

    private fun goToDetail(marvel: Marvel) {
        val intent = Intent(this, DetalheActivity::class.java).apply {
            putExtra(MARVEL_KEY, marvel)
        }
        startActivity(intent)
    }

    private fun goToLogin() {
        startActivity(Intent(this, LoginActivity::class.java))
    }

    private fun getCurrentUser(): User {
        return viewModel.getCurrentUser()
    }

    private fun showDataUser(){
        val user = getCurrentUser()
        binding.tvPresentation.text = getString(R.string.texto_home, user.name, user.mail)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.logout -> {
                viewModel.logout()
                this.finish()
                goToLogin()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }
}