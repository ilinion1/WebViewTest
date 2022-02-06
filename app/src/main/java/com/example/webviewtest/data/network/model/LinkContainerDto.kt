package com.example.webviewtest.data.network.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "all_link")
data class LinkContainerDto(

    @PrimaryKey(autoGenerate = true)
    @SerializedName("link" ) val link: String? = null,
    @SerializedName("home" ) val home: String? = null
)
