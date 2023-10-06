package com.samr.marvelcharacterswiki.ui.main

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.samr.marvelcharacterswiki.ui.theme.ThemeStore

class MainActivity : AppCompatActivity() {

    private val themeStore by lazy { ThemeStore(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)

        setContent {
            MainUi(themeStore)
        }
    }
}
