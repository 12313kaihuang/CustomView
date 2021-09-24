package com.yu.hu.simpleviews

import com.yu.hu.libbase.base.BaseFragment
import com.yu.hu.libcommon.base.BaseItemListActivity
import com.yu.hu.simpleviews.expandabletext.ExpandableTextTestFragment
import com.yu.hu.simpleviews.progressbtn.ProgressBtnTestFragment

class MainActivity : BaseItemListActivity() {

    override fun onInitItems() {
        addBtn("ProgressBtn") {
            toCommonActivity(ProgressBtnTestFragment::class.java)
        }
        addBtn("ExpandableTextView", ExpandableTextTestFragment::class.java)
    }

    private fun <T : BaseFragment<*>> addBtn(text: String, fragment: Class<T>) {
        addBtn(text) { toCommonActivity(fragment) }
    }
}