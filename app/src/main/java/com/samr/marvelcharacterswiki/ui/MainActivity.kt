package com.samr.marvelcharacterswiki.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.samr.domain.entities.CharacterUI
import com.samr.marvelcharacterswiki.R

class MainActivity : AppCompatActivity(), MainView {

    lateinit var presenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        presenter.fetchDataForMainScreen()
    }

    override fun onCharacterslistReceived(characters: List<CharacterUI>) {
        TODO("Not yet implemented")
    }

    override fun onError(errorMessage: String) {
        TODO("Not yet implemented")
    }
}