package com.shahinfasihi.githubsearch.presentation.github_user_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.shahinfasihi.githubsearch.R
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

    private val requestOptions = RequestOptions
        .placeholderOf(R.drawable.default_image)
        .error(R.drawable.default_image)

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

        viewModel.state.observe(viewLifecycleOwner) { state ->
            //TODO : control error
            if (state.isLoading == true) {
            } else {
                binding.progressLayout.visibility = View.GONE
                val username = getString(R.string.userDetail_username, state.user?.login)
                val followers =
                    getString(R.string.userDetail_followers, state.user?.followers.toString())
                val following =
                    getString(R.string.userDetail_following, state.user?.following.toString())

                Glide.with(view)
                    .setDefaultRequestOptions(requestOptions)
                    .load(state.user?.avatarUrl)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(binding.imgUser)
                binding.txtUsername.setText(username)
                binding.txtfollower.setText(followers)
                binding.txtfollowing.setText(following)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}