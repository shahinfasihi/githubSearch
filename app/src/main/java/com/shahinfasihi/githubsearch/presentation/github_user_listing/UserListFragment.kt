package com.shahinfasihi.githubsearch.presentation.github_user_listing

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.*
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shahinfasihi.githubsearch.R
import com.shahinfasihi.githubsearch.databinding.FragmentUserListBinding
import com.shahinfasihi.githubsearch.domain.model.User
import com.shahinfasihi.githubsearch.presentation.util.TopSpacingItemDecoration
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Exception

@AndroidEntryPoint
class UserListFragment : Fragment(), UserListAdapter.Interaction {

    private val viewModel: UserListViewModel by viewModels()
    private lateinit var searchView: SearchView
    private var recyclerAdapter: UserListAdapter? = null
    private lateinit var menu: Menu

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
        setHasOptionsMenu(true)

        initRecyclerView()
        subscribeObservers()
    }

    private fun subscribeObservers() {
        viewModel.state.observe(viewLifecycleOwner) { state ->
            //TODO : Control error
            if (state.isLoading == true) {
            } else {
                recyclerAdapter?.apply {
                    submitList(userList = state.users?.userList)
                }
            }
        }

    }


    private fun initRecyclerView() {
        binding.recyclerUserList.apply {
            layoutManager = LinearLayoutManager(this@UserListFragment.context)
            val topSpacingDecorator = TopSpacingItemDecoration(30)
            removeItemDecoration(topSpacingDecorator)
            addItemDecoration(topSpacingDecorator)

            recyclerAdapter = UserListAdapter(this@UserListFragment)
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                    val lastPosition = layoutManager.findLastVisibleItemPosition()
                    if (
                        lastPosition == recyclerAdapter?.itemCount?.minus(1)
                        && viewModel.state.value?.isLoading == false
                    ) {
                        viewModel.onEvent(UserListEvent.NextPage)
                    }
                }
            })
            adapter = recyclerAdapter
        }
    }

    private fun initSearchView() {
        activity?.apply {
            val searchManager: SearchManager =
                getSystemService(Context.SEARCH_SERVICE) as SearchManager
            searchView = menu.findItem(R.id.action_search).actionView as SearchView
            searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
            searchView.maxWidth = Integer.MAX_VALUE
            searchView.setIconifiedByDefault(true)
            searchView.isSubmitButtonEnabled = true
        }
        val searchPlate = searchView.findViewById<EditText>(androidx.appcompat.R.id.search_src_text)

        viewModel.state.value?.let { state ->
            if (state.searchQuery.isNotBlank()) {
                searchPlate.setText(state.searchQuery)
                searchView.isIconified = false
                binding.focusableView.requestFocus()
            }
        }
        searchPlate.setOnEditorActionListener { v, actionId, event ->

            if (actionId == EditorInfo.IME_ACTION_UNSPECIFIED
                || actionId == EditorInfo.IME_ACTION_SEARCH
            ) {
                val searchQuery = v.text.toString()
                viewModel.onEvent(UserListEvent.OnSearchQueryChange(searchQuery))
            }
            true
        }

        val searchButton = searchView.findViewById(androidx.appcompat.R.id.search_go_btn) as View
        searchButton.setOnClickListener {
            val searchQuery = searchPlate.text.toString()
            viewModel.onEvent(UserListEvent.OnSearchQueryChange(searchQuery))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        this.menu = menu
        inflater.inflate(R.menu.search_menu, this.menu)
        initSearchView()
    }

    override fun onItemSelected(position: Int, item: User) {
        try {
            viewModel.state.value?.let { _ ->
                val action =
                    UserListFragmentDirections.actionUserListFragmentToUserDetailFragment(username = item.login.toString())
                findNavController().navigate(action)
            } ?: throw Exception("User Null")
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}