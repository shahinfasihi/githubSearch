package com.shahinfasihi.githubsearch.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.shahinfasihi.githubsearch.BaseActivity
import com.shahinfasihi.githubsearch.R
import com.shahinfasihi.githubsearch.databinding.ActivityMainBinding
import com.shahinfasihi.githubsearch.presentation.github_user_detail.UserViewModel
import com.shahinfasihi.githubsearch.presentation.github_user_listing.UserListEvent
import com.shahinfasihi.githubsearch.presentation.github_user_listing.UserListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

//    private val viewModel: UserListViewModel by viewModels()
//    private val viewModel: UserViewModel by viewModels()

    override fun displayProgressBar(isLoading: Boolean) {
        TODO("Not yet implemented")
    }

    override fun expandAppBar() {
        TODO("Not yet implemented")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        setupActionBarWithNavController(navController)

//        binding.btn.setOnClickListener { subscribeObservers() }

    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }


    fun subscribeObservers() {
//        viewModel.onEvent(UserListEvent.OnSearchQueryChange(binding.txt.text.toString()))
//        println(viewModel.state.users?.totalCount)
    }


}