package com.example.noteapp.model

import androidx.annotation.DrawableRes

data class Notes(
    val id: Int,
    val title: String,
    val content: String,
    @DrawableRes val imagesReId: Int ?= null,
    val isSave: Boolean = false
)


