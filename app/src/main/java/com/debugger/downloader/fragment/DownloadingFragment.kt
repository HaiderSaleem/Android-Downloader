package com.debugger.downloader.fragment


import android.app.AlertDialog
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.debugger.downloader.R
import com.debugger.downloader.adapter.DownloadFilesAdapter
import com.debugger.downloader.database.DatabaseHandler
import com.debugger.downloader.model.FileParams
import com.debugger.downloader.utils.Utils
import com.downloader.OnCancelListener
import com.downloader.OnDownloadListener
import com.downloader.PRDownloader
import kotlinx.android.synthetic.main.item_download_files.*
import kotlinx.android.synthetic.main.fragment_downloading.*
import kotlinx.android.synthetic.main.pop_up_room_name.view.*
import java.io.File
import java.util.*
import kotlin.collections.ArrayList

class DownloadingFragment : BaseFragment(), View.OnClickListener {

    companion object {
        private var downloadingFragment: DownloadingFragment? = null
        private const val TAG = "DownloadingFragment"
        fun newInstance(): DownloadingFragment {
            if (downloadingFragment == null)
                downloadingFragment = DownloadingFragment()
            return downloadingFragment as DownloadingFragment
        }
    }

    private lateinit var downloadFilesAdapter: DownloadFilesAdapter
    private var dataList: ArrayList<FileParams>?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        return inflater.inflate(R.layout.fragment_downloading, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getData()
        fabMore.setOnClickListener(this)
    }


    private fun getData() {
        val dataBase = DatabaseHandler(context)
        dataList = ArrayList()
        dataList = dataBase.filesData
        if (!dataList.isNullOrEmpty())
            setAdapter()
        else {
            dataList = ArrayList()
            setAdapter()
            Toast.makeText(context, "No Files", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setAdapter() {

        downloadFilesAdapter = DownloadFilesAdapter(dataList!!,context!!)
        val layoutManager = LinearLayoutManager(context!!)
        layoutManager.stackFromEnd = true
        layoutManager.reverseLayout = true
        rvFiles.layoutManager = layoutManager
        rvFiles.adapter = downloadFilesAdapter


    }

    override fun onClick(p0: View?) {

        getUrl()
    }


    private fun getUrl() {
        val layoutInflater = LayoutInflater.from(context!!)
        val view = layoutInflater.inflate(R.layout.pop_up_room_name, null)

        val alertDialogBuilder = AlertDialog.Builder(context!!, R.style.Theme_AppCompat_Light_NoActionBar)
        alertDialogBuilder.setView(view)
        val alertDialogCongratulations = alertDialogBuilder.create()
        view.btnSubmit.setOnClickListener {
            val url = view.etRoomName.text.toString()
            if (url.isNotEmpty()) {
                alertDialogCongratulations.dismiss()

                startDownload(url)
            }

        }
        view.btnCancel.setOnClickListener {
            alertDialogCongratulations.dismiss()
        }

        val scale: Float = context!!.resources.displayMetrics.density
        val width = (250 * scale + 0.5f).toInt()
        val height = (200 * scale + 0.5f).toInt()

        alertDialogCongratulations.window!!.setLayout(
            width,
            height
        )
        alertDialogCongratulations.window?.setBackgroundDrawableResource(android.R.color.transparent)
        alertDialogCongratulations.show()
    }

    private fun startDownload(url: String) {

        val fileName  = url.substring( url.lastIndexOf('/')+1, url.length )
       /* val downloadId = PRDownloader.download(url, dirPath, fileName)
            .build()
            .setOnStartOrResumeListener { }
            .setOnPauseListener { }
            .setOnCancelListener { Log.d(TAG, "Cancelled") }
            .setOnProgressListener {
                Log.d(TAG, it.currentBytes.toString())
            }
            .start(object : OnDownloadListener {

                override fun onDownloadComplete() {}

                override fun onError(error: com.downloader.Error?) {
                    Log.d(TAG, error!!.serverErrorMessage + "")
                }

            })
*/
       // PRDownloader.pause(downloadId)
        //DatabaseHandler(context).addFile(downloadId.toString(), fileName,"0")
        val data = FileParams()
        data.fileName = fileName
        data.url = url
        data.status = "0"
        dataList!!.add(data)
        downloadFilesAdapter.notifyDataSetChanged()
    }



}
