package com.debugger.downloader.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.core.content.FileProvider
import com.debugger.downloader.BuildConfig
import com.debugger.downloader.R
import com.debugger.downloader.database.DatabaseHandler
import com.debugger.downloader.model.FileParams
import com.debugger.downloader.utils.Utils
import com.downloader.OnDownloadListener
import com.downloader.PRDownloader
import kotlinx.android.synthetic.main.item_download_files.view.*
import java.io.File

class DownloadFilesAdapter(private val idList:ArrayList<FileParams>,private val context:Context): RecyclerView.Adapter<DownloadFilesAdapter.ViewHolder>()
{
    val TAG ="DownloadAdapter"
    private val dir = File(Environment.getExternalStorageDirectory(), "/Downloader/AData/")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_download_files,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
       return idList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems()
    }

    inner class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView)
    {
        fun bindItems()
        {
            if(idList[adapterPosition].status!="1")
            {
                itemView.tvFileName.text = idList[adapterPosition].fileName
                itemView.bPause.setOnClickListener {
                    val dbHandler = DatabaseHandler(context)
                    if (idList[adapterPosition].status == "2") {
                        itemView.bPause.text = context.getString(R.string.pause)
                        idList[adapterPosition].status = "3"
                        dbHandler.updateStatus(0, idList[adapterPosition].fid)
                        resume(
                            idList[adapterPosition].url,
                            idList[adapterPosition].fileName,
                            idList[adapterPosition].fid
                        )
                    }
                    else if(idList[adapterPosition].status == "4")
                    {
                        itemView.bCancel.text = "Retry"
                    }
                    else {
                        itemView.bPause.text = context.getString(R.string.resume)
                        PRDownloader.pause(idList[adapterPosition].fid.toInt())
                        idList[adapterPosition].status = "2"

                        dbHandler.updateStatus(2, idList[adapterPosition].fid)
                    }
                }
                itemView.bCancel.setOnClickListener {
                    if (idList[adapterPosition].status == "4") {

                        resume(
                            idList[adapterPosition].url,
                            idList[adapterPosition].fileName,
                            idList[adapterPosition].fid
                        )
                        val dbHandler = DatabaseHandler(context)
                        dbHandler.updateStatus(5, idList[adapterPosition].fid)
                        itemView.bCancel.text = "Cancel"
                        idList[adapterPosition].status = "5"


                    } else {
                        PRDownloader.cancel(idList[adapterPosition].fid.toInt())
                        val dbHandler = DatabaseHandler(context)
                        dbHandler.updateStatus(4, idList[adapterPosition].fid)
                        itemView.bCancel.text = "Retry"
                        idList[adapterPosition].status = "4"

                    }
                }

                if (idList[adapterPosition].status == "3") {
                    itemView.bPause.text = context.getString(R.string.resume)
                }


                if (idList[adapterPosition].status == "0") {
                    idList[adapterPosition].status = "1"
                    val downloadId = PRDownloader.download(
                        idList[adapterPosition].url,
                        dir.absolutePath,
                        idList[adapterPosition].fileName
                    ).build()
                        .setOnStartOrResumeListener { }
                        .setOnPauseListener { }
                        .setOnCancelListener { Log.d(TAG, "Cancelled") }
                        .setOnProgressListener {
                            itemView.tvStatus.text = Utils.getProgressDisplayLine(it.currentBytes, it.totalBytes)
                            Log.d(TAG, it.currentBytes.toString())
                        }
                        .start(object : OnDownloadListener {

                            override fun onDownloadComplete() {
                                val dbHandler = DatabaseHandler(context)
                                dbHandler.updateStatus(1, idList[adapterPosition].fid)
                               // itemView.bPause.text = context.getString(R.string.complete)
                                itemView.bCancel.visibility = View.GONE
                                itemView.bComplete.visibility = View.VISIBLE
                                itemView.bComplete.setOnClickListener {
                                    playVideo()
                                }
                                Toast.makeText(
                                    context,
                                    idList[adapterPosition].fileName + " Completed",
                                    Toast.LENGTH_SHORT
                                ).show()
                                Log.d(TAG, idList[adapterPosition].fileName)
                            }

                            override fun onError(error: com.downloader.Error?) {
                                Log.d(TAG, error!!.serverErrorMessage + "")
                            }

                        })
                    idList[adapterPosition].fid = downloadId.toString()
                    DatabaseHandler(context).addFile(
                        downloadId.toString(),
                        idList[adapterPosition].fileName,
                        "1",
                        idList[adapterPosition].url
                    )
                }
                else if (idList[adapterPosition].status == "5" || idList[adapterPosition].status == "4")
                {
                    itemView.bComplete.visibility = View.GONE
                    itemView.bCancel.text ="Retry"
                    itemView.bCancel.visibility = View.VISIBLE
                    idList[adapterPosition].status = "4"
                }
            }
            else
            {
                itemView.tvFileName.text = idList[adapterPosition].fileName
                itemView.bCancel.visibility = View.GONE
                itemView.bComplete.visibility = View.VISIBLE
                itemView.bComplete.setOnClickListener {
                    playVideo()
                }
            }
        }
        private fun resume(url: String, name: String, fid: String)
        {
            val downloadId = PRDownloader.download(url,dir.absolutePath,name).build()
                .setOnStartOrResumeListener { }
                .setOnPauseListener { }
                .setOnCancelListener { Log.d(TAG, "Cancelled") }
                .setOnProgressListener {
                    itemView.tvStatus.text = Utils.getProgressDisplayLine(it.currentBytes,it.totalBytes)
                    Log.d(TAG, it.currentBytes.toString())
                }
                .start(object : OnDownloadListener {

                    override fun onDownloadComplete() {
                        val dbHandler = DatabaseHandler(context)
                        dbHandler.updateStatus(1, fid)
                        itemView.bCancel.visibility = View.GONE
                        itemView.bComplete.visibility = View.VISIBLE
                        itemView.bComplete.setOnClickListener {
                            playVideo()
                        }
                        Toast.makeText(context,idList[adapterPosition].fileName+" Completed",Toast.LENGTH_SHORT).show()
                        Log.d(TAG,name)
                    }

                    override fun onError(error: com.downloader.Error?) {
                        Log.d(TAG, error!!.serverErrorMessage + "")
                    }

                })
        }

        private fun playVideo() {
            //  val uri = ("file:"+list[adapterPosition].getvPath()).toUri()
            val uri =  Uri.parse(idList[adapterPosition].fileName)
            val file = File(dir.absolutePath+"/"+idList[adapterPosition].fileName)
            val apkUri = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".fileprovider", file)
            val arr = idList[adapterPosition].url.split(".")
            val myMime = MimeTypeMap.getSingleton()
            val mimeType = myMime.getMimeTypeFromExtension(arr[arr.size - 1])
            /*  val view = Intent(Intent.ACTION_VIEW).apply {
                  setDataAndType(uri, "video/mp4")
                  addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
              }*/
            Log.d("Video", "$apkUri $mimeType")
            context.startActivity(play(apkUri,mimeType))
        }

        private fun play(uri: Uri?, mimeType: String?): Intent? {

            val view = Intent(Intent.ACTION_VIEW).apply {
                setDataAndType( uri ,mimeType)
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }
            return Intent.createChooser(view, "Open with")


        }
    }

}