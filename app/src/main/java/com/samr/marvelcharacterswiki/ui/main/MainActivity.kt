package com.samr.marvelcharacterswiki.ui.main

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import com.samr.marvelcharacterswiki.ui.theme.ThemeStore
import com.sm.feature_listing_api.ListingFeatureApi
import org.koin.java.KoinJavaComponent

class MainActivity : AppCompatActivity() {

    private val themeStore by lazy { ThemeStore(this) }

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)

        setContent {
            val windowSize = calculateWindowSizeClass(activity = this)
            MainUi(themeStore)
        }
    }
}

