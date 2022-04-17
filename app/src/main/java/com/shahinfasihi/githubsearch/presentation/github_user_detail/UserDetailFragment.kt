package com.shahinfasihi.githubsearch.presentation.github_user_detail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import com.shahinfasihi.githubsearch.databinding.FragmentUserDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserDetailFragment : Fragment() {

    private val viewModel: UserViewModel by viewModels()
    private var _binding: FragmentUserDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var username: String

    companion object {
        const val USERNAME = "username"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            username = it.getString(USERNAME).toString()
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentUserDetailBinding.inflate(layoutInflater)
        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowTitleEnabled(false)

//        Toast.makeText(context, username, Toast.LENGTH_LONG).show()

        viewModel.state.observe(viewLifecycleOwner) { state ->
            if (state.isLoading == true) {
                Snackbar.make(view, "Loading", Snackbar.LENGTH_LONG).show()
            } else {
                Log.d("Kronos", state.user.toString())
                Snackbar.make(view, state.user?.login.toString(), Snackbar.LENGTH_LONG)
                    .show()
            }
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}