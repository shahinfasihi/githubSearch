package com.shahinfasihi.githubsearch.presentation.github_user_listing

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import com.shahinfasihi.githubsearch.R
import com.shahinfasihi.githubsearch.databinding.FragmentUserListBinding
import com.shahinfasihi.githubsearch.presentation.github_user_detail.UserDetailFragment

class UserListFragment : Fragment() {


    private var _binding: FragmentUserListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserListBinding.inflate(layoutInflater)
        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowTitleEnabled(false)

        binding.btn.setOnClickListener {
            //move inside the adapter
            val action = UserListFragmentDirections.actionUserListFragmentToUserDetailFragment(username = "shahinfasihi")
            view.findNavController().navigate(action)
        }
    }
}