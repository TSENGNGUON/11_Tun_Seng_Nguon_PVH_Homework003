package com.example.noteapp.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.zIndex
import com.example.noteapp.ui.theme.NoteAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeleAllNoteScreen(
    onCancelClick: () -> Unit = {},
    onDeleteClick: () -> Unit = {},
    showPopup: () -> Unit = {}
) {


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
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            Icon(
                imageVector = Icons.Default.Warning,
                contentDescription = null,
                tint = Color.Red.copy(alpha = 0.7f)
            )
            Spacer(modifier = Modifier.height(18.dp))
            Text("Delete ALL Notes?", fontSize = 18.sp, fontWeight = FontWeight.Normal)
            Spacer(modifier = Modifier.height(18.dp))
            Text("This will permanently delete all of your  notes. This action cannot be undone",
                fontSize = 12.sp,
                fontWeight = FontWeight.Normal,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(18.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Button(
                    onClick = {
                        onCancelClick()
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
                        onDeleteClick()
                        println("Delete is Clicked")
                    },
                    colors = ButtonDefaults.buttonColors(
                        Color.Transparent
                    ),

                ) {
                    Text(
                        text = "Delete",
                        fontWeight = FontWeight.Medium,
                        color = Color.Red
                    )
                }
            }
        }





    }
}

@Preview(showBackground = true)
@Composable
fun DeleteAllNotePreview() {
    NoteAppTheme {
        DeleAllNoteScreen()
    }
}