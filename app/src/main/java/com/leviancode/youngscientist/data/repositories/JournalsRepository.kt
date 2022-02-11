package com.leviancode.youngscientist.data.repositories

import androidx.lifecycle.LiveData
import com.leviancode.youngscientist.data.entites.Journal

interface JournalsRepository {
    fun fetchByYear(year: Int): LiveData<List<Journal>>
    fun getJournal(number: Int): LiveData<Journal>
    fun getYears(): LiveData<List<Int>>
}