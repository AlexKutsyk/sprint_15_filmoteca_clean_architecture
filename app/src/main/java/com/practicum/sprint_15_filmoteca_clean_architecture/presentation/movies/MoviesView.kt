package com.practicum.sprint_15_filmoteca_clean_architecture.presentation.movies

import com.practicum.sprint_15_filmoteca_clean_architecture.ui.movies.models.MoviesState
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

interface MoviesView : MvpView {

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun render (state: MoviesState)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showToast (message: String)
}