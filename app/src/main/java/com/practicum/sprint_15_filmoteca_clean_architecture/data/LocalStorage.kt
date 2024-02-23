package com.practicum.sprint_15_filmoteca_clean_architecture.data

import android.content.Context

class LocalStorage(context: Context) {

    companion object {
        const val FAVORITES_KEY = "FAVORITES_KEY"
    }

    private val sharedPref = context.getSharedPreferences(FAVORITES_KEY, Context.MODE_PRIVATE)

    fun addFavorite(movieId: String) {
        changeFavorites(movieId, isRemove = false)
    }

    fun removeFavorites(movieId: String) {
        changeFavorites(movieId, isRemove = true)
    }

    fun getSaveFavorites(): Set<String> {
        return sharedPref.getStringSet(FAVORITES_KEY, emptySet()) ?: emptySet()
    }

    private fun changeFavorites(movieId: String, isRemove: Boolean) {
        val mutableSetFM = getSaveFavorites().toMutableSet()
        val modifiedMutableSetFM = if (isRemove) mutableSetFM.remove(movieId) else mutableSetFM.add(movieId)
        if (modifiedMutableSetFM) sharedPref.edit().putStringSet(FAVORITES_KEY, mutableSetFM).apply()
    }
}