package com.practicum.sprint_15_filmoteca_clean_architecture.ui.movies

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.PersistableBundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.practicum.sprint_15_filmoteca_clean_architecture.MoviesApplication
import com.practicum.sprint_15_filmoteca_clean_architecture.util.Creator
import com.practicum.sprint_15_filmoteca_clean_architecture.ui.poster.PosterActivity
import com.practicum.sprint_15_filmoteca_clean_architecture.R
import com.practicum.sprint_15_filmoteca_clean_architecture.domain.models.Movie
import com.practicum.sprint_15_filmoteca_clean_architecture.presentation.movies.MoviesSearchPresenter
import com.practicum.sprint_15_filmoteca_clean_architecture.presentation.movies.MoviesView
import com.practicum.sprint_15_filmoteca_clean_architecture.ui.movies.models.MoviesState
import moxy.MvpActivity
import moxy.MvpView
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter

class MoviesActivity : MvpActivity(), MoviesView {

    @InjectPresenter
    lateinit var moviesSearchPresenter: MoviesSearchPresenter

    @ProvidePresenter
    fun providePresenter(): MoviesSearchPresenter {
        return Creator.provideMoviesSearchPresenter(
            context = this.applicationContext
        )
    }

    private lateinit var queryInput: EditText
    private lateinit var placeholderMessage: TextView
    private lateinit var moviesList: RecyclerView
    private lateinit var progressBar: ProgressBar

    private var textWatcher: TextWatcher? = null

    private val adapterMovies = MoviesAdapter {
        if (clickDebounce()) {
            val intent = Intent(this, PosterActivity::class.java)
            intent.putExtra("poster", it.image)
            startActivity(intent)
        }
    }

    private var isClickAllowed = true

    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movies)

        placeholderMessage = findViewById(R.id.placeholderMessage)
        queryInput = findViewById(R.id.queryInput)
        moviesList = findViewById(R.id.locations)
        progressBar = findViewById(R.id.progressBar)


        moviesList.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        moviesList.adapter = adapterMovies


        textWatcher = object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                moviesSearchPresenter?.searchDebounce(
                    p0?.toString() ?: ""
                )
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        }
        textWatcher?.let { queryInput.addTextChangedListener(it) }
    }

    private fun clickDebounce(): Boolean {
        val current = isClickAllowed
        if (isClickAllowed) {
            isClickAllowed = false
            handler.postDelayed({ isClickAllowed = true }, CLICK_DEBOUNCE_DELAY)
        }
        return current
    }

    companion object {
        private const val CLICK_DEBOUNCE_DELAY = 1000L
    }

    private fun showLoading() {
        progressBar.visibility = View.VISIBLE
        moviesList.visibility = View.GONE
        placeholderMessage.visibility = View.GONE
    }

    private fun showError(errorMessage: String) {
        moviesList.visibility = View.GONE
        progressBar.visibility = View.GONE
        placeholderMessage.visibility = View.VISIBLE
        placeholderMessage.text = errorMessage
    }

    private fun showContent(newMoviesList: List<Movie>) {
        moviesList.visibility = View.VISIBLE
        progressBar.visibility = View.GONE
        placeholderMessage.visibility = View.GONE
        adapterMovies.movies.clear()
        adapterMovies.movies.addAll(newMoviesList)
        adapterMovies.notifyDataSetChanged()
    }

    override fun render(state: MoviesState) {
        when {
            state.isLoading -> showLoading()
            state.message != null -> showError(state.message)
            else -> showContent(state.movies)
        }
    }

    override fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

}