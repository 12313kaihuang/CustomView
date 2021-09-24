package com.yu.hu.libbase.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.yu.hu.libbase.R
import com.yu.hu.libbase.databinding.ActivityCommonBaseBinding
import java.lang.Exception

/**
 * 用来展示fragment的
 */
class BaseCommonActivity : AppCompatActivity() {

    private lateinit var mViewBinding: ActivityCommonBaseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewBinding = ActivityCommonBaseBinding.inflate(layoutInflater)
        intent?.getStringExtra(PARAM_KEY_FRAGMENT)?.let {
            initFragment(it)
        }
        setContentView(mViewBinding.root)
    }

    private fun initFragment(clzName: String) {
        var transaction: FragmentTransaction? = null
        try {
            transaction = supportFragmentManager.beginTransaction()
            val fragment: Fragment = Class.forName(clzName).newInstance() as Fragment
            transaction.replace(R.id.fragment_container, fragment)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            transaction?.commit();
        }
    }

    companion object {
        const val PARAM_KEY_FRAGMENT = "key_fragment"
    }
}