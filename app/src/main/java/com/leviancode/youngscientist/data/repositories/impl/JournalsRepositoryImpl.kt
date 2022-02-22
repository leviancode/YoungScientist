package com.leviancode.youngscientist.data.repositories.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.leviancode.youngscientist.data.api.HtmlParser
import com.leviancode.youngscientist.data.entites.Journal
import com.leviancode.youngscientist.data.repositories.JournalsRepository
import com.leviancode.youngscientist.utils.years
import javax.inject.Inject

class JournalsRepositoryImpl @Inject constructor(private val parser: HtmlParser) : JournalsRepository {
    override fun fetchByYear(year: Int): LiveData<List<Journal>> {
        return liveData {
            val result = if (year > 2019){
                parser.parseNewSite().filter { it.year == year }
            } else parser.parseOldSite()

            emit(result)
        }
    }

    override fun getJournal(number: Int): LiveData<Journal> {
        return liveData {
            val journal = Journal(
                year = 2021,
                numberInYear = 2,
                number = number,
                cover = "",
                coverDescription = "Обкладинка журналу присвячена творчості Остапа Вишні – письменнику унікальної популярності, класику сатиричної прози, автору видатної збірки «Мисливські усмішки» та інших чудових творів. Він увійшов в історію української літератури як письменник-гуморист, засновник нового художнього жанру «нарис-усмішка». Своєю творчістю Остап Вишня продемонстрував, що гумор – це не лише веселощі, а й потужна зброя. Остап Вишня – письменник мільйонних тиражів, для нього було справою честі працювати для народу та в ім'я народу.",
                articles = listOf(),
                link = ""
            )
            emit(journal)
        }
    }

    override fun getYears(): LiveData<List<Int>> {
        return MutableLiveData<List<Int>>().apply {
            value = years
        }
    }
}