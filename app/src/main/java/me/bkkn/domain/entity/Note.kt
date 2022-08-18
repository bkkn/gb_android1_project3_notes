package me.bkkn.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Note(
    var title: String,
    var text: String,
    val timestamp: Long
) : Parcelable
