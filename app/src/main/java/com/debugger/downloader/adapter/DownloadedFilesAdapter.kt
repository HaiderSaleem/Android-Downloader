package com.debugger.downloader.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.MimeTypeMap
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.content.FileProvider
import androidx.recyclerview.widget.RecyclerView
import com.debugger.downloader.BuildConfig
import com.debugger.downloader.R
import com.debugger.downloader.R.mipmap.ic_fav
import com.debugger.downloader.R.mipmap.ic_no_fav
import com.debugger.downloader.database.DatabaseHandler
import com.debugger.downloader.model.DownloadedFilesParam
import java.io.File


class DownloadedFilesAdapter(val context: Context, val list:ArrayList<DownloadedFilesParam>,val check:Boolean): RecyclerView.Adapter<DownloadedFilesAdapter.ViewHolder>()
    {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_media_gallery,parent,false)

            return ViewHolder(view)
        }

        override fun getItemCount(): Int {
            return list.size
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bindItem()
        }

        inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView), View.OnClickListener {
            override fun onClick(p0: View?) {
                when(p0!!.id)
                {

                    R.id.tvFileName -> playVideo()

                }
            }

            private fun markFavourite() {
                val dbHandles = DatabaseHandler(context)
                dbHandles.addFavourite(list[adapterPosition])
                ibFavourite.background = context.getDrawable(ic_fav)
            }
            private fun removeFavourite() {
                val dbHandles = DatabaseHandler(context)
                dbHandles.removeFavourite(list[adapterPosition].name)
                ibFavourite.background = context.getDrawable(ic_no_fav)
            }


            private fun playVideo() {
                //  val uri = ("file:"+list[adapterPosition].getvPath()).toUri()
                val uri =  Uri.parse(list[adapterPosition].path)
                val file = File(list[adapterPosition].path)
                val apkUri = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".fileprovider", file)
                val arr = list[adapterPosition].path.split(".")
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


            private val tvFileName: TextView = itemView.findViewById(R.id.tvFileName)
            private val ibFavourite: ImageButton = itemView.findViewById(R.id.ibFavourite)

            fun bindItem()
            {
                if(!check)
                    ibFavourite.visibility = View.GONE

                tvFileName.text = list[adapterPosition].name
                val dbHandles = DatabaseHandler(context)
                if(dbHandles.getFavouriteStatus(list[adapterPosition].name)) {
                    ibFavourite.background = context.getDrawable(ic_fav)
                    ibFavourite.setOnClickListener{
                        removeFavourite()
                    }
                }
                else
                    ibFavourite.setOnClickListener{
                        markFavourite()
                    }
                tvFileName.setOnClickListener(this)

            }


        }


    }