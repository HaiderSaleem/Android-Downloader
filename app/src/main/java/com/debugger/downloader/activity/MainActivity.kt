package com.debugger.downloader.activity

import android.Manifest.permission
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.debugger.downloader.R
import com.debugger.downloader.fragment.DownloadedFragment
import com.debugger.downloader.fragment.DownloadingFragment
import com.debugger.downloader.fragment.MyFavouriteFragment
import com.downloader.PRDownloader
import com.downloader.PRDownloaderConfig
import com.google.android.material.navigation.NavigationView


class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        PRDownloader.initialize(applicationContext)

        checkPerm()
        setupPRDownloader()


      /*  val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        val drawer: DrawerLayout = findViewById(R.id.drawer_layout)*/
        val navigationView: NavigationView = findViewById(R.id.nav_view)
     /*   val toggle = ActionBarDrawerToggle(
            this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer.addDrawerListener(toggle)
        toggle.syncState()*/
        navigationView.setNavigationItemSelectedListener(this)

        navigationView.setCheckedItem(R.id.nav_downloading)
        navigationView.menu.performIdentifierAction(R.id.nav_downloading, 0)

        //setViewPager()
    }

    private fun checkPerm() {

        if (checkSelfPermission(
                baseContext!!,
                permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Permission is not granted
            ActivityCompat.requestPermissions(
                this,
                arrayOf(permission.WRITE_EXTERNAL_STORAGE),
                1
            )
        }
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == 1 && grantResults.size == 1
            && grantResults[0] == PackageManager.PERMISSION_GRANTED
        ) {

            Toast.makeText(baseContext," Permission granted", Toast.LENGTH_SHORT).show()

        } else if (requestCode == 1) {

            Toast.makeText(baseContext," Permission not granted", Toast.LENGTH_SHORT).show()

        }
    }

    private fun setupPRDownloader() {
        // Enabling database for resume support even after the application is killed:
        // Enabling database for resume support even after the application is killed:
        // Setting timeout globally for the download network requests:
        // Setting timeout globally for the download network requests:

        val config = PRDownloaderConfig.newBuilder()
            .setDatabaseEnabled(true)
            .setReadTimeout(30000)
            .setConnectTimeout(30000)
            .build()
        PRDownloader.initialize(applicationContext, config)



    }

    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {

        when(menuItem.itemId)
        {
            R.id.nav_downloading -> switchSelectedMenuFragment(DownloadingFragment.newInstance())

            R.id.nav_downloaded -> switchSelectedMenuFragment(DownloadedFragment.newInstance())

            R.id.nav_my_favourite -> switchSelectedMenuFragment(MyFavouriteFragment.newInstance())
        }
        /*val drawer: DrawerLayout = findViewById(R.id.drawer_layout)
        drawer.closeDrawer(GravityCompat.START)*/
        return true
    }

    /* private fun setViewPager() {

         val fragmentAdapter = MainViewPagerAdapter( applicationContext, supportFragmentManager)
         vpMain.adapter = fragmentAdapter
         tlHead.setupWithViewPager(vpMain)
     }*/

}
