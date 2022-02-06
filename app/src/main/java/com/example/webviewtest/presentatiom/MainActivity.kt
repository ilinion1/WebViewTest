package com.example.webviewtest.presentatiom

import android.os.Bundle
import android.util.Log
import android.webkit.WebView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.webviewtest.R
import com.example.webviewtest.data.network.api.ApiFactory
import com.example.webviewtest.databinding.ActivityMainBinding
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val compositeDisposable = CompositeDisposable()
    private val viewModel: WebViewViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        starsFragment(LoadFragment())

        lifecycleScope.launch(Dispatchers.IO) {
            disposable()
        }
    }

    private fun disposable() {
        val disposable = ApiFactory.apiService.loadLink()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    lifecycleScope.launch(Dispatchers.IO) {
                        val firstJob = Job()
                        viewModel.link.postValue(it.link)
                        viewModel.home.postValue(it.home)
                        firstJob.complete()
                        firstJob.join()
                        starsFragment(WebViewFragment())
                    }
                }, {
                    Log.d("MyLog", "${it.message}")
                }
            )
        compositeDisposable.add(disposable)
    }

    private fun starsFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragContainer, fragment)
            .commit()
    }

    override fun onBackPressed() {
        val webView = findViewById<WebView>(R.id.webView)
        if (webView.canGoBack()) {
            webView.goBack()
        } else super.onBackPressed()
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }
}