package com.leviancode.youngscientist.ui.screens.detail

import androidx.lifecycle.ViewModel
import com.leviancode.youngscientist.data.repositories.JournalsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: JournalsRepository
) : ViewModel() {

    fun getJournal(journalId: Int) = repository.getJournal(journalId)
}