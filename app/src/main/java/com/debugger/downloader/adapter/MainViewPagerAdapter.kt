package com.debugger.downloader.adapter


import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.debugger.downloader.R
import com.debugger.downloader.fragment.DownloadedFragment
import com.debugger.downloader.fragment.DownloadingFragment


class MainViewPagerAdapter( val context: Context,
                           fm: FragmentManager
) : FragmentPagerAdapter(fm) {


    override fun getItem(position: Int): Fragment {

        return when (position) {
            0 -> DownloadingFragment.newInstance()

            1 -> DownloadedFragment.newInstance()

            else -> DownloadingFragment.newInstance()

        }

    }
    override fun getCount(): Int {

        return 2
    }

    override fun getPageTitle(position: Int): CharSequence? {

        when(position)
        {
            0 -> return "Downloading"
            1 -> return "Downloaded"
        }
        return super.getPageTitle(position)
    }


}
