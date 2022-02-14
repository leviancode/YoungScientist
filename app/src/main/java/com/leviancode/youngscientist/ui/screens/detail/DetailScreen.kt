package com.leviancode.youngscientist.ui.screens.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.leviancode.youngscientist.R
import com.leviancode.youngscientist.ui.common.TopBar

@Composable
fun DetailScreen(
    journalId: Int,
    viewModel: DetailViewModel = hiltViewModel(),
    navigateBack: () -> Unit
) {
    val journal by viewModel.getJournal(journalId).observeAsState()

    Scaffold(
        topBar = {
            TopBar(
                title = stringResource(R.string.detail_title, journalId),
                navigationIcon = {
                    Icon(Icons.Filled.ArrowBack, contentDescription = null)
                },
                onNavigationIconClick = {
                    navigateBack()
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Row {
                Image(
                    modifier = Modifier
                        .weight(0.6f),
                    painter = painterResource(R.drawable.cover_test),
                    contentDescription = stringResource(
                        R.string.journal_content_description,
                        journal?.number ?: 0
                    )
                )
                Text(
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .weight(1.0f),
                    text = journal?.coverDescription ?: ""
                )
            }
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .padding(32.dp),
                onClick = {

                }
            ) {
                Text(
                    text = stringResource(R.string.read),
                )
            }
        }
    }
}