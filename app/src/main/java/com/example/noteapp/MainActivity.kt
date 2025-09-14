package com.example.noteapp

import android.os.Bundle
import android.util.Log
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
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Search
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
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import com.example.noteapp.components.AutoSliding
import com.example.noteapp.components.DeleAllNoteScreen
import com.example.noteapp.components.HeroNoteCard
import com.example.noteapp.components.NoteCard
import com.example.noteapp.components.PopupScreen
import com.example.noteapp.components.toFormattedDate
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

    // Store state of bottom sheet
    var showBottomSheet by remember { mutableStateOf(false) }
    // Sheet State
    var sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
    )
    // store note that selected
    var selectedNote by remember { mutableStateOf<Notes?>(null) }



    val defaultImages = listOf(
        R.drawable.demon_slayer,
        R.drawable.demon_slayer_fire,
        R.drawable.tomioka,
    )

    val randomImage = defaultImages.random()

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


                AutoSliding(notes, onMarkClick = {
                    scope.launch {
                        viewModel.update(it.copy(isSave = !it.isSave))
                    }
                })


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
                            },
                            onCardClick = {
                                selectedNote = note
                                showBottomSheet = true
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
                                    imagesReId = randomImage
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

        // show bottom sheet form
        if (showBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = {
                    selectedNote = null
                    showBottomSheet = false

                },
                sheetState = sheetState
            ) {
                LazyColumn(
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
                ) {
                    item {
                        Spacer(
                            modifier = Modifier.height(24.dp)

                        )

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 10.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            IconButton(
                                onClick = {
                                    scope.launch {
                                        val updateNote = selectedNote!!.copy(isEdit = !selectedNote!!.isEdit)
                                        viewModel.update(updateNote)
                                        selectedNote = updateNote
                                    }
                                },
                                modifier = Modifier
                                    .background( if (selectedNote!!.isEdit) Color.Red.copy(alpha = 0.6f) else
                                        Color.Blue.copy(alpha = 0.2f),
                                        shape = RoundedCornerShape(50.dp)
                                    )
                            ) {
                                Icon(
                                    imageVector = if (selectedNote!!.isEdit) Icons.Default.Delete else Icons.Default.Edit,
                                    contentDescription = null,
                                    tint = if (selectedNote!!.isEdit) Color.White else Color.Blue,

                                    )
                            }


                            if (selectedNote!!.isEdit){
                                IconButton(
                                    onClick = {
                                        scope.launch {
                                            Log.d("Edited", "Edit Check Is Clicked")
                                            val updateNote = selectedNote!!.copy(isEdit = !selectedNote!!.isEdit)
                                            viewModel.update(updateNote)
                                            selectedNote = updateNote
                                            showBottomSheet = false

                                        }
                                    },
                                    modifier = Modifier
                                        .background(
                                            Color.Blue.copy(alpha = 0.2f),
                                            shape = RoundedCornerShape(50.dp)
                                        )
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Check,
                                        contentDescription = null,
                                        tint = Color.Blue,

                                        )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(24.dp))
                    }

                    item {
                        if (selectedNote!!.isEdit){
                            OutlinedTextField(
                                value = selectedNote!!.title,
                                onValueChange = { newTitle -> selectedNote = selectedNote!!.copy(title = newTitle) },
                                label = { Text("Title") },
                                modifier = Modifier.fillMaxWidth()


                            )
                        }else{
                            Text(text = "${selectedNote!!.title}")
                        }
                        if (selectedNote!!.isEdit){

                        }else{
                            Spacer(modifier = Modifier.height(18.dp))
                        }
                        if (selectedNote!!.isEdit) {
                            Text("")
                        }else{
                            Text(text = "${selectedNote!!.createdDate.toFormattedDate()}")
                        }
                        if (
                            selectedNote!!.isEdit
                        ){

                        }else {
                            Spacer(modifier = Modifier.height(18.dp))
                        }
                        if (selectedNote!!.isEdit){
                            OutlinedTextField(
                                value = selectedNote!!.content,
                                onValueChange = { newContent -> selectedNote = selectedNote!!.copy(content = newContent) },
                                label = { Text("Content") },
                                modifier = Modifier.fillMaxWidth()


                            )
                        }else{
                            Text(text = "${selectedNote!!.content}")
                        }

                    }
                }
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