package com.yu.hu.simpleviews.expandabletext

import android.view.LayoutInflater
import com.yu.hu.libbase.base.BaseFragment
import com.yu.hu.simpleviews.databinding.FragmentExpandableTextTestBinding

class ExpandableTextTestFragment : BaseFragment<FragmentExpandableTextTestBinding>() {
    override fun onInitView() {

    }

    override fun createViewBinding(inflater: LayoutInflater): FragmentExpandableTextTestBinding =
        FragmentExpandableTextTestBinding.inflate(inflater, null, false)
}