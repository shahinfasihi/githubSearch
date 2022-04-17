package com.shahinfasihi.githubsearch

import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.shahinfasihi.githubsearch.presentation.github_user_listing.UserListViewModel
import javax.inject.Inject

abstract class BaseActivity : AppCompatActivity(),
    UICommunicationListener {

    override fun hideSoftKeyboard() {
        if (currentFocus != null) {
            val inputMethodManager = getSystemService(
                Context.INPUT_METHOD_SERVICE
            ) as InputMethodManager
            inputMethodManager
                .hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }
    }

}