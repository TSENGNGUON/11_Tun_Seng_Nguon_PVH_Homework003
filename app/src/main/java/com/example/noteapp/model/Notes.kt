package com.example.noteapp.model

import androidx.annotation.DrawableRes
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "notes")
data class Notes(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val content: String,
    @DrawableRes val imagesReId: Int,
    val isSave: Boolean = false,
    val createdDate: Long = System.currentTimeMillis() ,
    val isEdit: Boolean = false
)


