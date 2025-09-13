package com.example.noteapp.components

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.noteapp.MainScreen
import com.example.noteapp.R
import com.example.noteapp.model.Notes
import com.example.noteapp.ui.theme.NoteAppTheme

@Composable
fun HeroNoteCard(
    item: Notes,
    onMarkClick:(Notes) -> Unit 
){
    Card(
        modifier = Modifier
            .width(350.dp)
            .height(160.dp)
            .border(
                BorderStroke(1.dp, Color.Gray.copy(alpha = 0.2f)),
                shape = RoundedCornerShape(16.dp)
            )
    ) {
        Row(
            modifier = Modifier
                .background(Color.White)
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            Image(
                painter = painterResource(id = item.imagesReId ?: R.drawable.demon_slayer),
                contentDescription = null,
                modifier = Modifier.size(100.dp)
            )

            Spacer(modifier = Modifier
                .width(10.dp))



            Column(
                modifier = Modifier
                    .height(120.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "${item.title}",
                        fontWeight = FontWeight.Medium
                    )
                    IconButton(
                        onClick = {
                            onMarkClick(item)
                        }
                    ) {
                        Icon(
                            painter = painterResource(
                                id = if (item.isSave){
                                    R.drawable.mark
                                } else{
                                    R.drawable.unsave
                                }


                            ),
                            contentDescription = null,
                            tint = if (item.isSave) Color(0xFFFF8B8D) else Color.Gray

                        )
                    }
                }
                Spacer(modifier = Modifier
                    .height(5.dp))
                Row {
                    Text(
                        text = "${item.content}",
                        maxLines = 3,
                        color = Color(0xFF5D5D5D),
                        overflow = TextOverflow.Ellipsis,
                        fontWeight = FontWeight.Normal,
                        fontSize = 14.sp
                    )
                }

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HeroCardPreview() {


    NoteAppTheme {
        HeroNoteCard(
            Notes(
                1, "Notes App",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam mattis fringilla",
                R.drawable.demon_slayer
            ),
            onMarkClick = {}
        )
    }
}