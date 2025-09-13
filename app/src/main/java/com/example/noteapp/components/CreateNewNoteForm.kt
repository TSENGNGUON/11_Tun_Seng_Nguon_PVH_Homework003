package com.example.noteapp.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.Popup
import androidx.compose.ui.zIndex
import com.example.noteapp.ui.theme.NoteAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PopupScreen(
    onCancelClick: () -> Unit = {},
    onSaveClick: () -> Unit = {},
    showPopup: () -> Unit = {},
    title: String,
    content: String,
    onTitleChange: (String) -> Unit,
    onContentChange: (String) -> Unit,
) {

    val isInputValid = title.isNotBlank() && content.isNotBlank()

    Dialog(
       onDismissRequest = {showPopup()}
    ) {

            Column(
                modifier = Modifier
                    .zIndex(5f)
                    .width(300.dp)
                    .background(
                        Color.White,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .padding(24.dp)
                    .clip(RoundedCornerShape(16.dp))
            ) {
                Text("Create New Note", fontSize = 20.sp, fontWeight = FontWeight.Medium)
                Spacer(modifier = Modifier.height(18.dp))
                OutlinedTextField(
                    value = title,
                    onValueChange = { onTitleChange(it) },
                    label = { Text("Title") },
                    modifier = Modifier.fillMaxWidth()


                )
                Spacer(modifier = Modifier.height(10.dp))
                OutlinedTextField(
                    value = content,
                    onValueChange = { onContentChange(it) },
                    label = { Text("Content") },
                    modifier = Modifier.fillMaxWidth()

                )


                Spacer(modifier = Modifier.height(18.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Button(
                        onClick = {
                            showPopup()
                        },
                        colors = ButtonDefaults.buttonColors(
                            Color.Transparent
                        )

                    ) {
                        Text(
                            text = "Cancel",
                            color = Color.Blue.copy(
                                alpha = 0.8f
                            ),
                            fontWeight = FontWeight.Medium
                        )
                    }

                    Button(
                        onClick = {
                            println("Save is clicked")
                            onSaveClick()
                        },
                        colors = ButtonDefaults.buttonColors(
                            Color.Transparent
                        ),
                        enabled = isInputValid

                    ) {
                        Text(
                            text = "Save",
                            fontWeight = FontWeight.Medium,
                            color =
                                if (isInputValid) Color.Green.copy(alpha = 0.8f) else Color.Gray.copy(
                                    alpha = 0.8f
                                )
                        )
                    }
                }
            }





    }
}
@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    NoteAppTheme {
        PopupScreen(
            title = "",
            content = "",
            onTitleChange = {},
            onContentChange = {}
        )
    }
}