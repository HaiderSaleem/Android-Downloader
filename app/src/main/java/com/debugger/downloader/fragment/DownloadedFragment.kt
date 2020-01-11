package com.debugger.downloader.fragment


import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.debugger.downloader.R
import com.debugger.downloader.adapter.DownloadedFilesAdapter
import com.debugger.downloader.model.DownloadedFilesParam
import kotlinx.android.synthetic.main.fragment_downloaded.*
import java.io.File
import java.lang.Exception


class DownloadedFragment : Fragment() {


    companion object{
        private var downloadedFragment:DownloadedFragment?=null
        private const val TAG = "DownloadedFragment"
        fun newInstance():DownloadedFragment
        {
            if(downloadedFragment==null)
                downloadedFragment = DownloadedFragment()
            return downloadedFragment as DownloadedFragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_downloaded, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fetchLocalVideos()
    }

    @Suppress("DEPRECATION")
    private fun fetchLocalVideos() {
        val path = Environment.getExternalStorageDirectory().toString() + "/Downloader/AData/"

        val videoData = ArrayList<DownloadedFilesParam>()


        val directory = File(path)
        val files = directory.listFiles()
        try {
            for (file in files!!) {

                val mediaGalleryParams = DownloadedFilesParam()
                mediaGalleryParams.name = (file.name)
                mediaGalleryParams.path = (file.absolutePath)
                videoData.add(mediaGalleryParams)

            }
        } catch (e: Exception) {
            Log.d(TAG, e.message + "")
        }

        setAdapter(videoData)
    }

    private fun setAdapter(videoData: ArrayList<DownloadedFilesParam>) {

        try {
            val mediaGalleryAdapter = DownloadedFilesAdapter(context!!, videoData,true)
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
