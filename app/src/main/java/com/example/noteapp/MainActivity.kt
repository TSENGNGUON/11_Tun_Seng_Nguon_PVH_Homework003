package com.example.noteapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Scaffold
import androidx.compose.material3.*
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import com.example.noteapp.components.DeleAllNoteScreen
import com.example.noteapp.components.NoteCard
import com.example.noteapp.components.PopupScreen
import com.example.noteapp.database.NotesDatabase
import com.example.noteapp.model.Notes
import com.example.noteapp.repository.NotesRepository
import com.example.noteapp.ui.theme.NoteAppTheme
import com.example.noteapp.viewmodel.NotesViewModel
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val database = NotesDatabase.getDatabase(applicationContext)
        val repository = NotesRepository(database.notesDao())
        val viewModel = ViewModelProvider(this, NoteViewModelFactory(repository))[NotesViewModel::class.java]

        setContent {
            NoteAppTheme {
                MainScreen(viewModel)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(viewModel: NotesViewModel) {
    val borderCorner = 50.dp
    val borderWidth = 2.dp
    val borderColor = Color.Gray

    val notes by viewModel.allNotes.collectAsState(initial = emptyList())
    val scope = rememberCoroutineScope()
    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }
    var showPopup by remember { mutableStateOf(false) }
    var showDeletePopup by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            modifier =
                Modifier
                    .fillMaxSize(),
            topBar = {
                TopAppBar(
                    title = {
                        Column(
                            modifier = Modifier.padding(start = 5.dp),
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text("Seng Nguon", fontWeight = FontWeight.SemiBold)
                            Text("Laxera", color = Color(0xFF475467), fontSize = 16.sp)
                        }
                    },
                    navigationIcon = {
                        Image(
                            painter = painterResource(id = R.drawable.my_pf),
                            contentDescription = null,
                            modifier = Modifier
                                .clip(CircleShape)
                                .border(
                                    BorderStroke(borderWidth, borderColor),
                                    shape = RoundedCornerShape(borderCorner)
                                )


                        )
                    },
                    actions = {
                        IconButton(
                            onClick = {
                                showPopup = true
                            },
                            modifier = Modifier
                                .background(
                                    Color.Gray.copy(alpha = 0.2f),
                                    shape = RoundedCornerShape(50.dp)
                                )
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.plus_circle),
                                contentDescription = null,
                                tint = Color.Black
                            )
                        }
                        Spacer(modifier = Modifier.width(15.dp))

                        IconButton(
                            onClick = {
                                showDeletePopup = true

                            },
                            modifier = Modifier
                                .background(
                                    Color.Gray.copy(alpha = 0.2f),
                                    shape = RoundedCornerShape(50.dp)
                                )
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = null,
                                tint = Color.Red,

                                )
                        }
                    }
                )
            }

        ) { innerPadding ->

            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxWidth()
            ) {
                Spacer(modifier = Modifier.height(20.dp))


//                AutoSliding(notes)

                Spacer(modifier = Modifier.height(20.dp))



                LazyVerticalStaggeredGrid(
                    columns = StaggeredGridCells.Fixed(2),

                ) {

                    items(notes){
                            note ->
                        NoteCard(
                            note,
                            onMarkClick = {
                                scope.launch {
                                   viewModel.update(note.copy(isSave = !note.isSave))
                                }
                            }

                        )
                    }
                }


            }


        }

        if (showPopup) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.5f))
                    .clickable {
                        showPopup = false
                    },
                contentAlignment = Alignment.Center
            ) {
                PopupScreen(
                    showPopup = {
                        showPopup = false
                    },
                    title = title,
                    content = content,
                    onTitleChange = {
                        newTitle -> title = newTitle
                    },
                    onContentChange = {
                        newContent -> content = newContent
                    },
                    onSaveClick = {
                        scope.launch {
                            viewModel.insert(
                                Notes(
                                    title = title,
                                    content = content,
                                )
                                ,
                            )
                            title = ""
                            content = ""
                            showPopup = false
                        }

                    }

                )
            }

        }

        if (showDeletePopup) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.5f))
                    .clickable {
                        showDeletePopup = false
                    },
                contentAlignment = Alignment.Center
            ) {
                DeleAllNoteScreen(
                    showPopup = {
                        showDeletePopup = false
                    },
                    onCancelClick = {
                        showDeletePopup = false
                    },
                    onDeleteClick = {
                        scope.launch {
                            viewModel.delete(allNotes = notes)
                            showDeletePopup = false
                        }
                    }
                )
            }
        }
    }

}
@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {

    NoteAppTheme {
        MainScreen(
        viewModel = TODO()
        )
    }
}