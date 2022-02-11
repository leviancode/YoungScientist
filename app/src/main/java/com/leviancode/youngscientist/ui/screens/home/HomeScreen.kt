package com.leviancode.youngscientist.ui.screens.home

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.ScrollableTabRow
import androidx.compose.material.Tab
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.leviancode.youngscientist.R
import com.leviancode.youngscientist.data.entites.Journal
import com.leviancode.youngscientist.utils.years

@Composable
fun HomeScreen(viewModel: HomeViewModel = viewModel()) {
    val years by viewModel.years.observeAsState(initial = listOf())
    val selectedYear by viewModel.selectedYear.observeAsState(initial = viewModel.currentYear)
    val journals by viewModel.journals.observeAsState(initial = listOf())

    Scaffold(
        topBar = {
            TopBar {
                Log.i("HomeScreen", "top bar menu click!")
            }
        }
    ) {
        Column {
            YearsTabs(years = years, selectedYear) { selectedYear ->
                viewModel.loadJournals(selectedYear)
            }
            JournalsGrid(journals = journals) { selectedJournal ->

            }
        }
    }
}

@Composable
fun TopBar(onMenuClick: () -> Unit) {
    TopAppBar(
        title = { Text(stringResource(R.string.home_title)) },
        elevation = 0.dp
       /* navigationIcon = {
            IconButton(onClick = { onMenuClick() }) {
                Icon(Icons.Filled.Menu, contentDescription = null)
            }
        }*/
    )
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

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun JournalsGrid(journals: List<Journal>, onJournalClick: (Journal) -> Unit) {
    LazyVerticalGrid(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        cells = GridCells.Fixed(3),
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
    Image(
        modifier = Modifier
            .padding(8.dp)
            .clickable { onItemClick(journal) },
        painter = painterResource(R.drawable.cover_test),
        contentDescription = stringResource(
            R.string.journal_content_description,
            journal.numberTotal
        )
    )
}

@Composable
@Preview
fun YearsTabsPreview() {
    YearsTabs(years = years, selectedYear = 2021, onTabClick = {})
}