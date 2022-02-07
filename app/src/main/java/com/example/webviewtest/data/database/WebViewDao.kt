package com.example.webviewtest.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.webviewtest.data.network.model.LinkContainerDto

@Dao
interface WebViewDao {

    @Query("SELECT * FROM all_link")
    fun getLink(): LiveData<LinkContainerDto>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDatabase(linkContainerDto: LinkContainerDto)

}