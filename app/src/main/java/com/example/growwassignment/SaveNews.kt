package com.example.growwassignment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.growwassignment.models.NewsModel

class SaveNews : AppCompatActivity() {
    lateinit var recyclerView: RecyclerView
    lateinit var viewModel: NewsViewModel
    lateinit var newData: MutableList<NewsModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_save_news)


        recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
        newData = mutableListOf()

        val adapter = CustomAdapter(newData, object : CustomAdapter.SaveClickListener{
            override fun onSaveBtnClick(position: Int) {
                this@SaveNews?.let { viewModel.deleteNews(it,newData.get(position)) }
            }
        })



        viewModel = ViewModelProvider(this).get(NewsViewModel::class.java)

        this?.let {
            viewModel.getNewsFromDB(it.applicationContext)?.observe(this, Observer {
                newData.clear()
                newData.addAll(it)
                adapter.notifyDataSetChanged()
            })
        }

        recyclerView.adapter = adapter

    }


}