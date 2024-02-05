package com.practicum.sprint_15_filmoteca_clean_architecture.presentation.poster

import android.content.Context
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.practicum.sprint_15_filmoteca_clean_architecture.R

class PosterPresenter(private val view: PosterView, private val urlPoster: String) {

    fun onCreate() {

        view.setPoster(urlPoster)
    }
}