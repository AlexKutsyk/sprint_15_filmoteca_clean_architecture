package com.practicum.sprint_15_filmoteca_clean_architecture.ui.poster

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.practicum.sprint_15_filmoteca_clean_architecture.util.Creator
import com.practicum.sprint_15_filmoteca_clean_architecture.R
import com.practicum.sprint_15_filmoteca_clean_architecture.presentation.poster.PosterPresenter
import com.practicum.sprint_15_filmoteca_clean_architecture.presentation.poster.PosterView

class PosterActivity : AppCompatActivity(), PosterView {

    private lateinit var urlPoster: String

    private lateinit var posterPresenter: PosterPresenter

    private lateinit var poster: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        urlPoster = intent.extras?.getString("poster", "").toString()

        posterPresenter = Creator.providePosterPresenter(this, urlPoster)

        setContentView(R.layout.activity_poster)

        poster = findViewById(R.id.poster)

        posterPresenter.onCreate()

    }

    override fun setPoster(urlPoster: String) {
        Glide.with(this)
            .load(urlPoster)
            .into(poster)
    }
}