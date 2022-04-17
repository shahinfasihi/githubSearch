package com.shahinfasihi.githubsearch

import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity

// I wrote this code before but don't work on some circumstances so i need that to be removed
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

