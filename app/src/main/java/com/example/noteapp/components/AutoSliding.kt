package com.example.noteapp.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import com.example.noteapp.model.Notes
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AutoSliding(notes: List<Notes>){

    if (notes.isEmpty()){
        val listState = rememberLazyListState()

        LaunchedEffect(Unit) {
            while (isActive) {
                listState.scrollBy(1f)
                delay(16L)
            }
        }

        LazyRow(
            state = listState,
            modifier = Modifier.fillMaxWidth(),
//        userScrollEnabled = false
        ) {
            items(Int.MAX_VALUE){
                    item ->
                val item = notes[item % notes.size]
                Spacer(modifier = Modifier.width(15.dp))
                HeroNoteCard(item)
            }

        }
    }







}

