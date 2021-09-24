package com.yu.hu.simpleviews.progressbtn

import android.view.LayoutInflater
import android.widget.SeekBar
import com.yu.hu.libbase.base.BaseFragment
import com.yu.hu.simpleviews.databinding.FragmentProgressBtnTestBinding

class ProgressBtnTestFragment : BaseFragment<FragmentProgressBtnTestBinding>() {

    override fun onInitView() {
        mViewBinding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                mViewBinding.progressBar.setBtnText(
                    "$progress%",
                    ProgressButton.DisplayMode.PROGRESS,
                    progress
                )
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }

        })
    }

    override fun createViewBinding(inflater: LayoutInflater): FragmentProgressBtnTestBinding =
        FragmentProgressBtnTestBinding.inflate(inflater, null, false)
}