package com.tty.desh.utils

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.tty.desh.adapter.SimpleFragmentPagerAdapter

object Simpler {
    fun createAdapter(fm:FragmentManager, pages:ArrayList<Fragment>): SimpleFragmentPagerAdapter{
        return SimpleFragmentPagerAdapter(fm,pages)
    }
}