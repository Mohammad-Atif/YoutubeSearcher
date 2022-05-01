package com.example.youtubesearcher

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.AbsListView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.youtubesearcher.adapters.SearchResultAdapter
import com.example.youtubesearcher.databinding.ActivityMainBinding
import com.example.youtubesearcher.viewmodels.YoutubeViewModel

import java.util.*
/*
we can hit the youtube api only 100 times with the free quota,
after that it will not work
 */
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var searchResultAdapter: SearchResultAdapter
    val DELAY:Long = 1000
    lateinit var viewModel: YoutubeViewModel
    lateinit var manager:LinearLayoutManager
    private var isScroll = false
    private var currentItem = -1
    private var totalItem = -1
    private var scrollOutItem = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initRecyclerView()
        // checking the auto event trigger in edittext
        viewModel = ViewModelProvider(this).get(YoutubeViewModel::class.java)
        var timer = Timer()

        binding.txtSearch.addTextChangedListener (
            object:TextWatcher{
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }
                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    timer.cancel()
                    timer.purge()

                }
                override fun afterTextChanged(p0: Editable?) {
                    timer = Timer()
                    timer.schedule(object : TimerTask() {
                        override fun run() {
                            if(p0.toString().isNotEmpty() and p0.toString().isNotBlank())
                            viewModel.getVideoList(p0.toString(),true)
                        }
                    }, DELAY)
                }
            }
        )
        binding.btnSearch.setOnClickListener {
            if(binding.txtSearch.text.isNotBlank() and binding.txtSearch.text.isNotEmpty())
                viewModel.getVideoList(binding.txtSearch.text.toString(),true)
        }
        viewModel.searchList.observe(this, androidx.lifecycle.Observer {
            if (it != null) {
                searchResultAdapter.setData(it.items,binding.searchRecyclerView,viewModel.isNewSearch)
            }
        })


        binding.searchRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                    isScroll = true
                }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                currentItem = manager.childCount
                totalItem = manager.itemCount
                scrollOutItem = manager.findFirstVisibleItemPosition()
                if (isScroll && (currentItem + scrollOutItem == totalItem)){
                    isScroll = false
                    viewModel.getVideoList()

                    }
                }
        })


    }


    private fun initRecyclerView()
    {
        binding.searchRecyclerView.apply {
            manager = LinearLayoutManager(applicationContext)
            layoutManager= manager
            searchResultAdapter= SearchResultAdapter()
            adapter= searchResultAdapter
        }
    }
}