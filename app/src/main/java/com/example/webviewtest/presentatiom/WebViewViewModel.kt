package com.example.webviewtest.presentatiom


import android.app.Application
import android.os.Bundle
import android.util.Log
import android.webkit.WebView
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.webviewtest.R
import com.example.webviewtest.data.database.AppDatabase
import com.example.webviewtest.data.network.api.ApiFactory
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class WebViewViewModel(application: Application):AndroidViewModel(application) {


    private val compositeDisposable = CompositeDisposable()
    private val db = AppDatabase.getInstance(application)
    val getLink = db.webViewDao().getLink()
    var wragmentBundle = Bundle()

    fun loadData(){
        val disposable = ApiFactory.apiService.loadLink()
            .subscribeOn(Schedulers.io())
            .subscribe(
                {
                        db.webViewDao().insertDatabase(it)
                }, {
                    Log.d("MyLog", "${it.message}")
                }
            )
        compositeDisposable.add(disposable)
    }


    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
  }