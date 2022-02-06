package com.example.webviewtest.presentatiom


import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.webviewtest.data.database.AppDatabase
import com.example.webviewtest.data.network.api.ApiFactory
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class WebViewViewModel(application: Application):AndroidViewModel(application) {

    val link = MutableLiveData<String>()
    val home = MutableLiveData<String>()
    val compositeDisposable = CompositeDisposable()
    val db = AppDatabase.getInstance(application)
    val getLink = db.webViewDao().getLink()
    val getHome = db.webViewDao().getHome()

    fun loadData(){
        val disposable = ApiFactory.apiService.loadLink()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    viewModelScope.launch(Dispatchers.IO) {
                        val firstJob = Job()
                        link.postValue(it.link)
                        home.postValue(it.home)
                        firstJob.complete()
                        firstJob.join()
                    }
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