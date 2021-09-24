package com.example.painttest.maskfilter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.painttest.databinding.ActivityMaskFilterTestBinding

class MaskFilterTestActivity:AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMaskFilterTestBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}