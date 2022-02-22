package com.leviancode.youngscientist.data.api

import com.leviancode.youngscientist.data.entites.Journal
import it.skrape.core.htmlDocument
import it.skrape.fetcher.AsyncFetcher
import it.skrape.fetcher.response
import it.skrape.fetcher.skrape
import it.skrape.selects.html5.div
import java.util.regex.Pattern
import javax.inject.Inject

class HtmlParser @Inject constructor() {
    private val newUrl = "https://molodyivchenyi.ua/index.php/journal/issue/archive"
    private val oldUrl = "http://molodyvcheny.in.ua/ua/archive/"
    private val patternForTitleParse =
        Pattern.compile("№\\W?(\\d+)\\W\\((\\d+)\\).+\\(?(\\d{4})\\)?.*\$")

    suspend fun parseNewSite(): List<Journal> =
        skrape(AsyncFetcher) { // <-- could use any valid fetcher depending on your use-case
            request {
                url = newUrl
            }

            response { // 🍉 <-- response will make actual HTTP request and convert its response to a Result object
                // the scope of 'this' inside the response lambda is Result!
                // that means you can directly call any field or function of Result here.
                val result = mutableListOf<Journal>()
                htmlDocument {

                    div(".card.issue-summary") {
                        findAll {
                            forEach { element ->
                                var title = ""
                                var link = ""
                                val cover = element.eachImage.values.firstOrNull() ?: ""
                                element.eachLink.filterKeys { it.isNotBlank() }.forEach { k, v ->
                                    title = k
                                    link = v
                                }

                                val journal = Journal(
                                    number = title.getNumber(),
                                    numberInYear = title.getNumberInYear(),
                                    year = title.getYear(),
                                    link = link,
                                    cover = cover
                                )
                                result.add(journal)
                            }
                        }
                    }
                }
                result
            }
        }

    fun parseOldSite(): List<Journal> {
        return listOf()
    }

    private fun String.getYear(): Int {
        val matcher = patternForTitleParse.matcher(this)
        return if (matcher.find()) {
            matcher.group(3)?.toInt() ?: 0
        } else 0
    }

    private fun String.getNumber(): Int {
        val matcher = patternForTitleParse.matcher(this)
        return if (matcher.find()) {
            matcher.group(2)?.toInt() ?: 0
        } else 0
    }

    private fun String.getNumberInYear(): Int {
        val matcher = patternForTitleParse.matcher(this)
        return if (matcher.find()) {
            matcher.group(1)?.toInt() ?: 0
        } else 0
    }
}