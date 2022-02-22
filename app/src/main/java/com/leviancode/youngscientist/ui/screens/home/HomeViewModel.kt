package com.leviancode.youngscientist.ui.screens.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.switchMap
import com.leviancode.youngscientist.data.entites.Journal
import com.leviancode.youngscientist.data.repositories.JournalsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: JournalsRepository) : ViewModel() {
    private val _selectedYear = MutableLiveData(2021)
    val selectedYear: LiveData<Int> = _selectedYear

    val journals: LiveData<List<Journal>> = selectedYear.switchMap { year ->
        repository.fetchByYear(year)
    }

    val years: LiveData<List<Int>> = repository.getYears().map { it.sortedDescending() }

    val currentYear get() = Calendar.getInstance().get(Calendar.YEAR)

    init {
        loadJournals(currentYear - 1)
    }

    fun loadJournals(year: Int) {
        _selectedYear.value = year
    }

}