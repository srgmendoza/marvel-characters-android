package com.samr.marvelcharacterswiki.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.samr.marvelcharacterswiki.R
import com.samr.marvelcharacterswiki.ui.models.CharacterModel
import com.samr.marvelcharacterswiki.ui.presenters.CharactersListPresenter

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

}