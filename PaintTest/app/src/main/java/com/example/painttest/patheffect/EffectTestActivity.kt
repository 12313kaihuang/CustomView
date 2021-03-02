package com.example.painttest.patheffect

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.painttest.databinding.ActivityListBinding

class EffectTestActivity:AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewBinding = ActivityListBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        val adapter = ItemAdapter()
        viewBinding.recyclerView.adapter = adapter
        viewBinding.recyclerView.layoutManager = LinearLayoutManager(this)

        adapter.submitList((0..5).toList())
    }
}