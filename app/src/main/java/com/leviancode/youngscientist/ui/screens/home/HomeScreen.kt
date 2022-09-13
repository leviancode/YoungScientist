package com.leviancode.youngscientist.ui.screens.home

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.ScrollableTabRow
import androidx.compose.material.Tab
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import com.leviancode.youngscientist.R
import com.leviancode.youngscientist.data.entites.Journal
import com.leviancode.youngscientist.ui.common.TopBar
import com.leviancode.youngscientist.utils.years

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    navigateToJournalDetail: (journalId: Int) -> Unit
) {
    val years by viewModel.years.observeAsState(initial = listOf())
    val selectedYear by viewModel.selectedYear.observeAsState(initial = viewModel.currentYear)
    val journals by viewModel.journals.observeAsState(initial = listOf())

    Scaffold(
        topBar = {
            TopBar(
                title = stringResource(R.string.home_title),
                navigationIcon = {
                    Icon(Icons.Filled.Menu, contentDescription = null)
                },
                onNavigationIconClick = {
                    Log.i("HomeScreen", "top bar menu click!")
                }
            )
        },
        content = { padding ->
            Column(modifier = Modifier.padding(padding)) {
                YearsTabs(years = years, selectedYear) { selectedYear ->
                    viewModel.loadJournals(selectedYear)
                }
                JournalsGrid(journals = journals) { selectedJournal ->
                    navigateToJournalDetail(selectedJournal.number)
                }
            }
        })
}

@Composable
fun YearsTabs(years: List<Int>, selectedYear: Int, onTabClick: (year: Int) -> Unit) {
    val selectedTabIndex = years.indexOf(selectedYear)
    ScrollableTabRow(selectedTabIndex = if (selectedTabIndex >= 0) selectedTabIndex else 0,
        tabs = {
            years.forEachIndexed { index, year ->
                Tab(selected = selectedYear == year,
                    onClick = {
                        onTabClick(year)
                    },
                    text = {
                        Text(text = year.toString())
                    })
            }
        })
}

@Composable
fun JournalsGrid(journals: List<Journal>, onJournalClick: (Journal) -> Unit) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        contentPadding = PaddingValues(
            start = dimensionResource(R.dimen.grid_item_padding),
            top = dimensionResource(R.dimen.grid_item_padding),
            end = dimensionResource(R.dimen.grid_item_padding),
            bottom = dimensionResource(R.dimen.grid_item_padding)
        ),
        content = {
            items(journals.size) { index ->
                JournalItem(journals[index], onJournalClick)
            }
        })
}

@Composable
fun JournalItem(journal: Journal, onItemClick: (Journal) -> Unit) {
    Column(
        modifier = Modifier
            .clickable { onItemClick(journal) },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier
                .height(dimensionResource(R.dimen.grid_item_height)),
            painter = rememberImagePainter(journal.cover),
            contentDescription = stringResource(
                R.string.journal_content_description,
                journal.number
            ),
            contentScale = ContentScale.FillHeight
        )
        Text(
            modifier = Modifier
                .padding(top = 4.dp),
            textAlign = TextAlign.Center,
            text = journal.title
        )
    }

}

@Composable
@Preview
fun YearsTabsPreview() {
    YearsTabs(years = years, selectedYear = 2021, onTabClick = {})
}