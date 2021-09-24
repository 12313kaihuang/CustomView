package com.example.painttest.shader

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.painttest.databinding.ActivityListBinding

class ShaderTestActivity :AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewBinding = ActivityListBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        val adapter = ItemAdapter()
        viewBinding.recyclerView.adapter = adapter
        viewBinding.recyclerView.layoutManager = GridLayoutManager(this,2)

        val views = (0..12).toList()
        adapter.submitList(views)
    }

}