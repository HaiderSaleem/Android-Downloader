package com.debugger.downloader.fragment


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager

import com.debugger.downloader.R
import com.debugger.downloader.adapter.DownloadedFilesAdapter
import com.debugger.downloader.database.DatabaseHandler
import com.debugger.downloader.model.DownloadedFilesParam
import kotlinx.android.synthetic.main.fragment_downloaded.*
import java.lang.Exception

class MyFavouriteFragment : BaseFragment() {

    companion object{
        private var myFavouriteFragment:MyFavouriteFragment?=null
        private const val TAG = "MyFavouriteFragment"

        fun newInstance():MyFavouriteFragment
        {
            if(myFavouriteFragment==null)
                myFavouriteFragment = MyFavouriteFragment()
            return myFavouriteFragment as MyFavouriteFragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_my_favourite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fetchFavourite()
    }

    private fun fetchFavourite() {

        try {

            val dbHandler = DatabaseHandler(context)
            val favList = dbHandler.favourites
            setAdapter(favList)

        }
        catch (e:Exception) {

            Log.d(TAG, e.message+"")
        }
    }

    private fun setAdapter(videoData: ArrayList<DownloadedFilesParam>) {

        try {
            val mediaGalleryAdapter = DownloadedFilesAdapter(context!!, videoData,false)
            val layoutManager = LinearLayoutManager(context)
            layoutManager.stackFromEnd = true
            layoutManager.reverseLayout = true
            rvMedia.setLayoutManager(layoutManager)
            rvMedia.setAdapter( mediaGalleryAdapter)
            rvMedia.unVeil()
        } catch (e: Exception) {
            Log.d(TAG, e.toString() + "")
        }


    }



}
