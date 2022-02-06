package com.example.webviewtest.presentatiom

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.activityViewModels
import com.example.webviewtest.databinding.FragmentWebViewBinding


class WebViewFragment() : Fragment() {

    lateinit var binding: FragmentWebViewBinding
    private val viewModel: WebViewViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWebViewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val user = requireActivity().getSharedPreferences("hasVisited", Context.MODE_PRIVATE)
        val visited = user.getBoolean("hasVisited", false)
        if (!visited){
            viewModel.link.observe(this){
                webViewSetup(it)
            }
            user.edit().putBoolean("hasVisited", true).apply()
        } else {
            viewModel.home.observe(this){
                webViewSetup(it)
            }
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun webViewSetup(site: String) {
        binding.webView.webViewClient = WebViewClient()
        binding.webView.apply {
            loadUrl(site)
            settings.javaScriptEnabled = true
        }
    }
}