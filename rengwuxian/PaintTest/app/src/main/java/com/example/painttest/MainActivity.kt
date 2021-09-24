package com.example.painttest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.painttest.databinding.ActivityMainBinding
import com.example.painttest.maskfilter.MaskFilterTestActivity
import com.example.painttest.patheffect.EffectTestActivity
import com.example.painttest.shader.ShaderTestActivity

class MainActivity : AppCompatActivity() {
    private lateinit var mViewBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mViewBinding.root)
        setListener()
    }

    private fun setListener() {
        mViewBinding.shaderBtn.setOnClickListener {
            goToPage(ShaderTestActivity::class.java)
        }
        mViewBinding.effectBtn.setOnClickListener { goToPage(EffectTestActivity::class.java) }
        mViewBinding.maskFilterBtn.setOnClickListener { goToPage(MaskFilterTestActivity::class.java) }
    }

    private fun <T> goToPage(clz: Class<T>) {
        val intent = Intent(this, clz)
        startActivity(intent)
    }


}