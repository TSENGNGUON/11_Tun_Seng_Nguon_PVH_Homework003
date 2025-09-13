package com.example.noteapp.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.noteapp.model.Notes
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AutoSliding(
    notes: List<Notes>,
    onMarkClick: (Notes) -> Unit
) {
    if (notes.isEmpty()) {
        Text("No notes available", modifier = Modifier.fillMaxWidth())
        return
    }


    val listState = rememberLazyListState()

    // Auto-scroll only if > 1 item
    LaunchedEffect(Unit) {
        while (isActive) {
            listState.scrollBy(2f) // smaller value → slower scroll
            delay(16L)             // 16ms ≈ 60fps
        }
    }

    LazyRow(
        state = listState,
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(Int.MAX_VALUE) { index ->
            val item = notes[index % notes.size]
            if (item.isSave) {
                HeroNoteCard(
                    item,
                    onMarkClick = { onMarkClick(item) }
                )
            }
        }
    }
}







