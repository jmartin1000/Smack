package com.example.smack.Controller

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.os.Bundle
import android.support.v4.content.LocalBroadcastManager
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.example.smack.R
import com.example.smack.Services.AuthService
import com.example.smack.Services.UserDataService
import com.example.smack.Utilities.BROADCAST_USER_DATA_CHANGE
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.nav_header_main.*
import kotlinx.android.synthetic.main.nav_header_main.view.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        LocalBroadcastManager.getInstance(this).registerReceiver(
            userDataChangeReceiver,
            IntentFilter(BROADCAST_USER_DATA_CHANGE)
        )
    }

    private val userDataChangeReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            //setContentView(R.layout.nav_header_main)

            if (AuthService.isLoggedIn) {
                nav_drawer_header_include.userNameNavHeader.text = UserDataService.name
                nav_drawer_header_include.userEmailNavHeader.text = UserDataService.email
                val resourceId = resources.getIdentifier(
                    UserDataService.avatarName, "drawable",
                    packageName
                )
                nav_drawer_header_include.userImageNavHeader.setImageResource(resourceId)
                nav_drawer_header_include.userImageNavHeader.setBackgroundColor(UserDataService.returnAvatarColor(UserDataService.avatarColor))
                nav_drawer_header_include.loginButtonNavHeader.text = "Logout"
            }
        }
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    fun loginButtonNavClicked(view: View) {

        if (AuthService.isLoggedIn) {

            UserDataService.logout()
            nav_drawer_header_include.userNameNavHeader.text = ""
            nav_drawer_header_include.userEmailNavHeader.text = ""
            nav_drawer_header_include.userImageNavHeader.setImageResource(R.drawable.profiledefault)
            nav_drawer_header_include.userImageNavHeader.setBackgroundColor(Color.TRANSPARENT)
            nav_drawer_header_include.loginButtonNavHeader.text = "Login"

        } else {
            val loginIntent = Intent(this, LoginActivity::class.java)
            startActivity(loginIntent)
        }

    }

    fun addChannelClicked(view: View) {

    }

    fun sendMessageButtonClicked(view: View) {

    }
}
