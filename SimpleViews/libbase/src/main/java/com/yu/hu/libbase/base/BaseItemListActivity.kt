@file:Suppress("unused")

package com.yu.hu.libcommon.base

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.yu.hu.libbase.base.BaseCommonActivity
import com.yu.hu.libbase.base.BaseItemAdapter
import com.yu.hu.libbase.databinding.ActivityBaseBinding

/**
 * @auther hy
 * create on 2021/07/30 下午3:30
 */
abstract class BaseItemListActivity : AppCompatActivity() {
    protected lateinit var mViewBinding: ActivityBaseBinding
    protected lateinit var mItemAdapter: BaseItemAdapter
    private val itemList: MutableList<BaseItemAdapter.IBaseItem> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewBinding = ActivityBaseBinding.inflate(layoutInflater)
        setContentView(mViewBinding.root)

        mViewBinding.tvText.text = getName()
        mItemAdapter = createAdapter()
        mViewBinding.recyclerView.layoutManager = LinearLayoutManager(this)
        mViewBinding.recyclerView.adapter = mItemAdapter
        onInitItems()
    }

    override fun onStart() {
        super.onStart()
        mItemAdapter.submitList(itemList)
    }

    protected open fun createAdapter() = BaseItemAdapter()

    abstract fun onInitItems()

    protected fun addItem(item: BaseItemAdapter.IBaseItem) {
        itemList.add(item)
    }

    protected fun addBtn(btnText: String, clickListener: (() -> Unit)?) {
        addItem(BaseItemAdapter.BtnItem(btnText, clickListener))
    }

    protected fun <T : Activity> goToPage(clz: Class<T>) {
        startActivity(Intent(this, clz))
    }

    protected fun <T : Fragment> toCommonActivity(clz: Class<T>) {
        startActivity(Intent(this, BaseCommonActivity::class.java).also {
            it.putExtra(BaseCommonActivity.PARAM_KEY_FRAGMENT, clz.name)
        })
    }

    fun getName(): String = this::class.java.simpleName
}